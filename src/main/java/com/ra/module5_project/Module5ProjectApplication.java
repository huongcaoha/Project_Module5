package com.ra.module5_project;

import com.ra.module5_project.service.InitialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Module5ProjectApplication implements CommandLineRunner {
    @Autowired
    private InitialService initialService ;
    public static void main(String[] args) {
        SpringApplication.run(Module5ProjectApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        initialService.initialApplication();
    }
}
