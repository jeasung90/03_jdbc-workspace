package com.kh.common;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

// 공통 템플릿 (매번 반복적으로 작성될 코드를 메소드로 정의해두는 곳)

public class JDBCTemplate {
	// 모든 메소드 싹 다 static 메소드로 만들어버리면 됨
	// 이건 실행되자마자 메모리 영역에 다 올라감
	// 싱글톤 패턴 : 메모리 영역에 단 한번만 올려두고 매번 재사용 하는 개념 (Math 클래스 같은거....)
	
	/**
	 * 1. Connection 객체 생성 (DB와 접속) 한 후 해당 Connection 객체 반환해주는 메소드
	 * @return
	 */
	public static Connection getConnection() {
		
		Connection conn = null;
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","JDBC","JDBC");
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return conn;
	}
	
	/**
	 * 2. Commit 처리해주는 메소드 (Connection 전달받아서)
	 * @param conn
	 */
	public static void commit(Connection conn) {
		try {
			if(conn != null && !conn.isClosed()) {
			conn.commit();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 3. Rollback 처리해주는 메소드 (Connection 전달받아서)
	 * @param conn
	 */
	public static void rollback(Connection conn) {
		try {
			if(conn != null && !conn.isClosed()) {
			conn.rollback();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// JDBC용 객체들 전달받아서 반납처리해주는 메소드
	/**
	 * 4. Statement 관련 객체 전달 받아서 반납시켜주는 메소드
	 * @param stmt
	 */
	public static void close(Statement stmt) { // 얘가 부모라서 PreparedStatement 받을 수 있음!! 다형성 적용!!
		try {
			if(stmt != null && !stmt.isClosed()) {
				stmt.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 5. Connection 객체 전달 받아서 반납시켜주는 메소드
	 * @param conn
	 */
	public static void close(Connection conn) {
		try {
			if( conn != null && !conn.isClosed()) {
			conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 6. ResultSet  닫아주는 메소드
	 * @param rset
	 */
	public static void close(ResultSet rset) {
		try {
			if( rset != null && !rset.isClosed()) {
			rset.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	
}
