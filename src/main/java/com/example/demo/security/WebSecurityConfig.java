package com.example.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final DataSource dataSource;

    public WebSecurityConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider());
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/api/v1/admin/users").hasAnyAuthority("ADMIN")
                        .requestMatchers("/api/v1/create").hasAnyAuthority("GUEST", "CREATOR", "EDITOR", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/v1/register").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/process_register").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/questions/upload").hasAnyAuthority("CREATOR", "EDITOR", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/v1/questions/delete/all").hasAnyAuthority("CREATOR", "EDITOR", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/v1/random").hasAnyAuthority("GUEST", "CREATOR", "EDITOR", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/v1/reset").hasAnyAuthority("CREATOR", "EDITOR", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/v1/edit/").hasAnyAuthority("GUEST", "CREATOR", "EDITOR", "ADMIN")
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
