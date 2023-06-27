package site.connectdots.connectdotsprj.freeboard.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import site.connectdots.connectdotsprj.aws.service.S3Service;
import site.connectdots.connectdotsprj.freeboard.dto.request.FreeBoardModifyRequestDTO;
import site.connectdots.connectdotsprj.freeboard.dto.request.FreeBoardReplyWriteRequestDTO;
import site.connectdots.connectdotsprj.freeboard.dto.request.FreeBoardWriteRequestDTO;
import site.connectdots.connectdotsprj.freeboard.dto.response.*;
import site.connectdots.connectdotsprj.freeboard.entity.FreeBoard;
import site.connectdots.connectdotsprj.freeboard.entity.FreeBoardLike;
import site.connectdots.connectdotsprj.freeboard.entity.FreeBoardReply;
import site.connectdots.connectdotsprj.freeboard.exception.custom.UnauthorizedModificationException;
import site.connectdots.connectdotsprj.freeboard.repository.FreeBoardLikeRepository;
import site.connectdots.connectdotsprj.jwt.config.JwtUserInfo;
import site.connectdots.connectdotsprj.member.exception.custom.NotFoundMemberByAccountException;
import site.connectdots.connectdotsprj.member.exception.custom.NotFoundMemberByIdxException;
import site.connectdots.connectdotsprj.freeboard.repository.FreeBoardReplyRepository;
import site.connectdots.connectdotsprj.freeboard.repository.FreeBoardRepository;
import site.connectdots.connectdotsprj.freeboard.exception.custom.NotFoundFreeBoardException;
import site.connectdots.connectdotsprj.member.entity.Member;
import site.connectdots.connectdotsprj.member.repository.MemberRepository;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static site.connectdots.connectdotsprj.freeboard.exception.custom.FreeBoardErrorCode.*;
import static site.connectdots.connectdotsprj.member.exception.custom.enums.MemberErrorCode.NOT_FOUND_MEMBER;

@Service
@Transactional
@RequiredArgsConstructor
public class FreeBoardService {
    private final FreeBoardRepository freeBoardRepository;
    private final FreeBoardReplyRepository freeBoardReplyRepository;
    private final FreeBoardLikeRepository freeBoardLikeRepository;
    private final MemberRepository memberRepository;
    private final S3Service s3Service;
    private final Integer START_PAGE = 0;
    private final Integer SIZE = 10;
    private final String KEY = "freeBoardIdx";

