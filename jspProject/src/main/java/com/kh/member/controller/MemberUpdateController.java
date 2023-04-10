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
 * Servlet implementation class MemberUpdateController
 */
@WebServlet("/update.me")
public class MemberUpdateController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberUpdateController() {
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
		// 변경된 정보 수정하기
		// 수정 완료 후 성공시 정보변경 완료되었습니다. 알림 메세지 후 마이페이지로 이동 (변경정보 적용) (재요청)
		// 실패시 에러페이지로 포워딩(위임)
		request.setCharacterEncoding("UTF-8");
		
		String userId = request.getParameter("userId");
		String userName = request.getParameter("userName");
		String phone = request.getParameter("phone");
		String email = request.getParameter("email");
		String address = request.getParameter("address");
		String[] interests = request.getParameterValues("interest");
		String interest = "";
		
		if(interest != null) {
			interest = String.join(",",	interests);
		}
		
		Member m = new Member(userId, userName, phone, email, address, interest);
		
		Member updateMem = new MemberService().updateMember(m);

		HttpSession session = request.getSession();
		
		// 갱신한 정보 조회를 성공했다면 객체가 담겨있지만 실패했다면 null로 넘어오기때문에 null값인지 아니지를 확인하면 됨 (service에서 null로 초기화 했던 변수였음)
		if(updateMem !=null) {
			session.setAttribute("alertMsg", "정보변경 완료되었습니다.");
			session.setAttribute("loginUser", updateMem); // 동일 key값으로 작성하면 내용 덮어쓰기(갱신)됨.
			// 루트 뒤에 마이페이지 요청 매핑주소 넣기
			response.sendRedirect(request.getContextPath()+"/myPage.me");
		} else {
			request.setAttribute("errorMsg", "정보 변경 실패");
			request.getRequestDispatcher("views/common/errorPage.jsp").forward(request, response);
		}
		
	}

}
