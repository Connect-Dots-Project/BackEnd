package site.connectdots.connectdotsprj.freeboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.connectdots.connectdotsprj.freeboard.entity.FreeBoardLike;

import java.util.List;

public interface FreeBoardLikeRepository extends JpaRepository<FreeBoardLike, Long> {

    FreeBoardLike findByMemberAccountAndFreeboardIdx(String memberAccount, Long freeboardIdx);

    Long deleteByMemberAccountAndFreeboardIdx(String memberAccount, Long freeboardIdx);

    Long countByFreeboardIdx(Long freeBoardIdx);

    List<FreeBoardLike> findByMemberAccount(String memberAccount);


}
