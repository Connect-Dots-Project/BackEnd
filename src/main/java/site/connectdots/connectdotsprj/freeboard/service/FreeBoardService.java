package site.connectdots.connectdotsprj.freeboard.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.connectdots.connectdotsprj.freeboard.dto.request.FreeBoardReplyWriteRequestDTO;
import site.connectdots.connectdotsprj.freeboard.dto.request.FreeBoardWriteRequestDTO;
import site.connectdots.connectdotsprj.freeboard.dto.response.FreeBoardDetailReplyDTO;
import site.connectdots.connectdotsprj.freeboard.dto.response.FreeBoardDetailResponseDTO;
import site.connectdots.connectdotsprj.freeboard.dto.response.FreeBoardResponseDTO;
import site.connectdots.connectdotsprj.freeboard.entity.FreeBoard;
import site.connectdots.connectdotsprj.freeboard.entity.FreeBoardReply;
import site.connectdots.connectdotsprj.freeboard.exception.custom.LikeAndHateException;
import site.connectdots.connectdotsprj.freeboard.exception.custom.NotFoundMemberException;
import site.connectdots.connectdotsprj.freeboard.repository.FreeBoardReplyRepository;
import site.connectdots.connectdotsprj.freeboard.repository.FreeBoardRepository;
import site.connectdots.connectdotsprj.freeboard.exception.custom.NotFoundFreeBoardException;
import site.connectdots.connectdotsprj.member.entity.Member;
import site.connectdots.connectdotsprj.member.repository.MemberRepository;

import java.util.List;
import java.util.stream.Collectors;

import static site.connectdots.connectdotsprj.freeboard.exception.custom.FreeBoardErrorCode.*;

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
                                () -> new NotFoundMemberException(MEMBER_NOT_FOUND, dto.getMemberIdx())
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
            throw new LikeAndHateException(LIKE_AND_HATE_EXCEPTION);
        }
        freeBoard.setFreeBoardLikeCount(freeBoard.getFreeBoardLikeCount() + likeCountDelta);

        return getFreeBoardDetailResponseDTO(freeBoardIdx, freeBoard);
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
            throw new NotFoundMemberException(MEMBER_NOT_FOUND, memberIdx);
        });
    }

    private FreeBoard getFreeBoard(Long freeBoardIdx) {
        return freeBoardRepository.findById(freeBoardIdx).orElseThrow(() -> {
            throw new NotFoundFreeBoardException(FREE_BOARD_NOT_FOUND, freeBoardIdx);
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

    public List<FreeBoardResponseDTO> myPageFindAll(String account) {

        return freeBoardRepository.findAllByMemberMemberAccount(account)
                .stream().map(FreeBoardResponseDTO::new)
                .collect(Collectors.toList());
    }

}
