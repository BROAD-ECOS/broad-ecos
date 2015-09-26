package br.ufjf.nenc.broadecos.rankr;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import static org.springframework.boot.SpringApplication.run;

@SpringBootApplication
@EnableScheduling
public class Rankr {

    public static void main(String[] args) {
        run(Rankr.class, args);
    }
}
