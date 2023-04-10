package com.kh.board.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.board.model.service.BoardService;
import com.kh.board.model.vo.Board;
import com.kh.common.model.vo.PageInfo;

/**
 * Servlet implementation class BoardListController
 */
@WebServlet("/list.bo")
public class BoardListController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardListController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//-----------------페이징 처리-----------------------
		
		// 총 게시물의 개수를 알아야 페이지 수 처리할 수 있음 (한 페이지당 몇 개의 게시물을 띄울건지, 페이지의 마지막 수, 페이지 인덱스가 끝화면과 일치하도록 처리 필요, 마지막페이지에서 다음 버튼을 눌러도 변화 없도록 설정) 
		
		int listCount; // 현재 총 게시글의 개수
		int currentPage; // 현재 페이지
		int pageLimit; // 페이지 하단에 보여질 페이징바의 페이지 최대 개수
		int boardLimit; // 한 페이지에서 보여질 게시글 최대 개수
		int maxPage; // 가장 마지막 페이지가 몇인지 (총 페이지의 개수와 같음)
		int startPage; // 페이지 하단에 보여질 페이징바의 시작 수 (1-5/6-10 이런식의 맨 앞부분 수 1,6)
		int endPage; // 페이지 하단에 보여질 페이징바의 끝 수 (위의 예시에서 5,10)
		
		// listCount : 총 게시글 개수 구하기
		listCount = new BoardService().selectListCount();
//		System.out.println(listCount); // 확인용 
		
		// currentPage : 현재 페이지
		currentPage = Integer.parseInt(request.getParameter("currentPage"));
//		System.out.println(currentPage); // 확인용
		
		// pageLimit : 페이지 하단에 보여질 페이징바의 페이지 최대 개수 (목록 단위)
		pageLimit = 10;
		
		// boardLimit : 한 페이지에 보여질 게시글 개수 (게시글 단위)
		boardLimit = 10;
		
		// maxPage : listCount와 boardLimit의 영향을 받는 수
		
		/*
		 * - 공식 찾기
		 * 	게시글 총 개수		boardLimit		maxPage
		 * 		100개 				10			 10
		 * 		101개				10			 11
		 * 		102개				10			 12
		 * 나눗셈 후 올림 처리를 통해 maxPage를 구하자
		 * 
		 * 1) listCount를 double자료형으로 바꾸기
		 * 2) listCount/boardLimit
		 * 3) 결과를 올림처리 (Math.ceil()메소드)
		 * 4) 결과값을 int로 마무리
		 * */
		maxPage = (int)Math.ceil((double)listCount/boardLimit);
//		System.out.println(maxPage); // 확인용. 위에서 페이지수 107개 나왔으니 11로 나오면 됨
		
		// startPage : 페이징바의 시작 수
		/*
		 * 공식 찾기
		 * startPage : 1, 11, 21, 31, 41,.... n*pageLimit+1
		 * currentPage startPage pageLimit:10
		 * 1			1	=> 0*pageLimit+1 =1
		 * 5			1
		 * 10			1
		 * 11			11	=> 1*pageLimit+1 =11
		 * 15			11
		 * 21			21
		 * 
		 * (currentPage-1)/pageLimit *pageLimit+1 => pageLimit이 현재페이지일때 넘어가버릴 수 있어서 -1 해줘야함 
		 * */
		
		startPage = (currentPage-1)/pageLimit *pageLimit +1;
		
		// 1-10 / 11-20 ... : starPage + pageLimit-1
		// endPage : 페이징바 끝 수 
		endPage = startPage+pageLimit-1;
		
		// 총 페이지수가 13페이지라면?
		// startPage : 11 / endPage : 20 이 나오는 상황이 생겨서 처리해줘야함.
		// endPage>maxPage 일때의 조건을 걸어주기
		
		if(endPage>maxPage) { // 끝수가 총 페이지보다 크다면 해당 수를 총 페이지수로 바꿔주기
			endPage = maxPage;
		}
		
		// 페이지 정보들을 하나의 공간에 담아 보내기 (VO이용)
		PageInfo pi = new PageInfo(listCount, currentPage, pageLimit, boardLimit, maxPage, startPage, endPage);
		
//		System.out.println(pi);
		
		// 현재 사용자가 요청한 페이지에 (currentPage)에 보여질 게시글 리스트 조회
		ArrayList<Board> list = new BoardService().selectList(pi);
		
//		for(Board b : list) {
//			System.out.println(b);
//		} // 확인용
		
		//조회된 리스트와 페이징정보 request로 보내기
		request.setAttribute("list", list);
		request.setAttribute("pi", pi);
		request.getRequestDispatcher("views/board/boardListView.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
