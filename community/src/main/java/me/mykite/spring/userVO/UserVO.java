package me.mykite.spring.userVO;

import java.util.Date;

public class UserVO {
	
	private String userId;
	private String userName;
	private String userPw;
	private String phoneNum;
	private String email;
	private Date initDate;
	
	public UserVO(UserVO user) {
		this.userName = user.userName;
		this.userId = user.userId;
		this.userPw = user.userPw;
		this.phoneNum = user.phoneNum;
		this.email = user.email;
		this.initDate = user.initDate;
	}
	
	public UserVO() {
		
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserPw() {
		return userPw;
	}
	public void setUserPw(String userPw) {
		this.userPw = userPw;
	}
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getInitDate() {
		return initDate;
	}
	public void setInitDate(Date initDate) {
		this.initDate = initDate;
	}
	
}