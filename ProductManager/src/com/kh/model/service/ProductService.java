package com.kh.model.service;

import java.sql.Connection;
import java.util.ArrayList;

import static com.kh.common.JDBCTemplate.*;

import com.kh.model.dao.ProductDao;
import com.kh.model.vo.Product;

public class ProductService {

	/** 1. 전체 조회
	 * @return
	 */
	public ArrayList<Product> selectAll() {
		
		Connection conn = getConnection();
		
		ArrayList<Product> list = new ProductDao().selectAll(conn);
		
		close(conn);
		
		return list;
	}

	/**2.
	 * 신규등록
	 * @param p
	 * @return
	 */
	public static int newProduct(Product p) {
		
		Connection conn = getConnection();
		
		int result = new ProductDao().newProduct(conn,p);
		
		close(conn);
		
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
	 * @return
	 */
	public int updataProduct(String updataId, String product_id, String p_name, int price, String description,
			int stock) {
		
		Connection conn = getConnection();
		
		int result = new ProductDao().updataProduct(updataId,product_id,p_name,price,description,stock,conn);
		
		close(conn);
		
		return result;
	}
 
	/**
	 * 4. 정보삭제
	 * @param deleteId
	 * @return
	 */
	public int deleteProduct(String deleteId) {

		Connection conn = getConnection();
		
		int result = new ProductDao().deleteProduct(deleteId,conn);
		
		close(conn);
		
		return result;
	}

	/**
	 * 5. 정보 조회
	 * @param selectId
	 * @return
	 */
	public Product selectProduct(String selectId) {
		
		Connection conn = getConnection();

		Product p = new ProductDao().selectProduct(selectId,conn);
		
		close(conn);
		
		return p;
	}

	

}
