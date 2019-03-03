<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>KakaoBook : 스크랩 공유</title>

	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
	<link rel="stylesheet" href="${ contextPath }/css/KakaoStoryPageStyle.css" />
	<link rel="stylesheet" href="${ contextPath }/css/snackbar.css" />
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
		<form action="${ contextPath }/linkShare.do" method="post" style="width: 100%; height: 100%; display: flex; justify-content: space-between;">
			<div class="kakaostory-select">
				<div>공유 가능한 컨텐츠</div>
				<c:if test="${ list.size() != 0 }">
					<select multiple="multiple" name="post" required="required">
						<c:forEach var="item" items="${ list }">
							<option value="${ item.getLink() }">${ item.getTitle() }</option>
						</c:forEach>
					</select>
				</c:if>
				<c:if test="${ list.size() == 0 }">
					<div class="listNull">없음</div>
				</c:if>
			</div>
			<div class="kakaostory-share">
				<div>
					<div class="kakaostory-title">
						<img src="${ contextPath }/image/icon_kakaostory.png" width="100px" height="100px"/>
						KakaoStory 공유
					</div>
					<div class="kakaostory-content">
						<span>함께 게시할 글을 입력해주세요.</span>
						<textarea name="content" placeholder="생략 가능"></textarea>
					</div>
					<div class="kakaostory-button">
						<button type="submit">KakaoStory 공유하기</button>
					</div>
				</div>
			</div>
		</form>
	</div>
	
	<div id="snackbar"></div>
	
	<c:if test="${ error != null }">
		<script>
			var element = $("#snackbar");
			element.text("${ error }");
		    element.toggleClass("show");
		    setTimeout(function() { element.toggleClass("show") }, 2000);
		</script>
	</c:if>
	
	<%@ include file="footer.jsp" %>
</body>
</html>