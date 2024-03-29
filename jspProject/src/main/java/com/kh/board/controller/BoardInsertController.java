package com.kh.board.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import com.kh.board.model.service.BoardService;
import com.kh.board.model.vo.Attachment;
import com.kh.board.model.vo.Board;
import com.kh.board.model.vo.Category;
import com.kh.common.MyFileRenamePolicy;
import com.oreilly.servlet.MultipartRequest;

/**
 * Servlet implementation class BoardInsertController
 */
@WebServlet("/insert.bo")
public class BoardInsertController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardInsertController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 카테고리 목록 조회해오기
		ArrayList<Category> clist = new BoardService(). categoryList();
		
		request.setAttribute("clist", clist);
		
		request.getRequestDispatcher("views/board/boardEnrollForm.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		/*
		 * form 에서 multipart/form-data형식으로 전송했기때문에 기존 request에 parameter영역에서 꺼낼 수 없다
		 * 특정 라이브러리를 사용하여 전달받아야 함
		String category = request.getParameter("category");
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String file = request.getParameter("upfile");
		int userNo = Integer.parseInt(request.getParameter("userNo"));
		
		System.out.println(category);
		System.out.println(title);
		System.out.println(content);
		System.out.println(file);
		System.out.println(userNo);
		*/
		
		// cos.jar 라이브러리 추가 후 작업하기
		// form 전송방식이 일반 방식이 아닌 multipart/form-data방식이라면
		// request를 multipart 객체로 이관시켜서 다뤄야한다.
		
		// enctype이 multipart로 작성되어 넘어왔을 경우에 수정이 되도록
		if(ServletFileUpload.isMultipartContent(request)) { // multipart로 넘어온게 맞는지 확인하는 메소드. request에 담겨져서오기에 매개변수로 request를 넣어주는것
//			System.out.println("멀티파트 맞아요"); // 확인용
			// 파일 업로드를 위한 라이브러리 cos.jar
			
			// 1. 전송되는 파일을 처리할 작업 내용 (용량제한, 전달된 파일을 저장할 경로 설정)
			// 1_1. 용량 제한하기 (10Mbyte)
			/*
			 * byte - kbyte - mbyte - gbyte - tbyte
			 * */
			int maxSize = 10 * 1024 * 1024;
			
			// 1_2. 전송된 파일을 저장할 서버의 폴더 경로 알아내기
			/*
			 * 세션 객체에서 제공하는 getRealPath 메소드를 이용
			 * Webapp에 board_upfiles 폴더 경로까지는 잡아주어야 한다. 해당 폴더에 저장될 것이기 때문에.
			 * */
			// 경로 마지막에 / 붙여주기 (나중에 우리가 servlet에 경로 작성하며 / 추가해야하지 않도록 작성해두는것. 편의를 위한거라 더 편한 방향으로 진행)
			String savePath = request.getSession().getServletContext().getRealPath("/resources/board_upfiles/");
//			System.out.println(savePath); 
			
			/*
			 * 2. 전달된 파일명 수정 및 서버에 업로드 작업
			 * -HttpServletRequest request -> MultipartRequest multiRequest로 변환하기
			 * 
			 * 매개변수 생성자로 생성
			 * MultipartRequest multiRequest = new MultipartRequest(request객체, 저장할폴더경로, 용량제한, 인코딩값, 파일명 수정객체);
			 * 
			 * 위 구문 한줄이 실행되는 순간 지정한 경로에 파일이 업로드 된다.
			 * 사용자가 올린 파일명은 그대로 해당 폴더에 업로드 하지 않는게 일반적이다 - 같은 파일명이 있을 경우 덮어씌워질 경우도 있고
			 * 한글/특수문자가 포함된 파일명 경우는 업로드되는 서버에 따라 오류를 발생시킬 여지가 있기 때문에.
			 * 
			 * 기본적으로 파일명을 변경해주는 객체를 제공한다.
			 * DefaultFileRenamePolicy 객체
			 * 내부적으로 rename() 이라는 메소드가 실행되며 파일명 수정을 해준다. 
			 * 기본적으로 제공된 객체는 파일명 수정을 파일명 뒤에 숫자를 카운팅하는 형식으로만 진행해준다.
			 * 
			 *  ex)hello.jpg, hello1.jpg, hello2.jpg,...
			 *  
			 *  해당 rename 작업을 따로 정의하여 사용해볼것.
			 *  rename 메소드를 정의하면 해당 작업을 할 수 있다. (내 방식대로)
			 * */
			
			// 서버에 업로드 할 때 save modules without publishing 체크 안하면 서버가 잡는 경로로 지정이 되어서 폴더가 다른곳으로 설정되어서 업로드가 안되는것처럼 보임 
			// 저걸 체크해야 우리가 원하는 경로로 업로드가 되는 것. server option에 들어있음
			
			// 기본 제공 파일명 변경 객체 사용
//			MultipartRequest multiRequest = new MultipartRequest(request, savePath, maxSize, "UTF-8", new DefaultFileRenamePolicy());
			// 직접 만든 파일명 변경 객체 사용
			MultipartRequest multiRequest = new MultipartRequest(request, savePath, maxSize, "UTF-8", new MyFileRenamePolicy());
			
			// 3. DB에 저장할 정보들 추출하기 
			// - 카테고리 번호, 제목, 내용, 작성자회원번호를 추출하여 board 테이블에 insert하기
			String categoryNo = multiRequest.getParameter("category");
			String title = multiRequest.getParameter("title");
			String content = multiRequest.getParameter("content");
			String boardWriter = multiRequest.getParameter("userNo");
			
			Board b = new Board();
			b.setCategoryNo(categoryNo);
			b.setBoardTitle(title);
			b.setBoardContent(content);
			b.setBoardWriter(boardWriter);
//			System.out.println(b);
			
			Attachment at = null; // 처음에는 null로 초기화, 첨부파일이 있다면 객체 생성
			
			// multiRequest.getOriginalFileName("키");
			// 첨부파일이 있을 경우 원본명 / 없는 경우 null을 반환
			
			if(multiRequest.getOriginalFileName("upfile")!=null) {
				// 조회가 된 경우 (첨부파일이 있다)
				at = new Attachment(); // 첨부파일이 있을때만 객체를 생성. 그래서 아래의 조건문에서 null값으로 조건확인해서 첨부파일 유무 확인 가능
				at.setOriginName(multiRequest.getOriginalFileName("upfile")); // 원본명
				at.setChangeName(multiRequest.getFilesystemName("upfile")); // 수정명(실제 서버에 업로드 된 파일명)
				at.setFilePath("/resources/board_upfiles/");
			}
			
			// 서비스에게 준비된 객체들 전달하여 서비스 요청하기
			int result = new BoardService().insertBoard(b,at);
			
			if(result>0) {
				request.getSession().setAttribute("alertMsg", "게시글 작성 성공");
				response.sendRedirect(request.getContextPath()+"/list.bo?currentPage=1");
			} else {// 실패시에는 업로드 된 파일을 지워주는 작업이 필요하다. (게시글은 없는데 업로드 파일이 자원을 쓰고있으니)
				if(at!=null) { // 넘어온 파일이 있어서 객체가 생성됐다면
					// 만들어놓은 filePath와 서버에 저장된 파일 이름으로 저장해둬서 삭제 진행
					// 해당 파일 경로 잡아서 File객체 생성후 delete메소드로 파일 삭제 작업
					new File(savePath+at.getChangeName()).delete();
				}
				request.setAttribute("errorMsg", "게시글 작성 실패");
				request.getRequestDispatcher("view/common/errorPage.jsp").forward(request, response);
			}
		}
	}			
}
