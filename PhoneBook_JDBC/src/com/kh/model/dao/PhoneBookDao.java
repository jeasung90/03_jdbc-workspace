package com.kh.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static com.kh.common.JDBCTemplate.*;
import com.kh.model.vo.PhoneBook;

public class PhoneBookDao {

	/**
	 * 1. 전번 등록
	 * @param conn
	 * @param m
	 * @return
	 */
	public int inputPhone(Connection conn, PhoneBook m) {

		int result = 0;
		
		PreparedStatement pstmt = null;
		
		String sql = "INSERT INTO PHONEBOOK VALUES(seq_pbnum.nextval,?,?,?,?)";
		
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, m.getName());
			pstmt.setInt(2, m.getAge());
			pstmt.setString(3, m.getAddress());
			pstmt.setString(4, m.getPhone());
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		
		return result;
	}

	/**2.
	 * 이름 검색
	 * @param conn
	 * @param name
	 * @return
	 */
	public PhoneBook selectPhone(Connection conn, String name) {
		
		PhoneBook p = null;
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = "SELECT * FROM PHONEBOOK WHERE NAME = ?";
		
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, name);
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				p = new PhoneBook(rset.getString("name"),
								  rset.getInt("age"),
								  rset.getString("address"),
								  rset.getString("phone")
									);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		
		return p;
	}
	
	/**
	 * 3. 모든 전번
	 * @param conn
	 * @return
	 */
	public ArrayList<PhoneBook> allPhone(Connection conn) {
	
		ArrayList<PhoneBook> list = new ArrayList<PhoneBook>();
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = "SELECT * FROM PHONEBOOK";
		
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			rset = pstmt.executeQuery();
			/*  PBNUM
				NAME
				AGE
				ADDRESS
				PHONE */
			while(rset.next()) {
				
				list.add(new PhoneBook(rset.getInt("PBNUM"),
									   rset.getString("NAME"),
									   rset.getInt("AGE"),
									   rset.getString("ADDRESS"),
									   rset.getString("PHONE")
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


}
