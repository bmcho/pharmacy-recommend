package com.recommand.core.direction.controller;

import com.recommand.core.direction.service.DirectionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Slf4j
@Controller
@RequiredArgsConstructor
public class DirectionController {

    private final DirectionService directionService;
    private static final String DIRECTION_BASE_URL = "https://map.kakao.com/link/map/";

    @GetMapping("/dir/{encodingId}")
    public String searchDirection(@PathVariable("encodingId") String encodingId) {
        return "redirect:" + directionService.findDirectionUrlById(encodingId);
    }
}
