<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${ pageContext.request.contextPath }" />

<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>KakaoBook</title>
	
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
	<link rel="stylesheet" href="${ contextPath }/css/MainPageStyle.css" />
	<link rel="stylesheet" href="${ contextPath }/css/snackbar.css" />
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/css/bootstrap.min.css" integrity="sha384-PsH8R72JQ3SOdhVi3uxftmaW6Vc51MKb0q5P2rRUpPvrszuE4W1povHYgTpBfshb" crossorigin="anonymous">
	<script
  		src="https://code.jquery.com/jquery-3.2.1.min.js"
  		integrity="sha256-hwg4gsxgFZhOsEEamdOYGBf13FyQuiTwlAQgxVSNgt4="
  		crossorigin="anonymous"></script>
	<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.3/umd/popper.min.js" integrity="sha384-vFJXuSJphROIrBnz7yo7oB41mKfc8JzQZiCq4NCceLEaO4IHwicKwpJf9c9IpFgh" crossorigin="anonymous"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/js/bootstrap.min.js" integrity="sha384-alpBpkh1PFOepccYVYDB4do5UnbKysX5WZXm3XxPqe5iKTfUKjNkCk9SaVuEZflJ" crossorigin="anonymous"></script>
	<script src="//developers.kakao.com/sdk/js/kakao.min.js"></script>
</head>

<body>
	<%@ include file="header.jsp" %>
	
	<div class="main"></div>
	<div class="logo">
		<div class="title">KakaoBook</div>
		<div class="description">
			Scrap web contents and share with your friends! <br/>
			<strong>Anytime, AnyWhere</strong> You can see it on your cell phone.
		</div>
	</div>
	
	<%@ include file="footer.jsp" %>
	
	<div id="snackbar"></div>
	
	<script type='text/javascript'>
	  //<![CDATA[
	    // 사용할 앱의 JavaScript 키를 설정해 주세요.
	    Kakao.init('1f58fe7dcd7e83db12d0a5be92c011f4');
	    // 카카오 로그인 버튼을 생성합니다.
	    function loginWithKakao() {
	      // 로그인 창을 띄웁니다.
	      Kakao.Auth.login({
	        success: function(authObj) {
	          $.post("/login.do", authObj, function(data) {
	        	  window.location = data.uri;
	          });
	        },
	        fail: function(err) {
	          alert(JSON.stringify(err));
	        }
	      });
	    };
	  //]]>
	</script>
	
	<c:if test="${ error != null }">
		<script>
			var element = $("#snackbar");
			element.text("${ error }");
		    element.toggleClass("show");
		    setTimeout(function() { element.toggleClass("show") }, 2000);
		</script>
	</c:if>
	
	<c:if test="${ result != null }">
		<script>
			var element = $("#snackbar");
			element.text("${ result }");
		    element.toggleClass("show");
		    setTimeout(function() { element.toggleClass("show") }, 2000);
		</script>
	</c:if>
</body>
</html>