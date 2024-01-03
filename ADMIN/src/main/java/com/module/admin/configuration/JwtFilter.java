package com.module.admin.configuration;

import com.module.library.SERVICES.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
public class JwtFilter extends OncePerRequestFilter {

    JwtService jwtService;

    public JwtFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwtToken = parseJwt(request);

        System.out.println("Recieved jwt : "+ jwtToken);

        if(jwtToken!=null && jwtService.validateJwt(jwtToken)){
            try{
                String [] claimedData = jwtService.getCredentialsFromJwt(jwtToken);

                System.out.println(Arrays.toString(claimedData));

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        claimedData[0], null, List.of(new SimpleGrantedAuthority(claimedData[1]))
                );

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);

            } catch (Exception e){
                e.printStackTrace();
                throw new BadCredentialsException("Invalid Jwt Token");
            }}
        filterChain.doFilter(request, response);
    }

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");
        System.out.println("Header Auth : " + headerAuth);
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }
        return null;
    }
}
