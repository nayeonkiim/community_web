<%@ taglib uri="http://www.springframework.org/security/tags" prefix="s" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="/WEB-INF/views/layout/top.jsp" %>

<title>로그인</title>

<link rel="canonical" href="https://getbootstrap.com/docs/4.5/examples/sign-in/">

    <!-- Bootstrap core CSS -->
<link href="/resources/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2" crossorigin="anonymous">

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

      @media (min-width: 768px) {
        .bd-placeholder-img-lg {
          font-size: 3.5rem;
        }
      }
    </style>
    <!-- Custom styles for this template -->
    <link href="/resources/assets/css/signin.css" rel="stylesheet">

  </head>
  <body class="text-center">
    <form class="form-signin" action="/loginAsk" method="post">
		  <h1 class="h3 mb-3 font-weight-normal">Please sign in</h1>
		  
		  <label for="userId" class="sr-only">Id</label>
		  <input type="text" id="userId" name="userId" class="form-control" placeholder="UserId" required autofocus>
		  
		  <label for="password" class="sr-only">Password</label>
		  <input type="password" id="userPw" name="userPw" class="form-control" placeholder="Password" required>
		  <div style="color:red;">
		  	${loginFailMsg}
		  </div>
		  
		  <s:csrfInput/>
		  
		  <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
	</form>
</body>

