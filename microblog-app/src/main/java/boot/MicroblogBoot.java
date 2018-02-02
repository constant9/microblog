package boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableAutoConfiguration
@ComponentScan
//@PropertySource("classpath:application.properties")
public class MicroblogBoot {

    public static void main(String[] args) {
        SpringApplication.run(MicroblogBoot.class);
    }

}
