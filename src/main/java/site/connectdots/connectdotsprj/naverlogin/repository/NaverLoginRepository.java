package site.connectdots.connectdotsprj.naverlogin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.connectdots.connectdotsprj.naverlogin.entity.NaverMember;

import java.util.List;

public interface NaverLoginRepository extends JpaRepository<NaverMember, Long> {

    NaverMember findByEmail(String email);
}
