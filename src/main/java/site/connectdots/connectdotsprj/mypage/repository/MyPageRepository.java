package site.connectdots.connectdotsprj.mypage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import site.connectdots.connectdotsprj.member.entity.Member;
import site.connectdots.connectdotsprj.mypage.dto.response.MemberDetailDTO;

import java.util.List;

@Repository
public interface MyPageRepository extends JpaRepository<Member, Long> {

    List<MemberDetailDTO> findAll(Long MemberIdx);

}
