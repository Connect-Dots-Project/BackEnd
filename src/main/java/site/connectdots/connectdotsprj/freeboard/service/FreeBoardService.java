package site.connectdots.connectdotsprj.freeboard.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.connectdots.connectdotsprj.freeboard.dto.response.FreeBoardDetailResponseDTO;
import site.connectdots.connectdotsprj.freeboard.dto.response.FreeBoardResponseDTO;
import site.connectdots.connectdotsprj.freeboard.entity.FreeBoard;
import site.connectdots.connectdotsprj.freeboard.repository.FreeBoardRepository;
import site.connectdots.connectdotsprj.freeboard.exception.custom.FreeBoardErrorCode;
import site.connectdots.connectdotsprj.freeboard.exception.custom.NotFoundFreeBoardException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class FreeBoardService {
    private final FreeBoardRepository freeBoardRepository;

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
        return new FreeBoardDetailResponseDTO(freeBoard);
    }
}
