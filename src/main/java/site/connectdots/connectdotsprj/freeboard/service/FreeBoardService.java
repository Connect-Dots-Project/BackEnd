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
import site.connectdots.connectdotsprj.freeboard.exception.custom.NotFoundMemberException;
import site.connectdots.connectdotsprj.freeboard.repository.FreeBoardReplyRepository;
import site.connectdots.connectdotsprj.freeboard.repository.FreeBoardRepository;
import site.connectdots.connectdotsprj.freeboard.exception.custom.FreeBoardErrorCode;
import site.connectdots.connectdotsprj.freeboard.exception.custom.NotFoundFreeBoardException;
import site.connectdots.connectdotsprj.member.entity.Member;
import site.connectdots.connectdotsprj.member.repository.MemberRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class FreeBoardService {
    private final FreeBoardRepository freeBoardRepository;
    private final FreeBoardReplyRepository freeBoardReplyRepository;
    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public List<FreeBoardResponseDTO> findAll() {
        return freeBoardRepository.findAll()
                .stream()
                .map(FreeBoardResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public FreeBoardDetailResponseDTO findById(Long freeBoardIdx) {
        FreeBoard freeBoard = getFreeBoard(freeBoardIdx);
        updateViewCount(freeBoard);

        return getFreeBoardDetailResponseDTO(freeBoardIdx, freeBoard);
    }

    public List<FreeBoardResponseDTO> writeFreeBoard(FreeBoardWriteRequestDTO dto) {
        freeBoardRepository.save(
                FreeBoard.builder()
                        .freeBoardTitle(dto.getFreeBoardTitle())
                        .freeBoardContent(dto.getFreeBoardContent())
                        .freeBoardImg(dto.getFreeBoardImg())
                        .freeBoardLocation(dto.getFreeBoardLocation())
                        .freeBoardCategory(dto.getFreeBoardCategory())
                        .member(memberRepository.findById(dto.getMemberIdx()).orElseThrow(
                                () -> new NotFoundMemberException(FreeBoardErrorCode.MEMBER_NOT_FOUND, dto.getMemberIdx())
                        ))
                        .build()
        );

        return findAll();
    }

    public List<FreeBoardDetailReplyDTO> writeReplyByFreeBoard(FreeBoardReplyWriteRequestDTO dto) {

        freeBoardReplyRepository.save(
                dto.toEntity(
                        getFreeBoard(dto.getFreeBoardIdx()),
                        getMember(dto.getMemberIdx()))
        );

        return findAllByFreeBoardIdx(dto.getFreeBoardIdx());
    }

    public FreeBoardDetailResponseDTO updateLikeCount(Long freeBoardIdx, int likeCountDelta) {
        FreeBoard freeBoard = getFreeBoard(freeBoardIdx);
        freeBoard.setFreeBoardLikeCount(freeBoard.getFreeBoardLikeCount() + likeCountDelta);

        return getFreeBoardDetailResponseDTO(freeBoardIdx, freeBoard);
    }

    private FreeBoardDetailResponseDTO getFreeBoardDetailResponseDTO(Long freeBoardIdx, FreeBoard freeBoard) {
        List<FreeBoardDetailReplyDTO> replyList = findAllByFreeBoardIdx(freeBoardIdx);
        return new FreeBoardDetailResponseDTO(freeBoard, replyList);
    }

    private Member getMember(Long memberIdx) {
        return memberRepository.findById(memberIdx).orElseThrow(() -> {
            throw new NotFoundMemberException(FreeBoardErrorCode.MEMBER_NOT_FOUND, memberIdx);
        });
    }

    private FreeBoard getFreeBoard(Long freeBoardIdx) {
        return freeBoardRepository.findById(freeBoardIdx).orElseThrow(() -> {
            throw new NotFoundFreeBoardException(FreeBoardErrorCode.FREE_BOARD_NOT_FOUND, freeBoardIdx);
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
