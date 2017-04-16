package com.clamer.config;

import com.clamer.utility.jwt.JwtAuthenticationEntryPoint;
import com.clamer.utility.jwt.JwtAuthenticationTokenFilter;
import com.clamer.utility.jwt.JwtTokenUtility;
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
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@SuppressWarnings("SpringJavaAutowiringInspection")
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    private final JwtAuthenticationEntryPoint unauthorizedUserHandler;
    private final UserDetailsService userDetailsService;
    private final JwtTokenUtility jwtTokenUtility;

    @Bean  // 패스워드 인코더
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean  // JWT 필터
    public JwtAuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {
        return new JwtAuthenticationTokenFilter(userDetailsService, jwtTokenUtility);
    }

    @Autowired
    public WebSecurityConfig(JwtAuthenticationEntryPoint unauthorizedUserHandler, UserDetailsService userDetailsService, JwtTokenUtility jwtTokenUtility) {
        this.unauthorizedUserHandler = unauthorizedUserHandler;
        this.userDetailsService = userDetailsService;
        this.jwtTokenUtility = jwtTokenUtility;
    }

    @Autowired
    public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .userDetailsService(this.userDetailsService)
                .passwordEncoder(bCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
//                CSRF 비활성화
                .csrf()
                .disable()

//                미인증 사용자 처리
                .exceptionHandling()
                .authenticationEntryPoint(unauthorizedUserHandler)
                .and()

//                세션 생성 안함
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()

                .authorizeRequests()

//                인증 불필요한 리소스 허용 처리
                .antMatchers(
                        HttpMethod.GET,
                        "/",
                        "/*.html",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js"
                ).permitAll()

                .antMatchers("/auth/**")
                .permitAll()

                .anyRequest()
                .authenticated();

//         JWT 기반 인증 필터 등록
        httpSecurity
                .addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);

//        페이지 캐싱 비활성화
        httpSecurity.headers().cacheControl().disable();
    }
}