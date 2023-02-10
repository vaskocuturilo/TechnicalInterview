package com.example.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user =
                User.withDefaultPasswordEncoder()
                        .username("user")
                        .password("user")
                        .roles("USER")
                        .build();

        UserDetails admin =
                User.withDefaultPasswordEncoder()
                        .username("admin")
                        .password("admin")
                        .roles("ADMIN")
                        .build();

        return new InMemoryUserDetailsManager(user, admin);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/register").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/process_register").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/questions/upload").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/v1/questions/delete/all").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/v1/random").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/v1/reset").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/v1/edit/").hasRole("ADMIN")
                        .anyRequest().authenticated())
                .formLogin((form) -> form
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/api/v1/**")
                        .permitAll())
                .rememberMe((remember) -> remember.alwaysRemember(false))
                .logout((logout) -> logout
                        .logoutUrl("/logout")
                        .clearAuthentication(true)
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID", "remember-me")
                        .logoutSuccessUrl("/"));
        return http.build();
    }
}
