package com.example.FinalProject;


import com.example.FinalProject.repository.MyUserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Profile("dev")
@SpringBootApplication(exclude= {UserDetailsServiceAutoConfiguration.class})
public class FinalProjectApplication implements CommandLineRunner {

    @Value("(${example.description})")
    String description;
    @Value("(${example.message})")
    String message;

    @Bean
    public WebMvcConfigurer webMvcConfigurer(){
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry){
                registry.addMapping("/*").allowedHeaders("*").allowedOrigins("*").allowedMethods("*")
                        .allowCredentials(true);
            }
        };
    }
@Autowired
private MyUserRepository myUserRepository;
    public static void main(String[] args) {
        SpringApplication.run(FinalProjectApplication.class, args);

    }



    @Override
    public void run(String... args) throws Exception {
        System.out.println("Active profile is:" + message);

        System.out.println("My final project description is: " + description);

    }
}
