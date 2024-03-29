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
                .anonymous().disable()
                .csrf().disable()
                .headers((headers) -> headers
                        .frameOptions().disable())
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/", "/css/*", "/js/*").permitAll()
                        .requestMatchers("/api/v1/users/all").hasAnyAuthority("ADMIN")
                        .requestMatchers("/api/v1/create").hasAnyAuthority("GUEST", "USER", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/v1/subscribes").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/users/register").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/users/process_register").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/questions/upload").hasAnyAuthority("USER", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/v1/questions/delete/all").hasAnyAuthority("USER", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/v1/random").hasAnyAuthority("GUEST", "USER", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/v1/reset").hasAnyAuthority("USER", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/v1/edit/").hasAnyAuthority("GUEST", "USER", "ADMIN")
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
