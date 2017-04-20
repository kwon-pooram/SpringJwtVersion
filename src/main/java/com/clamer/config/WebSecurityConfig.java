package com.clamer.config;

import com.clamer.service.JwtUserDetailsServiceImpl;
import com.clamer.utility.jwt.JwtAuthenticationEntryPoint;
import com.clamer.utility.jwt.JwtAuthenticationTokenFilter;
import com.clamer.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    private final JwtAuthenticationEntryPoint unauthorizedUserHandler;
    private final JwtUserDetailsServiceImpl jwtUserDetailsService;
    private final JwtService jwtService;

    @Bean  // 패스워드 인코더
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean  // JWT 필터
    public JwtAuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {
        return new JwtAuthenticationTokenFilter(jwtUserDetailsService, jwtService);
    }

    @Autowired
    public WebSecurityConfig(JwtAuthenticationEntryPoint unauthorizedUserHandler, JwtUserDetailsServiceImpl jwtUserDetailsService, JwtService jwtService) {
        this.unauthorizedUserHandler = unauthorizedUserHandler;
        this.jwtUserDetailsService = jwtUserDetailsService;
        this.jwtService = jwtService;
    }

    @Autowired
    public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
//                유저 디테일 서비스 등록
                .userDetailsService(this.jwtUserDetailsService)
//                패스워드 인코더 등록
                .passwordEncoder(bCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
//                CSRF 비활성화
//                토큰 인증 방식 사용시 불필요함
                .csrf()
                .disable()

//                미인증 사용자 처리 핸들러 등록
                .exceptionHandling()
                .authenticationEntryPoint(unauthorizedUserHandler)
                .and()

//                세션 생성 안함
//                STATELESS : 세션 생성하지도, 사용하지도 않음
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()

                .authorizeRequests()


                /**
                 * 리소스 URI
                 * */
                .antMatchers(
                        HttpMethod.GET,
                        "/favicon.ico",
                        "/js/**",
                        "/css/**"
                ).permitAll()

                /**
                 * 인증 URI
                 * */
                .antMatchers("/auth/**").permitAll()

                /**
                 * 인증 불필요한 URI
                 * */
                .antMatchers("/public/**").permitAll()

                /**
                 * 어드민 계정 전용 URI
                 * */
                .antMatchers("/admin/**").hasRole("ADMIN")

                /**
                 * 뷰 URI
                 * */
                .antMatchers(HttpMethod.GET,"/", "/index").permitAll()
                .antMatchers(HttpMethod.GET,"/login").permitAll()



                .anyRequest()
                .authenticated();

//         JWT 기반 인증 필터 등록
        httpSecurity
                .addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);

//        페이지 캐싱 비활성화
        httpSecurity.headers().cacheControl().disable();
    }
}