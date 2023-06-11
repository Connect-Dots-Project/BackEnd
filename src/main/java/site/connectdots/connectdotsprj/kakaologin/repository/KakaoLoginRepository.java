package site.connectdots.connectdotsprj.kakaologin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.connectdots.connectdotsprj.kakaologin.entity.KakaoMember;

public interface KakaoLoginRepository extends JpaRepository<KakaoMember,Long> {
}
