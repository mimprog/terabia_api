package com.terabia.terabia.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        /*http
                .cors()
                .and()
                .csrf()
                .disable()
                .authorizeHttpRequests()
                .requestMatchers("/api/v1/**")
                .permitAll()
                .requestMatchers("/api/v1/management/**").hasAnyRole(ADMIN.name(), MANAGER.name())

                .requestMatchers(HttpMethod.GET, "/api/v1/management").hasAnyAuthority(ADMIN_READ.name(), MANAGER_READ.name())
                .requestMatchers(HttpMethod.POST, "/api/v1/management").hasAnyAuthority(ADMIN_CREATE.name(), MANAGER_CREATE.name())
                .requestMatchers(HttpMethod.PUT, "/api/v1/management").hasAnyAuthority(ADMIN_UPDATE.name(), MANAGER_UPDATE.name())
                .requestMatchers(HttpMethod.DELETE, "/api/v1/management").hasAnyAuthority(ADMIN_DELETE.name(), MANAGER_DELETE.name())

                .requestMatchers( "/api/v1/admin").hasRole(ADMIN.name())

                .requestMatchers(HttpMethod.GET, "/api/v1/admin").hasAnyAuthority(ADMIN_READ.name())
                .requestMatchers(HttpMethod.POST, "/api/v1/admin").hasAnyAuthority(ADMIN_CREATE.name())
                .requestMatchers(HttpMethod.DELETE, "/api/v1/admin").hasAnyAuthority(ADMIN_DELETE.name())
                .requestMatchers(HttpMethod.PUT, "/api/v1/admin").hasAnyAuthority(ADMIN_UPDATE.name())

                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();*/
        http
                .csrf()
                .disable()
                .authorizeHttpRequests()
                .anyRequest()
                .permitAll();
        return http.build();

    }


}
