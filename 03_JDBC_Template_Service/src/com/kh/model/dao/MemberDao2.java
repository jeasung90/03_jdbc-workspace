package com.kh.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.kh.model.vo.Member;
import com.kh.view.MemberMenu;

public class MemberDao2 {

	
	
	
	
	/** 1. 신규 등록
	 * 회원 추가하는 메소드
	 * @param m	입력받은 정보
	 * @return result 처리된 행 수
	 */
	public int insertMember(Member m) {
		
		int result = 0;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		String sql = "INSERT INTO MEMBER VALUES(seq_userno.nextval,?,?,?,?,? ,?,?,?,?,SYSDATE)";
		 
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "JDBC", "JDBC");
			
			pstmt = conn.prepareStatement(sql); // 애초에 pstmt 객체 생성시 sql문 담은채로 생성! (지금은 미완성)
			
			pstmt.setString(1, m.getUserId());
			pstmt.setString(2, m.getUserPwd());
			pstmt.setString(3, m.getUserName());
			pstmt.setString(4, m.getGender());
			pstmt.setInt(5, m.getAge());
			pstmt.setString(6, m.getEmail());
			pstmt.setString(7, m.getPhone());
			pstmt.setString(8, m.getAddress());
			pstmt.setString(9, m.getHobby());
			
			
			result = pstmt.executeUpdate(); // 여기서는 sql문 전달하지 않고 그냥 실행해야됨!! 이미 pstmt에 들어가 있음
			
			// 여기서 SQLException 날수도 있기 때문에 뒤에 커밋과 롤백이 있으면 실행이 안됨
			// 문제가 생기면 무조건 롤백을 해야함
			
			
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt != null && !pstmt.isClosed()) {
					pstmt.close();
				}
				if(conn != null && !conn.isClosed()) {
				conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(result > 0 ) {
			try {
				if(conn !=null && conn.isClosed()) {
				conn.commit();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else {
			try {
				if(conn !=null && conn.isClosed()) {
				conn.rollback();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	/**2. 전체조회 (ArrayList)
	 * 회원 전체 조회 메소드
	 * @return
	 */
	public ArrayList<Member> selectList() {

		ArrayList<Member> list = new ArrayList<Member>(); // 텅 빈 리스트
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = "SELECT * FROM MEMBER";
		
		
		
			try {
				
				Class.forName("oracle.jdbc.driver.OracleDriver");
				
				conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "JDBC", "JDBC");
			
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
				
				
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				try {
					rset.close();
					pstmt.close();
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		return list;
	}

	/**3. 아이디로 찾는 구문
	 * 사용자의 아이디로 회원 검색 요청을 처리해주는 메소드
	 * @param userId : 사용자가 입력한 검색하고자 하는 회원 아이디 값
	 * @return m : 그 아이디를 가진 회원
	 */
	public Member selectByUserId(String userId) {

		Member m = null;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = "SELECT * FROM MEMBER WHERE USERID = ?";
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "JDBC", "JDBC");
			
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

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				rset.close();
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		
		return m;
	}

	/** 4. 이름키워드로 정보 찾기
	 * @param userName
	 * @return
	 */
	public ArrayList<Member> selectByUserName(String userName) {
		
		ArrayList<Member> list = new ArrayList<Member>(); // 텅 빈 리스트
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = "SELECT * FROM MEMBER WHERE USERNAME LIKE ? "; // CASE 1

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "JDBC", "JDBC");
			
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
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				rset.close();
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		return list;
	}

	/**
	 * 5. 회원 변경
	 * @param userId
	 * @param userPwd
	 * @param email
	 * @param phone
	 * @param address 
	 * @return
	 */
	public int updateMember(String userId, String userPwd1,String userPwd2, String email, String phone, String address) {
		
		int result = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;

		String sql = "UPDATE MEMBER SET USERPWD = ?," //1
									 + "EMAIL = ?,"		// 2
									 + "PHONE = ?,"		// 3
									 + "ADDRESS = ?"	// 4
									 + "WHERE USERID = ? " // 5
									 + "AND USERPWD = ?"; // 6
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "JDBC", "JDBC");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, userPwd2);
			pstmt.setString(2, email);
			pstmt.setString(3, phone);
			pstmt.setString(4, address);
			pstmt.setString(5, userId);
			pstmt.setString(6, userPwd1);
			
			result = pstmt.executeUpdate();
			
			if(result > 0 ) {
				conn.commit();
			}else {
				conn.rollback();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 6. 계정 삭제
	 * @param delId
	 * @param delPwd
	 * @return
	 */
	public int deleteMember(String delId, String delPwd) {

		int result = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		String sql = "DELETE FROM MEMBER WHERE USERID = ? AND USERPWD = ?";
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "JDBC", "JDBC");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, delId );
			pstmt.setString(2, delPwd);
			
			result = pstmt.executeUpdate();
			
			if(result > 0 ) {
				conn.commit();
			}else {
				conn.rollback();
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
		
		return result;
	}
}
