<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${ pageContext.request.contextPath }" />

<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>KakaoBook Connect</title>
	
	<link rel="stylesheet" href="${ contextPath }/css/ConnectPageStyle.css" />
	<link rel="stylesheet" href="${ contextPath }/css/snackbar.css" />
	<script
  		src="https://code.jquery.com/jquery-3.2.1.min.js"
  		integrity="sha256-hwg4gsxgFZhOsEEamdOYGBf13FyQuiTwlAQgxVSNgt4="
  		crossorigin="anonymous"></script>
</head>
<body>

	<%@ include file="header.jsp" %>
	
	<div class="background"></div>
	<div class="dimmer"></div>

	<div class="Container">
		<div class="inner-box1">
			<div class="title">
				<img src="${ contextPath }/image/icon.png" height="80px" />
				<div class="title-text">
					KakaoBook 서비스 연결<br/>
					<span style="font-size: 1rem; font-weight: 500;">정책 및 약관 / 사용자 정보 입력</span>
				</div>
			</div>
			<div class="content">
				<div style="width: 50%;">
					<div class="box1-content">
						<div class="box1-mini-title">개인정보 제공 동의 항목</div>
						<div class="box1-mini-content">- KakaoBook 앱 연결</div>
						<div class="box1-mini-content">- 사용자 정보 요청</div>
						<div class="box1-mini-content">- 카카오톡 프로필 요청</div>
					</div>
					<div class="box1-content">
						<div class="box1-mini-title">카카오스토리 글 작성</div>
						<div class="box1-mini-content">- 링크 포스팅</div>
					</div>
					<div class="box1-content">
						<div class="box1-mini-title">카카오톡 메시지 전송</div>
						<div class="box1-mini-content">- 나에게 보내기</div>
					</div>
				</div>
				<div style="width: 50%;">
					<div class="box1-content">
						<div class="box1-mini-title">개인정보 제3자 제공 동의</div>
						<div class="box1-mini-content" style="text-indent: 0;">사용자의 개인정보는<br/>(주)카카오에서 관리</div>
					</div>
					<div class="box1-content">
						<div class="box1-mini-title">정보 수집 목적 / 보유 기간</div>
						<div class="box1-mini-content" style="text-indent: 0;">
							카카오 계정 연결을 통한<br/>KakaoBook 서비스 제공 목적<hr style="width:40%;"/>KakaoBook 탈퇴시 정보 파기</div>
					</div>
				</div>
			</div>
		</div>
		<div class="inner-box2">
			<div class="form">
				<form action="${ contextPath }/connect.do" method="post">
					<input value="${ param.nickname }" type="text" name="nickname" placeholder="닉네임" required />
					<input value="${ param.age }" type="text" name="age" placeholder="사용자 나이" required />
					<select name="gender" required>
						<option value="" hidden="true" style="color: grey;">사용자 성별</option>
						<option value="man">남성</option>
						<option value="woman">여성</option>
					</select>
					<input value="${ param.favorite }" type="text" name="favorite" placeholder="관심사 1가지" required />
					<button type="submit">KakaoBook Connect</button>
				</form>
			</div>
		</div>
	</div>
	
	<%@ include file="footer.jsp" %>
	
	<div id="snackbar"></div>
	
	<c:if test="${ error != null }">
		<script>
			var element = $("#snackbar");
			element.text("${ error }");
		    element.toggleClass("show");
		    setTimeout(function() { element.toggleClass("show") }, 2000);
		</script>
	</c:if>
</body>
</html>