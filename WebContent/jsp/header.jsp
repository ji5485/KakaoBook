<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${ pageContext.request.contextPath }" />
    
<style>
@import url(http://fonts.googleapis.com/earlyaccess/notosanskr.css);

* {
	font-family: 'Noto Sans KR';
}

li {
	list-style: none;
}

.Middle {
	width: 80%;
	min-width: 700px;
	height: 50%;
	padding-right: 50px;
	padding-left: 50px;
	margin: 0 auto;
	display: flex;
	justify-content: space-between;
}

.Header {
	width: 100%;
	display: flex;
	align-items: center;
	color: white;
	min-width: 800px;
	margin-top: 10px;
}

.Header {
	position: fixed;
	top: 0;
	z-index: 50;
}

.HeaderLogo {
	display: flex;
	align-items: center;
	font-size: 1.1rem;
}

.HeaderLogo a, .HeaderLogo a:hover, .HeaderLogo a:active {
	font-weight: 500;
	text-decoration: none;
	color: inherit;
}

.HeaderMenu {
	width: 400px;
	display: flex;
	justify-content: space-between;
	font-size: 0.8rem;
}

.HeaderMenu li {
	cursor: default;
	display: flex;
	align-items: center;
	position: relative;
}

.HeaderMenu a li {
	cursor: pointer;
}

.HeaderMenu a, .HeaderMenu a:hover, .HeaderMenu a:active {
	text-decoration: none;
	color: inherit;
	display: flex;
	align-items: center;
}

.HeaderMenu a li:hover {
	text-decoration: underline;
}

.dropdownMenu {
	visibility: hidden;
	position: absolute;
	width: 100px;
	background: transparent;
	border: 1px solid white;
	top: 60px;
	transition: .1s all;
}

.dropdownMenu li {
	padding: 10px;
}

.dropdownVisible {
	visibility: visible;
}

.nickname {
	border: 1px solid white;
	padding-right: 7px;
	padding-left: 7px;
}
</style>

<div class="Header">
	<div class="Middle">
		<div class="HeaderLogo">
			<a href="/">KakaoBook</a>
		</div>
		<div class="HeaderMenu">
			<c:if test="${ nickname == null }">
				<a><li onClick="loginWithKakao()">카카오 로그인</li></a>
			</c:if>
			<c:if test="${ nickname != null }">
				<a><li class="nickname" style="height: 100%">${ nickname } 님</li></a>
				<div class="dropdownMenu">
					<a href="${ contextPath }/jsp/userInfo.jsp"><li>사용자 정보</li></a>
					<a href="${ contextPath }/logout.do"><li>로그아웃</li></a>
				</div>
			</c:if>
			<li>|</li>
			<a href="${ contextPath }/search.do"><li>컨텐츠 검색</li></a>
			<li>|</li>
			<a href="${ contextPath }/scrapList.do"><li>스크랩 내역</li></a>
			<li>|</li>
			<a href="${ contextPath }/isstoryuser.do"><li>스크랩 공유</li></a>
		</div>
	</div>
	
	<script>
		$(".nickname, .dropdownMenu").mouseover(function() {
			$(".dropdownMenu").addClass("dropdownVisible");
		});
		
		$(".nickname, .dropdownMenu").mouseout(function() {
			$(".dropdownMenu").removeClass("dropdownVisible");
		})
	</script>
</div>