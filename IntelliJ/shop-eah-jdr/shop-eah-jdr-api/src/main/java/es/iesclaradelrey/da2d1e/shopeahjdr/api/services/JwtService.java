package es.iesclaradelrey.da2d1e.shopeahjdr.api.services;

public interface JwtService {
    String generateAccessToken(String username);
    String generateRefreshToken(String username);
    String extractUsername(String token);
    boolean isTokenExpired(String token);
    boolean isTokenValid(String token, String username);
}
