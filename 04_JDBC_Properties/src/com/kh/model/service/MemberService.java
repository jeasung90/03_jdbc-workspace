package com.kh.model.service;

import java.sql.Connection;
import java.util.ArrayList;

import static com.kh.common.JDBCTemplate.*; // 이렇게 하면 JDBCTemplate에 있는 모든 스테틱 메소드 그냥 호출 가능 스테틱 붙이고 끝에 .*
import com.kh.model.dao.MemberDao;
import com.kh.model.vo.Member;

public class MemberService {

	/**
	 * 1. 신규등록
	 * JDBCTemplate 을 이용하여 conn을 가져오고 
	 * JDBCTemplate 을 이용하여 commit 과 rollback을 해주고
	 * JDBCTemplate 을 통하여 자원 반납함
	 * @param m
	 * @return
	 */
	public int insertMember(Member m) {

		// 1) jdbc driver 등록
		// 2) Connection 객체 생성
		Connection conn = /*JDBCTemplate.*/getConnection();
		
		int result = new MemberDao().insertMember(conn,m); // pstmt 만들려면 conn 필요함
		
		// 6) 트랜젝션 처리
		if(result > 0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		
		// 7) 다쓴자원 반납처리
		close(conn);
		
		return result;
	}

	/**
	 * 2. 전체 조회
	 * @return
	 */
	public ArrayList<Member> selectList() {
		
		Connection conn = getConnection();
		
		ArrayList<Member> list =  new MemberDao().selectList(conn);
		
		close(conn);
		
		return list;
	}

	/**
	 * 3. 회원 아이디 검색
	 * @param userId
	 * @return
	 */
	public Member selectByUserId(String userId) {
		
		Connection conn = getConnection();
		
		Member m = new MemberDao().selectByUserId(conn,userId);
		
		close(conn);
		
		return m;
	}

	/**
	 * 4. 이름 키워드 검색
	 * @param userName
	 * @return
	 */
	public ArrayList<Member> selectByUserName(String userName) {
		
		Connection conn = getConnection();
		
		ArrayList<Member> list = new MemberDao().selectByUserName(conn,userName);
		
		close(conn);
		
		return list;
	}

	/**
	 * 5. 회원정보 변경
	 * @param userId
	 * @param userPwd1
	 * @param userPwd2
	 * @param email
	 * @param phone
	 * @param address
	 * @return
	 */
	public int updateMember(String userId, String userPwd1, String userPwd2, String email, String phone,
			String address) {
		Connection conn = getConnection();
		
		int result = new MemberDao().updateMember(conn,userId, userPwd1, userPwd2, email, phone, address);
		
		if(result > 0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		
		close(conn);
		
		return result;
	}

	/**
	 * 6. 계정 삭제
	 * @param delId
	 * @param delPwd
	 * @return
	 */
	public int deleteMember(String delId, String delPwd) {
		Connection conn = getConnection();
		
		int result = new MemberDao().deleteMember(conn, delId, delPwd);
				
		if(result > 0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		
		close(conn);
		return 0;
	}

}
