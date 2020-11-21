package me.mykite.spring.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import me.mykite.spring.mapper.user.userMapper;
import me.mykite.spring.userVO.UserDetailsVO;
import me.mykite.spring.userVO.UserVO;

//�ش� ��ü�� db���� ������ userDetailsVO ��ü�� ����
@Service
public class UserDetailsServiceCustom implements UserDetailsService{
	
	@Autowired
	private userMapper mapper;
	
	@Override
	public UserDetails loadUserByUsername(String inputUserId) throws UsernameNotFoundException {
		
		UserDetailsVO userDetails = new UserDetailsVO();
		
		UserVO userVo = mapper.selectOneInfo(inputUserId);
		
		if(userVo == null) {
			return null;
		}else {
			userDetails.setUsername(userVo.getUserId());
			userDetails.setPassword(userVo.getUserPw());
			
			userDetails.setAuthorities(mapper.selectUserAuthOne(inputUserId));
		}
		return userDetails;
	}
}
