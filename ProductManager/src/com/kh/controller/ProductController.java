package com.kh.controller;

import java.util.ArrayList;

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

}
