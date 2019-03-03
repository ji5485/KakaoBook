<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${ pageContext.request.contextPath }" />

<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>KakaoBook : ${ sessionScope.nickname } 님 정보</title>

	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
	<link rel="stylesheet" href="${ contextPath }/css/UserInfoPageStyle.css" />
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/css/bootstrap.min.css" integrity="sha384-PsH8R72JQ3SOdhVi3uxftmaW6Vc51MKb0q5P2rRUpPvrszuE4W1povHYgTpBfshb" crossorigin="anonymous">
	<script
  		src="https://code.jquery.com/jquery-3.2.1.min.js"
  		integrity="sha256-hwg4gsxgFZhOsEEamdOYGBf13FyQuiTwlAQgxVSNgt4="
  		crossorigin="anonymous"></script>
	<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.3/umd/popper.min.js" integrity="sha384-vFJXuSJphROIrBnz7yo7oB41mKfc8JzQZiCq4NCceLEaO4IHwicKwpJf9c9IpFgh" crossorigin="anonymous"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/js/bootstrap.min.js" integrity="sha384-alpBpkh1PFOepccYVYDB4do5UnbKysX5WZXm3XxPqe5iKTfUKjNkCk9SaVuEZflJ" crossorigin="anonymous"></script>
</head>

<body>
	<%@ include file="header.jsp" %>
	
	<div class="background"></div>
	<div class="dimmer"></div>
	
	<div class="Container">
		<c:if test="${ sessionScope.nickname != null }">
			<div class="userInfo">
				<c:if test="${ sessionScope.profile_image.equals('') }">
					<img src="${ contextPath }/image/icon.png" width="200px" height="200px" />
				</c:if>
				<c:if test="${ !sessionScope.profile_image.equals('') }">
					<img src="${ sessionScope.profile_image }" width="200px" height="200px" />
				</c:if>
				<div class="userInfo-text">
					<div class="userInfo-text-name">${ sessionScope.nickname } 님</div>
				</div>
			</div>
			<div class="userInfo-additional">
				<div class="userInfo-additional-up">
					<div>성별</div>
					<div>나이</div>
					<div>관심사</div>
				</div>
				<div class="userInfo-additional-down">
					<div>
						<c:if test="${ sessionScope.gender == 'man' }">남자</c:if>
						<c:if test="${ sessionScope.gender != 'man' }">여자</c:if>
					</div>
					<div>${ sessionScope.age }세</div>
					<div>${ sessionScope.favorite }</div>
				</div>
			</div>
			<div class="unlink-button">
				<a href="${ contextPath }/unlink.do">가입 탈퇴</a>
			</div>
		</c:if>
		<c:if test="${ sessionScope.nickname == null }">
			<div class="login">
				<div>
					<div class="login-ban">
						<i class="fa fa-ban" aria-hidden="true"></i>
					</div>
					<div style="padding-bottom: 30px;">로그인이 필요한 서비스입니다.</div>
				</div>
			</div>
		</c:if>
	</div>
	
	<c:if test="${ error != null }">
		<div class="alert alert-danger alert-dismissible fade show" role="alert">
		  <button type="button" class="close" data-dismiss="alert" aria-label="Close" style="outline: none;">
		    <i class="fa fa-times" aria-hidden="true"></i>
		  </button>
		  ${ error }
		</div>
	</c:if>
	
	<%@ include file="footer.jsp" %>
</body>
</html>