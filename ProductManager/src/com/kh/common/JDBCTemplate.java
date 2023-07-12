package com.kh.common;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class JDBCTemplate {

	/** conn 생성
	 * @return
	 */
	public static Connection getConnection() {
		
		Connection conn = null;
		
		Properties prop = new Properties();
		
		try {
			prop.load(new FileInputStream("resources/driver.properties"));
			
			Class.forName(prop.getProperty("driver"));
			
			conn = DriverManager.getConnection(prop.getProperty("url"),prop.getProperty("name"),prop.getProperty("pass"));
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return conn;
		
	}
	
	/** 커밋
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
	
	/** 롤백
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


	/** stmt 닫아
	 * @param stmt
	 */
	public static void close(Statement stmt) {
		try {
			if(stmt != null && !stmt.isClosed()) {
			stmt.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/** rset 닫아
	 * @param rset
	 */
	public static void close(ResultSet rset) {
		try {
			if(rset != null && !rset.isClosed()) {
			rset.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/** conn 닫아
	 * @param conn
	 */
	public static void close(Connection conn) {
		try {
			if(conn != null && !conn.isClosed()) {
			conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
