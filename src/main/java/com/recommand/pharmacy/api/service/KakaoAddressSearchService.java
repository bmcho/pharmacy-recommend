package com.recommand.pharmacy.api.service;

import com.recommand.pharmacy.api.dto.KakaoApiResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

import java.net.URL;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoAddressSearchService {

    private final RestTemplate restTemplate;
    private final kakaoUriBuilderService kakaoUriBuilderService;

    @Value("${kakao.rest.api.key}")
    private String kakoRestApiKey;

    public KakaoApiResponseDto requestAddressSearch(String address) {

        if(ObjectUtils.isEmpty(address)) return null;

        // kakao api 호출
        return restTemplate.exchange(
                kakaoUriBuilderService.buildUriByAddressSearch(address),
                HttpMethod.GET,
                (HttpEntity<?>) Map.of(HttpHeaders.AUTHORIZATION, "KakaoAK " + kakoRestApiKey),
                KakaoApiResponseDto.class).getBody();
    }

}
