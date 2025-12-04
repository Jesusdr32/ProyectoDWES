package es.iesclaradelrey.da2d1e.shopeahjdr.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
        "es.iesclaradelrey.da2d1e.shopeahjdr.common",
        "es.iesclaradelrey.da2d1e.shopeahjdr.web"
})
public class WebApp {

    public static void main(String[] args)  {
        SpringApplication.run(WebApp.class, args);
    }

}
