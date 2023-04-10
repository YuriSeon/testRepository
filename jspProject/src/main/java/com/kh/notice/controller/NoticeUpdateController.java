package com.kh.notice.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.notice.model.service.NoticeService;
import com.kh.notice.model.vo.Notice;

/**
 * Servlet implementation class NoticeUpdateController
 */
@WebServlet("/update.no")
public class NoticeUpdateController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoticeUpdateController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 처음에 jsp 실행되기전에 연결하며 수정 전 객체를 보내줘야해서 get방식으로 작성
		int noticeNo = Integer.parseInt(request.getParameter("no"));
		Notice n = new NoticeService().selectNotice(noticeNo);
		
		request.setAttribute("notice", n);
		request.getRequestDispatcher("views/notice/noticeUpdate.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// submit버튼을 눌렀을 때 실행될 구문을 작성. 
		request.setCharacterEncoding("UTF-8");
		// 여기서 가져오는건 input상자 hidden상태로 숨겨서 가져온걸 가져온거고 매핑주소에서서는 가져올 수 없음.
		// 매핑주소에 있는건 jsp페이지가 읽히며 실행이 되면서 값을 꺼내올 수 없게 된다. 위의 get방식에서는 페이지가 생성되기 전이라서 값을 꺼내올 수 있었던 것임.
		int noticeNo = Integer.parseInt(request.getParameter("nno"));
		Notice n = new Notice();
		String reTitle = request.getParameter("title");
		String reContent = request.getParameter("content");
		n.setNoticeNo(noticeNo);
		n.setNoticeTitle(reTitle);
		n.setNoticeContent(reContent);
		
		int result = new NoticeService().updateNotice(n);
		
		if(result>0) { // 수정한 글번호를 이용해서 상세보기 페이지 보내기
			request.getSession().setAttribute("alertMsg", "공지사항 수정 완료");
			response.sendRedirect(request.getContextPath()+"/list.no");
		} else {
			request.setAttribute("errorMsg", "공지사항 수정 실패");
			request.getRequestDispatcher("views/common/errorPage.jsp").forward(request, response);
		}
	}

}
