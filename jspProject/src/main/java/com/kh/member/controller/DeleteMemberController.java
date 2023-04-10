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
 * Servlet implementation class DeleteMemberController
 */
@WebServlet("/delete.me")
public class DeleteMemberController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteMemberController() {
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
		
		// Parameter값을 꺼내올때만 인코딩을 해야하고 session에서 값을 꺼내올때는 인코딩을 하지 않아도 됨
		// session에는 이미 인코딩 된 상태로 값을 저장해두기 때문!
		HttpSession session = request.getSession();
		
//		String userId = request.getParameter("userId"); // hidden으로 숨겨온 것 Parameter영역에서 바로 꺼내오기 (방법 1)
		String userId = ((Member)session.getAttribute("loginUser")).getUserId(); // loginUser에서 꺼내오기 (방법 2)
		String userPwd = request.getParameter("userPwd");
		
		int result = new MemberService().deleteMember(userId, userPwd);
		
		if(result>0) {
			session.removeAttribute("loginUser"); // 로그인된 회원정보 삭제
			session.setAttribute("alertMsg", "그동안 감사했습니다. 다시 뵙길 바라겠습니다.");
			response.sendRedirect(request.getContextPath());
			// 서버한테 요청하면 클라이언트쪽으로 바로 
			// 실제로 데이터에 변경이 생기거나 수정한다면 redirect로 다시 수정되지 않도록 막기위해 사용해야함
		} else {
			session.setAttribute("alertMsg", "회원 탈퇴 실패");
			request.getRequestDispatcher("views/common/errorPage.jsp").forward(request, response);
			// request.getRequestDispatcher()이 메소드자체가 (webapp/)를 찍는것 그래서 뒤의 경로를 지정해줘야 사용 가능 
			
			// jsp파일을 불러오고 나서 그 jsp파일에서 서블릿을 부르며 페이지가 실행됨
			// 데이터에 변동이 생기지 않고 다시 재실행 되더라도 문제가 되지 않는 경우에 사용함.
		}
	}

}
