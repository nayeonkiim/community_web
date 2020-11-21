package me.mykite.spring.mapper.user;

import java.util.List;

import me.mykite.spring.config.annotation.Mapper;
import me.mykite.spring.userVO.UserVO;

@Mapper
public interface userMapper {

	//�ϳ��� UserVO ��ü ��ȸ
	public UserVO selectOneInfo(String userId);
	//��ü ȸ�� ��ȸ
	public List<UserVO> selectAllInfo();
	//ȸ�� ���� ���
	public void insertOneInfo(UserVO user);
	//���� ����
	public void insertAuthInfo(String userId);
	//���� select
	public List<String> selectUserAuthOne(String userId);
	
}
