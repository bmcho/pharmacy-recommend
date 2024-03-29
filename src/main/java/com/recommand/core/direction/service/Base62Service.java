 package com.recommand.core.direction.service;

import io.seruco.encoding.base62.Base62;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class Base62Service {

    private static final Base62 base62Instance = Base62.createInstance();

    public String encodingDirectionId(long directionId) {
        return new String(base62Instance.encode(String.valueOf(directionId).getBytes()));
    }

    public long decodingDirectionId(String encodingDirectionId) {
        String resultDirectionId = new String(base62Instance.decode(encodingDirectionId.getBytes()));
        return Long.parseLong(resultDirectionId);
    }
}
