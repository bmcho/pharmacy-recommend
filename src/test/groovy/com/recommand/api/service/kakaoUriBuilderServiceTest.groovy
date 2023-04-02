package com.recommand.api.service

import spock.lang.Specification

import java.nio.charset.StandardCharsets

class kakaoUriBuilderServiceTest extends Specification {

    private KakaoUriBuilderService kakaoUriBuilderService

    def setup() {
        kakaoUriBuilderService = new KakaoUriBuilderService()
    }

    def "buildUriByAddressSearch"() {
        given:
        String address = "서울 종로구"
        def charset = StandardCharsets.UTF_8

        when:
        def uri = kakaoUriBuilderService.buildUriByAddressSearch(address)
        def decode = URLDecoder.decode(uri.toString(), charset)

        then:
        println uri
        println decode == "https://dapi.kakao.com/v2/local/search/address.json?query=서울 종로구"
    }
}
