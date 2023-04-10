<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.ArrayList, com.kh.board.model.vo.*"%>
<% 
	ArrayList<Category> clist = (ArrayList<Category>)request.getAttribute("clist");
	Board b = (Board)request.getAttribute("b");
	Attachment a = (Attachment)request.getAttribute("a");
	Category ct = (Category)request.getAttribute("c");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	#update-form>table{
		border: 1px solid white;
	}
	#update-form input, textarea{
		width : 100%;
		box-sizing : border-box;
	}
</style>
</head>
<body>
	<%@include file="../common/menubar.jsp" %>
	<div class="outer" align="center">
		<h2 >글작성페이지</h2>
		<form action="<%=contextPath %>/update.bo" method="post" id="update-form" enctype="multipart/form-data">
			<input type="hidden" name="bno" value="<%=b.getBoardNo()%>">
			<script>
				$(function(){
					// option에 있는 text와 조회해온 게시글 카테고리와 일치하는지 찾아내어 선택되어있게 작업하기
					$("#update-form option").each(function(){
						//if($(this).text()=="<%=ct.getCategoryName()%>"){
						//	$(this).attr("select", true);
						//}
						console.log($(this));
					})
				});
			</script>
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
					<td><input type="text" name="title" value="<%=b.getBoardTitle() %>" required></td>
				</tr>
				<tr>
					<th>내용</th>
					<td><textarea name="content" rows="10" cols="30" required><%=b.getBoardContent() %></textarea></td>
				</tr>
				<tr>
					<th>첨부파일</th>
					<td>
						<%if (a != null) {%> 
							<!-- 기존 첨부파일이 있었을 경우 수정할때 첨부파일 정보를 보내야한다. -->
							<!-- 파일번호, 변경된 파일명 전달하기 -->
							<%=a.getOriginName()%> 
							<input type="hidden" name="fileNo" value="<%=a.getFileNo()%>">
							<input type="hidden" name="originFileName" value="<%=a.getChangeName()%>">
							<!-- 
							기존객체의 변경된 이름값이 현재 수정하기 전의 파일의 이름이라서 벨류값은 변경되어 서버에 저장되어있던 이름을 넘겨주고 
							name값에는 기존파일의 이름이라고 넣어줘야 기존 파일을 삭제 후 새로운 파일의 이름을 저장해서 교체진행 가능함 
							-->
						<%} %> 
						<input type="file" name="reUpfile" required>
					</td>
				</tr>
			</table>
			<br>
			<div align="center">
				<button type="submit">수정하기</button>
				<button type="button" onclick="history.back();">취소</button>
			</div>
		</form>
	</div>
</body>
</html>