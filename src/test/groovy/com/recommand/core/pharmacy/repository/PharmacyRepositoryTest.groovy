package com.recommand.core.pharmacy.repository

import com.recommand.AbstractIntegrationContainerBaseTest
import com.recommand.core.pharmacy.entity.Pharmacy
import com.recommand.core.pharmacy.repository.PharmacyRepository
import org.springframework.beans.factory.annotation.Autowired

import java.time.LocalDateTime

class PharmacyRepositoryTest extends AbstractIntegrationContainerBaseTest {

    @Autowired
    private PharmacyRepository pharmacyRepository

    def setup(){
        pharmacyRepository.deleteAll()
    }

    def "PharmacyRepository save"() {
        given:
        String name = "테스트 약국"
        String address = "서울 특별시 종로구"
        double latitude = 36.11
        double longitude = 128.11

        def pharmacy = Pharmacy.builder()
                .pharmacyAddress(address)
                .pharmacyName(name)
                .latitude(latitude)
                .longitude(longitude)
                .build()

        when:
        def result = pharmacyRepository.save(pharmacy)

        then:
        result.getPharmacyAddress() == address
        result.getPharmacyName() == name
        result.getLatitude() == latitude
        result.getLongitude() == longitude

    }

    def "PharmacyRepository saveAll()"() {
        given:
        String name = "테스트 약국"
        String address = "서울 특별시 종로구"
        double latitude = 36.11
        double longitude = 128.11

        def pharmacy = Pharmacy.builder()
                .pharmacyAddress(address)
                .pharmacyName(name)
                .latitude(latitude)
                .longitude(longitude)
                .build()

        when:
        pharmacyRepository.saveAll(Arrays.asList(pharmacy))
        def result = pharmacyRepository.findAll()

        then:
        result.size() == 1
    }

    def "BaseTimeEntity insert"() {
        given:
        LocalDateTime now = LocalDateTime.now()
        String address = "서울 특별시 성복구 종암동"
        String name = "은혜 약국"

        def pharmacy = Pharmacy.builder()
                .pharmacyAddress(address)
                .pharmacyName(name)
                .build()

        when:
        pharmacyRepository.save(pharmacy)

        def result = pharmacyRepository.findAll()

        then:
        result.get(0).getCreateDate().isAfter(now)
        result.get(0).getModifiedDate().isAfter(now)
    }

}