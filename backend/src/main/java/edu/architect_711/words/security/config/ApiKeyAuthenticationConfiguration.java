package edu.architect_711.words.security.config;

import edu.architect_711.words.security.filter.ApiKeyAuthenticationFilter;

import edu.architect_711.words.security.auth_entry_point.ApiKeyAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity @Configuration @RequiredArgsConstructor
public class ApiKeyAuthenticationConfiguration {
    private final ApplicationContext context;
    private final ApiKeyAuthenticationEntryPoint apiKeyAuthenticationEntryPoint;

    @Bean
    public SecurityFilterChain securityFilterChainAllAuthenticated(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
               .formLogin(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(request -> request
                        .anyRequest().authenticated())
                .addFilterBefore((ApiKeyAuthenticationFilter) context.getBean("apiKeyAuthenticationFilter"), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(ex -> ex
                    .authenticationEntryPoint(apiKeyAuthenticationEntryPoint))
                .build();
    }

}
