<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ include file="/WEB-INF/views/layout/top.jsp" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="s" %>

<%@ include file="/WEB-INF/views/layout/top.jsp" %>
<body>
	<div id="modifyFail" class="alert alert-danger hidden" role="alert">글이 수정이 실패하였습니다. 다시 시도해주세요.</div>
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
				<c:set var="b" value="${board}"/> 
				<form action="/${boardName}/askModify/${bNum}" method="POST">
				  <div class="form-group">
				    <label for="bTitle">Title</label>
				    <input type="text" class="form-control" id="bTitle" name="bTitle" value="${b.bTitle}">
				  </div>
				  <div class="form-group">
				    <label for="exampleFormControlInput1">아이디</label>
				    <input type="text" class="form-control" id="bId" name="bId" value="${b.bId}" readOnly>
				  </div>
				  <div class="form-group">
				    <label for="exampleFormControlTextarea1">textarea</label>
				    <textarea class="form-control" id="exampleFormControlTextarea1" name="bContent" rows="3">${b.bContent}</textarea>
				  </div>
				  <s:csrfInput />
				  <input class="btn btn-primary" type="submit" value="수정완료">
				</form>
			</div>
		</div>
	</div>
	<script>
		var result = '${result}';
		$(function(){
			if(result === 'modifyFail'){
				$('#modifyFail').removeClass('hidden');
				$('#modifyFail').fadeOut(4000);
			}
		})
	</script>

</body>