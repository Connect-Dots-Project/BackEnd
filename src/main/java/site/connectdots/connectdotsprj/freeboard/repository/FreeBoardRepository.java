package site.connectdots.connectdotsprj.freeboard.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import site.connectdots.connectdotsprj.freeboard.entity.FreeBoard;
import site.connectdots.connectdotsprj.hotplace.entity.Hotplace;
import site.connectdots.connectdotsprj.member.entity.Member;

import java.util.List;

public interface FreeBoardRepository extends JpaRepository<FreeBoard, Long> {
    List<FreeBoard> findAllByOrderByFreeBoardIdxDesc();

    List<FreeBoard> findAllByMemberMemberIdx(Long memberIdx);

    List<FreeBoard> findAllByMemberMemberAccount(String account);

    Page<FreeBoard> findAll(Pageable pageable);

    List<FreeBoard> findByFreeBoardIdxIn(List<Long> freeboardIdxList);

    List<FreeBoard> findByMember(Member member);
}

