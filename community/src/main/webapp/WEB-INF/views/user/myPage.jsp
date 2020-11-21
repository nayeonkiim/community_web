<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@include file="/WEB-INF/views/layout/top.jsp" %>
<body>
	<div class="container">
		<div class="row">
			<div class="col-lg">
				<div class="jumbotron jumbotron-fluid">
				  <div class="container">
				    <h1 class="display-4">MyPage</h1>
				  </div>
				</div>
			</div>
		</div>
		<div>
			<table class="table">
			  <tbody>
			    <tr>
			      <th scope="row">아이디</th>
			      <td>${userInfo.userId}</td>
			    </tr>
			    <tr>
			      <th scope="row">이름</th>
			      <td>${userInfo.userName}</td>
			    </tr>
			    <tr>
			      <th scope="row">전화번호</th>
			      <td>${userInfo.phoneNum}</td>
			    </tr>
			    <tr>
			      <th scope="row">이메일</th>
			      <td>${userInfo.email}</td>
			    </tr>
			    <tr>
			      <th scope="row">가입 날짜</th>
			      <td>
			      	<fmt:formatDate value="${userInfo.initDate}" pattern="yyyy-MM-dd"/>
			      </td>
			    </tr>
			  </tbody>
			</table>
		</div>
	</div>	
</body>
</html>