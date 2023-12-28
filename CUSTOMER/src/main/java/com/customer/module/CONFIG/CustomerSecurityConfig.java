package com.customer.module.CONFIG;


import com.module.library.AUTH.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@EnableWebSecurity
@Configuration
public class CustomerSecurityConfig implements WebMvcConfigurer{

    CustomUserDetailsService customUserDetailsService;
    AuthenticationProvider authenticationProvider;
    JwtFilter jwtFilter;

    public CustomerSecurityConfig(CustomUserDetailsService customUserDetailsService, AuthenticationProvider authenticationProvider, JwtFilter jwtFilter) {
        this.customUserDetailsService = customUserDetailsService;
        this.authenticationProvider = authenticationProvider;
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {

        security.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        security.csrf(AbstractHttpConfigurer::disable);
        security.anonymous(AbstractHttpConfigurer::disable);

        security.authenticationProvider(authenticationProvider);

        security.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        security.authorizeHttpRequests(http -> {
            http.requestMatchers("/login/**", "/signup/**").permitAll()
                .anyRequest().permitAll();
        });

        return security.build();
    }


    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Apply CORS to all endpoints
                .allowedOrigins("http://localhost:4200") // Allow requests from Angular app
                .allowedMethods("GET", "POST", "PUT", "DELETE") // Allow specific HTTP methods
                .allowedHeaders("*") //understand
                .exposedHeaders("*") //understand
                .maxAge(3600); // Set preflight request cache time
    }

}
