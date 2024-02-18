package org.studyonline.content.feignclient;

import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @Description: Circuit breaker degradation processing
 * @Author: Chengguang Li
 * @Date: 18/02/2024 11:07 pm
 */

@Component
@Slf4j
public class MediaServiceClientFallbackFactory implements FallbackFactory<MediaServiceClient> {
    @Override
    public MediaServiceClient create(Throwable throwable) {
        return new MediaServiceClient() {
            @Override
            public String upload(MultipartFile filedata, String objectName) throws IOException {
                  log.debug("The service that calls remote upload files is circuit breaker and downgraded.{},{}",throwable.toString(),throwable);
                  return null;
            }
        };
    }
}
