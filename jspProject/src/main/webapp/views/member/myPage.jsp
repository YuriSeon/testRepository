<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>My Page</title>
<style>
    .outer {
        background-color: black;
        color: white;
        width: 1000px;
        margin: auto;
        margin-top: 50px;
    }
    
    #myPage-form table {
        margin: auto;
    }
    
    #myPage-form input {
        margin: 5px;
    }
    #changePwd, #deleteForm{
    	color : black;
    }
    </style>
</head>
<body>
    <!-- myPage.me -->
    <%@ include file = "../common/menubar.jsp" %>
    <!-- 해당 페이지에 있는 변수 사용하려면 include가 더 위에 있어야 한다 -->
    <!-- 페이지가 열릴때마다 include된게 있다면 그때마다 재실행 되어서 include에 있는 변수나 조건식들이 초기화된다 -->
<%
	String id = loginUser.getUserId();
	String name = loginUser.getUserName();
	String phone = (loginUser.getPhone()==null)? "":loginUser.getPhone(); 
	String email = (loginUser.getEmail()==null)? "":loginUser.getEmail();
	String address = (loginUser.getAddress()==null)? "":loginUser.getAddress();
	String interest = (loginUser.getInterest()==null)? "":loginUser.getInterest();
	
%>
	<div class="outer">
		<br>
		<h2 align="center">마이페이지</h2>
		<form action="<%=contextPath %>/update.me" method="post"
			id="myPage-form">
			<table>
				<tr>
					<!-- 아이디 수정 불가능하게 읽기전용으로 만들기 -->
					<td>* 아이디</td>
					<td><input type="text" name="userId" maxlength="12" value="<%=id %>" readonly required></td>
				</tr>
				<tr>
					<td>* 이름</td>
					<td><input type="text" name="userName" value="<%=name %>" required></td>
					<td></td>
				</tr>
				<tr>
					<td>전화번호</td>
					<td><input type="text" name="phone" value="<%=phone %>" placeholder="-포함해서 입력"></td>
					<td></td>
				</tr>
				<tr>
					<td>이메일</td>
					<td><input type="email" name="email" value="<%=email %>"></td>
					<td></td>
				</tr>
				<tr>
					<td>주소</td>
					<td><input type="text" name="address" value="<%=address %>"></td>
					<td></td>
				</tr>
				<tr>
					<td>관심분야</td>
	                <td colspan="2">
	                <input type="checkbox" name="interest" id="sports" value="운동"><label for="sports">운동</label>
	                <input type="checkbox" name="interest" id="movies" value="영화감상"><label for="movies">영화감상</label>
	                <input type="checkbox" name="interest" id="board" value="보드타기"><label for="board">보드타기</label>
	                <input type="checkbox" name="interest" id="cook" value="요리"><label for="cook">요리</label>
	                <input type="checkbox" name="interest" id="game" value="게임"><label for="game">게임</label>
	                <input type="checkbox" name="interest" id="book" value="독서"><label for="book">독서</label>
	                </td>
				</tr>
			</table>
			<script>
				$(function(){
					var interest = "<%=interest %>"; 
					/* 문자열처리 안하면 결과 다르게 나옴 자바스크립트변수에 자바변수 넣을수는 있지만 역순으로는 불가능함 */
					/* 자바코드가 먼저 생성이 되기 때문에 자바스크립트 변수를 자바에 넣을수가 없는 구조임 */
					/* console.log(interest); 확인용 */
					$("input[type=checkbox]").each(function(){
						// console.log($(this).val());
						// 로그인된 사용자의 interest에 있는 요소가 있다면 checked하기
						// 일치하지 않으면 (찾지 못하면) -1을 반환한다. 찾으면 해당 인덱스를 반환
						// interest.search($(this).val());
						if(interest.search($(this).val())!= -1){
							// 일치하는 요소 체크하기
							$(this).attr("checked", true);
						}
					});
					
				});
			
			</script>
			<br>
			<br>
			<div align="center">
				<button type="submit" class="btn btn-info">정보변경</button>
                <button type="button" class="btn btn-warning" data-toggle="modal" data-target="#changePwd">비밀번호 변경</button>
				<button type="button" class="btn btn-danger" data-toggle="modal" data-target="#deleteForm">회원 탈퇴</button>
			</div>
		</form>
		<!-- 비밀번호 변경 모달영역 -->
		<!-- The Modal -->
		<div class="modal" id="changePwd">
		  <div class="modal-dialog">
		    <div class="modal-content">
		
		      <!-- Modal Header -->
		      <div class="modal-header">
		        <h4 class="modal-title">비밀번호 변경</h4>
		        <button type="button" class="close" data-dismiss="modal">&times;</button>
		      </div>
		
		      <!-- Modal body -->
		      <div class="modal-body" align="center">
		        <!-- 현재 비밀번호, 변경할 비밀번호, 변경할 비밀번호 재입력 -->
		        <form action="<%=contextPath%>/updatePwd.me" method="post">
		        <!-- form태그 안에 데이터 숨겨서 보내기 -->
		        <input type="hidden" name="userId" value="<%=id %>">
		        <table>
		        	<tr>
		        		<td>현재 비밀번호</td>
		        		<td><input type="password" name="userPwd" required></td>
		        	</tr>
		        	<tr>
		        		<td>변경할 비밀번호</td>
		        		<td><input type="password" name="updatePwd" required></td>
		        	</tr>
		        	<tr>
		        		<td>변경할 비밀번호 확인</td>
		        		<td><input type="password" id="chkPwd" required></td>
		        	</tr>
		        </table>
		        <br>
		        
		        <button type="submit" onclick="return validate();" class="btn btn-primary" >비밀번호 변경</button>
		      </form>
		      <script>
		      	function validate(){
		      		var loginPwd = "<%=loginUser.getUserPwd()%>";
		      		var inputPwd = $("input[name=userPwd]").val();
		      		var updatePwd = $("input[name=updatePwd]").val();
		      		var chkPwd = $("#chkPwd").val();
		      		
		      		if(loginPwd == inputPwd){
		      			if(updatePwd!=chkPwd){
		      				alert("변경할 비밀번호와 확인이 일치하지 않습니다.");
		      				$("input[name=updatePwd]").select();
		      				return false;
		      			}
		      		} else {
		      			alert("현재 비밀번호가 일치하지 않습니다.");
		      			$("input[name=userPwd]").focus();
		      			return false;
		      		}
		      	}
		      
		      </script>
		      
		      </div>
		
		
		    </div>
		  </div>
		</div>
		
		<!-- 회원탈퇴 변경 모달영역 -->
		<!-- The Modal -->
		<div class="modal" id="deleteForm">
		  <div class="modal-dialog">
		    <div class="modal-content">
		
		      <!-- Modal Header -->
		      <div class="modal-header">
		        <h4 class="modal-title">회원 탈퇴</h4>
		        <button type="button" class="close" data-dismiss="modal">&times;</button>
		      </div>
		
		      <!-- Modal body -->
		      <div class="modal-body" align="center">
		        <form action="<%=contextPath %>/delete.me" method="post">
		        	<div>정말로 회원 탈퇴하시갰습니까?</div>
		        	<table>
		        		<tr>
		        			<td>비밀번호</td>
		        			<td><input type="password" required></td>
		        		</tr>
		        	</table>
		        	<input type="hidden" name="userId" value="<%=id %>">
		        	<button type="submit" class="btn btn-danger">확인</button>
		        	<button type="button" onclick="back();" class="btn btn-info">취소</button>
		        	
		        	<script>
		        		function back(){
		        			location.href="<%=contextPath %>";
		        		}
		        	</script>
		        	
		        </form>
		      </div>
			
		
		    </div>
		  </div>
		</div>
		<br>
		<br>
		<br>
	</div>
</body>
</html>