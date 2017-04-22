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

    /**********************************************************************
     *
     * JWT 인증 필터
     * @see com.clamer.config.WebSecurityConfig 클래스에서 필터 등록
     *
     * 클라이언트와 서버 간의 모든 통신은 항상 이 필터를 거침.
     * 리퀘스트 객체 헤더에 담긴 JWT 를 이용하여 인증 과정 수행.
     *
     **********************************************************************/

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

        // 리퀘스트 헤더에서 "Authorization" 이름으로 등록되어 있는 JWT 가져오기
        String tokenHeader = "Authorization";
        String token = request.getHeader(tokenHeader);

        // 가져온 JWT 에서 사용자 이름 추출
        String username = jwtService.getUsernameFromToken(token);

        logger.info("[" + username + "] 사용자 인증 처리중");

        // 사용자 이름이 NULL이 아니고
        if (username != null
                // STATELESS 세션 적용되고 있는지 (세션에 저장된 정보가 있는지 없는지 확인)
                && SecurityContextHolder.getContext().getAuthentication() == null
                ) {

            // 토큰상의 정보와 DB 에 저장되어 있는 정보 비교를 위해서 데이터베이스에서 정보 가져옴
            UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(username);

            // 유효한 토큰인지 검증 과정 수행
            if (jwtService.validateToken(token, userDetails)) {

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
                logger.info("[" + username + "] 사용자 인증 완료");
            }
        } else {
            logger.info("[" + username + "] 사용자 인증 실패");
        }
        chain.doFilter(request, response);
    }
}