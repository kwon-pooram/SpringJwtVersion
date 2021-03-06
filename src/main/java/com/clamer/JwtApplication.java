package com.clamer;

import com.clamer.domain.entity.Authority;
import com.clamer.domain.entity.AuthorityType;
import com.clamer.domain.entity.User;
import com.clamer.domain.repository.AuthorityRepository;
import com.clamer.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootApplication
public class JwtApplication implements CommandLineRunner {

	private final UserRepository userRepository;
	private final AuthorityRepository authorityRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	public JwtApplication(UserRepository userRepository, AuthorityRepository authorityRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.userRepository = userRepository;
		this.authorityRepository = authorityRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	public static void main(String[] args) {
		SpringApplication.run(JwtApplication.class, args);
	}


//	샘플 데이터
	@Override
	public void run(String... args) throws Exception {

//		ADMIN Authority
		Authority adminAuthority = new Authority();
		adminAuthority.setAuthorityType(AuthorityType.ROLE_ADMIN);

//		USER Authority
		Authority userAuthority = new Authority();
		userAuthority.setAuthorityType(AuthorityType.ROLE_USER);

//		API_ALLOWED Authority
		Authority apiAllowedAuthority = new Authority();
		apiAllowedAuthority.setAuthorityType(AuthorityType.ROLE_API_ALLOWED);


//		Save Authorities
		authorityRepository.save(adminAuthority);
		authorityRepository.save(userAuthority);
		authorityRepository.save(apiAllowedAuthority);

//		user1
		List<Authority> authorityListForUser1 = new ArrayList<>();
		authorityListForUser1.add(userAuthority);
		authorityListForUser1.add(adminAuthority);

		User user1 = new User();
		user1.setEmail("user1@test.com");
		user1.setUsername("user1");
		user1.setPassword(bCryptPasswordEncoder.encode("user1"));

		user1.setEnabled(true);
		user1.setCredentialsNonExpired(true);
		user1.setAccountNonExpired(true);
		user1.setAccountNonLocked(true);

		user1.setPasswordUpdatedAt(new Date());
		user1.setAuthorities(authorityListForUser1);

		userRepository.save(user1);


//		user2
		List<Authority> authorityListForUser2 = new ArrayList<>();
		authorityListForUser2.add(userAuthority);

		User user2 = new User();
		user2.setEmail("user2@test.com");
		user2.setUsername("user2");
		user2.setPassword(bCryptPasswordEncoder.encode("user2"));

		user2.setEnabled(true);
		user2.setCredentialsNonExpired(true);
		user2.setAccountNonExpired(true);
		user2.setAccountNonLocked(true);

		user2.setPasswordUpdatedAt(new Date());
		user2.setAuthorities(authorityListForUser2);

		userRepository.save(user2);


//		user 3
		List<Authority> authorityListForUser3 = new ArrayList<>();
		authorityListForUser3.add(apiAllowedAuthority);
		User user3 = new User();
		user3.setEmail("user3@test.com");
		user3.setUsername("user3");
		user3.setPassword(bCryptPasswordEncoder.encode("user3"));

		user3.setEnabled(true);
		user3.setCredentialsNonExpired(true);
		user3.setAccountNonExpired(true);
		user3.setAccountNonLocked(true);

		user3.setPasswordUpdatedAt(new Date());
		user3.setAuthorities(authorityListForUser3);

		userRepository.save(user3);

	}
}
