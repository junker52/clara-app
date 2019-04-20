package org.clara.translate.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class ClaraWebAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClaraWebAppApplication.class, args);
    }
}
