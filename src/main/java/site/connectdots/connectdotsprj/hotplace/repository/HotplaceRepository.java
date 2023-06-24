package site.connectdots.connectdotsprj.hotplace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import site.connectdots.connectdotsprj.global.enums.Location;
import site.connectdots.connectdotsprj.hotplace.entity.Hotplace;
import site.connectdots.connectdotsprj.member.entity.Member;

import java.util.List;

public interface HotplaceRepository extends JpaRepository<Hotplace, Long> {

    /**
     * 행정구역으로 핫플레이스 게시물 목록 조회하기
     *
     * @param location 조회할 행정구역
     * @return 해당 행정구역의 핫플레이스 게시물 목록 반환
     */
    List<Hotplace> findByLocation(Location location);

    List<Hotplace> findByKakaoLocation(String kakaoLocation);

    /**
     * 내용에서 키워드로 게시물 목록 조회하기 (내용에서 검색하기)
     *
     * @param keyword 내용에서 조회하고 싶은 키워드
     * @return 키워드로 조회시 핫플레이스 게시물 목록 반환
     */
    List<Hotplace> findByHotplaceContentContaining(String keyword);


    //작성자로 조회하기
    //List<Member> findByNickname(String nickname);

    @Query("SELECT h FROM Hotplace h WHERE h.member =:member")
    List<Hotplace> findAllByMember(Member member);


    List<Hotplace> findAllByOrderByHotplaceWriteDateDesc();

    // 핫플레이스 이미지 저장경로 찾기
    @Query("SELECT h FROM Hotplace h WHERE h.hotplaceImg =:fileName")
    String findByHotplaceImg(@Param("fileName") String fileName);
}