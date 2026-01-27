package es.iesclaradelrey.da2d1e.shopeahjdr.web;

import es.iesclaradelrey.da2d1e.shopeahjdr.common.repositories.CategoryRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan(basePackages = {"es.iesclaradelrey.da2d1e.shopeahjdr.common"})
@EnableJpaRepositories(basePackages = {"es.iesclaradelrey.da2d1e.shopeahjdr.common"})
@SpringBootApplication(scanBasePackages = {
        "es.iesclaradelrey.da2d1e.shopeahjdr.common",
        "es.iesclaradelrey.da2d1e.shopeahjdr.web"
})
public class WebApp {

    public static void main(String[] args)  {



        SpringApplication.run(WebApp.class, args);
    }

}
