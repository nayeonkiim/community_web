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
	
	/*단순 로그인 화면 요청*/
	@RequestMapping("/loginView")
	public String tologinPage(HttpServletRequest request) {
		String uri = request.getHeader("Referer");
		System.out.println(request.getAttribute("loginFailMsg"));
		//맨 처음 로그인 페이지로 접속 시도한 페이지를 계속 저장해 두고 
		//로그인 실패시에도 그 페이지 정보가 저장되어 있기 위해 session에 저장
		if(uri != null && !uri.contains("/loginView")) {
			request.getSession().setAttribute("prevPage", uri);
		}
		return "/user/login";
	}
	
	/*단순 회원가입 화면 요청*/
	@RequestMapping("/registerUser")
	public String toRegistPage() {
		return "/user/register";
	}
	
	//ajax 이용 - 아이디 중복 체크
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
	
	/* 회원가입 요청 */
	@RequestMapping("/registerAsk")
	public String askForRegist(UserVO user, Model model) {
		boolean result = userService.addUser(user);
		
		//회원 가입 성공시 로그인 화면으로
		if(result) {
			return "/user/login";
		//실패시 회원가입 화면으로
		}else {
			return "/user/register";
		}
	}
	
	/* 관리자 페이지 요청 */
	@RequestMapping("/admin/adminUserInfoView")
	public String toAdminsPage(Authentication auth, Model model) {
		System.out.println(auth.getName());
		List<UserVO> user = userService.selectAllUser();
		model.addAttribute("userMem", user);
		return "/admin/adminUserInfoView";
	}
	
	/* mypage 요청 */
	@RequestMapping("/user/myPage")
	public String toMyPage(Authentication auth, Model model) {
		System.out.println(auth.getName());
		String userId = auth.getName();
		UserVO user = userService.selectOneUser(userId);
		model.addAttribute("userInfo", user);
		return "/user/myPage";
	}
}
