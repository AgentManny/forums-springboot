package gg.manny.forums.web.config;

import gg.manny.forums.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomizeAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        //set our response to OK status
        response.setStatus(HttpServletResponse.SC_OK);
        response.sendRedirect(request.getAttribute("redirect") == null ? "/" : (String) request.getAttribute("redirect"));

        request.getSession().setAttribute("user", userRepository.findByUsername(authentication.getName()));

        for (GrantedAuthority auth : authentication.getAuthorities()) {
            /*if ("ADMIN".equals(auth.getAuthority())) {
                response.sendRedirect("/dashboard");
            }*/
        }
    }

}