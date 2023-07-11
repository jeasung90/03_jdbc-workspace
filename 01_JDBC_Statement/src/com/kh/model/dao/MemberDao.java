package com.kh.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
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
	 *  * JDBC용 객체
	 *  - Connection  			: DB의 연결정보를 담고있는 객체
	 *  - [prepared] Statement	: 연결된 DB에 SQL문을 전달해서 실행하고 그 결과를 받아내는 객체 ★★★★★
	 *  - ResultSet				: SELECT문 실행 후 조회된 결과물들이 담겨있는 객체
	 *  
	 *  ★★★ JDBC 과정 (순서중요!!)
	 *  1) jdbc driver 등록
	 *  2) Connection 객체 생성
	 *  3) Statement 객체 생성
	 *  4) sql문 전달하면서 실행
	 *  5) 결과 받기
	 *  	> select 문일 경우 => ResultSet 객체(조회돈 데이터들이 담겨있음) 받음  -> 6_1
	 *  	> dml문일 경우	 => int (처리된 행 수) -> 6_2
	 *  
	 *  6_1) ResultSet에 담겨있는 데이터들을 하나씩 하나씩 vo 객체에 주섬주섬 옮겨담기 (커서 옮겨가며)
	 *  6_2) 트랜젝션 처리
	 *  7) 다 사용한 JDBC 객체를 반드시 자원 반납
	 *  
	 */
	
	/**
	 * 사용자가 입력한 정보들을 추가시켜주는 메소드
	 * @param m	: 상용자가 입력한 값들이 주섬주섬 담겨있는 Member 객체
	 * @return 
	 */
	public int insertMember(Member m) {
		// insert문 => 처리된 행수 (int) => 트랜젝션 처리
		
		// 필요한 변수들 먼저 셋팅
		int result =0;			// 처리된 결과(처리된 행수)를 받아줄 변수
		Connection conn = null; // 연결된 DB의 연결정보를 담는 객체
		Statement stmt = null;	// "완성된 sql(실제로 값이 다 채워짐 상태로)"문 전달해서 곧바로 실행 후 결과 
		
		// 실행할 sql문 (완성된 형태로 만들어두기!!)
		// INSERT INTO MEMBER VALUES(seq_userno.nextval,'XXX','XXX','XXX','X',XX ,'XXX','XXXXXX','XXX','XXX,XXX',SYSDATE)
	
		String sql = "INSERT INTO MEMBER VALUES(seq_userno.nextval,"
				+ "'" + m.getUserId() + "', "
				+ "'" + m.getUserPwd() + "', "
				+ "'" + m.getUserName() + "', "
				+ "'" + m.getGender() + "', "
					  + m.getAge()  + ", "
				+ "'" + m.getEmail() + "', "
				+ "'" + m.getPhone() + "', "
				+ "'" + m.getAddress() + "', "
				+ "'" + m.getHobby() + "', SYSDATE)";
		// System.out.println(sql); 콘솔에 찍어서 쿼리문 확인
		
		try {
			// 1) jdbc driver 등록
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			// 2) Connection 객체 생성 == DB연결
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "JDBC", "JDBC");
			
			// 3) Statement 객체 생성
			stmt = conn.createStatement();
			
			// 4,5) sql문을 전달하면서 실행 후 결과 받기
			result = stmt.executeUpdate(sql);
			
			// 6) 트랜젝션 처리
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
			// 7) 다 사용한 jdbc용 객체 반납
			try {
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		return result;
	}

	/**
	 * 사용자가 요청한 회원 전체 조회를 처리해주는 메소드
	 * @return 조회된 결과가 있었으면 조회된 결과들이 담겨있는 list  조회된 결과가 없으면 텅 빈 list
	 */
	public ArrayList<Member> selectList() {
		// select문(여러행 조회) => ResultSet객체 => ArrayList에 담기
		
		// 필요한 변수들 셋팅
		ArrayList<Member> list = new ArrayList<>();
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rset = null; // select문 생성시 조회된 결과값들을 최초로 담는
		
		String sql = "SELECT * FROM MEMBER";
		try {
			// 1) jdbc driver 등록
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			// 2) Connetion 생성
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "JDBC", "JDBC");
			
			// 3) Statement 생성
			stmt = conn.createStatement();
			
			// 4,5) sql 실행 및 결과받기
			rset = stmt.executeQuery(sql);
			
			// 6) RsultSet으로 부터 하나씩 뽑아서 vo객체에 주섬주섬 담고 + list에 vo객체 추가
			while(rset.next()) {
				
				// 현재 rset의 커서가 가리키고 있는 한 행의 데이터들을 싹 뽑아서 member 객체 주섬주섬 담기
				Member m = new Member()	;
				
				m.setUserNo(rset.getInt("USERNO"));
				m.setUserId(rset.getString("USERID"));
				m.setUserPwd(rset.getString("USERPWD"));
				m.setUserName(rset.getString("USERNAME"));
				m.setGender(rset.getString("GENDER"));
				m.setAge(rset.getInt("AGE"));
				m.setEmail(rset.getString("EMAIL")); 
				m.setPhone(rset.getString("PHONE")); 
				m.setAddress(rset.getString("ADDRESS")); 
				m.setHobby(rset.getString("HOBBY")); 
				m.setEnrollDate(rset.getDate("ENROLLDATE"));
				// 현재 참조하고 있는 행에 대한 모든 컬럼에 대한 테이블들을 한 Member 객체에 담기
				
				list.add(m);// 리스트에 해당 회원의 객체 차곡차곡담기
				
			}
			
			// 반목문이 다 끝난 시점에
			// 만약에 조회된 데이터가 없었다면 list가 텅 빈상태일거임
			// 만약에 조회된 데이터가 있었다면 list에 뭐라도 담겨 있을 것!
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				rset.close();
				stmt.close();
				conn.close();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return list; // 텅빈리스트 | 뭐라도 담겨있는 리스트
		
	
		
	}
	/**
	 * 사용자의 아이디로 회원검색요청 처리해주는 메소드
	 * @param userId	사용자가 입력한 검색하고자 하는 회원 아이디값
	 */
	public void selectByUserId(String userId) {
		// select문 (한행)
		// 그럼 굳이 ArrayList 필요 없음 한개만 있으면 될듯
		Member m = null; // 조회 결과가 있을수도 있고 없을수도 있으니까
		
		// JDBC 개체
		Connection conn = null;
		Statement stmt = null;
		ResultSet rset = null;
		
		String sql = "SELECT * FROM MEMBER WHERE USERID = '"+userId+"'";
			
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","JDBC","JDBC");
		
			stmt = conn.createStatement();
			
			rset = stmt.executeQuery(sql);
			
			
			if(rset.next()) { // 한 행이라도 조회됬을때
				// 조회됬다면 해당 조회 된 컬럼값들을 뽑아서 한 Member 객체의 각 필드에 담기
				m = new Member(rset.getInt("USERNO"),
							   rset.getString("USERID"),
							   rset.getString("USERPWD"),
							   rset.getString("USERNAME"),
							   rset.getString("gender"),
							   rset.getInt("age"),
							   rset.getString("email"),
							   rset.getString("phone"),
							   rset.getString("address"),
							   rset.getString("hobby"),
							   rset.getDate("enrolldate"));

				
			}else { 
				
			}
			// 위으 조건문 다 끝난 시점에
			// 만약에 조회된 데이터가 없었을 경우 => m은 null
			// 만약에 조회된 데이터가 있었을 경우 => m은 생성 후 뭐라도 담겨있음
			
			
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				rset.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			if(m == null) { // 검색결과가 없을 경우 (조회된 데이터 없음)
				new MemberMenu().displayNodata(userId+"에 해당하는 검색결과가 없습니다.");
				
			}else {
				new MemberMenu().dispalyMember(m);
			}
		}
	}

	public int deleteMember(String delId, String delPwd) {
		
		Connection conn = null;
		Statement stmt = null;
		int result =0;
		
		String sql = "DELETE FROM MEMBER WHERE USERID = '"+delId+"' AND USERPWD = '"+delPwd+"'";
			
		
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");

				conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","JDBC","JDBC");
				stmt = conn.createStatement();
				result = stmt.executeUpdate(sql);
				
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
				// 7) 다 사용한 jdbc용 객체 반납
				try {
					stmt.close();
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
			}
			
			return result;
		
		
		
		
	}

	@SuppressWarnings("finally")
	public ArrayList<Member> selectByUserName(String userName) {
		ArrayList<Member> list = new ArrayList<>();

		// select문 (한행)
				// 그럼 굳이 ArrayList 필요 없음 한개만 있으면 될듯
				//Member m = null; // 조회 결과가 있을수도 있고 없을수도 있으니까
				
				// JDBC 개체
				Connection conn = null;
				Statement stmt = null;
				ResultSet rset = null;
				
				String sql = "SELECT * FROM MEMBER WHERE USERNAME LIKE '%"+userName+"%'";
					
				try {
					Class.forName("oracle.jdbc.driver.OracleDriver");
					
					conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","JDBC","JDBC");
				
					stmt = conn.createStatement();
					
					rset = stmt.executeQuery(sql);
					
					
					/*
					 * if(rset.next()) { // 한 행이라도 조회됬을때 m = new Member(rset.getInt("USERNO"),
					 * rset.getString("USERID"), rset.getString("USERPWD"),
					 * rset.getString("USERNAME"), rset.getString("gender"), rset.getInt("age"),
					 * rset.getString("email"), rset.getString("phone"), rset.getString("address"),
					 * rset.getString("hobby"), rset.getDate("enrolldate")); list.add(m);
					 * 
					 * 
					 * }else {
					 * 
					 * }
					 */
					
					while(rset.next()) {
						
						// 현재 rset의 커서가 가리키고 있는 한 행의 데이터들을 싹 뽑아서 member 객체 주섬주섬 담기
						Member m = new Member()	;
						
						m.setUserNo(rset.getInt("USERNO"));
						m.setUserId(rset.getString("USERID"));
						m.setUserPwd(rset.getString("USERPWD"));
						m.setUserName(rset.getString("USERNAME"));
						m.setGender(rset.getString("GENDER"));
						m.setAge(rset.getInt("AGE"));
						m.setEmail(rset.getString("EMAIL")); 
						m.setPhone(rset.getString("PHONE")); 
						m.setAddress(rset.getString("ADDRESS")); 
						m.setHobby(rset.getString("HOBBY")); 
						m.setEnrollDate(rset.getDate("ENROLLDATE"));
						// 현재 참조하고 있는 행에 대한 모든 컬럼에 대한 테이블들을 한 Member 객체에 담기
						
						list.add(m);// 리스트에 해당 회원의 객체 차곡차곡담기
						
					}
					// 위으 조건문 다 끝난 시점에
					// 만약에 조회된 데이터가 없었을 경우 => m은 null
					// 만약에 조회된 데이터가 있었을 경우 => m은 생성 후 뭐라도 담겨있음
					
					
					
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}finally {
					try {
						rset.close();
						stmt.close();
						conn.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
					/*if(m == null) { // 검색결과가 없을 경우 (조회된 데이터 없음)
						new MemberMenu().displayNodata(userName+"에 해당하는 검색결과가 없습니다.");
						
					}else {
						new MemberMenu().displatMemberList(list);
					}
				}*/
				return list; // 텅빈리스트 | 뭐라도 담겨있는 리스트
	}
	}

	/*public void updateMember(String userId, String userPwd, String email, String phone, String address) {
		Connection conn = null;
		Statement stmt = null;
		int result =0;
		
		String sql = "update MEMBER SET userpwd = '" + userPwd
				+ "', EMAIL = '" +email
				+ "', PHONE = '" +phone
				+ "', ADDRESS = '" +address
				+ "WHERE USERID  = '" + userId
				+ "'";
	}
	*/
	/**
	 * 사용자가 입력한 아이디의 정보 변경 요청 처리해주는 메소드
	 * @param m  
	 * @return result :  처리된 행 수
	 */
	public int updateMember(Member m) {
		Connection conn = null;
		Statement stmt = null;
		int result =0;
		String sql = "UPDATE MEMBER "
				+ "SET USERPWD = '" + m.getUserPwd()
				+ "', EMAIL = '" + m.getEmail()
				+ "', PHONE = '" + m.getPhone()
				+ "', ADDRESS = '" + m.getAddress()
				
				+ "' WHERE USERID  = '" + m.getUserId()
				+ "'";

		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","JDBC","JDBC");
			
			conn.setAutoCommit(false);
			
			stmt = conn.createStatement();
			
			result = stmt.executeUpdate(sql);
			
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
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		return result;
		}
		
		
	}
}
