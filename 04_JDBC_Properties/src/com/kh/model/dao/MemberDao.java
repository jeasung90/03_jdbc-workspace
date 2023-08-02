package com.kh.model.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

import static com.kh.common.JDBCTemplate.*;
import com.kh.model.vo.Member;
import com.kh.view.MemberMenu;

// DAO (Date Access Object) : DB에 직접적으로 접근해서 사용자의 요청에 맞는 sql문 실행 후 결과받기(jdbc)
//								결과를 controller로 다시 리턴
public class MemberDao {

	/*
	 *  기존의 방식 : DAO 클래스가 사용자가 요청할 때마다 실행햐야되는 SQL문을 자바소스코드 내에 명시적으로 작성 => 정적코딩방식
	 *  
	 *  > 문제점 : SQL문을 수정해야 될 경우 자바소스코드를 수정해야됨 => 수정된 내용을 반영시키고자 한다면 프로그램을 재구동 해야함!
	 *  
	 *  > 해결방식 : SQL문들을 별도로 관리하는 외부파일(.xml)을 만들어서 실시간으로 그 파일에 기록된 sql문을 읽어드려서 실행 => 동적코딩방식
	 *  			여러줄 쓸 수 있도록 => xml로 하는게 좋음
	 *  
	 */
	
	private Properties prop = new Properties();
	
	public MemberDao() {
		try {
			prop.loadFromXML(new FileInputStream("resources/query.xml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
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
		
		String sql = prop.getProperty("insertMember");
		
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
		
		String sql = prop.getProperty("selectList");
		
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
		
		String sql = prop.getProperty("selectByUserId");
		
		try {
			
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, userId);
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
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
		
		String sql = prop.getProperty("selectByUserName"); // CASE 1
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
		String sql = prop.getProperty("updateMember");
		
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
		String sql = prop.getProperty("deleteMember");
		
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
