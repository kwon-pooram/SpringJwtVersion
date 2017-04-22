package com.clamer.service;

import com.clamer.domain.entity.User;
import com.clamer.domain.repository.UserRepository;
import com.clamer.utility.exception.GeneralException;
import com.clamer.utility.jwt.JwtUserFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


/**
 * Created by stephan on 20.03.16.
 */
@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {

    /**********************************************************************
     *
     * JWT 기반 인증 시 호출되는 서비스
     *
     * Spring Security 의 UserDetailsService 로 인증 과정을 수행하기 때문에
     * loadUserByUsername 메소드 @Override
     *
     **********************************************************************/

    private final UserRepository userRepository;

    @Autowired
    public JwtUserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 데이터베이스에서 사용자 이름으로 사용자 검색
        User user = userRepository.findByUsername(username);

        // 사용자 정보 없음
        if (user == null) {
            throw new GeneralException("입력하신 정보와 일치하는 사용자를 찾을 수 없습니다.");
        }

        // 이메일 미인증 사용자
        else if (!user.isEnabled()) {
            throw new GeneralException("이메일 인증 후 로그인 가능합니다");
        }

        // 정상적으로 인증 완료, JWT 유저 객체 생성 및 반환
        else {
            return JwtUserFactory.create(user);
        }
    }
}
