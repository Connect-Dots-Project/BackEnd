package site.connectdots.connectdotsprj.chat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.connectdots.connectdotsprj.chat.entity.Livechat;
import site.connectdots.connectdotsprj.chat.repository.LivechatRepository;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class LivechatService {

    private final LivechatRepository livechatRepository;


    // 글 작성
    public Livechat createLivechat() {
        return livechatRepository.save(Livechat.builder().build());
    }

    // 전체 조회
    @Transactional(readOnly = true)
    public List<Livechat> findAllLivechat() {
        return livechatRepository.findAll();
    }

    // 해시 태그 전체 조회 (인기순 정렬)
    public List<String> findAllHashtag() {
        return livechatRepository.findHashtagsOrderByCount();
    }

    // 해시 태그로 게시글 조회하기
    public List<Livechat> findAllLivechatByHashtag(String hashtag) {
        return livechatRepository.findAllByLivechatHashtag(hashtag);
    }

    // 글 삭제
    public void deleteLivechat() {
    }

    // 글 수정
    public void modifyLivechat() {
    }

}
