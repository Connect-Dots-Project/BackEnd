package site.connectdots.connectdotsprj.freeboard.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.connectdots.connectdotsprj.freeboard.dto.request.FreeBoardModifyRequestDTO;
import site.connectdots.connectdotsprj.freeboard.dto.request.FreeBoardReplyWriteRequestDTO;
import site.connectdots.connectdotsprj.freeboard.dto.request.FreeBoardWriteRequestDTO;
import site.connectdots.connectdotsprj.freeboard.dto.response.FreeBoardDetailReplyDTO;
import site.connectdots.connectdotsprj.freeboard.dto.response.FreeBoardDetailResponseDTO;
import site.connectdots.connectdotsprj.freeboard.dto.response.FreeBoardResponseDTO;
import site.connectdots.connectdotsprj.freeboard.entity.FreeBoard;
import site.connectdots.connectdotsprj.freeboard.entity.FreeBoardReply;
import site.connectdots.connectdotsprj.freeboard.exception.custom.LikeAndHateException;
import site.connectdots.connectdotsprj.freeboard.exception.custom.UnauthorizedModificationException;
import site.connectdots.connectdotsprj.global.config.TokenUserInfo;
import site.connectdots.connectdotsprj.member.exception.custom.NotFoundMemberByAccountException;
import site.connectdots.connectdotsprj.member.exception.custom.NotFoundMemberByIdxException;
import site.connectdots.connectdotsprj.freeboard.repository.FreeBoardReplyRepository;
import site.connectdots.connectdotsprj.freeboard.repository.FreeBoardRepository;
import site.connectdots.connectdotsprj.freeboard.exception.custom.NotFoundFreeBoardException;
import site.connectdots.connectdotsprj.member.entity.Member;
import site.connectdots.connectdotsprj.member.repository.MemberRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static site.connectdots.connectdotsprj.freeboard.exception.custom.FreeBoardErrorCode.*;
import static site.connectdots.connectdotsprj.member.exception.custom.enums.MemberErrorCode.NOT_FOUND_MEMBER;

@Service
@Transactional
@RequiredArgsConstructor
public class FreeBoardService {
    private final FreeBoardRepository freeBoardRepository;
    private final FreeBoardReplyRepository freeBoardReplyRepository;
    private final MemberRepository memberRepository;

    /**
     * 자유게시판의 모든 목록을 리턴해주는 메서드
     *
     * @return
     */
    @Transactional(readOnly = true)
    public List<FreeBoardResponseDTO> findAll() {
        return freeBoardRepository.findAll()
                .stream()
                .map(FreeBoardResponseDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * 자유게시판의 인덱스로 찾는 메서드
     *
     * @param freeBoardIdx
     * @return
     */
    @Transactional(readOnly = true)
    public FreeBoardDetailResponseDTO findById(Long freeBoardIdx) {
        FreeBoard freeBoard = getFreeBoard(freeBoardIdx);
        updateViewCount(freeBoard);

        return getFreeBoardDetailResponseDTO(freeBoardIdx, freeBoard);
    }

    /**
     * 자유게시판 작성 메서드
     *
     * @param dto
     * @return
     */
    public List<FreeBoardResponseDTO> writeFreeBoard(FreeBoardWriteRequestDTO dto) {
        freeBoardRepository.save(
                FreeBoard.builder()
                        .freeBoardTitle(dto.getFreeBoardTitle())
                        .freeBoardContent(dto.getFreeBoardContent())
                        .freeBoardImg(dto.getFreeBoardImg())
                        .freeBoardLocation(dto.getFreeBoardLocation())
                        .freeBoardCategory(dto.getFreeBoardCategory())
                        .member(memberRepository.findById(dto.getMemberIdx()).orElseThrow(
                                () -> new NotFoundMemberByIdxException(NOT_FOUND_MEMBER, dto.getMemberIdx())
                        ))
                        .build()
        );

        return findAll();
    }

    /**
     * 자유게시판에 리플을 다는 메서드
     *
     * @param dto
     * @return
     */
    public List<FreeBoardDetailReplyDTO> writeReplyByFreeBoard(FreeBoardReplyWriteRequestDTO dto) {

        freeBoardReplyRepository.save(
                dto.toEntity(
                        getFreeBoard(dto.getFreeBoardIdx()),
                        getMember(dto.getMemberIdx()))
        );

        return findAllByFreeBoardIdx(dto.getFreeBoardIdx());
    }

    /**
     * 자유게시판의 좋아요 싫어요 기능
     * 본인 글은 익셉션 발생
     *
     * @param freeBoardIdx
     * @param likeCountDelta
     * @param userAccount
     * @return
     */
    public FreeBoardDetailResponseDTO updateLikeCount(Long freeBoardIdx, int likeCountDelta, String userAccount) {
        FreeBoard freeBoard = getFreeBoard(freeBoardIdx);
        if (freeBoard.getMember().getMemberAccount().equals(userAccount)) {
            throw new LikeAndHateException(UNAUTHORIZED_LIKE_AND_HATE_EXCEPTION);
        }
        freeBoard.setFreeBoardLikeCount(freeBoard.getFreeBoardLikeCount() + likeCountDelta);

        return getFreeBoardDetailResponseDTO(freeBoardIdx, freeBoard);
    }


    public List<FreeBoardResponseDTO> myPageFindAll(String account) {

        return freeBoardRepository.findAllByMemberMemberAccount(account)
                .stream().map(FreeBoardResponseDTO::new)
                .collect(Collectors.toList());
    }

    public FreeBoardDetailResponseDTO modifyFreeBoard(FreeBoardModifyRequestDTO dto, TokenUserInfo tokenUserInfo) {
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

        return getFreeBoardDetailResponseDTO(savedFreeBoard.getFreeBoardIdx(), savedFreeBoard);
    }

    private void validateDTO(FreeBoardModifyRequestDTO dto, TokenUserInfo tokenUserInfo) {
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
    private FreeBoardDetailResponseDTO getFreeBoardDetailResponseDTO(Long freeBoardIdx, FreeBoard freeBoard) {
        List<FreeBoardDetailReplyDTO> replyList = findAllByFreeBoardIdx(freeBoardIdx);
        return new FreeBoardDetailResponseDTO(freeBoard, replyList);
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


}
