package com.learnavro;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration;

@SpringBootApplication
public class CoffeeOrdersServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CoffeeOrdersServiceApplication.class, args);
    }

}
