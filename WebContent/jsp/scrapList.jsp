<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${ pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>KakaoBook : ScrapList</title>
	<link rel="stylesheet" href="${ contextPath }/css/ScrapListPageStyle.css" />
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
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
	
	<div class="scrap-container">
		<div class="user">${ sessionScope.nickname } 님의 스크랩 내역입니다.</div>
		
		<div class="scrap-list">
			<c:if test="${ result.size() == 0 }">
				<div style="width: 100%; display: flex; justify-content: center; padding: 30px;">스크랩 내역이 존재하지 않습니다.</div>
			</c:if>
			<c:if test="${ result.size() != 0 }">
				<table style="width: 100%;">
					<thead>
						<tr>
							<th style="width: 45%;">Title</th>
							<th style="width: 25%;">Blogger</th>
							<th style="width: 20%;">Date</th>
							<th style="width: 10%"></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="item" items="${ result }" varStatus="status">
							<tr>
								<td onClick="goPage('${ item.getLink() }')">${ item.getTitle() }</td>
								<td>${ item.getBlogger() }</td>
								<td>${ item.getDate() }</td>
								<td class="remove" onClick="remove('${ item.getLink() }')"><i class="fa fa-times" aria-hidden="true"></i></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</c:if>
		</div>
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
	
	<script>
		function goPage(page) {
			window.open(page, '_blank');
		}
		
		function remove(page) {
			$.post('${ contextPath }/removeScrap.do', { link: page }, function(data) {
				console.log(data);
				location.href = "${ contextPath }/scrapList.do";
			})
		}
	</script>

</body>
</html>