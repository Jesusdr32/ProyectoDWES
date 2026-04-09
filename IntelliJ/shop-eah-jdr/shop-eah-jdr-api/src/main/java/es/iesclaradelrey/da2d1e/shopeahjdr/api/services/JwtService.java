package es.iesclaradelrey.da2d1e.shopeahjdr.api.services;

public interface JwtService {
    String generateAccessToken(String username);
    String extractUsername(String token);
    boolean isTokenExpired(String token);
}
