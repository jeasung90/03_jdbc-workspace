package com.kh.controller;

import java.util.ArrayList;

import com.kh.model.dao.ProductDao;
import com.kh.model.service.ProductService;
import com.kh.model.vo.Product;
import com.kh.view.ProductMenu;

public class ProductController {

	/**
	 * 1. 전체 조회
	 */
	public void selectAll() {
		
		ArrayList<Product> list =  new ProductService().selectAll();
		
		if(list.isEmpty()) {
			new ProductMenu().nodata("조회된 결과가 없습니다.");
		}else {
			new ProductMenu().listData(list);
		}
		
	}

	/**
	 * 2. 신규등록
	 * @param p
	 */
	public void newProduct(Product p) {
		
		int result = ProductService.newProduct(p);
		
		if(result > 0) {
			new ProductMenu().done("정상등록 되었습니다.");
		}else {
			new ProductMenu().fail("신규등록에 실패하였습니다.");
		}
	
	}

	/**
	 * 3. 정보 조회
	 * @param updataId
	 * @param product_id
	 * @param p_name
	 * @param parseInt
	 * @param description
	 * @param parseInt2
	 */
	public void updataProduct(String updataId, String product_id, String p_name, int price, String description,
			int stock) {
		
		int result = new ProductService().updataProduct(updataId,product_id,p_name,price,description,stock);
		
		if(result > 0) {
			new ProductMenu().done("정상수정 되었습니다.");
		}else {
			new ProductMenu().fail("정보수정에 실패하였습니다.");
		}
		
	}

	/**
	 * 4. 제품삭제
	 * @param deleteId
	 */
	public void deleteProduct(String deleteId) {
		int result = new ProductService().deleteProduct(deleteId);
		if(result > 0) {
			new ProductMenu().done("정상삭제 되었습니다.");
		}else {
			new ProductMenu().fail("정보삭제에 실패하였습니다.\n제품번호를 확인하세요.");
		}
		
	}

	/**
	 * 5. 제품검색
	 * @param selectId
	 */
	public void selectProduct(String selectId) {
	Product p = new ProductService().selectProduct(selectId);
		
	if(p==null) {
		new ProductMenu().fail("해당 제품이 없습니다. 제품번호를 다시 확인해 주세요");
	}else {
		new ProductMenu().donePhone(p);
	}
		
	}


}
