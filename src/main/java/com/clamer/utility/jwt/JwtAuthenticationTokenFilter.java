package com.clamer.utility.jwt;

import com.clamer.service.JwtService;
import com.clamer.service.JwtUserDetailsServiceImpl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    // 인증 필터
    // 클라이언트에서 서버 호출시 항상 필터를 거쳐서 인증 과정 수행

    private final Log logger = LogFactory.getLog(this.getClass());

    private final JwtUserDetailsServiceImpl jwtUserDetailsService;
    private final JwtService jwtService;

    @Autowired
    public JwtAuthenticationTokenFilter(JwtUserDetailsServiceImpl jwtUserDetailsService, JwtService jwtService) {
        this.jwtUserDetailsService = jwtUserDetailsService;
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String tokenHeader = "Authorization";
        String token = request.getHeader(tokenHeader);
        String username = jwtService.getUsernameFromToken(token);

        logger.info("[" + username + "] 사용자 인증 처리중");

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(username);

            if (jwtService.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
                logger.info("[" + username + "] 사용자 인증 완료");
            }
        }
        chain.doFilter(request, response);
    }
}