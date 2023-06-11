package site.connectdots.connectdotsprj.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.connectdots.connectdotsprj.member.entity.Member;
import site.connectdots.connectdotsprj.member.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class MemberLogoutService {

    private final MemberRepository memberRepository;

    public boolean logout(String email) {
        Member foundMember = memberRepository.findByMemberAccount(email);

        if (foundMember != null) {
            foundMember.setMemberCookieDate(null);
            foundMember.setMemberSessionId(null);
            memberRepository.save(foundMember);
            return true;
        }

        return false;
    }


}
