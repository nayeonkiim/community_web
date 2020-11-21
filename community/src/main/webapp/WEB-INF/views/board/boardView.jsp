<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="s" %>

<%@include file="/WEB-INF/views/layout/top.jsp" %>

<div id="removeOK" class="alert alert-danger hidden" role="alert">글이 삭제 되었습니다.</div>
<div id="writeArticleOk" class="alert alert-danger hidden" role="alert">글이 등록 되었습니다.</div>
<div id="modifyOk" class="alert alert-danger hidden" role="alert">글이 수정 되었습니다.</div>
<div class="container">
	<div class="row">
		<div class="col-lg">
			<div class="jumbotron jumbotron-fluid">
			  <div class="container">
			    <h1 class="display-4">${boardName}</h1>
			  </div>
			</div>
		</div>
	</div>
	
	<div class="row mb-3">
		<div class="col">
			<a href="/board/${boardName}/0/0" type="button" class="btn btn-primary">글쓰기</a>
		</div>
		<div class="col">
		<form class="form-inline mt-2 mt-md-0 d-flex justify-content-end" action="/board/${boardName}/${nowPage}" name="searchForm">
	        <select name="type" class="mr-1">
	        	<option value="">--</option>
	        		<option value="bTitle">제목</option>
	        		<option value="bContent">내용</option>
	        		<option value="bId">작성자</option>
	        </select>
	        <s:csrfInput />
	        <input class="form-control mr-sm-2" type="text" placeholder="Search" name="searchValue" aria-label="Search">
	        <input type="submit" class="btn btn-outline-success my-2 my-sm-0" value="search">
	        
	      </form>
		</div>
	</div>
	
	<div class="row">
		<table class="table text-center">
		  <thead class="thead-light">
		    <tr>
		      <th scope="col">제목</th>
		      <th scope="col">아이디</th>
		      <th scope="col">날짜</th>
		      <th scope="col">조회수</th>
		    </tr>
		  </thead>
		  
		  <tbody>
		  <c:forEach var="i" items="${board.boardInfo}">
		    <tr>
		      <td><a href="/board/${boardName}/readArticle/${i.bNum}/${nowPage}">${i.bTitle}</a></td>
		      <td>${i.bId}</td>
		      <td>${i.bRegDate}</td>
		      <td>${i.bFrequency}</td>
		    </tr>
		  </c:forEach>
		  </tbody>
		</table>
	</div>
	
	<div class="row">
		<div class="col align-middle" aria-label="Page navigation example">
		  <ul class="pagination">
		  	<c:set var="NUM_FOR_BLOCK" value="${board.NUM_FOR_BLOCK}"/>
		  	<c:if test="${nowPage > NUM_FOR_BLOCK}">
			    <li class="page-item">
			      <a class="page-link" href="/board/${boardName}/${(nowPage-1)/NUM_FOR_BLOCK + 1}" aria-label="Previous">
			        <span aria-hidden="true">&laquo;</span>
			      </a>
			    </li>
		    </c:if>
		    
		    <c:forEach var="j" begin="${block[0]}" end="${block[1]}">
		    	<li class="page-item">
		    		<div>
					    <form id="searchForm" method="post" action="/board/${boardName}/${j}">
						    <input type="hidden" name="type" value="${type}">
						    <input type="hidden" name="searchValue" value="${searchValue}">
						    <s:csrfInput />
						    <button type="submit" class="page-link" id="buttonClick">${j}</button>
						</form>
					</div>
			    </li>
		    </c:forEach>
		    <c:if test="${block[1] < block[2]}">
		    <li class="page-item">
		      <a class="page-link" href="/board/${boardName}/${block[1]+1}" aria-label="Next">
		        <span aria-hidden="true">&raquo;</span>
		      </a>
		    </li>
		    </c:if>
		  </ul>
		</div>
	</div>
</div>

<script>
	var result = '${result}';
	$(function(){
		if(result === 'removeOK'){
			$('#removeOK').removeClass('hidden');
			$('#removeOK').fadeOut(4000);
		}
		if(result === 'writeArticleOk'){
			$('#writeArticleOk').removeClass('hidden');
			$('#writeArticleOk').fadeOut(4000);
		}
		if(result === 'modifyOk'){
			$('#modifyOk').removeClass('hidden');
			$('#modifyOk').fadeOut(4000);
		}
	})
</script>
