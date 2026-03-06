package es.iesclaradelrey.da2d1e.shopeahjdr.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain getSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                // recursos públicos
                .authorizeHttpRequests(auth -> auth.requestMatchers(
                        "/",
                        "/bootstrap/**",
                        "/css/**",
                        "/images/**",
                        "/img/**",
                        "/js/**",
                        "/webfonts/**",
                        "/register/**"
                ).permitAll())

                // H2 console requiere login
                .authorizeHttpRequests(auth -> auth.requestMatchers("/h2-console/**").authenticated())

                // administración requiere login
                .authorizeHttpRequests(auth -> auth.requestMatchers("/admin/**").authenticated())

                // el resto permitido
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())

                // login por formulario
                .formLogin(Customizer.withDefaults())

                // desactivar HTTP Basic
                .httpBasic(AbstractHttpConfigurer::disable)

                // H2 necesita esto
                .csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**"))
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));

        return http.build();
    }
}