package me.mykite.spring.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import me.mykite.spring.mapper.user.userMapper;
import me.mykite.spring.userVO.UserVO;

@Service
public class UserService {
	
	@Autowired
	private userMapper usermapper;
	
	@Autowired
	private BCryptPasswordEncoder crypt;
	
	//아이디 중복 체크
	public boolean userIdDup(String userId) {
		UserVO exist = usermapper.selectOneInfo(userId);
		
		if(exist == null) {
			return true;
		}else {
			System.out.println("exist:"+exist.getUserId());
			return false;
		}
	}
	
	//userId 중복체크 후 없으면 넣어
	public boolean addUser(UserVO user) {
		UserVO exist = usermapper.selectOneInfo(user.getUserId());
		
		if(exist != null) {
			return false;
		}else {
			UserVO uservo = new UserVO(user);
			
			uservo.setUserPw(crypt.encode(user.getUserPw()));
			usermapper.insertOneInfo(uservo);
			usermapper.insertAuthInfo(uservo.getUserId());
			return true;
		}
	}
	
	//전체 멤버 정보 불러와
	public List<UserVO> selectAllUser(){
		return usermapper.selectAllInfo();
	}
	
	//한 멤버 정보 리턴
	public UserVO selectOneUser(String userId) {
		return usermapper.selectOneInfo(userId);
	}
}
