package site.connectdots.connectdotsprj.mypage.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.connectdots.connectdotsprj.mypage.dto.response.MemberDetailDTO;
import site.connectdots.connectdotsprj.mypage.repository.MyPageRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MyPageService {

    private final MyPageRepository myPageRepository;

    @Transactional(readOnly = true)
    public List<MemberDetailDTO> findAll() {
        return myPageRepository.findAll()
                .stream()
                .map(MemberDetailDTO::new)
                .collect(Collectors.toList());
    }
}
