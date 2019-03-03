<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>KakaoBook - Search</title>
	<link rel="stylesheet" href="${ contextPath }/css/SearchPageStyle.css" />
	<link rel="stylesheet" href="${ contextPath }/css/snackbar.css" />
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
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
	
	<div class="background"></div>
	<div class="dimmer"></div>
	
	<div class="formContainer">
		<div class="formContainer-Middle">
			<form action="${ contextPath }/search.do">
				<select required name="sort" id="sort">
					<option value="sim">정확도순</option>
					<option value="date">최신순</option>
				</select>
				<input type="text" name="query" required />
				<button type="submit"><i class="fa fa-search" aria-hidden="true"></i></button>
			</form>
		</div>
	</div>
	
	<div class="container">
		<div class="itemContainer">
			<c:if test="${ result != null }">
				<c:forEach var="item" items="${ result }" varStatus="status">
					<c:if test="${ !item.getDescription().equals('') }">
						<div class="item">
							<div class="item-upward">
								<div class="item-title">${ item.getTitle() }</div>
								<div class="item-blogInfo">
									<div class="item-blogInfo-blogger">${ item.getBloggername() }</div>
									<div>${ item.getPostdate().substring(0, 4) }/${ item.getPostdate().substring(4, 6) }/${ item.getPostdate().substring(6) }</div>
								</div>
								<div class="item-description">
									${ item.getDescription() }
								</div>
							</div>
							<div class="item-function">
								<div onClick="goPage('${ item.getLink() }')">
									<i class="fa fa-link" aria-hidden="true"></i>&nbsp;&nbsp;내용 보기
								</div>
								<div onClick="scrap('${ item.getTitle() }', '${ item.getBloggername() }' , '${ item.getLink() }')">
									<i class="fa fa-inbox" aria-hidden="true"></i>&nbsp;&nbsp;스크랩 하기
								</div>
							</div>
						</div>
					</c:if>
				</c:forEach>
			</c:if>
		</div>
	</div>
	
	<div class="pagingContainer">
		<div class="pagingContainer-Middle">
			<c:if test="${ startPage == 1 }">
				<a class="paging" href="#">
					<i class="fa fa-caret-left" aria-hidden="true"></i>
				</a>
			</c:if>
			<c:if test="${ startPage != 1 }">
				<a class="paging" href="${ contextPath }/search.do?query=${ param.query }&sort=${ param.sort }&pageNo=${ startPage - 1 }">
					<i class="fa fa-caret-left" aria-hidden="true"></i>
				</a>
			</c:if>
			<c:forEach var="page" begin="${ startPage }" end="${ endPage }">
				<a class="paging" href="${ contextPath }/search.do?query=${ param.query }&sort=${ param.sort }&pageNo=${ page }">${ page }</a>
			</c:forEach>
			<a class="paging" href="${ contextPath }/search.do?query=${ param.query }&sort=${ param.sort }&pageNo=${ endPage + 1 }">
				<i class="fa fa-caret-right" aria-hidden="true"></i>
			</a>
		</div>
	</div>
	
	<div id="snackbar"></div>

	<%@ include file="footer.jsp" %>
	
	<script>
		function goPage(page) {
			window.open(page, '_blank');
		}
		
		function scrap(title, blogger, link) {
			$.post('${ contextPath }/scrap.do', {
				"title": title, "blogger": blogger, "link": link
			}, function(data) {
				var element = document.getElementById("snackbar");
				if (data.result === "Success") element.textContent = "스크랩 성공.  카카오톡을 확인해주세요.";
				else element.textContent = "스크랩 실패.  이미 스크랩한 컨텐츠 입니다.";
				element.className = "show";
				setTimeout(function() { element.className = element.className.replace("show", "") }, 2000);
			});
		}
		
		window.onload = function() {
			if ("${ param.sort == null || param.sort.trim().equals("") }" === "true") $("#sort").val("sim").prop("selected", true);
			else $("#sort").val("${ param.sort }").prop("selected", true);
		}
		
		$(window).scroll(function() {
			if ($(window).scrollTop() > 50) {
				$('.Header').css('background', '#1D1D1D').css('height', '60px');
				$('.formContainer').css('background', '#1D1D1D').css('top', '60px');
			}
			else {
				$('.Header').css('background', 'transparent').css('height', '70px');
				$('.formContainer').css('background', 'transparent').css('top', '70px');
			}
		})
	</script>
</body>
</html>