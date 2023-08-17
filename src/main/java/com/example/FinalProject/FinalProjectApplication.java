package com.example.FinalProject;




import com.example.FinalProject.repository.MyUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;




@SpringBootApplication
public class FinalProjectApplication implements CommandLineRunner {



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


    }
}
