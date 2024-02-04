package org.studyonline;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableSwagger2Doc
public class MediaApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(MediaApiApplication.class, args);
    }

}
