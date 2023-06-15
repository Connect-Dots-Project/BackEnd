package site.connectdots.connectdotsprj.hotplace.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import site.connectdots.connectdotsprj.global.enums.Location;
import site.connectdots.connectdotsprj.hotplace.entity.Hotplace;
import site.connectdots.connectdotsprj.member.entity.Member;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class HotplaceRepositoryTest {

    @Autowired
    HotplaceRepository hotplaceRepository;

    @Test
    @DisplayName("데이터 1개 저장")
    void initial1111Test() {
        hotplaceRepository.save(
                Hotplace.builder()
                        .hotplaceContent("ASDFADSFDSFSF내용")
                        .location(Location.강남구)
//                        .member(Member.builder()
//                                .memberIdx(1L)
//                                .build())
                        .build()
        );
    }


//    @Test
//    @DisplayName("hotplace bulk data 50")
//    void insertBulkTest() {
//
//        String[] contents = {"치킨맛집", "맛도리", "꿀잼", "데이트코스"};
//        Location[] locations = {
//                Location.강남구, Location.강북구, Location.강동구, Location.강서구,
//                Location.관악구, Location.광진구, Location.구로구, Location.금천구,
//                Location.노원구, Location.도봉구, Location.동대문구, Location.동작구,
//                Location.마포구, Location.서대문구, Location.서초구, Location.성동구,
//                Location.성북구, Location.송파구, Location.양천구, Location.영등포구,
//                Location.용산구, Location.은평구, Location.종로구, Location.중구,
//                Location.중랑구
//        };
//
//        for (int i = 1; i <= 50; i++) {
//            hotplaceRepository.save(
//                    Hotplace.builder()
//                            .hotplaceContent(contents[i % 4])
//                            .location(locations[i % 25])
//                            .member(Member.builder()
//                                    .memberIdx((long) i)
//                                    .build())
//                            .build()
//            );
//        }
//    }

    @Test
    @DisplayName("전체 조회시 데이터가 50개여야만 한다.")
    void findAllTest() {
        //given

        //when
        List<Hotplace> all = hotplaceRepository.findAll();

        //then
        assertEquals(50, all.size());
        System.out.println(all);
//        all.forEach(System.out::println);
    }


    @Test
    @DisplayName("3번째 게시물의 hotplace Location은 강서구여야만 한다.")
    void findByIdxTest() {
        //given
        Long hotplaceIdx = 3L;
        //when
        Hotplace hotplace = hotplaceRepository.findById(hotplaceIdx)
                .orElseThrow();
        //then
        assertEquals("강서구", hotplace.getLocation().name());
    }

    @Test
    @DisplayName("findByIdTest")
    void findByIdTest() {
        //given
        Long hotplaceIdx = 7L;

        //when
        Hotplace hotplace = hotplaceRepository
                .findById(hotplaceIdx)
                .orElseThrow(
                        () -> new RuntimeException("없음")
                );

        //then
        assertEquals("금천구", hotplace.getLocation().toString());

        assertNotNull(hotplace);

        System.out.println("\n\n\n");
        System.out.println("foundHotplace = " + hotplace);
        System.out.println("\n\n\n");
    }

    @Test
    @DisplayName("deleteByIdTest")
    @Transactional
    @Rollback(value = true)
    void deleteByIdTest() {
        //given
        Long hotplaceIdx = 10L;
        //when
        hotplaceRepository.deleteById(hotplaceIdx);

        List<Hotplace> hotplaceList = hotplaceRepository.findAll();
        //then
        assertEquals(49, hotplaceList.size());


    }

    @Test
    @DisplayName("modifyTest")
    void modifyTest() {
        //given
        Long hotplaceIdx = 10L;
        Location location = Location.강남구;
        String content = "내용 수정수정~~~~";

        //when
        Optional<Hotplace> hotplace = hotplaceRepository.findById(hotplaceIdx);
        hotplace.ifPresent(hp -> {
            hp.setLocation(location);
            hp.setHotplaceContent(content);

            hotplaceRepository.save(hp);
        });

        //then
        assertTrue(hotplace.isPresent());

        Hotplace place = hotplace.get();
        assertEquals("강남구", place.getLocation().name());
    }


    @Test
    @DisplayName("modifyOneTest")
    void modifyOneTest() {
        //given
        Long hotplaceIdx = 5L;
        //when
        Hotplace hotplace = hotplaceRepository.findById(hotplaceIdx)
                .orElseThrow();

        hotplace.setHotplaceContent("내용수정해봄!");

        hotplaceRepository.save(hotplace);

        //then
        System.out.println("\n\n\n");
        System.out.println(hotplace);
        System.out.println("\n\n\n");

    }


    @Test
    @DisplayName("위치정보를 서대문구로 조회시, 2개가 조회되어야 한다.")
    void testFindByHotplaceLocation() {
        //given
        Location location = Location.서대문구;
        //when
        List<Hotplace> foundLocationList = hotplaceRepository.findByLocation(location);
        //then
        assertEquals(2, foundLocationList.size());

        System.out.println("\n\n\n");
        System.out.println("foundLocationList 1번째 = " + foundLocationList.get(0));
        System.out.println("\n\n\n");
        System.out.println("foundLocationList 2번째 = " + foundLocationList.get(1));
        System.out.println("\n\n\n");
    }


    @Test
    @DisplayName("내용에서 '맛'이 들어간 키워드로 검색하면 24개가 조회되어야 한다")
    void testFindByHotplaceContentContaining() {
        //given
        String keyword = "맛";
        //when
        List<Hotplace> hotplaceList = hotplaceRepository.findByHotplaceContentContaining(keyword);
        //then
        assertEquals(24, hotplaceList.size());

    }


    @Test
    @DisplayName("kakaoLocation으로 찾기")
    void findByKakaoLocationTest() {
        //given
        String kakaoLocation = "강남구";
        //when
        List<Hotplace> byKakaoLocation = hotplaceRepository.findByKakaoLocation(kakaoLocation);
        System.out.println("--------------byKakaoLocation = " + byKakaoLocation);
        //then
        assertEquals(2, byKakaoLocation.size());
    }

}