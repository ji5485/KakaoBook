<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.Calendar" %>

<style>

.Footer {
	width: 100%;
	height: 60px;
	display: flex;
	align-items: center;
	color: white;
	position: fixed;
	bottom: 10px;
	z-index: 10;
}

</style>

<%
	Calendar c = Calendar.getInstance();
%>

<div class="Footer">
	<div class="Middle">
		Copyright &copy; <%= c.get(Calendar.YEAR) %> Powered by Kakao, Naver, and Goorm Service
	</div>
</div>