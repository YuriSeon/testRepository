<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	.outer{
	        background-color: black;
	        color: white;
	        width: 1000px;
	        height: 500px;
	        margin: auto;
	        margin-top: 50px;
	 }
	 
	 #enroll-form table{border : 1px solid white;}
	 #enroll-form input, textarea{
	 		width: 100%;
	 		box-sizing : border-box;
	 }
</style>
</head>
<body>
<%@include file="../common/menubar.jsp" %>
	<div class="outer">
		<h2 align="center">공지사항 작성</h2>
		
		<form action="<%=contextPath %>/insert.no" method="post" id="enroll-form">
			<table align="center">
				<tr>
					<td width="50">제목</td>
					<td width="350"><input type="text" name="title" required></td>
				</tr>
				<tr>
					<td>내용</td>
					<td></td>
				</tr>
				<tr>
					<td colspan="2">
						<textarea rows="10" cols="20" style="resize:none" name="content" required></textarea>
					</td>
				</tr>
			</table>
			<br><br>
			<div align="center">
				<button type="submit">등록하기</button>
				<button type="button" onclick="history.back();">뒤로가기</button>
				<!-- history.back()은 뒤로가는 함수다 html자체 내장 함수라서 따로 작성 안하고 불러와서 사용하는 것-->
			</div>
		</form>
	</div>
</body>
</html>