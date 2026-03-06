package es.iesclaradelrey.da2d1e.shopeahjdr.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordConfiguration {

    @Bean
    public PasswordEncoder passwordEncoder() {
        // utiliza un algoritmo de codificación con 2^12 iteraciones
        return new BCryptPasswordEncoder(12);
    }
}
