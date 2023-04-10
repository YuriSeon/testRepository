package com.kh.board.model.service;

import java.sql.Connection;
import java.util.ArrayList;

import com.kh.board.model.dao.BoardDao;
import com.kh.board.model.vo.Attachment;
import com.kh.board.model.vo.Board;
import com.kh.board.model.vo.Category;
import com.kh.common.JDBCTemplate;
import com.kh.common.model.vo.PageInfo;

public class BoardService {
	
	// 총 게시글 개수 구하는 메소드
	public int selectListCount() {
		Connection conn = JDBCTemplate.getConnection();
		
		int listCount = new BoardDao().selectListCount(conn);
		
		// 처리된 행의 수가 아닌 총 게시글의 개수를 조회만 해온 것. 그래서 트랜잭션 처리 안해도 됨
		JDBCTemplate.close(conn);
		
		return listCount;
		
	}

	public ArrayList<Board> selectList(PageInfo pi) {
		Connection conn = JDBCTemplate.getConnection();
		
		ArrayList<Board> list = new BoardDao().selectListCount(conn, pi);
		
		JDBCTemplate.close(conn);
		
		return list;
	}
	
	public ArrayList<Category> categoryList(){
		Connection conn = JDBCTemplate.getConnection();
		
		ArrayList<Category> list = new BoardDao().categoryList(conn);
		
		JDBCTemplate.close(conn);
		
		return list;
	}

	public int increaseCount(int boardNo) {
		Connection conn = JDBCTemplate.getConnection();
		
		int result = new BoardDao().increaseCount(conn, boardNo);
		
		if(result>0) {
			JDBCTemplate.commit(conn);
		} else {
			JDBCTemplate.rollback(conn);
		}
		return result;
	}

	public Board selectBoard(int boardNo) {
		Connection conn = JDBCTemplate.getConnection();
		
		Board b = new BoardDao().selectBoard(conn, boardNo);
		
		JDBCTemplate.close(conn);
		
		return b;
	}

	public int insertBoard(Board b, Attachment at) {
		Connection conn = JDBCTemplate.getConnection();
		
		// 게시글이 작성될 때 첨부파일이 있거나 또는 없는 경우도 생각하여 작업하기
		int result = new BoardDao().insertBoard(conn, b);
		
		int result2 = 1; // 첨부파일이 없더라도 게시글 commit은 해야하니 해당 조건에 부합하게 1로 초기화 해놓기 
		
		//첨부파일이 있는 경우에 Attachment 테이블에 insert 작업하기
		if(at!=null) {
			//첨부파일업로드가 성공하면 조건문 안으로 들어와서 초기화가 다시 되니 판별가능
			result2 = new BoardDao().insertAttachment(conn, at);
		}
		
		if(result>0 && result2>0) {
			JDBCTemplate.commit(conn);
		} else {
			JDBCTemplate.rollback(conn);
		}
		
		JDBCTemplate.close(conn);
		
		return result*result2; // 둘 중 하나라도 0이 나오면 0을 반환하게 작성하기
	}

	public Category selectCategory(int boardNo) {
		Connection conn = JDBCTemplate.getConnection();
		
		Category c = new BoardDao().selectCategory(conn, boardNo);
		
		JDBCTemplate.close(conn);
		
		return c;
	}

	public Attachment selectAttachment(int boardNo) {
		Connection conn = JDBCTemplate.getConnection();
		
		Attachment a = new BoardDao().selectAttachment(conn, boardNo);
		
		JDBCTemplate.close(conn);
		
		return a;
	}

	// 정보 수정 메소드
	public int updateBoard(Board b, Attachment at) {
		
		Connection conn = JDBCTemplate.getConnection();
		
		// 새로운 첨부파일 없고 기존 첨부파일도 없는 경우 - board update
		// 새로운 첨부파일 있고 기존 첨부파일 없는 경우 - board - update / Attachment -insert
		// 새로운 첨부파일 있고 기존 첨부파일 있는 경우 - board - update / Attachment - update
		
		int result = new BoardDao().updateBoard(conn, b, at);
		
		int result2 =1;
		
		if(at != null) { // 새롭게 추가된 첨부파일이 있는 경우
			if(at.getFileNo() != 0) { // 기존의 첨부파일이 있었을 경우 (변경)
				result2 = new BoardDao().updateAttachment(conn, at);
			} else { // 기존의 첨부파일이 없었을 경우 (추가)
				result2 = new BoardDao().newInsertAttachment(conn, at);
			}
		}
		if(result>0 && result2>0) {
			JDBCTemplate.commit(conn);
		} else {
			JDBCTemplate.rollback(conn);
		}
		
		JDBCTemplate.close(conn);
		
		return result;
	}

	public int deleteBoard(int bno) {
		Connection conn = JDBCTemplate.getConnection();
		
		int result = new BoardDao(). deleteBoard(conn, bno);
		
		if(result>0) {
			JDBCTemplate.commit(conn);
		} else {
			JDBCTemplate.rollback(conn);
		}
		
		JDBCTemplate.close(conn);
		
		return result;
	}


}
