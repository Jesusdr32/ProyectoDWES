package es.iesclaradelrey.da2d1e.shopeahjdr.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
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

                // administración requiere rol ADMIN (RBAC añadido aquí)
                .authorizeHttpRequests(auth -> auth.requestMatchers("/admin/**").hasRole("ADMIN"))

                // perfil de usuario requiere autenticación
                .authorizeHttpRequests(auth -> auth.requestMatchers("/users/profile/**").authenticated())

                // Si el usuario no esta logado no podrá entrar
                .authorizeHttpRequests(auth -> auth.requestMatchers("/users/logout").authenticated())

                // el resto permitido
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())

                // login por formulario
                .formLogin(form -> form
                        .loginPage("/users/login")
                        .loginProcessingUrl("/users/login")
                        .defaultSuccessUrl("/", true)
                        .permitAll())

                // logout por formulario
                .logout(logout -> logout.logoutUrl("/users/logout").logoutSuccessUrl("/"))

                // desactivar HTTP Basic
                .httpBasic(AbstractHttpConfigurer::disable)

                // H2 necesita esto
                .csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**"))
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));

        return http.build();
    }
}