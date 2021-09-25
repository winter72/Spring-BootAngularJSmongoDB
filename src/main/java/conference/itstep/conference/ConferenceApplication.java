package conference.itstep.conference;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;


@SpringBootApplication
@ComponentScan(basePackages="conference.itstep.conference")
@EnableAutoConfiguration
@PropertySource("classpath:application.properties")

public class ConferenceApplication {

    public static void main(String[] args) {

        SpringApplication.run(ConferenceApplication.class, args);
    }

}
