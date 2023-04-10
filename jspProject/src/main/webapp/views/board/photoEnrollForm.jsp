<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	#enroll-form>table{
		border:1px solid white;
	}
	#enroll-form input, textarea{
		width : 100%
		box-sizing : border-box;
	}
	
</style>
</head>
<body>
	<%@include file="../common/menubar.jsp" %>
	<div class="outer">
		<h2 align="center">사진 게시글 작성하기</h2>
		<br>
		
		<form action="<%=contextPath %>/insert.ph" method="post" id="enroll-form" enctype="multipart/form-data">
			<input type="hidden" name="userNo" value="<%=loginUser.getUserNo() %>">
			<table align="center">
				<tr>
					<th width="100">제목</th>
					<td colspan="3"><input type="text" name="title" required></td>
				</tr>
				<tr>
					<th>내용</th>
					<td colspan="3"><textarea name="content" rows="10" cols="30" required style="resize:none"></textarea></td>
				</tr>
				<tr>
					<th >대표이미지</th>
					<td colspan="3" align="center">
						<img width="250" height="170" id="titleImg">
					</td>
				</tr>
				<tr>
					<th>상세이미지</th>
					<td><img id="contentImg1" width="150" height="120"></td>
					<td><img id="contentImg2" width="150" height="120"></td>
					<td><img id="contentImg3" width="150" height="120"></td>
				</tr>
			</table>
			
			<!-- 파일 첨부 영역 -->
			<div id="file-area" align="center">
			<!-- onchange -> 상태가 변경되었을때 실행하는 이벤트 -->
			<input type="file" id="file1" name="file1" onchange="loadImg(this,1);" required> <!-- 대표이미지라서 필수입력사항 -->
			<input type="file" id="file2" name="file2" onchange="loadImg(this,2);"> 
			<input type="file" id="file3" name="file3" onchange="loadImg(this,3);"> 
			<input type="file" id="file4" name="file4" onchange="loadImg(this,4);"> <!-- 매개변수로 보내줘야 해당 이벤트객체를 넘길 수 있음 -->
			</div>
			<br><br>
			
			<div align="center">
				<button type="submit">작성하기</button>
			</div>
		</form>
		<script>
			$(function(){ 
				$("#file-area").hide(); // 상세이미지 숨기기 위해서 작성
				$("#titleImg").click(function(){
					$("#file1").click();
				});
				
				$("#contentImg1").click(function(){
					$("#file2").click();
				});
				
				$("#contentImg2").click(function(){
					$("#file3").click();
				});
				
				$("#contentImg3").click(function(){
					$("#file4").click();
				});
			});
			
			function loadImg(inputFile, num){ // 넘겨온 매개변수 받아줘야 해당 이벤트객체 사용 가능
				
				/* console.log("이벤트발생"); */
				// console.log(inputFile.files); // filelist처럼 해당하는 속성을 배열에 담아서 가져욤. 파일이 있다면 length가 1로 늘어나고 파일을 지우면 0으로 줄어듦
				// inputFile : 현재 변화가 생긴 요소객체
				// inputFile.files : 업로드된 파일의 정보를 배열의 형태로 반환하는 속성
				// 파일 선택시 length가 1이 반환된다. 취소하면 파일정보가 없어지니 length가 0이 된다.
				if(inputFile.files.length == 1){
					// 선택한 파일이 존재한다면
					// 해당 파일을 읽어서 영역에 미리보기 시켜주기
					// 파일 읽어줄 객체 FileReader
					var reader = new FileReader(); // 클래스타입이 없기에 다른 변수 선언처럼 var로 적어줌
					// 파일을 읽어줄 메소드 : readAsDataURL(파일)
					// -파일을 읽어들이는 순간 고유한 url을 부여한다.
					// -부여된 url을 src에 추가하면 해당 이미지를 띄워줄수있음.
					
					reader.readAsDataURL(inputFile.files[0]);
					
					// 파일 읽기가 완료된 시점에 img src속성에 해당 결과 url을 담아주는 작업하기
					// reader가 읽힌 시점에 실행될 함수
					
					reader.onload = function(e){ // e : 이벤트 객체
						// consloe.log(e); 이벤트 객체
						// consloe.log(e.target); 이벤트객체의 타겟
						// consloe.log(e.target.result); 이벤트객체의 타겟의 result 속성인데 부여된 url이 result에 들어있어서 가져오는 것
						// 부여된 url은 target에 result 속성에 들어있다 해당 result를 
						// img src속성에 부여하면 미리보기 가능
						
						switch(num){
						case 1 : $("#titleImg").attr("src",e.target.result); break;
						case 2 : $("#contentImg1").attr("src",e.target.result); break;
						case 3 : $("#contentImg2").attr("src",e.target.result); break;
						case 4 : $("#contentImg3").attr("src",e.target.result); break;
						}
					}
				} else { // url의 result 값을 넣어놓은걸 파일 선택이 헤제된다면 들어간 이미지도 제거해줘야 하기 때문에 값에 null을 넣어준다
					
					switch(num){
					case 1 : $("#titleImg").attr("src",null); break;
					case 2 : $("#contentImg1").attr("src",null); break;
					case 3 : $("#contentImg2").attr("src",null); break;
					case 4 : $("#contentImg3").attr("src",null); break;
					}
				}
			}
		</script>
	</div>
</body>
</html>