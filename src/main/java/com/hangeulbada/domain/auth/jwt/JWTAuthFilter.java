package com.hangeulbada.domain.auth.jwt;

import com.hangeulbada.domain.auth.component.JwtTokenProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JWTAuthFilter extends OncePerRequestFilter {

    private final String secretKey;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        log.info("JWTAuthFilter");
        final String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            if (isValidAccessToken(token)) {
                Authentication auth = jwtTokenProvider.getAuthentication(token);
                log.info("auth" + auth);
                SecurityContextHolder.getContext().setAuthentication(auth);
                filterChain.doFilter(request, response);
            }
            else{
                log.info("Token is not valid: Access token has expired");
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.setContentType("application/json");
                response.getWriter().write("{\"error\": \"Access token has expired\", \"code\": 401}");
                response.getWriter().flush();
            }
//            else if (jwtTokenProvider.isRefreshTokenValid(jwt)) {
//                String newToken = jwtTokenProvider.generateAccessTokenFromRefreshToken(jwt);
//                if (newToken != null) {
//                    response.setHeader("Authorization", "Bearer " + newToken);  // Optionally return the new token in the response header
//                    setAuthenticationContext(newToken, request);
//                }
//            }
        }
        filterChain.doFilter(request, response);
    }


    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }

    private boolean isValidAccessToken(String token) {
        try {
            log.info("Token validation");
            log.info("Token: " + token);
            return !extractAllClaims(token).getExpiration().before(new java.util.Date());
        } catch (Exception e) {
            log.info("Token validation error: " + e.getMessage());
            return false;
        }
    }
}