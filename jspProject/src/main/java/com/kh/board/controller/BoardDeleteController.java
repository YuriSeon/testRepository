package com.kh.board.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.board.model.service.BoardService;
import com.kh.board.model.vo.Attachment;

/**
 * Servlet implementation class BoardDeleteController
 */
@WebServlet("/delete.bo")
public class BoardDeleteController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardDeleteController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int bno = Integer.parseInt(request.getParameter("bno"));
			
//		System.out.println(bno);
			
		int result = new BoardService().deleteBoard(bno);
		
			System.out.println(result);
			
			if(result>0) {
				Attachment a = new BoardService().selectAttachment(bno);
				if(a!=null) {
					System.out.println(request.getContextPath()+a.getFilePath()+a.getChangeName());
					System.out.println(a.getFilePath()+a.getChangeName());
					new File(a.getFilePath()+a.getChangeName()).delete();
				}
				request.getSession().setAttribute("alertMsg", "게시물 삭제 성공");
				response.sendRedirect(request.getContextPath()+"/list.bo?currentPage=1");
				
			} else {
				request.setAttribute("errorMsg", "게시물 삭제 실패");
				request.getRequestDispatcher("views/common/errorPage.jsp").forward(request, response);;
				
			}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
