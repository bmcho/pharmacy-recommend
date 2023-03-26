package com.recommand.api.service;

import ch.qos.logback.core.util.StringCollectionUtil;
import com.recommand.api.dto.KakaoApiResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoCategorySearchService {

    private final RestTemplate restTemplate;
    private final kakaoUriBuilderService kakaoUriBuilderService;
    private static final String PHARMACY_CATEGORY = "PM9";
    @Value("${kakao.rest.api.key}")
    private String kakaoRestApiKey;

    @Retryable(retryFor = {RuntimeException.class}, maxAttempts = 2, backoff = @Backoff(2000))
    public KakaoApiResponseDto requestCategorySearch(double latitude, double longitude, double radius) {

        if (ObjectUtils.isEmpty(latitude) || ObjectUtils.isEmpty(longitude) || ObjectUtils.isEmpty(radius)) return null;

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, "KakaoAK " + kakaoRestApiKey);
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        // kakao api 호출
        return restTemplate.exchange(
                kakaoUriBuilderService.buildUriByCategorySearch(latitude, longitude, radius, PHARMACY_CATEGORY),
                HttpMethod.GET,
                httpEntity,
                KakaoApiResponseDto.class).getBody();
    }

    @Recover
    public KakaoApiResponseDto recoverRequestCategorySearch(RuntimeException e, String address) {
        log.error("[RequestCategorySearch-All retries failed] error: {}", e);
        return null;
    }

}
