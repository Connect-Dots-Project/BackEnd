package site.connectdots.connectdotsprj.chat.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import site.connectdots.connectdotsprj.chat.entity.Livechat;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class LivechatRepositoryTest {
    @Autowired
    LivechatRepository livechatRepository;

//    @Test
//    @DisplayName("insert bulk")
//    void insertBulk() {
//        //given
//        String[] hash = {"강남역", "맛집", "별빛청하", "나무"};
//
//        for (int i = 2; i <= 20; i++) {
//            livechatRepository.save(Livechat.builder()
//                    .livechat_content("안뇽" + i)
//                    .livechatHashtag(hash[(int) (Math.random()*3)])
//                    .memberIdx((long) i)
//                    .build());
//        }
//
//        //when
//
//        //then
//    }


    @Test
    @DisplayName("글 작성에 성공할 것이다.")
    @Rollback(value = true)
    void createTest() {
        //given
        Long memberIdx = 5L;
        Livechat insertData = Livechat.builder()
                .livechat_content("안뇽")
                .livechatHashtag("해시태그")
                .memberIdx(memberIdx)
                .build();

        //when
        Livechat saved = livechatRepository.save(insertData);

        //then
        assertEquals("안뇽", saved.getLivechat_content());
        assertEquals("해시태그", saved.getLivechatHashtag());
        assertEquals(5, saved.getMemberIdx());

    }

    @Test
    @DisplayName("작성자가 중복될 경우 글 작성에 실패한다.")
    @Rollback(value = true)
    void duplicateMemberTest() {
        //given
        Long memberIdx = 1L;

        //when
        //then
        assertThrows(RuntimeException.class, () -> {
            livechatRepository.save(Livechat.builder()
                    .livechat_content("안뇽하하하하")
                    .livechatHashtag("해시태그하하하")
                    .memberIdx(memberIdx)
                    .build());
        });
    }

    @Test
    @DisplayName("해시태그 인기순 조회에 성공할 것이다.")
    void hashtagTest() {
        //given

        //when
        List<String> hashtagsOrderByCount = livechatRepository.findHashtagsOrderByCount();
        hashtagsOrderByCount.forEach(System.out::println);

        //then
    }

    @Test
    @DisplayName("해시태그로 게시글 조회에 성공할 것이다.")
    void findByHashTagTest() {
        //given
        String hashtag = "강남역";


        //when
        List<Livechat> byLivechatHashtag = livechatRepository.findAllByLivechatHashtag(hashtag);
        byLivechatHashtag.forEach(System.out::println);


        //then
    }


}