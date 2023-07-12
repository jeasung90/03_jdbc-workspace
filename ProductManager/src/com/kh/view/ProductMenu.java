package com.kh.view;

import java.util.ArrayList;
import java.util.Scanner;

import com.kh.controller.ProductController;
import com.kh.model.vo.Product;

public class ProductMenu {

	Scanner sc = new Scanner(System.in);
	ProductController pc = new ProductController();
	
	public void mainMenu() {
		
		while(true) {
			System.out.println("=== 상품관리 프로그램 ==");
			System.out.println("1. 전체 조회");
			System.out.println("2. 상품 추가");
			System.out.println("3. 상품 수정");
			System.out.println("4. 상품 삭제");
			System.out.println("5. 상품 검색");
			System.out.println("0. 프로그램 종료");
			
			System.out.print(">> 메뉴 선택 : ");
			int menu = sc.nextInt();
			sc.nextLine();
			
			switch(menu) {
			case 1 :
				pc.selectAll();
				break;
			case 2 :
				break;
			case 3 :
				break;
			case 4 :
				break;
			case 5 :
				break;
			case 0 :
				System.out.println("프로그램을 종료합니다.");
				return;
			
			
			}
		}
		
	}
//------------------------------------------- 디스플 ---------------------
	/** 1.
	 * 데타 없을떄
	 * @param string
	 */
	public void nodata(String message) {
		System.out.println("요청에 실패하였습니다. : "+message);
	}
	
	/**1.
	 * 데타가 리스트로 있을때
	 * @param list
	 */
	public void listData(ArrayList<Product> list) {
		System.out.println("조회된 데이터는 다음과 같습니다.");
		
		for(Product p : list) {
			System.out.println(p);
		}
	}

	
	
}
