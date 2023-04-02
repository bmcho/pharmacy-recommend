package com.recommand.core.direction.service

import spock.lang.Specification

class Base62ServiceTest extends Specification {

    private Base62Service base62Service

    def setup() {
        base62Service = new Base62Service()
    }

    def "check base62 encoder/decoder"() {
        given:
        long id = 5;

        when:
        def encodingId = base62Service.encodingDirectionId(id)
        def decodingId = base62Service.decodingDirectionId(encodingId)

        then:
        id == decodingId
    }
}
