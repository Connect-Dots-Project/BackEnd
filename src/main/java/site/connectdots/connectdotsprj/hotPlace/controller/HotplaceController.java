package site.connectdots.connectdotsprj.hotPlace.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.connectdots.connectdotsprj.hotPlace.entity.Hotplace;
import site.connectdots.connectdotsprj.hotPlace.service.HotplaceService;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/contents/hot-place")
public class HotplaceController {

    private final HotplaceService hotplaceService;

    @GetMapping
    public ResponseEntity<?> list() {

        List<Hotplace> hotplaceList = hotplaceService.findAll();

        return ResponseEntity.ok().body(hotplaceList);
    }

}
