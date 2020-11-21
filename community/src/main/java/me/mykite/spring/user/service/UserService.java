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
	
	//���̵� �ߺ� üũ
	public boolean userIdDup(String userId) {
		UserVO exist = usermapper.selectOneInfo(userId);
		
		if(exist == null) {
			return true;
		}else {
			System.out.println("exist:"+exist.getUserId());
			return false;
		}
	}
	
	//userId �ߺ�üũ �� ������ �־�
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
	
	//��ü ��� ���� �ҷ���
	public List<UserVO> selectAllUser(){
		return usermapper.selectAllInfo();
	}
	
	//�� ��� ���� ����
	public UserVO selectOneUser(String userId) {
		return usermapper.selectOneInfo(userId);
	}
}
