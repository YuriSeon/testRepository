package com.kh.member.model.service;

import java.sql.Connection;

import com.kh.common.JDBCTemplate;
import com.kh.member.model.dao.MemberDao;
import com.kh.member.model.vo.Member;

public class MemberService {

	public Member loginMember(String userId, String userPwd) {
		// 커넥션객체로 db에 접속
		Connection conn = JDBCTemplate.getConnection();
		
		Member m = new MemberDao().loginMember(conn,userId, userPwd);
		
		// 조회는 commit or rollback 할 필요가 없기에 자원반납만
		JDBCTemplate.close(conn);
		
		return m;
	}

	public int insertMember(Member m) {
		Connection conn = JDBCTemplate.getConnection();
		
		int result = new MemberDao().insertMember(conn, m);
		
		if(result>0) {
			JDBCTemplate.commit(conn);
		} else {
			JDBCTemplate.rollback(conn);
		}
		
		JDBCTemplate.close(conn);
		
		return result;
	}

	public Member updateMember(Member m) {
		Connection conn = JDBCTemplate.getConnection();
		
		int result = new MemberDao().updateMember(conn, m);
		// 변경된 회원 정보를 세션에 담아야 하기 때문에 다시 조회해오기
		// result로 결과처리된 성공여부만 알 수 있고 변경된 정보는 db에 저장이 되어있기에 front부분에서는 알 수 없음.
		// db에서 변경된 결과를 다시 조회해와서 session에 다시 변경된 정보를 저장하는 과정을 추가로 해줘야 함.
		
		Member updateMem = null; // 변경된 회원 정보 담을 객체 변수
		
		if(result>0) {
			JDBCTemplate.commit(conn);
			//갱신된 정보 조회해오기
			updateMem = new MemberDao().selectMember(conn, m.getUserId());
		} else {
			JDBCTemplate.rollback(conn);
		}
		JDBCTemplate.close(conn);
		
		return updateMem;
	}

	public Member updatePwd(String userId, String userPwd, String updatePwd) {
		Connection conn = JDBCTemplate.getConnection();
		
		int result = new MemberDao().updatePwd(conn, userId, userPwd, updatePwd);
		
		Member updateMem = null;
		
		if(result>0) {
			JDBCTemplate.commit(conn);
			updateMem = new MemberDao().selectMember(conn, userId);
			
		} else {
			JDBCTemplate.rollback(conn);
		}
		
		JDBCTemplate.close(conn);
		
		return updateMem;
	}

	public int deleteMember(String userId, String userPwd) {
		Connection conn = JDBCTemplate.getConnection();
		
		int result = new MemberDao().deleteMember(conn, userId, userPwd);
		
		if(result>0) {
			JDBCTemplate.commit(conn);
		} else {
			JDBCTemplate.rollback(conn);
		}
		
		JDBCTemplate.close(conn);
		
		return result;
	}

}
