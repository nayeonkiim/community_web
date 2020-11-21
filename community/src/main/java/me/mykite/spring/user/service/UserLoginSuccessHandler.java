package me.mykite.spring.user.service;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Service;

@Service
public class UserLoginSuccessHandler implements AuthenticationSuccessHandler{

	//인증이 된 경우 처리 위한 클래스
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
			//Security가 요청을 가로챈 경우 사용자가 원래 요청했던 URI 정보를 저장한 객체
			RequestCache requestCache = new HttpSessionRequestCache();
			SavedRequest savedRequest = requestCache.getRequest(request, response);
			
			//로그인 페이지로 요청
			String prevPage = (String)request.getSession().getAttribute("prevPage");
			
			String uri = "/";
			if(prevPage != null)
				request.getSession().removeAttribute("prevPage");
	
			//인터셉터 된 경우
			if(savedRequest != null) {
				 uri = savedRequest.getRedirectUrl();
				System.out.println(uri);
				
				requestCache.removeRequest(request, response);
			}else if(prevPage != null && !prevPage.equals("")) {
				uri = prevPage;
			}
			
			Enumeration<String> list = request.getSession().getAttributeNames();
			while(list.hasMoreElements()) {
				System.out.println(list.nextElement());
			}
			response.sendRedirect(uri);
	}
}
