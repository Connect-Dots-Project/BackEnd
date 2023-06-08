package site.connectdots.connectdotsprj.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.connectdots.connectdotsprj.hotplace.entity.HotplaceLocation;
import site.connectdots.connectdotsprj.member.dto.request.MemberSignUpRequestDTO;
import site.connectdots.connectdotsprj.member.entity.Member;
import site.connectdots.connectdotsprj.member.exception.custom.SignUpFailException;
import site.connectdots.connectdotsprj.member.repository.MemberRepository;

import static site.connectdots.connectdotsprj.member.exception.custom.SignUpFailErrorCode.*;

@Service
@RequiredArgsConstructor
public class MemberSignUpService {

    private final MemberRepository memberRepository;

    public boolean signUp(MemberSignUpRequestDTO dto) {
        validateDTO(dto);

        Member save = memberRepository.save(
                Member.builder()
                        .memberAccount(dto.getAccount())
                        .memberPassword(dto.getFirstPassword())
                        .memberBirth(dto.getBirthDay().atStartOfDay())
                        .memberName(dto.getName())
                        .memberNickname(dto.getNickName())
                        .memberPhone(dto.getPhone())
                        .memberLocation(dto.getLocation())
                        .memberGender(dto.getGender())
                        .memberComment(dto.getComment())
                        .build()
        );

        return save.getMemberAccount() != null;
    }


    private void validateDTO(MemberSignUpRequestDTO dto) {
        if (!dto.getFirstPassword().equals(dto.getSecondPassword())) {
            throw new SignUpFailException(VALIDATE_PASSWORD_EXCEPTION, dto.getFirstPassword());
        }

        if (!checkLocation(dto.getLocation())) {
            throw new SignUpFailException(VALIDATE_LOCATION_EXCEPTION, dto.getLocation());
        }

        if (duplicateAccount(dto.getAccount())) {
            throw new SignUpFailException(DUPLICATE_ACCOUNT, dto.getAccount());
        }

        if (duplicatePhone(dto.getPhone())) {
            throw new SignUpFailException(DUPLICATE_PHONE_EXCEPTION, dto.getPhone());
        }

        if (duplicateNickName(dto.getNickName())) {
            throw new SignUpFailException(DUPLICATE_NICK_NAME_EXCEPTION, dto.getNickName());
        }
    }


    private boolean checkLocation(String location) {
        for (HotplaceLocation value : HotplaceLocation.values()) {
            if (value.name().equals(location)) return true;
        }
        return false;
    }

    private boolean duplicateNickName(String nickname) {
        return memberRepository.findByMemberNickname(nickname) != null;
    }

    private boolean duplicateAccount(String account) {
        return memberRepository.findByMemberAccount(account) != null;
    }

    private boolean duplicatePhone(String phone) {
        return memberRepository.findByMemberPhone(phone) != null;
    }

}