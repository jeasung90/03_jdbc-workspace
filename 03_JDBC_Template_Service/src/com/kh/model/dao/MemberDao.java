package com.kh.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static com.kh.common.JDBCTemplate.*;
import com.kh.model.vo.Member;
import com.kh.view.MemberMenu;

// DAO (Date Access Object) : DB에 직접적으로 접근해서 사용자의 요청에 맞는 sql문 실행 후 결과받기(jdbc)
//								결과를 controller로 다시 리턴
public class MemberDao {

	/*
	 *  * Statement 와 PreparedStatement 의 특징
	 *  - 둘 다 sql문 실행하고 결과를 받아내는 객체 ( 둘 중 하나 쓰면 됨 )
	 * 
	 * - Statement 와 PreparedStatement 의 차이점
	 * - Statement 같은 경우 sql문을 바로 전달하면서 실행시키는 객체
	 * 	 (즉, sql문을 완성 형태로 만들어 둬야됨!! 사용자가 입력한 값이 다 채워진 형태로!)
	 * 			
	 * 		> 기존의 Statement 방식
	 * 		1) Connection 객체를 통해 Statement 객체 생성 : stmt = conn.createStatement();
	 * 		2) Statement 객체를 통해 "완성된 sql문"실행 및 결과 받기 결과 = stmt.executeXXX(완성된 sql문);
	 * 
	 * - PreparedStatement 같은 경우 "미완성된 sql문"을 잠시 보관해둘 수 있는 객체
	 * 	 (즉, 사용자가 입력한 값들을 채워두지 않고, 각각 들어갈 공간만 확보만 미리 해놓아도 됨)
	 * 	 단, 해당 sql문 본격적으로 실행하기 전에는 빈공간을 사용자가 입력한 값으로 채워서 실행하긴 해야됨
	 * 
	 * 		> PreparedStatment 방식
	 * 		1) Connection 객체를 통해 PreparedStatment 객체 생성 : pstmt = conn.PreparedStatment(미완성된 sql문);
	 * 		2) pstm에 담긴 sql문이 미완성 상태일 경우 우선은 완성시켜야함. pstmt.setXXX(1, '대체할 값');
	 * 		3) 해당 완성된 sql문 실행 결과 받기			: 결과 = pstmt.executeXXX();
	 */
	
	
	
	
	/**
	 * 회원 추가하는 메소드
	 * @param conn2 
	 * @param m	입력받은 정보
	 * @return result 처리된 행 수
	 */
	public int insertMember(Connection conn, Member m) {
		// insert문 => 처리된 행수 => 트랜젝션 처리
		
		int result = 0;
		// Conn 은 만들어서 가져옴
		PreparedStatement pstmt = null;
		
		String sql = "INSERT INTO MEMBER VALUES(seq_userno.nextval,?,?,?,?,? ,?,?,?,?,SYSDATE)";
		
		try {
			
			
			// 3) prepareStatement 객체 생성
			pstmt = conn.prepareStatement(sql); 
			
			pstmt.setString(1, m.getUserId());
			pstmt.setString(2, m.getUserPwd());
			pstmt.setString(3, m.getUserName());
			pstmt.setString(4, m.getGender());
			pstmt.setInt(5, m.getAge());
			pstmt.setString(6, m.getEmail());
			pstmt.setString(7, m.getPhone());
			pstmt.setString(8, m.getAddress());
			pstmt.setString(9, m.getHobby());
			
			// 4,5) sql 문 실행 및 결과 받기
			
			result = pstmt.executeUpdate();
			
			
			

			
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
			// conn은 아직 반납하면 안됨! (트랜젝션 처리 서비스 가서 해야함!)
		}
		
		return result;
	}

