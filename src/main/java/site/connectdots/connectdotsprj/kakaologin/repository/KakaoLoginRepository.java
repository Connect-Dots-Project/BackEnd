package site.connectdots.connectdotsprj.kakaologin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.connectdots.connectdotsprj.kakaologin.entity.KakaoMember;

import java.util.Optional;

public interface KakaoLoginRepository extends JpaRepository<KakaoMember,Long> {

    // 카카오이메일로 카카오멤버 정보 조회
    Optional<KakaoMember> findByKakaoEmail(String kakaoEmail);

    // 카카오이메일 중복체크
    boolean existsByKakaoEmail(String kakaoEmial);

}
