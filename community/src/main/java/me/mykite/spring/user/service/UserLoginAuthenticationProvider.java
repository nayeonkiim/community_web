package me.mykite.spring.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import me.mykite.spring.userVO.UserDetailsVO;

@Service
public class UserLoginAuthenticationProvider implements AuthenticationProvider{
	
	@Autowired
	UserDetailsService userDetailsService;
	
	@Autowired
	BCryptPasswordEncoder pwEncoding;
	
	//실제 인증을 구현하는 로직 - 인증 성공 시 새로운 Authentication 객체를 만들어 계정 정보와 권한 정보 넣어 리턴
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		
		//로그인 폼에 적은 아이디와 비밀번호
		String userId = authentication.getName();
		String userPw = (String)authentication.getCredentials();
		
		UserDetailsVO userDetails = (UserDetailsVO) userDetailsService
				.loadUserByUsername(userId);
		
		//BCryptPasswordEncoder로 비밀번호 암호화 했으므로 matches 사용해야함
		//일치하는 객체가 없거나 아이디,비밀번호가 일치하지 않는 경우
		if(userDetails == null || !userId.equals(userDetails.getUsername())
				|| !pwEncoding.matches(userPw, userDetails.getPassword())) {
			throw new BadCredentialsException(userId);
		//잠긴 계정인 경우
		}else if(!userDetails.isAccountNonLocked()) {
			throw new LockedException(userId);
		
		//비활성화된 계정인 경우
		}else if(!userDetails.isEnabled()) {
			throw new DisabledException(userId);
			
		//만료된 계정인 경우
		}else if(!userDetails.isAccountNonExpired()) {
			throw new AccountExpiredException(userId);
		// 비밀번호 만료된 경우
		}else if(!userDetails.isCredentialsNonExpired()) {
			throw new CredentialsExpiredException(userId);
		}
		
		userDetails.setPassword(null);
		
		Authentication newAuth = new UsernamePasswordAuthenticationToken(
				userDetails, null, userDetails.getAuthorities());
		return newAuth;
	}

	@Override
	//위의 authenticate 메소드에서 반환한 객체가 유효한 타입이 맞는지 검사
	//null 값이거나 잘못된 타입을 반환했을 경우 인증 실패로 간주 
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
