package com.recommand.core.pharmacy.controller;

import com.recommand.core.pharmacy.cache.PharmacyRedisTemplateService;
import com.recommand.core.pharmacy.dto.PharmacyDto;
import com.recommand.core.pharmacy.entity.Pharmacy;
import com.recommand.core.pharmacy.service.PharmacyRepositoryService;
import com.recommand.core.pharmacy.service.PharmacySearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PharmacyController {

    private final PharmacyRepositoryService pharmacyRepositoryService;
    private final PharmacyRedisTemplateService pharmacyRedisTemplateService;

    @GetMapping("/redis/save")
    public String save() {

        List<PharmacyDto> pharmacyList = pharmacyRepositoryService.findAll()
                .stream().map(pharmacy -> PharmacyDto.builder()
                        .id(pharmacy.getId())
                        .pharmacyName(pharmacy.getPharmacyName())
                        .pharmacyAddress(pharmacy.getPharmacyAddress())
                        .latitude(pharmacy.getLatitude())
                        .longitude(pharmacy.getLongitude())
                        .build())
                .toList();

        pharmacyList.forEach(pharmacyRedisTemplateService::save);

        return "success";
    }
}
