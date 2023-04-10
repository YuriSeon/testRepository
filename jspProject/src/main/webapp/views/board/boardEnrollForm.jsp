<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.ArrayList, com.kh.board.model.vo.Category"%>
<%
	ArrayList<Category> clist = (ArrayList<Category>)request.getAttribute("clist");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	#enroll-form>table{
		border: 1px solid white;
	}
	#enroll-form input, textarea{
		width : 100%;
		box-sizing : border-box;
	}
</style>
</head>
<body>
	<%@include file="../common/menubar.jsp" %>
	<div class="outer">
		<h2 aline="center">글작성페이지</h2>
		<!-- 카테고리, 제목, 내용, 첨부파일, 작성자 번호 (input hidden넘겨주거나 session에서 뽑아오기) -->
		<form action="<%=contextPath %>/insert.bo" method="post" id="enroll-form" enctype="multipart/form-data">
			<!-- 전달하는 방식이 multipart/form-data로 변경하면 전송하는 방식이 달라졌기에 기존의 방식처럼 getParameter로 받을 수 없음 -->
			<input type="hidden" name="userNo" value="<%=loginUser.getUserNo()%>">
			<!-- 카테고리 테이블에서 조회해온 카테고리 목록 선택상자 만들 -->
			<table align="center">
				<tr>
					<th width="100">카테고리</th>
					<td width="500">
						<select name="category">
							<%for(Category c : clist) { %>
								<option value="<%=c.getCategoryNo()%>"><%=c.getCategoryName() %></option>
							<%} %>
						</select>
					</td>
				</tr>
				<tr>
					<th>제목</th>
					<td><input type="text" name="title" required></td>
				</tr>
				<tr>
					<th>내용</th>
					<td><textarea name="content" rows="10" cols="30" required></textarea></td>
				</tr>
				<tr>
					<th>첨부파일</th>
					<td><input type="file" name="upfile" required></td>
				</tr>
			</table>
			<br>
			<div align="center">
			<button type="submit">등록하기</button>
			<button type="reset">초기화</button>
			</div>
		</form>
	</div>
</body>
</html>