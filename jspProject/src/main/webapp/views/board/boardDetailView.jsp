<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.kh.board.model.vo.Board, com.kh.board.model.vo.Category, com.kh.board.model.vo.Attachment"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	#detail-area *{
		border : 1px solid white;
		text-align : center;
	}
	#detail-form input,p{
		width : 100%;
		box-sizing : border-box;
	}
	
</style>
</head>
<body>
	<% 
		Board b = (Board)request.getAttribute("board"); 
		Category c = (Category)request.getAttribute("c");
		Attachment a = (Attachment)request.getAttribute("a");
	%>
	<%@include file="../common/menubar.jsp" %>
	<div class="outer">
	<br><br>
	<h2 align="center">일반게시판 상세보기</h2>
	<table id="detail-area" align="center">
		<tr>
			<th width="70">카테고리</th>
			<td width="100"><%=c.getCategoryName() %></td>
			<th width="70">제목</th>
			<td width="250" ><%=b.getBoardTitle()%></td>
		</tr>
		<tr>
			<th>작성자</th>
			<td><%=b.getBoardWriter() %></td>
			<th>작성일</th>
			<td><%=b.getCreateDate() %></td>
		</tr>
		<tr>
			<th>내용</th>
			<td colspan="3" style="height:200px"><%=b.getBoardContent() %></td>
		</tr>
		<tr>
			<th>첨부파일</th>
			<%if(a!=null) { %>
				<td colspan="3">  <!-- 둘 같은 내용 --> <!-- 사용자에게 보여줄때는 원본이름 보여주는게 좋음 -->
				<a href="<%=contextPath + a.getFilePath() + a.getChangeName()%>" download><%=a.getOriginName() %></a>
				<%-- <a href="<%=contextPath%><%=a.getFilePath()%><%=a.getChangeName()%>" download>다운로드</a> --%>
				</td>
			<%} else { %>
				<td colspan="3">첨부파일이 없습니다.</td>
			<%} %>
		</tr>
	</table>
	<br><br>
		<%if(loginUser != null && loginUser.getUserId().equals(b.getBoardWriter())) { %>
			<div align="center">
				<button onclick="location.href='<%=contextPath %>/update.bo?bno='+<%=b.getBoardNo() %>" class="btn btn-info">수정하기</button>
				<button onclick="location.href='<%=contextPath %>/delete.bo?bno='+<%=b.getBoardNo() %>" class="btn btn-danger">삭제하기</button>
			</div>
		<%} %>
	</div>
</body>
</html>