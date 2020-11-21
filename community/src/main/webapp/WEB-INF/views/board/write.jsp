<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="s" %>

<%@include file="/WEB-INF/views/layout/top.jsp" %>

<title>글쓰기</title>

<body>
	<div class="container">
		<div class="row">
			<div class="col-lg">
				<div class="jumbotron jumbotron-fluid">
				  <div class="container">
				    <h1 class="display-4">Write</h1>
				    <p><c:out value="${boardName}" /></p>
				  </div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col">
				<form action="/board/${boardName}/${bNum}/askWriteArticle" method="POST">
				  <div class="form-group">
				    <label for="bTitle">Title</label>
				    <input type="text" class="form-control" id="bTitle" name="bTitle">
				  </div>
				  <div class="form-group">
				    <label for="exampleFormControlInput1">아이디</label>
				    <input type="text" class="form-control" id="bId" name="bId" value="${user.username}" readOnly>
				  </div>
				  <div class="form-group">
				    <label for="exampleFormControlTextarea1">textarea</label>
				    <textarea class="form-control" id="exampleFormControlTextarea1" name="bContent" rows="3"></textarea>
				  </div>
				  
				  <s:csrfInput />
				  
				  <input class="btn btn-primary btn-md btn-block" type="submit" value="글 등록하기"></input>
				</form>
			</div>
		</div>
	</div>
</body>
