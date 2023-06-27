package site.connectdots.connectdotsprj.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import site.connectdots.connectdotsprj.global.enums.Location;
import site.connectdots.connectdotsprj.member.dto.request.MemberNicknameCheckRequestDTO;
import site.connectdots.connectdotsprj.member.dto.request.MemberSignUpRequestDTO;
import site.connectdots.connectdotsprj.member.dto.response.MemberNicknameCheckResponseDTO;
import site.connectdots.connectdotsprj.member.dto.response.MemberSignUpResponseDTO;
import site.connectdots.connectdotsprj.member.entity.Member;
import site.connectdots.connectdotsprj.member.exception.custom.SignUpFailException;
import site.connectdots.connectdotsprj.member.repository.MemberRepository;

import static site.connectdots.connectdotsprj.member.exception.custom.enums.SignUpFailErrorCode.*;

@Service
@RequiredArgsConstructor
public class MemberSignUpService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder encoder;

    public MemberSignUpResponseDTO signUp(MemberSignUpRequestDTO dto) {
        validateDTO(dto);

        String encodePassword = encoder.encode(dto.getFirstPassword());

        Member save = memberRepository.save(
                Member.builder()
                        .memberAccount(dto.getAccount())
                        .memberPassword(encodePassword)
                        .memberBirth(dto.getBirthDay().atStartOfDay())
                        .memberName(dto.getName())
                        .memberNickname(dto.getNickName())
                        .memberPhone(dto.getPhone())
                        .memberLocation(dto.getLocation())
                        .memberGender(dto.getGender())
                        .memberComment(dto.getComment())
                        .memberLoginMethod(dto.getLoginMethod())
                        .build()
        );

        boolean isSignUp = save.getMemberAccount() != null;

        return new MemberSignUpResponseDTO(isSignUp);
    }


    // private method
    private void validateDTO(MemberSignUpRequestDTO dto) {
        if (!dto.getFirstPassword().equals(dto.getSecondPassword())) {
            throw new SignUpFailException(VALIDATE_PASSWORD_EXCEPTION, dto.getFirstPassword());
        }

        if (!checkLocation(dto.getLocation())) {
            throw new SignUpFailException(VALIDATE_LOCATION_EXCEPTION, dto.getLocation());
        }

        if (isDuplicateAccount(dto.getAccount())) {
            throw new SignUpFailException(DUPLICATE_ACCOUNT, dto.getAccount());
        }

        if (isDuplicatePhone(dto.getPhone())) {
            throw new SignUpFailException(DUPLICATE_PHONE_EXCEPTION, dto.getPhone());
        }

        if (isDuplicateNickName(dto.getNickName())) {
            throw new SignUpFailException(DUPLICATE_NICK_NAME_EXCEPTION, dto.getNickName());
        }
    }

    private boolean checkLocation(String location) {
        for (Location value : Location.values()) {
            if (value.name().equals(location)) return true;
        }
        return false;
    }

    private boolean isDuplicateNickName(String nickname) {
        return memberRepository.findByMemberNickname(nickname) != null;
    }

    private boolean isDuplicateAccount(String account) {
        return memberRepository.findByMemberAccount(account) != null;
    }

    private boolean isDuplicatePhone(String phone) {
        return memberRepository.findByMemberPhone(phone) != null;
    }

    public MemberNicknameCheckResponseDTO checkNickname(MemberNicknameCheckRequestDTO dto) {
        System.out.println(memberRepository.findAll());
        memberRepository.findAll().forEach(System.out::println);
        System.out.println("\n\n\n-------------------------");
        System.out.println(dto);
        System.out.println(memberRepository.findByMemberNickname(dto.getNickname()));
        System.out.println("-------------------------\n\n\n");

        return MemberNicknameCheckResponseDTO.builder()
                .checkNickname(memberRepository.findByMemberNickname(dto.getNickname()) == null)
                .build();
    }
}