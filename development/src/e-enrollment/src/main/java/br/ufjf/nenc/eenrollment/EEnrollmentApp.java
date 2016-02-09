package br.ufjf.nenc.eenrollment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class EEnrollmentApp {

    public static void main(String[] args) {
        SpringApplication.run(EEnrollmentApp.class, args);
    }
}
