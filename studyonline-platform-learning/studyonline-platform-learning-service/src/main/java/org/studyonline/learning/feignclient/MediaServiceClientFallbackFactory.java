package org.studyonline.learning.feignclient;

import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.studyonline.base.model.RestResponse;

/**
 * @Description:
 * @Author: Chengguang Li
 * @Date: 28/02/2024 5:14 pm
 */
@Slf4j
@Component
public class MediaServiceClientFallbackFactory implements FallbackFactory<MediaServiceClient> {
    @Override
    public MediaServiceClient create(Throwable throwable) {
        return new MediaServiceClient() {
            @Override
            public RestResponse<String> getPlayUrlByMediaId(String mediaId) {
                log.error("Remote call media asset management service circuit breaker abnormality：{}", throwable.getMessage());
                return null;
            }
        };
    }
}

