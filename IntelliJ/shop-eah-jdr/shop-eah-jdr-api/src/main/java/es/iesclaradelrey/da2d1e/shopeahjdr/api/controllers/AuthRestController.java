package es.iesclaradelrey.da2d1e.shopeahjdr.api.controllers;

import es.iesclaradelrey.da2d1e.shopeahjdr.api.dto.LoginRequestDto;
import es.iesclaradelrey.da2d1e.shopeahjdr.api.dto.LoginResponseDto;
import es.iesclaradelrey.da2d1e.shopeahjdr.api.dto.NumericSimpleValueDto;
import es.iesclaradelrey.da2d1e.shopeahjdr.api.services.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthRestController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthRestController(AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto request) {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String accessToken = jwtService.generateAccessToken(request.getUsername());
        String refreshToken = jwtService.generateRefreshToken(request.getUsername());

        LoginResponseDto response = LoginResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();

        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<NumericSimpleValueDto> refresh(@RequestBody NumericSimpleValueDto request) {
        String refreshToken = String.valueOf(request.getValue());

        try {
            // Extraer usuario
            String username = jwtService.extractUsername(refreshToken);

            // Obtener claims para comprobar tipo
            Claims claims = Jwts.parser()
                    .verifyWith(
                            Keys.hmacShaKeyFor(
                                    java.util.Base64.getDecoder().decode("TU_SECRET")
                            )
                    )
                    .build()
                    .parseSignedClaims(refreshToken)
                    .getPayload();

            String type = claims.get("type", String.class);

            // Validar refresh token
            if (!jwtService.isTokenExpired(refreshToken) && "refresh".equals(type)) {
                String newAccessToken = jwtService.generateAccessToken(username);

                return ResponseEntity.ok(
                        NumericSimpleValueDto.builder()
                                .value(Long.parseLong(newAccessToken))
                                .build()
                );
            }

            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
