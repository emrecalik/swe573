package com.emrecalik.wikimed.server.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest,
                         HttpServletResponse httpServletResponse,
                         AuthenticationException e) throws IOException, ServletException {

        final String expired = (String) httpServletRequest.getAttribute("expired");
        if (expired != null) {
            log.error(expired);
            httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Expired JWT Token");
        } else {
            log.error("Responding with unauthorized error. Message - {}", e.getMessage());
            httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
        }
    }
}
