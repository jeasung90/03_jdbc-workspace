package com.kh.model.service;

import java.sql.Connection;
import java.util.ArrayList;

import static com.kh.common.JDBCTemplate.*;

import com.kh.model.dao.PhoneBookDao;
import com.kh.model.vo.PhoneBook;

public class PhoneBookService {

	/**
	 * 1. 전번 등록
	 * @param m
	 * @return
	 */
	public int inputPhone(PhoneBook m) {
	
		Connection conn = getConnection();
		
		int result = new PhoneBookDao().inputPhone(conn,m);
		
		if(result >0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		
		close(conn);
		
		return result;
	}

	/**2. 이름검색
	 * @param name
	 * @return
	 */
	public PhoneBook selectPhone(String name) {

		Connection conn = getConnection();
		
		PhoneBook p = new PhoneBookDao().selectPhone(conn,name);
		
		close(conn);
		
		return p;
	}

	
	/**3. 전체 조회
	 * @return
	 */
	public ArrayList<PhoneBook> allPhone() {
		
		Connection conn = getConnection();
		
		ArrayList<PhoneBook> list = new PhoneBookDao().allPhone(conn);
		
		close(conn);
		
		return list;
	}


}
