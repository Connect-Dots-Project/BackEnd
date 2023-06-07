package site.connectdots.connectdotsprj.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.connectdots.connectdotsprj.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
