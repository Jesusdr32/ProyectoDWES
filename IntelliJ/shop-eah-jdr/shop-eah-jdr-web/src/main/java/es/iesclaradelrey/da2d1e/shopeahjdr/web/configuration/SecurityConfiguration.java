package es.iesclaradelrey.da2d1e.shopeahjdr.web.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {
    @Bean
    public SecurityFilterChain getSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                // Sobre la consola de H2
                .authorizeHttpRequests(auth -> auth.requestMatchers("/h2-console", "/h2-console/*").authenticated())
                .csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/*"))
                // Sobre el área de administración
                .authorizeHttpRequests(auth -> auth.requestMatchers("/admin", "/admin/*").authenticated())
                // En el resto de la tienda
                .authorizeHttpRequests(auth -> auth.requestMatchers("/", "/*").permitAll())
                .headers(AbstractHttpConfigurer::disable)
                .formLogin(Customizer.withDefaults());

        return http.build();
    }
}
