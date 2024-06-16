package com.ageurdo.demo_user_auth_api.jwt;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUserDetailsService detailsService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String token = request.getHeader(JwtUtils.JWT_AUTHORITIES);
//        if (token == null || token.startsWith(JwtUtils.JWT_BEARER)){
//            log.info("Jwt Token está nulo, vazio ou não iniciado 'Bearer'.");
//            filterChain.doFilter(request, response);
//            return;
//        }
//
        if (token == null){
            log.info("Jwt Token está nulo.");
            filterChain.doFilter(request, response);
            return;
        }
        if (!JwtUtils.isTokenValid(token)){
            log.warn("Jwt Token esta inválido ou expirado.");
            filterChain.doFilter(request, response);
            return;
        }
        log.info("Jwt Token: {}", token);
        String cpf = JwtUtils.getCpfFromToken(token);
        toAuthentication(request, cpf);

        filterChain.doFilter(request, response);
    }

    private void toAuthentication(HttpServletRequest request, String cpf) {
        UserDetails userDetails = detailsService.loadUserByUsername(cpf);

        UsernamePasswordAuthenticationToken authenticationToken = UsernamePasswordAuthenticationToken
                .authenticated(userDetails, null, userDetails.getAuthorities());

        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }
}
