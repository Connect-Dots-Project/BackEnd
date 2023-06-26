package site.connectdots.connectdotsprj.jwt.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import site.connectdots.connectdotsprj.jwt.config.JwtTokenProvider;
import site.connectdots.connectdotsprj.jwt.entity.Auth;
import site.connectdots.connectdotsprj.member.entity.Member;

@SpringBootTest
@Transactional
@Rollback(value = false)
class AuthRepositoryTest {
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private AuthRepository authRepository;

    @Test
    @DisplayName("insert")
    void insertTest() {
        //given

        String refreshToken = jwtTokenProvider.createRefreshToken(
                Member.builder()
                        .memberAccount("test1@naver.com")
                        .build());
        //when

        Auth save = authRepository.save(Auth.builder()
                .account("test1@naver.com")
                .refreshToken(refreshToken)
                .build());

        System.out.println(save);

        //then
    }

    @Test
    @DisplayName("같은 account 계정으로 refresh Token이 업데이트 되어야 한다.")
    void updateTokenTest() {
        //given
        Member member = Member.builder()
                .memberAccount("test1@naver.com")
                .build();
        String refreshToken = jwtTokenProvider.createRefreshToken(member);

        //when
//        int i = authRepository.updateOrInsertRefreshTokenByAccount(refreshToken, member.getMemberAccount());


        //then
    }

}