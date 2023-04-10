package com.kh.member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kh.member.model.service.MemberService;
import com.kh.member.model.vo.Member;

/**
 * Servlet implementation class UpdatePwdController
 */
@WebServlet("/updatePwd.me")
public class UpdatePwdController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdatePwdController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		
		// 방법 1. hidden 타입으로 숨겨서 보낸 데이터 꺼내보기 (session에 없는 경우에는 hidden으로 숨겨서 데이터를 담아서 보내줘야한다.)
		//String userId = request.getParameter("userId");
		
		
		// 방법 2. session에서 loginUser로 id값 꺼내오기
		HttpSession session = request.getSession();
		
		// 마지막엔 String으로 아이디값을 받지만 session에서 꺼내올때는 Member로 형변환 후 getter메소드로 꺼내오기
		String userId = ((Member)session.getAttribute("loginUser")).getUserId();
		String userPwd = request.getParameter("userPwd");
		String updatePwd = request.getParameter("updatePwd");
		
		Member updateMem = new MemberService().updatePwd(userId, userPwd, updatePwd);
		
		if(updateMem != null) {
			
			session.setAttribute("loginUser", updateMem);
			session.setAttribute("alertMsg", "비밀번호 변경 성공하셨습니다.");
			response.sendRedirect(request.getContextPath());
			
		} else {
			
			request.setAttribute("errorMsg", "비밀번호 변경 실패");
			request.getRequestDispatcher("views/common/errorPage.jsp").forward(request, response);
			
		}
		
	}

}
