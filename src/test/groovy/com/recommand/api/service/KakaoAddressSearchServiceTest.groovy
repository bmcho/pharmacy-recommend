package com.recommand.api.service

import com.recommand.AbstractIntegrationContainerBaseTest
import org.springframework.beans.factory.annotation.Autowired

class KakaoAddressSearchServiceTest extends AbstractIntegrationContainerBaseTest {

    @Autowired
    private KakaoAddressSearchService kakaoAddressSearchService

    def "if address param value is null, return null"() {
        given:
        String address = null

        when:
        def result = kakaoAddressSearchService.requestAddressSearch(address)

        then:
        result == null
    }

    def "if address param value is valid, return kakaoApiResponseDto"() {
        given:
        String address = "서울 성북구 종암로 10길"

        when:
        def result = kakaoAddressSearchService.requestAddressSearch(address)

        then:
        result.documentList.size() > 0
        result.metaDto.totalCount > 0
        result.documentList.get(0).addressName != null
    }

    def "valid input address, return valid lat, lon"() {
        given:
        boolean actualResult = false

        when:
        def searchResult = kakaoAddressSearchService.requestAddressSearch(inputAddress)

        then:
        if (searchResult == null) actualResult = false
        else actualResult = searchResult.getDocumentList().size() > 0

        expect:
        actualResult == expectedResult

        where:
        inputAddress               | expectedResult
        "서울 특별시 성북구 종암동"    | true
        "서울 성북구 종암동 91"       | true
        "서울 대학로"                | true
        "서울 성북구 종암동 false"    | false
        "광진구 구의동 251-45"        | true
        "광진구 구의동 251-45555"     | false
        ""                          | false
    }
}
