package com.recommand.api.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Slf4j
@Service
public class KakaoUriBuilderService {

    private static final String KAKAO_LOCAL_SEARCH_ADDRESS_URL = "https://dapi.kakao.com/v2/local/search/address.json";
    private static final String KAKAO_LOCAL_CATEGORY_SEARCH_URL = "https://dapi.kakao.com/v2/local/search/category.json";

    public URI buildUriByAddressSearch(String address) {
        URI uri = UriComponentsBuilder
                .fromHttpUrl(KAKAO_LOCAL_SEARCH_ADDRESS_URL)
                .queryParam("query", address)
                .build()
                .encode()
                .toUri();

        log.info("[kakaoUriBuilderService buildUriByAddressSearch] address: {}, uri: {}", address, uri);

        return uri;
    }

    public URI buildUriByCategorySearch(double latitude, double longitude, double radius, String category) {
        double meterRadius = radius * 1000;

        URI uri = UriComponentsBuilder
                .fromHttpUrl(KAKAO_LOCAL_CATEGORY_SEARCH_URL)
                .queryParam("category_group_code", category)
                .queryParam("x", latitude)
                .queryParam("y", longitude)
                .queryParam("radius", meterRadius)
                .queryParam("sort", "distance")
                .build()
                .encode()
                .toUri();

        log.info("[kakaoUriBuilderService buildUriByCategorySearch] uri: {}", uri);

        return uri;
    }
}
