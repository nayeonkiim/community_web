<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="s" %>


<!doctype html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="Mark Otto, Jacob Thornton, and Bootstrap contributors">
    <meta name="generator" content="Jekyll v4.1.1">
    
    <meta id="_csrf" name="_csrf" content="${_csrf.token}" />
    <meta id="_csrf_header" name="_csrf_header" content="${_csrf.headerName}" />
    
    <title>블로그 꾸미기</title>

    <link rel="canonical" href="https://getbootstrap.com/docs/4.5/examples/jumbotron/">

    <!-- Bootstrap core CSS -->
<link href="/resources/dist/css/bootstrap.min.css?ver=<%=System.currentTimeMillis()%>" rel="stylesheet" integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2" crossorigin="anonymous">

    <!-- Favicons -->
<meta name="msapplication-config" content="/resources/assets/img/favicons/browserconfig.xml">
<meta name="theme-color" content="#563d7c">


    <style>
      .bd-placeholder-img {
        font-size: 1.125rem;
        text-anchor: middle;
        -webkit-user-select: none;
        -moz-user-select: none;
        -ms-user-select: none;
        user-select: none;
      }
      .hidden {
	  display: none; }

      @media (min-width: 768px) {
        .bd-placeholder-img-lg {
          font-size: 3.5rem;
        }
      }
    </style>
    <!-- Custom styles for this template -->
    <link href="/resources/examples/jumbotron.css" rel="stylesheet">
    <script src="/resources/assets/js/cs.js"></script>
    <script src="//code.jquery.com/jquery-1.11.0.min.js"></script>
    
    <script type="text/javascript">
		function btnClick(formName) {
			formName.submit();
		};
	</script>
    
  </head>
  <body>
  
  	<!-- 인증 사용자 정보 -->
  	<s:authentication property="principal" var="user" />
  	
    <nav class="navbar navbar-expand-md navbar-dark fixed-top bg-dark">
	  <a class="navbar-brand" href="#"></a>
	  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarsExampleDefault" aria-controls="navbarsExampleDefault" aria-expanded="false" aria-label="Toggle navigation">
	    <span class="navbar-toggler-icon"></span>
	  </button>

	  <div class="collapse navbar-collapse" id="navbarsExampleDefault">
	    <ul class="navbar-nav mr-auto ml-5">
	      <li class="nav-item active">
	        <a class="nav-link" href="/">Home</a>
	      </li>
	      <li class="nav-item active">
	      	<a class="nav-link" href="/board/board_Main/1">Main Board</a>
	      </li>
	      <li class="nav-item active">
	        <a class="nav-link" href="/board/Greetings/1">Greetings</a>
	      </li>
	      <li class="nav-item active">
	      	<a class="nav-link" href="/board/Notice/1">Notice</a>
	      </li>
	    </ul>
	  </div>
    
	  <div align="right">
	    <ul class="navbar-nav">
	    	<s:authorize access="isAnonymous()">
	     		<li><a href="/loginView" class="btn btn-outline-success my-2 my-sm-0 mr-3" type="submit">login</a></li>
	      		<li><a href="/registerUser" class="btn btn-outline-success my-2 my-sm-0 mr-5" type="submit">Join Us</a></li>
	      	</s:authorize>
	      	
	      	<s:authorize access="isAuthenticated()">
	      		<li><a href="#" class="btn btn-outline-success my-2 my-sm-0 mr-3" onclick="javascript:btnClick(logoutAskOne);">logout</a></li>
	      		<li><a href="/user/myPage" class="btn btn-outline-success my-2 my-sm-0 mr-3" type="submit">myPage</a></li>
	      		<s:authorize access="hasRole('ROLE_ADMIN')">
	      			<li><a href="/admin/adminUserInfoView" class="btn btn-outline-success my-2 my-sm-0 mr-3">관리자페이지</a></li>
	      		</s:authorize>
	      	</s:authorize>
	 	</ul>
	  </div>
	</nav>
	
	<s:authorize access="isAuthenticated()">
		<!--현재 기기 로그아웃 -->
		<form name="logoutAskOne" action="/logoutAsk" method="post">
			<s:csrfInput/>
		</form>
	</s:authorize>
