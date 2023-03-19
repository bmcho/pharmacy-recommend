package com.recommand.core.direction.service;

import com.recommand.api.dto.DocumentDto;
import com.recommand.core.direction.entity.Direction;
import com.recommand.core.direction.repository.DirectionRepository;
import com.recommand.core.pharmacy.dto.PharmacyDto;
import com.recommand.core.pharmacy.service.PharmacySearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class DirectionService {

    private static final int MAX_SEARCH_COUNT = 3; // 최대 추천 개수
    private static final double RADIUS_KM = 10.0; // 반경 10km 이내

    private final PharmacySearchService pharmacySearchService;
    private final DirectionRepository directionRepository;

    @Transactional
    public List<Direction> saveAll(List<Direction> directions) {
        if (CollectionUtils.isEmpty(directions)) return Collections.emptyList();
        return directionRepository.saveAll(directions);
    }

    public List<Direction> buildDirectionList(DocumentDto documentDto) {

        return pharmacySearchService.searchPharmacyDtoList()
                .stream().map(x ->
                        Direction.builder()
                                .inputAddress(documentDto.getAddressName())
                                .inputLatitude(documentDto.getLatitude())
                                .inputLongitude(documentDto.getLongitude())
                                .targetPharmacyName(x.getPharmacyName())
                                .targetAddress(x.getPharmacyAddress())
                                .targetLatitude(x.getLatitude())
                                .targetLongitude(x.getLongitude())
                                .distance(calculateDistance(documentDto.getLatitude(), documentDto.getLongitude(), x.getLatitude(), x.getLongitude()))
                                .build())
                .filter(x -> x.getDistance() <= RADIUS_KM)
                .sorted(Comparator.comparing(Direction::getDistance))
                .limit(MAX_SEARCH_COUNT)
                .collect(Collectors.toList());
    }


    // Haversine formula
    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        lat1 = Math.toRadians(lat1);
        lon1 = Math.toRadians(lon1);
        lat2 = Math.toRadians(lat2);
        lon2 = Math.toRadians(lon2);

        double earthRadius = 6371; //Kilometers
        return earthRadius * Math.acos(Math.sin(lat1) * Math.sin(lat2) + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon1 - lon2));
    }
}
