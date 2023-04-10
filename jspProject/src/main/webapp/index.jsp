<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%-- <%=JDBCTemplate.getConnection() %> 확인용도--%>
	
	<%@ include file="views/common/menubar.jsp" %>
	
	<!-- 
		C R U D
		Create(insert) Read(select) Uupdate Delete
		
		--회원서비스
		로그인 : Read(select)
		회원가입 : Create(insert)
		회원정보수정 : Update
		회원탈퇴 : Update or Delete
		마이페이지 : Read(select)
		
		--게시판
		게시글 조회 : R
		게시글 작성 : C
		게시글 수정 : U
		게시글 삭제 : U or D
		
		댓글 작성 : C
		댓글 조회 : R
		댓글 수정 : U
		댓글 삭제 : U or D
	 -->
	 
</body>
</html>