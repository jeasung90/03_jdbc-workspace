package com.kh.model.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import static com.kh.common.JDBCTemplate.*;
import com.kh.model.vo.Product;

public class ProductDao {

	private Properties prop = new Properties();
	
	public ProductDao() {
		try {
			prop.loadFromXML(new FileInputStream("resources/query.xml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<Product> selectAll(Connection conn) {

		ArrayList<Product> list = new ArrayList<Product>();
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = prop.getProperty("selectAll");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			rset = pstmt.executeQuery();
			
			/* PRODUCT_ID
				P_NAME
				PRICE
				DESCRIPTION
				STOCK*/
			while(rset.next()) {
				list.add(new Product(rset.getString("PRODUCT_ID"),
									 rset.getString("P_NAME"),
									 rset.getInt("PRICE"),
									 rset.getString("DESCRIPTION"),
									 rset.getInt("STOCK")
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

	/**2.
	 * 신규등록
	 * @param conn
	 * @param p
	 * @return
	 */
	public int newProduct(Connection conn, Product p) {
		
		int result =0; 
		PreparedStatement pstmt = null;
		
		String sql = prop.getProperty("newProduct");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, p.getProduct_Id());
			pstmt.setString(2, p.getP_Name());
			pstmt.setInt(3, p.getPrice());
			pstmt.setString(4, p.getDescription());
			pstmt.setInt(5, p.getStock());
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		
		
		return result;
	}

	/**
	 * 3. 정보변경
	 * @param updataId
	 * @param product_id
	 * @param p_name
	 * @param price
	 * @param description
	 * @param stock
	 * @param conn
	 * @return
	 */
	public int updataProduct(String updataId, String product_id, String p_name, int price, String description,
			int stock, Connection conn) {

		int result = 0;
		
		PreparedStatement pstmt = null;
		
		String sql = prop.getProperty("updataProduct");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, product_id);
			pstmt.setString(2, p_name);
			pstmt.setInt(3, price);
			pstmt.setString(4, description);
			pstmt.setInt(5, stock);
			pstmt.setString(6, updataId);
		
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		
		
		
		return result;
	}

	/**
	 * 4. 정보삭제
	 * @param deleteId
	 * @param conn
	 * @return
	 */
	public int deleteProduct(String deleteId, Connection conn) {

		int result =0;
		PreparedStatement pstmt = null;
		
		String sql = prop.getProperty("deleteProduct");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, deleteId);
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		
		
		return result;
	}

	/**
	 * 5. 정보 조회
	 * @param selectId
	 * @param conn 
	 * @return
	 */
	public Product selectProduct(String selectId, Connection conn) {

		Product p = null;
		
		PreparedStatement pstmt =null;
		ResultSet rset = null;
		
		String sql = prop.getProperty("selectProduct");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, selectId);
			
			rset = pstmt.executeQuery();

			while(rset.next()) {
				p = new Product(rset.getString("PRODUCT_ID"),
								rset.getString("P_NAME"),
								rset.getInt("PRICE"),
								rset.getString("DESCRIPTION"),
								rset.getInt("STOCK")
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

	

}
