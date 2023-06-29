package site.connectdots.connectdotsprj.freeboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.connectdots.connectdotsprj.freeboard.entity.FreeBoardReply;
import site.connectdots.connectdotsprj.member.entity.Member;

import java.util.List;

public interface FreeBoardReplyRepository extends JpaRepository<FreeBoardReply, Long> {

    List<FreeBoardReply> findAllByFreeBoardFreeBoardIdx(Long freeBoardIdx);

    List<FreeBoardReply> findByMember(Member member);

}
