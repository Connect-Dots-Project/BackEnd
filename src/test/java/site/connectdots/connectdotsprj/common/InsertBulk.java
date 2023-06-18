package site.connectdots.connectdotsprj.common;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import site.connectdots.connectdotsprj.freeboard.entity.FreeBoard;
import site.connectdots.connectdotsprj.freeboard.entity.FreeBoardCategory;
import site.connectdots.connectdotsprj.freeboard.entity.FreeBoardReply;
import site.connectdots.connectdotsprj.freeboard.repository.FreeBoardReplyRepository;
import site.connectdots.connectdotsprj.freeboard.repository.FreeBoardRepository;
import site.connectdots.connectdotsprj.member.entity.Gender;
import site.connectdots.connectdotsprj.member.entity.Member;
import site.connectdots.connectdotsprj.member.entity.MemberLoginMethod;
import site.connectdots.connectdotsprj.member.repository.MemberRepository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@SpringBootTest
@Transactional
@Rollback(value = false)
public class InsertBulk {
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private FreeBoardRepository freeBoardRepository;

    @Autowired
    private FreeBoardReplyRepository freeBoardReplyRepository;

    @Test
    @DisplayName("100명의 무작위 회원을 가입시킬 것이다.")
    void memberDummyDataTest() {

        String[] names = {
                "이영희", "박민수", "정진영", "최미영", "김영수",
                "박지영", "이동민", "김소연", "황성민", "신영자",
                "오준호", "김미경", "박진우", "조은정", "한철수",
                "이영희", "박민수", "정진영", "최미영", "김영수",
                "박지영", "이동민", "김소연", "황성민", "신영자",
                "오준호", "김미경", "박진우", "조은정", "한철수",
                "이영희", "박민수", "정진영", "최미영", "김영수",
                "박지영", "이동민", "김소연", "황성민", "신영자",
                "오준호", "김미경", "박진우", "조은정", "한철수",
                "이영희", "박민수", "정진영", "최미영", "김영수",
                "박지영", "이동민", "김소연", "황성민", "신영자",
                "오준호", "김미경", "박진우", "조은정", "한철수",
                "이영희", "박민수", "정진영", "최미영", "김영수",
                "박지영", "이동민", "김소연", "황성민", "신영자",
                "오준호", "김미경", "박진우", "조은정", "한철수",
                "이영희", "박민수", "정진영", "최미영", "김영수",
                "박지영", "이동민", "김소연", "황성민", "신영자",
                "오준호", "김미경", "박진우", "조은정", "한철수",
                "이영희", "박민수", "정진영", "최미영", "김영수",
                "박지영", "이동민", "김소연", "황성민", "신영자"
        };

        for (int i = 1; i <= 100; i++) {
            int number1 = (int) (Math.random() * 9000) + 1000;
            int number2 = (int) (Math.random() * 9000) + 1000;

            Gender gender = Gender.M;
            if (i % 2 == 0) {
                gender = Gender.F;
            }

            LocalDateTime startDate = LocalDateTime.of(1983, 1, 1, 0, 0);
            LocalDateTime endDate = LocalDateTime.of(2003, 12, 31, 0, 0);

            long days = startDate.until(endDate, ChronoUnit.DAYS);
            long randomDays = (long) (Math.random() * days);

            LocalDateTime randomDate = startDate.plusDays(randomDays);

            String[] location = {"도봉구", "노원구", "강북구", "은평구", "종로구", "성북구", "중랑구", "서대문구", "동대문구",
                    "강서구", "마포구", "중구", "성동구", "광진구", "강동구", "영등포구", "용산구", "양천구",
                    "구로구", "동작구", "송파구", "강남구", "서초구", "관악구", "금천구"};

            memberRepository.save(
                    Member.builder()
                            .memberAccount("test" + i + "@google.com")
                            .memberPassword("1234")
                            .memberName(names[i % 100])
                            .memberNickname("nickName" + i)
                            .memberGender(gender)
                            .memberBirth(randomDate)
                            .memberPhone("010-" + number1 + "-" + number2)
                            .memberLocation(location[i % 25])
                            .memberComment("hello world" + i)
                            .memberLoginMethod(MemberLoginMethod.COMMON)
                            .build()
            );

        }

    }

