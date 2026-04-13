package es.iesclaradelrey.da2d1e.shopeahjdr.api.filters;

import es.iesclaradelrey.da2d1e.shopeahjdr.api.services.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.NullMarked;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOError;
import java.io.IOException;

@Component
@NullMarked
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String AUTH_HEADER = "Authorization";
    private static final String AUTH_PREFIX = "Bearer ";

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtService jwtService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        // 1. Si ya está autenticado, saltamos este filtro de inmediato
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            filterChain.doFilter(request, response);
            return;
        }

        // 2. Extraer y comprobar si la cabecera es para JWT
        final String authHeader = request.getHeader(AUTH_HEADER);
        if (authHeader == null || !authHeader.startsWith(AUTH_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }

        // 3. Obtener el token de la cabecera
        final String jwt = authHeader.substring(AUTH_PREFIX.length());

        try {
            final String username = jwtService.extractUsername(jwt);

            // 4. Si el nombre de usuario no llega, salir
            if (username == null || username.isBlank()) {
                filterChain.doFilter(request, response);
                return;
            }

            // 5. Buscar el usuario - Esto lanza excepción userNotfoundException si no existe el usuario
            UserDetails userDetails =  this.userDetailsService.loadUserByUsername(username);

            // 6. Verificar si el token ha caducado
            if (jwtService.isTokenValid(jwt, userDetails.getUsername())) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        } catch (Exception e) {

        }
        filterChain.doFilter(request, response);
    }
}
