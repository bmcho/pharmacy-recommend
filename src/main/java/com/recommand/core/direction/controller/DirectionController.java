package com.recommand.core.direction.controller;

import com.recommand.core.direction.entity.Direction;
import com.recommand.core.direction.service.DirectionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Controller
@RequiredArgsConstructor
public class DirectionController {

    private final DirectionService directionService;
    private static final String DIRECTION_BASE_URL = "https://map.kakao.com/link/map/";

    @GetMapping("/dir/{encodingId}")
    public String searchDirection(@PathVariable("encodingId") String encodingId) {
        Direction direction = directionService.findById(encodingId);

        String directionUrl = UriComponentsBuilder.fromHttpUrl(DIRECTION_BASE_URL +
                String.join(",",
                        direction.getTargetPharmacyName(), String.valueOf(direction.getTargetLatitude()), String.valueOf(direction.getTargetLongitude())
                )
        ).toUriString();

        return "redirect:" + directionUrl;
    }
}
