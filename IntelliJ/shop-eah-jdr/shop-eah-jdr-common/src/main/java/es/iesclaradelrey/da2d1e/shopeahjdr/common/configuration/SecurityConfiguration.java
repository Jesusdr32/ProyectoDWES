package es.iesclaradelrey.da2d1e.shopeahjdr.common.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain getSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                // H2 console libre
                .authorizeHttpRequests(auth -> auth.requestMatchers("/h2-console", "/h2-console/*", "/h2-console/**").permitAll())

                // Recursos estáticos libres (bootstrap, css, images, img, js, webfonts)
                .authorizeHttpRequests(auth -> auth.requestMatchers(
                        "/bootstrap/**",
                        "/css/**",
                        "/images/**",
                        "/img/**",
                        "/js/**",
                        "/webfonts/**"
                ).permitAll())

                // TODO lo demás libre (para poder entrar a todas las páginas)
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())

                // Para que H2 console funcione bien
                .csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/*", "/h2-console/**"))
                .headers(AbstractHttpConfigurer::disable)

                // Lo puedes dejar puesto aunque ahora esté todo permitido
                .formLogin(Customizer.withDefaults());

        return http.build();
    }
}