    /**
     * 페이징 처리하여 게시판의 글을 가져온다
     *
     * @return : 1 페이지당 10개의 글
     */
    @Transactional(readOnly = true) // 읽기 전용으로 가져온다면 조회할 때 성능이 좋다.
    public List<FreeBoardResponseDTO> findAll(Integer page) {
        Sort freeBoardIdx = Sort.by(KEY);
        PageRequest pageRequest = PageRequest.of(page, SIZE, freeBoardIdx.descending());

        return freeBoardRepository.findAll(pageRequest)
                .stream().map(FreeBoardResponseDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * 자유게시판의 인덱스로 찾는 메서드
     *
     * @param freeBoardIdx : 매개변수로 받은 인덱스
     * @return : 해당 글의 리플을 포함시켜 리턴
     */
    public FreeBoardDetailResponseDTO detailView(Long freeBoardIdx, JwtUserInfo jwtUserInfo) {
        Member loginMember = memberRepository.findByMemberAccount(jwtUserInfo.getAccount());

        Long likeCount = freeBoardLikeRepository.countByFreeboardIdx(freeBoardIdx);
        // TODO : likeCount 수정해야함

        FreeBoard freeBoard = getFreeBoard(freeBoardIdx);
        updateViewCount(freeBoard); // TODO : 계정당 시간을 부여해서 새로고침 조회수를 막아야 함.

        return getFreeBoardDetailResponseDTO(freeBoardIdx, freeBoard, loginMember);
    }

    /**
     * 자유게시판 작성 메서드
     *
     * @param dto
     * @param uploadFilePath
     * @return
     */
    public List<FreeBoardResponseDTO> writeFreeBoard(FreeBoardWriteRequestDTO dto, JwtUserInfo jwtUserInfo, String uploadFilePath) {

        freeBoardRepository.save(
                FreeBoard.builder()
                        .freeBoardTitle(dto.getFreeBoardTitle())
                        .freeBoardContent(dto.getFreeBoardContent())
                        .freeBoardLocation(dto.getFreeBoardLocation())
                        .freeBoardCategory(dto.getFreeBoardCategory())
                        .freeBoardImg(uploadFilePath)
                        .member(memberRepository.findByMemberAccount(jwtUserInfo.getAccount()))
                        .build()
        );

        return findAll(START_PAGE);
    }

    /**
     * 자유게시판에 리플을 다는 메서드
     *
     * @param dto
     * @return
     */
    public List<FreeBoardDetailReplyDTO> writeReplyByFreeBoard(FreeBoardReplyWriteRequestDTO dto, JwtUserInfo jwtUserInfo) {
        Member foundMember = memberRepository.findByMemberAccount(jwtUserInfo.getAccount());
        FreeBoard foundFreeBoard = freeBoardRepository.findById(dto.getFreeBoardIdx()).orElseThrow();

        FreeBoardReply saved = FreeBoardReply.builder()
                .freeBoardReplyContent(dto.getFreeBoardReplyContent())
                .freeBoard(foundFreeBoard)
                .member(foundMember)
                .build();

        freeBoardReplyRepository.save(saved);

        return findAllByFreeBoardIdx(dto.getFreeBoardIdx());
    }

    /**
     * 자유게시판의 좋아요 싫어요 기능
     * 본인 글은 익셉션 발생
     *
     * @param freeBoardIdx
     * @param memberAccount
     * @return
     */
    public FreeBoardLikeResultResponseDTO updateLikeCount(Long freeBoardIdx, String memberAccount) {
        FreeBoard foundFreeBoard = freeBoardRepository.findById(freeBoardIdx).orElseThrow();

        if (foundFreeBoard.getMember().getMemberAccount().equals(memberAccount)) {
            return FreeBoardLikeResultResponseDTO.builder()
                    .message("본인 글은 추천할 수 없습니다.")
                    .count(freeBoardLikeRepository.countByFreeboardIdx(freeBoardIdx))
                    .build();
        }

        FreeBoardLike foundFreeBoardLike = freeBoardLikeRepository.findByMemberAccountAndFreeboardIdx(memberAccount, freeBoardIdx);
        String message = "";

        if (foundFreeBoardLike == null) {
            // 좋아요
            freeBoardLikeRepository.save(FreeBoardLike.builder()
                    .freeboardIdx(freeBoardIdx)
                    .memberAccount(memberAccount)
                    .build());
            message = "좋아요를 눌렀습니다.";

            foundFreeBoard.setFreeBoardLikeCount(foundFreeBoard.getFreeBoardLikeCount() + 1);
        } else {
            // 싫어요
            freeBoardLikeRepository.deleteByMemberAccountAndFreeboardIdx(memberAccount, freeBoardIdx);
            message = "좋아요를 취소했습니다.";

            foundFreeBoard.setFreeBoardLikeCount(foundFreeBoard.getFreeBoardLikeCount() - 1);
        }

        freeBoardRepository.save(foundFreeBoard);

        return FreeBoardLikeResultResponseDTO.builder()
                .message(message)
                .count(freeBoardLikeRepository.countByFreeboardIdx(freeBoardIdx))
                .build();
    }


    public List<FreeBoardResponseDTO> myPageFindAll(String account) {

        return freeBoardRepository.findAllByMemberMemberAccount(account)
                .stream().map(FreeBoardResponseDTO::new)
                .collect(Collectors.toList());
    }

    public FreeBoardDetailResponseDTO modifyFreeBoard(FreeBoardModifyRequestDTO dto, JwtUserInfo tokenUserInfo) {
        validateDTO(dto, tokenUserInfo);
        Member byId = memberRepository.findById(dto.getMemberIdx()).orElseThrow();

        FreeBoard savedFreeBoard = freeBoardRepository.save(
                FreeBoard.builder()
                        .freeBoardIdx(dto.getFreeBoardIdx())
                        .freeBoardImg(dto.getFreeBoardImg())
                        .freeBoardTitle(dto.getFreeBoardTitle())
                        .freeBoardContent(dto.getFreeBoardContent())
                        .freeBoardLocation(dto.getFreeBoardLocation())
                        .freeBoardCategory(dto.getFreeBoardCategory())
                        .member(byId)
                        .build()
        );

        return getFreeBoardDetailResponseDTO(savedFreeBoard.getFreeBoardIdx(), savedFreeBoard, byId);
    }


    public FreeBoardDeleteResponseDTO delete(JwtUserInfo userInfo, Long boardIdx) {
        Boolean isDelete = FALSE;
        Member foundMember = memberRepository.findByMemberAccount(userInfo.getAccount());
        FreeBoard foundFreeBoard = freeBoardRepository.findById(boardIdx).orElseThrow();

        System.out.println("\n\n\n\n\n\n\n");
        System.out.println(foundFreeBoard);
        System.out.println("---------------------------------");
        System.out.println(foundFreeBoard.getMember());
        System.out.println(foundMember);

        if (foundMember == null || foundFreeBoard == null) {
            return FreeBoardDeleteResponseDTO.builder()
                    .isDelete(isDelete)
                    .build();
        }

        if (foundMember.getMemberIdx() == foundFreeBoard.getMember().getMemberIdx()) {
            System.out.println("delete!!!!!!!!!!!!!!!!");
            freeBoardRepository.deleteById(boardIdx);
            freeBoardRepository.flush();
            isDelete = TRUE;
        }

        return FreeBoardDeleteResponseDTO.builder().isDelete(isDelete).build();
    }

    private void validateDTO(FreeBoardModifyRequestDTO dto, JwtUserInfo tokenUserInfo) {
        if (tokenUserInfo == null) {
            throw new NotFoundMemberByAccountException(NOT_FOUND_MEMBER, "token이 없습니다.");
        }

        Member foundMember = memberRepository.findByMemberAccount(tokenUserInfo.getAccount());

        if (foundMember == null) {
            // 멤버 조회 실패
            throw new NotFoundMemberByAccountException(NOT_FOUND_MEMBER, tokenUserInfo.getAccount());
        }

        if (foundMember.getMemberIdx() != dto.getMemberIdx()) {
            // 작성자가 아님.
            throw new UnauthorizedModificationException(UNAUTHORIZED_MODIFICATION_EXCEPTION, tokenUserInfo.getAccount());
        }
    }

    /**
     * 자유게시판 상세보기 메서드
     *
     * @param freeBoardIdx
     * @param freeBoard
     * @return
     */
    private FreeBoardDetailResponseDTO getFreeBoardDetailResponseDTO(Long freeBoardIdx, FreeBoard freeBoard, Member member) {
        List<FreeBoardDetailReplyDTO> replyList = findAllByFreeBoardIdx(freeBoardIdx);

        return new FreeBoardDetailResponseDTO(freeBoard, replyList, member);
    }

    private Member getMember(Long memberIdx) {
        return memberRepository.findById(memberIdx).orElseThrow(() -> {
            throw new NotFoundMemberByIdxException(NOT_FOUND_MEMBER, memberIdx);
        });
    }

    private FreeBoard getFreeBoard(Long freeBoardIdx) {
        return freeBoardRepository.findById(freeBoardIdx).orElseThrow(() -> {
            throw new NotFoundFreeBoardException(NOT_FOUND_FREE_BOARD, freeBoardIdx);
        });
    }

    private List<FreeBoardDetailReplyDTO> findAllByFreeBoardIdx(Long freeBoardIdx) {
        List<FreeBoardReply> freeBoardReplyList = freeBoardReplyRepository.findAllByFreeBoardFreeBoardIdx(freeBoardIdx);

        return freeBoardReplyList.stream()
                .map(FreeBoardDetailReplyDTO::new)
                .collect(Collectors.toList());
    }

    private void updateViewCount(FreeBoard freeBoard) {
        freeBoard.setFreeBoardViewCount(freeBoard.getFreeBoardViewCount() + 1);
        freeBoardRepository.save(freeBoard);
    }


    public String uploadFreeBoardImg(MultipartFile freeBoardImg) throws IOException {
        String uniqueFileName = UUID.randomUUID() + "_" + freeBoardImg.getOriginalFilename();

        return s3Service.uploadToS3Bucket(freeBoardImg.getBytes(), uniqueFileName);
    }
}
