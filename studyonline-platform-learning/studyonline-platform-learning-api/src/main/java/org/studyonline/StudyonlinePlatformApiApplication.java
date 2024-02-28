package org.studyonline;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages={"org.studyonline.*.feignclient"})
public class StudyonlinePlatformApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudyonlinePlatformApiApplication.class, args);
    }

}
