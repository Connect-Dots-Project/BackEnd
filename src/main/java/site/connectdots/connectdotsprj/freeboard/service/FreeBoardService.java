package site.connectdots.connectdotsprj.freeboard.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.connectdots.connectdotsprj.freeboard.dto.request.FreeBoardReplyWriteRequestDTO;
import site.connectdots.connectdotsprj.freeboard.dto.response.FreeBoardDetailReplyDTO;
import site.connectdots.connectdotsprj.freeboard.dto.response.FreeBoardDetailResponseDTO;
import site.connectdots.connectdotsprj.freeboard.dto.response.FreeBoardResponseDTO;
import site.connectdots.connectdotsprj.freeboard.entity.FreeBoard;
import site.connectdots.connectdotsprj.freeboard.entity.FreeBoardReply;
import site.connectdots.connectdotsprj.freeboard.repository.FreeBoardReplyRepository;
import site.connectdots.connectdotsprj.freeboard.repository.FreeBoardRepository;
import site.connectdots.connectdotsprj.freeboard.exception.custom.FreeBoardErrorCode;
import site.connectdots.connectdotsprj.freeboard.exception.custom.NotFoundFreeBoardException;
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
        List<FreeBoard> freeBoardList = freeBoardRepository.findAll();

        return freeBoardList.stream()
                .map(FreeBoardResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public FreeBoardDetailResponseDTO findById(Long freeBoardIdx) {
        FreeBoard freeBoard = freeBoardRepository.findById(freeBoardIdx).orElseThrow(() -> {
            throw new NotFoundFreeBoardException(FreeBoardErrorCode.FREE_BOARD_NOT_FOUND, freeBoardIdx);
        });

        List<FreeBoardDetailReplyDTO> replyList = findAllByFreeBoardIdx(freeBoardIdx);

        return new FreeBoardDetailResponseDTO(freeBoard, replyList);
    }

    public List<FreeBoardDetailReplyDTO> writeReplyByFreeBoard(FreeBoardReplyWriteRequestDTO dto) {
        freeBoardReplyRepository.save(
                dto.toEntity(
                        freeBoardRepository.findById(dto.getFreeBoardIdx()).orElseThrow(),
                        memberRepository.findById(dto.getMemberIdx()).orElseThrow()
                )
        );

        return findAllByFreeBoardIdx(dto.getFreeBoardIdx());
    }


    private List<FreeBoardDetailReplyDTO> findAllByFreeBoardIdx(Long freeBoardIdx) {
        List<FreeBoardReply> freeBoardReplyList = freeBoardReplyRepository.findAllByFreeBoardFreeBoardIdx(freeBoardIdx);

        return freeBoardReplyList.stream()
                .map(FreeBoardDetailReplyDTO::new)
                .collect(Collectors.toList());
    }
}
