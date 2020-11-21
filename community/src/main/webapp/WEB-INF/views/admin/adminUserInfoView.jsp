<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@include file="/WEB-INF/views/layout/top.jsp" %>

<!-- 전체 사용자 정보 나오도록 -->
<body>
	<div class="container">
		<div class="row">
			<div class="col-lg">
				<div class="jumbotron jumbotron-fluid">
				  <div class="container">
				    <h1 class="display-4">Member Info</h1>
				  </div>
				</div>
			</div>
		</div>
		<div>
			<table class="table">
			  <thead>
			    <tr>
			      <th scope="col">아이디</th>
			      <th scope="col">이름</th>
			      <th scope="col">전화번호</th>
			      <th scope="col">email</th>
			      <th scope="col">가입날짜</th>
			    </tr>
			  </thead>
			  <tbody>
			  	<c:forEach var="i" items="${userMem}">
			    <tr>
			      <td>${i.userId}</td>
			      <td>${i.userName}</td>
			      <td>${i.phoneNum}</td>
			      <td>${i.email}</td>
			      <td>
			      	<fmt:formatDate value="${i.initDate}" pattern="yyyy-MM-dd"/>
			      </td>
			    </tr>
			    </c:forEach>
			  </tbody>
			</table>
		</div>
	</div>
</body>
</html>