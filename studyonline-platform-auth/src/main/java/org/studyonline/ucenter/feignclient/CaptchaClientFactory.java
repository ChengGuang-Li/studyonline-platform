package org.studyonline.ucenter.feignclient;

import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: Chengguang Li
 * @Date: 26/02/2024 10:31 am
 */

@Slf4j
@Component
public class CaptchaClientFactory implements FallbackFactory<CaptchaClient> {
    @Override
    public CaptchaClient create(Throwable throwable) {
        return new CaptchaClient() {
            @Override
            public Boolean verify(String key, String code) {
                log.debug("Circuit breaker exception when calling verification code service:{}", throwable.getMessage());
                return null;
            }
        };
    }
}
