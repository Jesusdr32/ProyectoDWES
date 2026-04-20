package es.iesclaradelrey.da2d1e.shopeahjdr.api.configuration;

import es.iesclaradelrey.da2d1e.shopeahjdr.api.filters.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableMethodSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain getSecurityFilterChain(HttpSecurity http, JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {
        http

                // Activar configuración CORS para permitir peticiones desde el frontend Vue/Vite desarrollo de interfaces
                .cors(Customizer.withDefaults())

                // Definición de permisos de acceso a las rutas
                .authorizeHttpRequests(auth -> auth

                        // Permitir peticiones preflight OPTIONS necesarias para CORS
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // Rutas públicas sin autenticación
                        .requestMatchers("/api/v*/auth/**", "/swagger-ui/**", "/v3/api-docs/**").permitAll()

                        // Cualquier otra petición requiere autenticación
                        .anyRequest().authenticated()
                )

                // Desactivar autenticación HTTP Basic
                .httpBasic(AbstractHttpConfigurer::disable)

                // Desactivar protección CSRF
                .csrf(AbstractHttpConfigurer::disable)

                // Desactivar login por formulario
                .formLogin(AbstractHttpConfigurer::disable)

                // Desactivar sesiones: la API funciona con JWT y estado stateless
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // Desactivar logout
                .logout(AbstractHttpConfigurer::disable)

                // Añadir el filtro JWT antes del filtro de autenticación por usuario/contraseña
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)

                // Configuración necesaria para usar la consola H2
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));

        return http.build();
    }

    // Desarrollo de interfaces
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        // Configuración CORS para permitir que el frontend en localhost:5173
        // pueda llamar a la API en localhost:8000
        CorsConfiguration configuration = new CorsConfiguration();

        // Origen permitido: aplicación frontend Vue/Vite
        configuration.setAllowedOrigins(List.of("http://localhost:5173"));

        // Métodos HTTP permitidos desde el frontend
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        // Permitir cualquier cabecera, incluido Authorization con Bearer token
        configuration.setAllowedHeaders(List.of("*"));

        // Permitir envío de credenciales si fueran necesarias
        configuration.setAllowCredentials(true);

        // Aplicar esta configuración a todas las rutas de la API
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}