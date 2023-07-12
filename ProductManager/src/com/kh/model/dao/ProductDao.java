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

}
