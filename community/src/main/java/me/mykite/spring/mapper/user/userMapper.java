package me.mykite.spring.mapper.user;

import java.util.List;

import me.mykite.spring.config.annotation.Mapper;
import me.mykite.spring.userVO.UserVO;

@Mapper
public interface userMapper {

	//하나의 UserVO 객체 조회
	public UserVO selectOneInfo(String userId);
	//전체 회원 조회
	public List<UserVO> selectAllInfo();
	//회원 정보 등록
	public void insertOneInfo(UserVO user);
	//권한 설정
	public void insertAuthInfo(String userId);
	//권한 select
	public List<String> selectUserAuthOne(String userId);
	
}
