package com.kh.member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class LogoutController
 */
@WebServlet("/logout.me")
public class LogoutController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LogoutController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 로그아웃 처리
		// 로그인 되어있는 loginUser객체 session에서 삭제시키기
		HttpSession session = request.getSession();
		// session을 다시 생성하는것처럼 보여도 이미 생성된 session의 기존 정보를 가져오는 것. 새롭게 생성된 session에는 정보가 없기에 의미가 없어서 기존 session이 생성되는 것
		
		// 로그인된 회원정보 객체 삭제
		session.removeAttribute("loginUser");
		
		// 만약 세션을 초기화 하고싶다면?
		// session.invalidate(); // 세션만료 (초기화) -- 로그아웃 하고나면 세션에 담겨있는 값을 사용할 필요가 없어지니 세션만료(초기화)하는 것.
		
		// 재요청 방식으로 메인페이지로 돌아가기
		response.sendRedirect(request.getContextPath()); // /jsp로 돌아감
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
