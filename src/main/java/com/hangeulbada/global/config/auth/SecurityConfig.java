package com.hangeulbada.global.config.auth;

import com.hangeulbada.domain.login.CustomOAuth2UserService;
import com.hangeulbada.domain.login.OAuth2SuccessHandler;
import com.hangeulbada.domain.login.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final CustomOAuth2UserService oAuth2UserService;
    private final TokenService tokenService;
    private final OAuth2SuccessHandler successHandler;

    @Bean
    public void filterChain(HttpSecurity http) throws Exception {
        http.httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .requestMatchers("/token/**").permitAll()
                .requestMatchers("/api/**").permitAll()
                .requestMatchers("/resources/**").permitAll()
                .anyRequest().authenticated()
                .and()
//                .addFilterBefore(new JwtAuthFilter(tokenService),
//                        UsernamePasswordAuthenticationFilter.class)
                .oauth2Login().loginPage("/token/expired")
                .successHandler(successHandler)
                .userInfoEndpoint().userService(oAuth2UserService);

//        http.addFilterBefore(new JwtAuthFilter(tokenService), UsernamePasswordAuthenticationFilter.class);
    }
}

