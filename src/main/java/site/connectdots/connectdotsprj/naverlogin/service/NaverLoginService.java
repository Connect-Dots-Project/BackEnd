package site.connectdots.connectdotsprj.naverlogin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.connectdots.connectdotsprj.member.repository.MemberRepository;
import site.connectdots.connectdotsprj.naverlogin.dto.response.OAuthInfoResponse;
import site.connectdots.connectdotsprj.naverlogin.entity.NaverMember;
import site.connectdots.connectdotsprj.naverlogin.entity.OAuthLoginParams;
import site.connectdots.connectdotsprj.naverlogin.repository.NaverLoginRepository;
import site.connectdots.connectdotsprj.util.jwt.AuthTokens;
import site.connectdots.connectdotsprj.util.jwt.AuthTokensGenerator;

@Service
@RequiredArgsConstructor
public class NaverLoginService {
    private final NaverLoginRepository memberRepository;
    private final AuthTokensGenerator authTokensGenerator;
    private final RequestOAuthInfoService requestOAuthInfoService;

    public AuthTokens login(OAuthLoginParams params) {
        OAuthInfoResponse oAuthInfoResponse = requestOAuthInfoService.request(params);
        Long memberId = findOrCreateMember(oAuthInfoResponse);
        return authTokensGenerator.generate(memberId);
    }

    private Long findOrCreateMember(OAuthInfoResponse oAuthInfoResponse) {
        return memberRepository.findByEmail(oAuthInfoResponse.getEmail())
                .getNaverId();
//                .map(NaverMember::getNaverId)
//                .orElseGet(() -> newMember(oAuthInfoResponse));
    }

    private Long newMember(OAuthInfoResponse oAuthInfoResponse) {
        NaverMember member = NaverMember.builder()
                .email(oAuthInfoResponse.getEmail())
                .nickname(oAuthInfoResponse.getNickname())
                .oAuthProvider(oAuthInfoResponse.getOAuthProvider())
                .build();

        return memberRepository.save(member).getNaverId();
    }
}
