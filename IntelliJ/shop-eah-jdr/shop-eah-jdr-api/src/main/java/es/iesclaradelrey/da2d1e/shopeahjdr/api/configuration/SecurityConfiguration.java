package es.iesclaradelrey.da2d1e.shopeahjdr.api.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain getSecurityFilterChain(HttpSecurity http) throws Exception {
        http

                // Definición de permisos de acceso a las rutas
                .authorizeHttpRequests(auth -> auth
                        // Cualquier otra petición está permitida
                        .anyRequest().permitAll()
                )

                // Desactivar autenticación HTTP Basic
                .httpBasic(AbstractHttpConfigurer::disable)

                // Configuración necesaria para usar la consola H2
                .csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**"))
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));

        return http.build();
    }
}