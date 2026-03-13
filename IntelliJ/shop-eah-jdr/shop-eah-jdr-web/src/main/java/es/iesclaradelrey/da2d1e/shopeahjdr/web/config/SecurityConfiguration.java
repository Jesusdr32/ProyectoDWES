package es.iesclaradelrey.da2d1e.shopeahjdr.web.config;

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

                        // Recursos públicos accesibles sin autenticación
                        .requestMatchers(
                                "/",
                                "/bootstrap/**",
                                "/css/**",
                                "/images/**",
                                "/img/**",
                                "/js/**",
                                "/webfonts/**",
                                "/register/**"
                        ).permitAll()

                        // Panel de administración: solo usuarios con rol ADMIN
                        .requestMatchers("/admin/**").hasRole("ADMIN")

                        // Consola de H2: requiere estar autenticado
                        .requestMatchers("/h2-console/**").authenticated()

                        // Perfil de usuario: requiere autenticación
                        .requestMatchers("/users/profile/**").authenticated()

                        // Logout: solo disponible para usuarios autenticados
                        .requestMatchers("/users/logout").authenticated()

                        // Cualquier otra petición está permitida
                        .anyRequest().permitAll()
                )

                // Configuración del login mediante formulario
                .formLogin(form -> form
                        .loginPage("/users/login")          // página personalizada de login
                        .loginProcessingUrl("/users/login") // URL que procesa el login
                        .defaultSuccessUrl("/", true)       // redirección tras login correcto
                        .permitAll()                        // acceso público a la página de login
                )

                // Configuración del logout
                .logout(logout -> logout
                        .logoutUrl("/users/logout")         // URL para cerrar sesión
                        .logoutSuccessUrl("/")              // redirección tras logout
                )

                // Desactivar autenticación HTTP Basic
                .httpBasic(AbstractHttpConfigurer::disable)

                // Configuración necesaria para usar la consola H2
                .csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**"))
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));

        return http.build();
    }
}