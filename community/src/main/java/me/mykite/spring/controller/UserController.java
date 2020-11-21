package me.mykite.spring.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import me.mykite.spring.user.service.UserService;
import me.mykite.spring.userVO.UserVO;

@Controller
public class UserController { 
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("/")
	public String toMain() {
		return "/main";
	}
	
	/*�ܼ� �α��� ȭ�� ��û*/
	@RequestMapping("/loginView")
	public String tologinPage(HttpServletRequest request) {
		String uri = request.getHeader("Referer");
		System.out.println(request.getAttribute("loginFailMsg"));
		//�� ó�� �α��� �������� ���� �õ��� �������� ��� ������ �ΰ� 
		//�α��� ���нÿ��� �� ������ ������ ����Ǿ� �ֱ� ���� session�� ����
		if(uri != null && !uri.contains("/loginView")) {
			request.getSession().setAttribute("prevPage", uri);
		}
		return "/user/login";
	}
	
	/*�ܼ� ȸ������ ȭ�� ��û*/
	@RequestMapping("/registerUser")
	public String toRegistPage() {
		return "/user/register";
	}
	
	//ajax �̿� - ���̵� �ߺ� üũ
	@RequestMapping(value="/user/IdDuplicate", method=RequestMethod.GET)
	@ResponseBody
	public String IdDuplicated(String userId, Model model) {
		System.out.println("userId: "+ userId);
		boolean result = false;
		if(!userId.equals("userId=")) {
			System.out.println(userId);
			result = userService.userIdDup(userId);
		}
		System.out.println(result);
		
		if(result == true) {
			return "canUse";
		}else {
			return "no";
		}
	}
	
	/* ȸ������ ��û */
	@RequestMapping("/registerAsk")
	public String askForRegist(UserVO user, Model model) {
		boolean result = userService.addUser(user);
		
		//ȸ�� ���� ������ �α��� ȭ������
		if(result) {
			return "/user/login";
		//���н� ȸ������ ȭ������
		}else {
			return "/user/register";
		}
	}
	
	/* ������ ������ ��û */
	@RequestMapping("/admin/adminUserInfoView")
	public String toAdminsPage(Authentication auth, Model model) {
		System.out.println(auth.getName());
		List<UserVO> user = userService.selectAllUser();
		model.addAttribute("userMem", user);
		return "/admin/adminUserInfoView";
	}
	
	/* mypage ��û */
	@RequestMapping("/user/myPage")
	public String toMyPage(Authentication auth, Model model) {
		System.out.println(auth.getName());
		String userId = auth.getName();
		UserVO user = userService.selectOneUser(userId);
		model.addAttribute("userInfo", user);
		return "/user/myPage";
	}
}
