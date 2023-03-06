package com.recommand.api.service

import com.recommand.AbstractIntegrationContainerBaseTest
import org.springframework.beans.factory.annotation.Autowired

class KakaoAddressSearchServiceTest extends AbstractIntegrationContainerBaseTest {

    @Autowired
    private KakaoAddressSearchService kakaoAddressSearchService

    def "if address param value is null, return null"(){
        given:
        String address = null

        when:
        def result = kakaoAddressSearchService.requestAddressSearch(address)

        then:
        result == null
    }

    def "if address param value is valid, return kakaoApiResponseDto" (){
        given:
        String address = "서울 성북구 종암로 10길"

        when:
        def result = kakaoAddressSearchService.requestAddressSearch(address)

        then:
        result.documentList.size() > 0
        result.metaDto.totalCount > 0
        result.documentList.get(0).addressName != null
    }
}