	/**2. 전체조회 (ArrayList)
	 * 회원 전체 조회 메소드
	 * @param conn 
	 * @return
	 */
	public ArrayList<Member> selectList(Connection conn) {
		// select문 (여러행) => ResultSet 객체
		ArrayList<Member> list = new ArrayList<Member>(); // 텅 빈 리스트
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = "SELECT * FROM MEMBER";
		
			try {
				
				pstmt = conn.prepareStatement(sql);
				
				rset = pstmt.executeQuery();
				
				while(rset.next()) {
					
					list.add(new Member(rset.getInt("userno"),
										rset.getString("userid"),
										rset.getString("userpwd"),
										rset.getString("username"),
										rset.getString("gender"),
										rset.getInt("age"),
										rset.getString("email"),
										rset.getString("phone"),
										rset.getString("address"),
										rset.getString("hobby"),
										rset.getDate("enrolldate")
										));
				}
				
				
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				close(rset);
				close(pstmt);
			}
		return list;
	}

	/**3. 아이디로 찾는 구문
	 * 사용자의 아이디로 회원 검색 요청을 처리해주는 메소드
	 * @param conn2 : 사용자가 입력한 검색하고자 하는 회원 아이디 값
	 * @param userId 
	 * @return m : 그 아이디를 가진 회원
	 */
	public Member selectByUserId(Connection conn, String userId) {
		// select문 => 한행문 => ResultSet 객체 => Member
		Member m = null;
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = "SELECT * FROM MEMBER WHERE USERID = ?";
		
		try {
			
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, userId);
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				m = new Member(rset.getInt("userno"),
										rset.getString("userid"),
										rset.getString("userpwd"),
										rset.getString("username"),
										rset.getString("gender"),
										rset.getInt("age"),
										rset.getString("email"),
										rset.getString("phone"),
										rset.getString("address"),
										rset.getString("hobby"),
										rset.getDate("enrolldate")
										);
			}else {
				
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
			
		}
		
		return m;
	}

	/** 4. 이름키워드로 정보 찾기
	 * @param conn 
	 * @param userName
	 * @return
	 */
	public ArrayList<Member> selectByUserName(Connection conn, String userName) {
		
		ArrayList<Member> list = new ArrayList<Member>(); // 텅 빈 리스트
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = "SELECT * FROM MEMBER WHERE USERNAME LIKE ? "; // CASE 1
		//String sql = "SELECT * FROM MEMBER WHERE USERNAME LIKE '%' || ? || '%' "; // CASE2
		try {
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, "%"+userName+"%"); // CASE 1
			//pstmt.setString(1, userName); // CASE 2
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				
				list.add(new Member(rset.getInt("userno"),
									rset.getString("userid"),
									rset.getString("userpwd"),
									rset.getString("username"),
									rset.getString("gender"),
									rset.getInt("age"),
									rset.getString("email"),
									rset.getString("phone"),
									rset.getString("address"),
									rset.getString("hobby"),
									rset.getDate("enrolldate")
									));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
			
		}
		return list;
	}

	/**
	 * 5. 회원 변경
	 * @param conn 
	 * @param userId
	 * @param userPwd
	 * @param email
	 * @param phone
	 * @param address 
	 * @return
	 */
	public int updateMember(Connection conn, String userId, String userPwd1,String userPwd2, String email, String phone, String address) {
		
		int result = 0;
		PreparedStatement pstmt = null;
		// update Member set userpwd =? Emil?phone?address? where userid , pw
		String sql = "UPDATE MEMBER SET USERPWD = ?," //1
									 + "EMAIL = ?,"		// 2
									 + "PHONE = ?,"		// 3
									 + "ADDRESS = ?"	// 4
									 + "WHERE USERID = ? " // 5
									 + "AND USERPWD = ?"; // 6
		
		try {
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, userPwd2);
			pstmt.setString(2, email);
			pstmt.setString(3, phone);
			pstmt.setString(4, address);
			pstmt.setString(5, userId);
			pstmt.setString(6, userPwd1);
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		return result;
	}

	/**
	 * 6. 계정 삭제
	 * @param conn 
	 * @param delId
	 * @param delPwd
	 * @return
	 */
	public int deleteMember(Connection conn, String delId, String delPwd) {

		int result = 0;
		PreparedStatement pstmt = null;
		
		// delete from member where userid = ? and userpwd =?
		String sql = "DELETE FROM MEMBER WHERE USERID = ? AND USERPWD = ?";
		
		try {
			
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, delId);
			pstmt.setString(2, delPwd);
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		
		
		
		return result;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
