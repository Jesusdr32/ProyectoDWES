package es.iesclaradelrey.da2d1e.shopeahjdr.web.config;


import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

@Component
public class RegisterSecurity implements AuthenticationSuccessHandler, AuthenticationFailureHandler, LogoutSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) {
        System.out.println("LOGIN OK: " + authentication.getName());
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) {
        System.out.println("LOGIN FAIL: " + request.getParameter("username"));
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request,
                                HttpServletResponse response,
                                Authentication authentication) {
        String user = authentication != null ? authentication.getName() : "anonymous";
        System.out.println("LOGOUT OK: " + user);
    }
}

