package com.hangeulbada.global.config;

import com.hangeulbada.domain.auth.jwt.JWTAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${custom.jwt.secretKey}")
    private String secretKey;
    private static final String[] WHITE_LIST = {
            "/users/**",
            "/**"
    };

    @Bean
    protected SecurityFilterChain config(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.headers().frameOptions().disable();

        HttpSecurity httpSecurity = http.authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(WHITE_LIST).permitAll()
                        // accept swagger
                        .requestMatchers("/v3/api-docs/**").permitAll()
                        .requestMatchers("/swagger-ui/**").permitAll()
                        .requestMatchers("/**").permitAll())
                .authorizeHttpRequests().anyRequest().authenticated()
                .and()
                .addFilterBefore(new JWTAuthFilter(secretKey), UsernamePasswordAuthenticationFilter.class);
        ;
        return http.build();
    }

}