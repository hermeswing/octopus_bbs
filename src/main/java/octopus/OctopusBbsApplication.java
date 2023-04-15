package octopus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing // @EntityListeners 를 사용할 수 있도록 함.
@SpringBootApplication
public class OctopusBbsApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(OctopusBbsApplication.class, args);
    }
    
}
