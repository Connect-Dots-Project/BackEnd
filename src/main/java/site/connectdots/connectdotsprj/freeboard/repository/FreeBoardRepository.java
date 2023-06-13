package site.connectdots.connectdotsprj.freeboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.connectdots.connectdotsprj.freeboard.entity.FreeBoard;

import java.util.List;

public interface FreeBoardRepository extends JpaRepository<FreeBoard, Long> {

    List<FreeBoard> findAllByMemberMemberIdx(Long memberIdx);

    List<FreeBoard> findAllByMemberMemberAccount(String account);

}

