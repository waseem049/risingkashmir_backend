package com.rsl.risingkashmir.security.jwt.filter;

import com.rsl.risingkashmir.utility.JWTUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.sun.istack.NotNull;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    private static final String AUTHORIZATION_HEADER = "Authorization";

    private final JWTUtil util;
    private final UserDetailsService userDetailsService;
    

    public SecurityFilter(JWTUtil util, UserDetailsService userDetailsService) {
        this.util = util;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(
            @NotNull HttpServletRequest request,
            @NotNull HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        // Reading Token from Authorization Header
        String token = request.getHeader(AUTHORIZATION_HEADER);

        if (token != null) {
            String username = util.getSubject(token);

            // if username is not null & Context Authentication must be null
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                try {
                    UserDetails user = userDetailsService.loadUserByUsername(username);
                    boolean isValid = util.isValidToken(token, user.getUsername());

                    if (isValid) {
                        UsernamePasswordAuthenticationToken authenticationToken =
                                new UsernamePasswordAuthenticationToken(username, null, user.getAuthorities());
                        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    }
                } catch (Exception e) {
                    // Handle token validation exception, e.g., log or respond with unauthorized status
                    // Log the exception or send an unauthorized response
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}