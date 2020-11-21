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
	
	//���� ������ �����ϴ� ���� - ���� ���� �� ���ο� Authentication ��ü�� ����� ���� ������ ���� ���� �־� ����
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		
		//�α��� ���� ���� ���̵�� ��й�ȣ
		String userId = authentication.getName();
		String userPw = (String)authentication.getCredentials();
		
		UserDetailsVO userDetails = (UserDetailsVO) userDetailsService
				.loadUserByUsername(userId);
		
		//BCryptPasswordEncoder�� ��й�ȣ ��ȣȭ �����Ƿ� matches ����ؾ���
		//��ġ�ϴ� ��ü�� ���ų� ���̵�,��й�ȣ�� ��ġ���� �ʴ� ���
		if(userDetails == null || !userId.equals(userDetails.getUsername())
				|| !pwEncoding.matches(userPw, userDetails.getPassword())) {
			throw new BadCredentialsException(userId);
		//��� ������ ���
		}else if(!userDetails.isAccountNonLocked()) {
			throw new LockedException(userId);
		
		//��Ȱ��ȭ�� ������ ���
		}else if(!userDetails.isEnabled()) {
			throw new DisabledException(userId);
			
		//����� ������ ���
		}else if(!userDetails.isAccountNonExpired()) {
			throw new AccountExpiredException(userId);
		// ��й�ȣ ����� ���
		}else if(!userDetails.isCredentialsNonExpired()) {
			throw new CredentialsExpiredException(userId);
		}
		
		userDetails.setPassword(null);
		
		Authentication newAuth = new UsernamePasswordAuthenticationToken(
				userDetails, null, userDetails.getAuthorities());
		return newAuth;
	}

	@Override
	//���� authenticate �޼ҵ忡�� ��ȯ�� ��ü�� ��ȿ�� Ÿ���� �´��� �˻�
	//null ���̰ų� �߸��� Ÿ���� ��ȯ���� ��� ���� ���з� ���� 
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
