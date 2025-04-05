package com.example.FireFly_backend.config;

import com.example.FireFly_backend.enums.Role;
import com.example.FireFly_backend.filters.JwtAuthenticationFilter;
import com.example.FireFly_backend.handlers.JwtAuthenticationEntryPoint;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.json.HTTP;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import static com.example.FireFly_backend.enums.Role.ADMIN;

/**
 * Configuration class for setting up security configurations, including authentication,
 * authorization, and OAuth2 integration.
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;
    private final ObjectMapper objectMapper;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(customizer -> {
                    customizer.authenticationEntryPoint(new JwtAuthenticationEntryPoint(objectMapper));
                })
                .sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers(HttpMethod.GET, "/api/v1/finalProduct/all").hasRole("ADMIN")
                        .requestMatchers("/api/v1/midProduct/**").hasRole("ADMIN")
                        .requestMatchers("/api/v1/firstProduct/**").hasRole("ADMIN")
                        .requestMatchers("/api/v1/finalProductNeed/**").hasRole("ADMIN")
                        .requestMatchers("/api/v1/midProductNeed/**").hasRole("ADMIN")

                        .requestMatchers("/api/v1/firstProductOrder/allByMaterialType").hasAnyRole("ADMIN","METALIST", "WOODER","SEWER")
                        .requestMatchers("/api/v1/firstProductOrder/**").hasRole("ADMIN")
                        .requestMatchers("/api/v1/midProductOrder/**").hasRole("ADMIN")
                        .requestMatchers("/api/v1/finalProductOrder/**").hasRole("ADMIN")



                        .anyRequest().permitAll()
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(httpSecurityLogoutConfigurer -> {
                    httpSecurityLogoutConfigurer.logoutUrl("/api/v1/auth/logout");
                    httpSecurityLogoutConfigurer.addLogoutHandler(logoutHandler);
                    httpSecurityLogoutConfigurer.logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext());
                });

        return http.build();
    }
}
