package site.connectdots.connectdotsprj.chat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.connectdots.connectdotsprj.chat.dto.request.LivechatCreateRequestDTO;
import site.connectdots.connectdotsprj.chat.dto.response.LivechatCreateResponseDTO;
import site.connectdots.connectdotsprj.chat.dto.response.LivechatListAndHashtagListResponseDTO;
import site.connectdots.connectdotsprj.chat.entity.Livechat;
import site.connectdots.connectdotsprj.chat.dto.response.LivechatListResponseDTO;
import site.connectdots.connectdotsprj.chat.repository.LivechatRepository;

import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Boolean.*;

@Service
@Transactional
@RequiredArgsConstructor
public class LivechatService {

    private final LivechatRepository livechatRepository;

    // 전체 조회
    @Transactional(readOnly = true)
    public LivechatListAndHashtagListResponseDTO findAllLivechat() {
        List<String> hashtagList = findAllHashtag();
        List<Livechat> livechatList = livechatRepository.findAllByOrderByLivechatIdxDesc();

        return createLivechatListAndHashtagListResponseDTO(hashtagList, livechatList);
    }

    // 해시 태그로 게시글 조회하기
    public LivechatListAndHashtagListResponseDTO findAllLivechatByHashtag(String hashtag) {
        List<String> hashtagList = findAllHashtag();
        List<Livechat> livechatList = livechatRepository.findAllByLivechatHashtagOrderByLivechatIdxDesc(hashtag);

        return createLivechatListAndHashtagListResponseDTO(hashtagList, livechatList);
    }

    // LivechatListAndHashtagListResponseDTO 생성하는 메소드
    private LivechatListAndHashtagListResponseDTO createLivechatListAndHashtagListResponseDTO(List<String> hashtagList, List<Livechat> livechatList) {
        List<LivechatListResponseDTO> responseDTOList = livechatList.stream()
                .map(e -> {
                    return LivechatListResponseDTO.builder()
                            .content(e.getLivechatContent())
                            .hashtag(e.getLivechatHashtag())
                            .memberNickname(e.getMemberNickname())
                            .build();
                })
                .collect(Collectors.toList());

        return LivechatListAndHashtagListResponseDTO.builder()
                .hashtagList(hashtagList)
                .livechatList(responseDTOList)
                .build();
    }


    // 해시 태그 전체 조회 (인기순 정렬)
    private List<String> findAllHashtag() {
        return livechatRepository.findHashtagsOrderByCount();
    }


    // 글 작성
    public LivechatCreateResponseDTO createLivechat(LivechatCreateRequestDTO dto) {
        String nickname = dto.getNickname();
        Long count = livechatRepository.countByMemberNickname(nickname);

        Boolean isCreate = FALSE;

        if (count == 0) {
            Livechat saved = livechatRepository.save(dto.toEntity());
            isCreate = TRUE;
        }

        return new LivechatCreateResponseDTO(isCreate);
    }

    // 글 삭제
    public void deleteLivechat() {

    }

}
