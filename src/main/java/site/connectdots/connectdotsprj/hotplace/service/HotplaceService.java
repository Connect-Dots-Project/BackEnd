package site.connectdots.connectdotsprj.hotplace.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.connectdots.connectdotsprj.hotplace.repository.HotplaceRepository;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class HotplaceService {

    private final HotplaceRepository hotplaceRepository;

}
