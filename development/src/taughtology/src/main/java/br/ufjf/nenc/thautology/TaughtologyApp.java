package br.ufjf.nenc.thautology;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class TaughtologyApp {

    public static void main(String[] args) {
        SpringApplication.run(TaughtologyApp.class, args);
    }
}
