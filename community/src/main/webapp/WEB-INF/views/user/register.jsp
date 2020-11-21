<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="s" %>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
    
<%@ include file="/WEB-INF/views/layout/top.jsp" %>
    <title>Checkout example · Bootstrap</title>

    <link rel="canonical" href="https://getbootstrap.com/docs/4.5/examples/checkout/">

    <!-- Bootstrap core CSS -->
<link href="/resources/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2" crossorigin="anonymous">

<meta name="msapplication-config" content="/resources/assets/img/favicons/browserconfig.xml">
<meta name="theme-color" content="#563d7c">
<c:set var="request" value="javax.servlet.http.HttpServletRequest"/>

    <style>
      .bd-placeholder-img {
        font-size: 1.125rem;
        text-anchor: middle;
        -webkit-user-select: none;
        -moz-user-select: none;
        -ms-user-select: none;
        user-select: none;
      }

      @media (min-width: 768px) {
        .bd-placeholder-img-lg {
          font-size: 3.5rem;
        }
      }
    </style>
    <!-- Custom styles for this template -->
    <link href="/resources/assets/css/form-validation.css" rel="stylesheet">
  </head>
  <body class="bg-light">
    <div class="container">
	  <div class="py-5 text-center">
	    <h2>회원가입</h2>
	  </div>

	  <div class="row">
	    <div class="col-md-8 order-md-1">
	      <form class="needs-validation" action="/registerAsk" method="post">
	      	 <div class="row">
		         <div class="col-md-8 mb-3">
		            <label for="userId">아이디</label>
		            <input type="text" class="form-control" id="userId" name="userId" required>
		            <div id="useOk" style="color:red";></div>
		          </div>
		          <div class="col-md-4 mb-3 mt-4">
		            <input type="button" id="emailDupCheck" class="btn btn-primary" value="중복확인">
	          	 </div>
          	 </div>
	         <div class="row-md">
			      <div class="mb-3">
			        <label for="username">이름</label>
			        <input type="text" class="form-control" id="username" name="userName" required>
			      </div>
		      </div>
		      <div class="row-md">
				 <div class="mb-3">
		          <label for="userPw">비밀번호</label>
		          <input type="password" class="form-control" id="userPw" name="userPw" required>
		         </div>
	         </div>
	         <div class="row-md">
		         <div class="mb-3">
		          <label for="phoneNum">전화번호</label>
		          <input type="text" class="form-control" id="phoneNum" name="phoneNum" placeholder="01011112222">
		        </div>
	        </div>
	        <div class="row-md">
		        <div class="mb-3">
		          <label for="email">이메일</label>
		          <input type="email" class="form-control" id="email" name="email" placeholder="you@example.com">
		        </div>
	        </div>
	        
	        <s:csrfInput/>
	        <input class="btn btn-primary btn-md btn-block" type="submit" value="Join"></input>
	      </form>
	    </div>
	  </div>
	 </div>
	 <script>
		$('#emailDupCheck').on('click', function(){
			var params = "userId="+ $('#userId').val();
						
			$.ajax({
					url : "/user/IdDuplicate",
					type : "GET",
					data : params,
					dataType : "text",
					success : function(data){
						if(data =="canUse")
							$('#useOk').html("사용 가능한 아이디 입니다.");
						else
							$('#useOk').html("이미 존재하는 아이디 입니다. 다른 아이디를 사용해주세요.");
					},
					 error: function(jqXHR, exception) {
					        if (jqXHR.status === 0) {
					            alert('Not connect.\n Verify Network.');
					        } 
					        else if (jqXHR.status == 400) {
					            alert('Server understood the request, but request content was invalid. [400]');
					        } 
					        else if (jqXHR.status == 401) {
					            alert('Unauthorized access. [401]');
					        } 
					        else if (jqXHR.status == 403) {
					            alert('Forbidden resource can not be accessed. [403]');
					        } 
					        else if (jqXHR.status == 404) {
					            alert('Requested page not found. [404]');
					        } 
					        else if (jqXHR.status == 500) {
					            alert('Internal server error. [500]');
					        } 
					        else if (jqXHR.status == 503) {
					            alert('Service unavailable. [503]');
					        } 
					        else if (exception === 'parsererror') {
					            alert('Requested JSON parse failed. [Failed]');
					        } 
					        else if (exception === 'timeout') {
					            alert('Time out error. [Timeout]');
					        } 
					        else if (exception === 'abort') {
					            alert('Ajax request aborted. [Aborted]');
					        } 
					        else {
					            alert('Uncaught Error.n' + jqXHR.responseText);
					        }
					    }
				});
			});
	</script>
