package com.hangeulbada.global.config;

import com.hangeulbada.domain.auth.service.JwtAuthFilter;
import com.hangeulbada.domain.auth.service.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private static final String[] WHITE_LIST = {
            "/users/**",
            "/**"
    };

    private final JwtTokenProvider jwtUtil;

    @Bean
    protected SecurityFilterChain config(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.headers().frameOptions().disable();

        http.addFilterBefore(new JwtAuthFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);

        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers(WHITE_LIST).permitAll()
                // accept swagger
                .requestMatchers("/v3/api-docs/**").permitAll()
                .requestMatchers("/swagger-ui/**").permitAll()
                .requestMatchers("/**").permitAll())
        ;
        return http.build();
    }

}