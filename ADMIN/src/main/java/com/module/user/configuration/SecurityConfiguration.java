package com.module.user.configuration;

import com.module.library.AUTH.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration implements WebMvcConfigurer {


    UserDetailsServiceImpl userDetailsServiceImpl;
    AuthenticationProvider authenticationProvider;

    JwtDecoder jwtDecoder;

    public SecurityConfiguration(UserDetailsServiceImpl userDetailsServiceImpl,
                                 AuthenticationProvider authenticationProvider,
                                 JwtDecoder jwtDecoder) {
        this.userDetailsServiceImpl = userDetailsServiceImpl;
        this.authenticationProvider = authenticationProvider;
        this.jwtDecoder = jwtDecoder;
    }



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {

        security.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                                .httpBasic(Customizer.withDefaults());
        security.csrf(AbstractHttpConfigurer::disable);

        security.authenticationProvider(authenticationProvider);

        security.authorizeHttpRequests(http -> http.requestMatchers("/login", "/add_user", "/logout-success").permitAll()
                    .requestMatchers("/admin/**").hasAuthority("SCOPE_ADMIN")
                    .anyRequest().authenticated());

        security.logout(logout -> logout.logoutSuccessUrl("/logout-success"));

        security.oauth2ResourceServer(http -> http.jwt(jwtConfigurer -> jwtConfigurer.decoder(jwtDecoder)));
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
