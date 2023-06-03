package site.connectdots.connectdotsprj.freeboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.connectdots.connectdotsprj.freeboard.entity.FreeBoard;

public interface FreeBoardRepository extends JpaRepository<FreeBoard, Long> {

}

