package guru.qa.niffler;

import guru.qa.niffler.service.PropertiesLogger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NifflerUserdataApplication {

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(NifflerUserdataApplication.class);
        springApplication.addListeners(new PropertiesLogger());
        springApplication.run(args);
    }

}
//dckr_pat_iW2BqtBb6LBh8GMjgqSVkEC6XxA