    @Test
    @DisplayName("500개의 무작위 자유게시판을 생성할 것이다.")
    void freeBoardDummyDataTest() {

        String[] contents = {
                "우리는 열매를 천하를 그들의 무엇을 살 대중을 이상은 역사를 것이다. 우리의 천지는 인간이 말이다. 듣기만 하여도 꽃이 풍부하게 충분히 거선의 설레는 있는가? 위하여서 인간이 인생을 찾아 있는 있는 온갖 위하여, 얼음에 것이다. 속잎나고, 길을 눈에 동력은 것이다. 피가 보내는 역사를 것은 그들은 대한 그들을 구하지 이상, 말이다. 장식하는 그들의 끓는 돋고, 할지니, 피부가 그들에게 아름다우냐?",
                "품으며, 실로 이상의 피는 우리는 가치를 우리의 든 청춘에서만 그리하였는가? 인생의 이상 가진 물방아 피어나는 인생을 사막이다. 구하지 많이 얼마나 피가 그들의 청춘의 것이다. 창공에 봄날의 가치를 품으며, 얼음에 청춘은 이것이다. 사는가 이상 그러므로 구하기 그들에게 피다. 작고 커다란 착목한는 속에 물방아 풍부하게 그들은 인생에 부패뿐이다.",
                "피고 낙원을 사라지지 고행을 방지하는 것이다. 곳으로 영락과 갑 품에 이성은 풍부하게 피어나는 듣는다. 풍부하게 무엇을 옷을 얼마나 있는 말이다. 속에 끓는 앞이 몸이 피부가 예수는 풍부하게 설산에서 있으며, 위하여서. 공자는 않는 옷을 수 희망의 풀이 그것은 힘차게 인간의 있으랴?",
                "구하지 사는가 과실이 뜨거운지라, 피가 것이다. 이상의 얼마나 풍부하게 얼마나 없으면 물방아 원대하고, 능히 봄바람이다. 바이며, 이상의 그들은 새 투명하되 사랑의 있다. 있으며, 아니한 몸이 보는 뼈 길을 교향악이다. 꽃이 노래하며 용감하고 커다란 같으며, 그들은 청춘의 교향악이다. 목숨을 얼마나 튼튼하며, 용감하고 힘차게 쓸쓸하랴?",
                "이것을 보이는 뭇 것은 우리는 같이, 귀는 구할 황금시대의 아니다. 품에 만물은 피고, 피가 말이다. 목숨을 밥을 꾸며 석가는 거친 더운지라 구하지 끓는다. 이상 든 품에 새 못하다 불러 쓸쓸한 갑 품고 약동하다. 같은 같지 원질이 아니다. 뛰노는 속에 청춘의 이는 과실이 곳이 방황하여도, 방황하였으며, 수 봄바람이다.",
                "풍부하게 생의 불어 것이다. 청춘에서만 산야에 예수는 것은 힘차게 피가 그들은 웅대한 이것이다. 인간이 것이 위하여 피다. 심장은 앞이 못할 이상의 인간의 들어 천하를 것이다. 위하여서, 구하지 관현악이며, 피에 옷을 불어 기관과 동력은 황금시대의 사막이다. 이상을 청춘의 것은 이것이다.",
                "붙잡아 싸인 피부가 그들은 힘있다. 내려온 무엇을 그들은 대한 작고 품고 사막이다. 우리 그들의 이상 있으랴? 황금시대의 끝까지 열락의 인도하겠다는 피가 때문이다. 날카로우나 우리 불러 물방아 그들은 위하여, 것이다.",
                "때에, 없으면 같지 싶이 천고에 커다란 것이다. 너의 무엇을 무엇을 것이다. 산야에 같은 열매를 수 피다. 어디 만물은 두손을 그들의 약동하다. 우리는 뜨고, 그들의 곳으로 이것을 우리의 영락과 그러므로 피가 것이다. 오직 꽃 있는 우리의 있을 피는 어디 끓는다. 주며, 실로 이것은 영원히 피가 아름답고 하는 이것이다.",
                "있는 희망의 청춘이 되려니와, 봄날의 들어 밝은 피다. 할지라도 없으면, 든 날카로우나 인간이 이상의 청춘의 있으며, 얼마나 것이다. 이상, 인간이 인간은 뿐이다. 같이, 있을 자신과 설레는 못하다 산야에 뜨거운지라, 동력은 끝에 봄바람이다. 사랑의 그들의 꽃이 끓는 싸인 봄바람이다.",
                "웅대한 내는 풀이 황금시대를 용감하고 이상의 있는 것이다. 대한 풀이 인간은 무한한 하는 꽃이 그림자는 천자만홍이 것이다. 이것은 바이며, 품에 힘있다. 이상 싹이 피가 구하지 실현에 넣는 봄바람이다. 더운지라 작고 바로 공자는 뛰노는 주는 교향악이다.",
                "구할 품에 몸이 용감하고 하는 석가는 철환하였는가? 뼈 기쁘며, 것이다.보라, 원대하고, 있는 아름다우냐? 없으면, 이상, 풍부하게 그들의 위하여, 그리하였는가? 청춘 그들의 것이 대중을 청춘은 그들은 구하지 불러 부패뿐이다. 있음으로써 일월과 이상을 위하여, 것은 때문이다. 사랑의 기쁘며, 있는 그러므로 산야에 부패를 많이 있으랴?",
                "노년에게서 석가는 생명을 품으며, 것이다. 무한한 하여도 하는 같은 싶이 우리의 미묘한 그들을 원대하고, 봄바람이다. 아름답고 소담스러운 아니한 천하를 이것이다. 청춘의 같이, 가지에 가진 꽃이 때문이다. 시들어 날카로우나 인간에 얼음이 아름다우냐? 위하여, 그것을 넣는 용감하고 우는 이상은 보이는 힘있다. 보는 가는 그들은 듣는다.",
                "웅대한 실현에 발휘하기 없으면, 듣는다. 착목한는 따뜻한 인생을 풍부하게 황금시대를 투명하되 피에 이것은 있다. 무한한 곧 찬미를 이성은 뜨거운지라, 안고, 청춘 크고 위하여서. 현저하게 품에 피가 갑 위하여 간에 부패뿐이다. 대고, 속잎나고, 끓는 너의 교향악이다. 돋고, 그러므로 끓는 우리 이상을 무엇이 청춘은 가슴에 있는가? 가지에 청춘 인생에 같이 오아이스도 든 피어나기 그들의 시들어 철환하였는가?",
                "자신과 인간이 고동을 운다. 같은 창공에 눈이 피가 반짝이는 이상을 피부가 그들은 그들에게 칼이다. 아름답고 피고, 실로 열락의 반짝이는 이상은 것이 운다. 많이 동력은 역사를 있는 이것이다. 풀이 튼튼하며, 힘차게 뛰노는 아름다우냐? 과실이 뭇 품고 일월과 속에서 넣는 구하지 칼이다.",
                "옷을 그들은 그것을 피가 있으랴? 낙원을 이것은 인생에 사막이다. 소금이라 피는 인생에 것이 청춘의 심장의 것이다. 이는 않는 이상의 힘있다. 광야에서 그들은 봄바람을 설산에서 얼음에 얼마나 끓는 풀밭에 사랑의 것이다.",
                "인생의 구하지 따뜻한 더운지라 트고, 충분히 보내는 설산에서 봄바람이다. 이 들어 기관과 이것이다. 이성은 아름답고 가슴이 품고 할지니, 지혜는 산야에 것은 그러므로 피다. 그들은 위하여, 그러므로 목숨이 커다란 주며, 것이다. 바이며, 끝까지 청춘의 우리의 위하여 것이다.",
                "우리 따뜻한 할지라도 봄바람이다. 할지라도 인간은 군영과 맺어, 설레는 희망의 소금이라 원질이 얼음이 힘있다. 이것은 꽃 두손을 놀이 웅대한 위하여서. 실현에 커다란 곳으로 우리 청춘이 충분히 설레는 관현악이며, 이성은 사막이다. 때에, 이상, 있는 못할 바이며, 가슴에 내려온 봄바람이다. 동력은 가슴에 위하여 뼈 튼튼하며, 이상은 힘있다. 이것을 내는 것이다.보라, 천자만홍이 보는 인도하겠다는 날카로우나 많이 보라.",
                "보는 모래뿐일 열매를 전인 더운지라 못할 아름다우냐? 얼마나 너의 동산에는 이것이야말로 장식하는 미인을 이것이다. 인생에 생생하며, 되려니와, 끝까지 무엇이 그러므로 이것이야말로 무한한 말이다. 피어나기 우리는 주며, 것이다. 가슴에 가치를 때까지 우리의 물방아 봄날의 피다. 온갖 이상의 시들어 영원히 이것은 같이, 이것이다. 있는 인생에 안고, 따뜻한 가치를 낙원을 미인을 피고 그림자는 칼이다.",
                "작고 때에, 노래하며 든 피다. 넣는 앞이 대한 구하기 이상은 청춘의 열락의 힘있다. 물방아 보는 이상 길을 있다. 간에 예가 생명을 넣는 풍부하게 돋고, 쓸쓸하랴? 이상의 희망의 얼음과 끝에 실현에 따뜻한 웅대한 피다.",
                "없으면 노년에게서 공자는 피가 지혜는 위하여서, 가는 가치를 것이다. 위하여서, 품고 끝에 방황하여도, 되려니와, 싶이 천고에 끓는 것이다. 이는 꽃이 굳세게 위하여 희망의 부패뿐이다. 천자만홍이 긴지라 크고 남는 그러므로 그들은 인생의 운다. 그들은 피어나는 쓸쓸한 발휘하기 공자는 할지라도 많이 것이다. 이상 이것을 그들의 갑 거친 위하여, 스며들어 청춘 바이며, 철환하였는가? 커다란 내려온 찾아다녀도, 있는 옷을 인생을 따뜻한 때에, 때문이다.",
        };

        for (int i = 1; i <= 5000; i++) {
            Member byId = memberRepository.findById((long) (Math.random() * 50) + 1).orElseThrow();
            freeBoardRepository.save(
                    FreeBoard.builder()
                            .freeBoardTitle("자유게시판 제목입니다." + i)
                            .freeBoardContent(contents[i % 20])
                            .freeBoardLocation(byId.getMemberLocation())
                            .freeBoardCategory(FreeBoardCategory.values()[i % 8])
                            .freeBoardLikeCount(((long) (Math.random() * 1000) + 1))
                            .freeBoardViewCount(((long) (Math.random() * 1000) + 1))
                            .member(byId)
                            .build()
            );

        }

    }

    @Test
    @DisplayName("5000개의 무작위 댓글을 생성할 것이다.")
    void freeBoardReplyDummyDataTest() {

        for (int i = 1; i <= 50000; i++) {
            int memberIdx = (int) (Math.random() * 50) + 1;
            int freeBoardIdx = (int) (Math.random() * 5000) + 1;

            freeBoardReplyRepository.save(
                    FreeBoardReply.builder()
                            .freeBoardReplyContent("댓글내용입니다" + i)
                            .member(Member.builder()
                                    .memberIdx((long) memberIdx)
                                    .build())

                            .freeBoard(FreeBoard.builder()
                                    .freeBoardIdx((long) freeBoardIdx)
                                    .build())
                            .build()
            );
        }
    }

}
