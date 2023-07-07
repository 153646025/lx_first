package com.example.springbootplus;

import com.example.springbootplus.entity.User;
import com.example.springbootplus.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@SpringBootApplication
public class SpringbootplusApplication {




    public static void main(String[] args) {
        SpringApplication.run(SpringbootplusApplication.class, args);
    }


}
