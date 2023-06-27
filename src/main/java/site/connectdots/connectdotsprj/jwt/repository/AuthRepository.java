package site.connectdots.connectdotsprj.jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.connectdots.connectdotsprj.jwt.entity.Auth;

public interface AuthRepository extends JpaRepository<Auth, Long> {
    Auth findByAccount(String account);

}
