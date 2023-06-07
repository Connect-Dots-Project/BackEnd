package site.connectdots.connectdotsprj.hotplace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.connectdots.connectdotsprj.hotplace.entity.Hotplace;
import site.connectdots.connectdotsprj.hotplace.entity.HotplaceLocation;

import java.util.List;

public interface HotplaceRepository extends JpaRepository<Hotplace, Long> {

    /**
     * 행정구역으로 핫플레이스 게시물 목록 조회하기
     *
     * @param hotplaceLocation 조회할 행정구역
     * @return 해당 행정구역의 핫플레이스 게시물 목록 반환
     */
    List<Hotplace> findByHotplaceLocation(HotplaceLocation hotplaceLocation);

    /**
     * 내용에서 키워드로 게시물 목록 조회하기 (내용에서 검색하기)
     *
     * @param keyword 내용에서 조회하고 싶은 키워드
     * @return 키워드로 조회시 핫플레이스 게시물 목록 반환
     */
    List<Hotplace> findByHotplaceContentContaining(String keyword);


    //작성자로 조회하기
    //List<Member> findByNickname(String nickname);
}