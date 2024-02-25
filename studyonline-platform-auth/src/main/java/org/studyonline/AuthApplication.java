package org.studyonline;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Description:
 * @Author: Chengguang Li
 * @Date: 20/02/2024 1:37 pm
 */
@SpringBootApplication
@EnableFeignClients(basePackages = "org.studyonline.ucenter.feignclient")
public class AuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class,args);
    }
}
