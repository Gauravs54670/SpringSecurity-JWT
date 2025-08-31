package com.gaurav.SpringSecurity.jwt;

import com.gaurav.SpringSecurity.service.CustomUserDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Map;

@Slf4j @Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final CustomUserDetails customUserDetails;
    public JwtAuthenticationFilter (
            CustomUserDetails customUserDetails,
            JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
        this.customUserDetails = customUserDetails;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        String requestPath = request.getServletPath();
        //skip the authentication for this endpoint
        if(requestPath.startsWith("/auth")) {
            filterChain.doFilter(request, response);
            return;
        }
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String jwtToken = authorizationHeader.substring(7);
            try {
                String username = this.jwtUtils.extractUserName(jwtToken);
                if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails = this.customUserDetails.loadUserByUsername(username);
                    if(this.jwtUtils.validateToken(jwtToken, userDetails)) {
                        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                    }
                }
            }
            catch (Exception exception) {
                log.warn("Authentication failed");
            }
        }
        filterChain.doFilter(request, response);
    }
}
