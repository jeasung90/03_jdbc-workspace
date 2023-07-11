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
	 * @param m	입력받은 정보
	 * @return result 처리된 행 수
	 */
	public int insertMember(Member m) {
		// insert문 => 처리된 행수 => 트랜젝션 처리
		
		int result = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		// 실행할 sql문( 미완성된 형태로 둘 수 있음 )
		// INSERT INTO MEMBER VALUES(seq_userno.nextval,'XXX','XXX','XXX','X',XX ,'XXX','XXXXXX','XXX','XXX,XXX',SYSDATE)
		// 미리 사용자가 입력한 값들이 들어갈 수 있게 공간확보 (? == 홀더) 만 해두면 됨!
		String sql = "INSERT INTO MEMBER VALUES(seq_userno.nextval,?,?,?,?,? ,?,?,?,?,SYSDATE)";
		
		// 1) jdbc driver 등록
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			// 2) Connection 생성
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "JDBC", "JDBC");
			
			// 3) prepareStatement 객체 생성
			pstmt = conn.prepareStatement(sql); // 애초에 pstmt 객체 생성시 sql문 담은채로 생성! (지금은 미완성)
			
			// > 빈공간을 실제값 (사용자가 입력한 값)으로 채워준 후 실행
			// pstmt.setString(홀더순번, 대체할 값); => '대체할 값' (홑따옴표 자동으로 해줌)
			// pstmt.setInt(홀더순번, 대체할 값);
			
			
			pstmt.setString(1, m.getUserId());
			pstmt.setString(2, m.getUserPwd());
			pstmt.setString(3, m.getUserName());
			pstmt.setString(4, m.getGender());
			pstmt.setInt(5, m.getAge());
			pstmt.setString(6, m.getEmail());
			pstmt.setString(7, m.getPhone());
			pstmt.setString(8, m.getAddress());
			pstmt.setString(9, m.getHobby());
			
			// 4,5) sql 문 실행 및 결고 ㅏ받기
			
			result = pstmt.executeUpdate(); // 여기서는 sql문 전달하지 않고 그냥 실행해야됨!! 이미 pstmt에 들어가 있음
			


			
			
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

	/**2. 전체조회 (ArrayList)
	 * 회원 전체 조회 메소드
	 * @return
	 */
	public ArrayList<Member> selectList() {
		// select문 (여러행) => ResultSet 객체
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
		// select문 => 한행문 => ResultSet 객체 => Member
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
		
		String sql = "SELECT * FROM MEMBER WHERE USERNAME LIKE ? ";
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "JDBC", "JDBC");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, "%"+userName+"%");
			
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
		// update Member set userpwd =? Emil?phone?address? where userid , pw
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
		
		// delete from member where userid = ? and userpwd =?
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
