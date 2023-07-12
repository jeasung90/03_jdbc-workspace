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

}
