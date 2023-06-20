package site.connectdots.connectdotsprj.chat.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import site.connectdots.connectdotsprj.chat.entity.Livechat;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class LivechatRepositoryTest {
    @Autowired
    LivechatRepository livechatRepository;

    @Test
    @DisplayName("글 작성에 성공할 것이다.")
    @Rollback(value = true)
    void createTest() {
        //given
        Long memberIdx = 5L;
        Livechat insertData = Livechat.builder()
                .livechatContent("안뇽")
                .livechatHashtag("해시태그")
                .memberIdx(memberIdx)
                .build();

        //when
        Livechat saved = livechatRepository.save(insertData);

        //then
        assertEquals("안뇽", saved.getLivechatContent());
        assertEquals("해시태그", saved.getLivechatHashtag());
        assertEquals(5, saved.getMemberIdx());

    }

    @Test
    @DisplayName("글 전체 조회에 성공할 것이며 최신글 순서로 출력될 것이다.")
    void findAllTest() {
        //given

        //when
        List<Livechat> all = livechatRepository.findAllByOrderByLivechatIdxDesc();
        all.forEach(System.out::println);

        //then
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
                    .livechatContent("안뇽하하하하")
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

    @Test
    @DisplayName("해시태그로 게시글 조회에 성공할 것이며 최신글 순서로 정렬이 된다")
    void findAllByLivechatHashtagOrderByLivechatIdxDescTest() {
        //given

        //when
        List<Livechat> list = livechatRepository.findAllByLivechatHashtagOrderByLivechatIdxDesc("별빛청하");
        list.forEach(System.out::println);

        //then
    }


}