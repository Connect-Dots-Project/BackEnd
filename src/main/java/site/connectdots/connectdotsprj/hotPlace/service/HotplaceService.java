package site.connectdots.connectdotsprj.hotPlace.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import site.connectdots.connectdotsprj.hotPlace.entity.Hotplace;
import site.connectdots.connectdotsprj.hotPlace.repository.HotplaceRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class HotplaceService {

    private final HotplaceRepository hotplaceRepository;

    public List<Hotplace> findAll() {
        return hotplaceRepository.findAll();
    }
}
