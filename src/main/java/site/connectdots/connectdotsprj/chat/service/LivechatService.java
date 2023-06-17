package site.connectdots.connectdotsprj.chat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.connectdots.connectdotsprj.chat.repository.LivechatRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class LivechatService {

    private final LivechatRepository livechatRepository;

    // 글 작성
    public void createLivechat() {

    }

    // 전체 조회
    public void findAllLivechat() {

    }

    // 해시 태그 전체 조회
    public void findAllHashtag() {

    }

    // 해시 태그로 게시글 조회하기
    public void findAllLivechatByHashtag() {

    }

    // 글 삭제
    public void deleteLivechat() {

    }

    // 글 수정
    public void modifyLivechat() {

    }

}
