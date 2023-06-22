package site.connectdots.connectdotsprj.jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import site.connectdots.connectdotsprj.jwt.entity.Auth;

import java.util.Optional;

public interface AuthRepository extends JpaRepository<Auth, Long> {
    Optional<Auth> findByAccount(String account);

    @Modifying
    @Query("UPDATE Auth a SET a.refreshToken = :refreshToken WHERE a.account = :account")
    int updateRefreshTokenByAccount(@Param("refreshToken") String refreshToken, @Param("account") String account);

}
