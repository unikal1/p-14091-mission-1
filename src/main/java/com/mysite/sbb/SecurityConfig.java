package com.mysite.sbb;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {


         return http
                 .csrf((csrf) -> csrf.ignoringRequestMatchers("/h2-console/**"))
                 .authorizeHttpRequests((auth) -> {
                    auth.requestMatchers("/**").permitAll();
                 })
                 .headers((headers) -> headers
                         .addHeaderWriter((new XFrameOptionsHeaderWriter(
                                 XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN
                         ))))
                 .formLogin((login) -> login
                         .loginPage("/user/login")
                         .defaultSuccessUrl("/"))
                 .logout((logout) -> logout
                         .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
                         .logoutSuccessUrl("/")
                         .invalidateHttpSession(true))
                 .build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
