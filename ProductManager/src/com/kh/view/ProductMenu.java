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
				newProduct();
				break;
			case 3 :
				updataProduct();
				break;
			case 4 :
				deleteProduct();
				break;
			case 5 :
				selectProduct();
				break;
			case 0 :
				System.out.println("프로그램을 종료합니다.");
				return;
			
			
			}
		}
		
	}
	
	

	//-----------------------------------------입력 -------------------------------
/**2. 입력받는 메소드
 * 
 */
private void newProduct() {
	System.out.println("==== 제품 추가 ====");
	System.out.print("제품번호 :");
	String product_id = sc.nextLine();
	System.out.print("제품명 :");
	String p_name = sc.nextLine();
	System.out.print("가격 :");
	String price = sc.nextLine();
	System.out.print("설명 :");
	String description = sc.nextLine();
	System.out.print("재고 :");
	String stock = sc.nextLine();
	Product p = new Product(product_id,p_name,Integer.parseInt(price) ,description,Integer.parseInt(stock));
	pc.newProduct(p);
	
	}

/** 3. 제품변경
 * 
 */
private void updataProduct() {
	System.out.println("==== 제품 변경 ====");
	System.out.print("변경하실 제품의 제품번호 : ");
	String updataId = sc.nextLine();
	System.out.print("변경될 제품번호 :");
	String product_id = sc.nextLine();
	System.out.print("변경될 제품명 :");
	String p_name = sc.nextLine();
	System.out.print("변경될 가격 :");
	String price = sc.nextLine();
	System.out.print("변경될 설명 :");
	String description = sc.nextLine();
	System.out.print("변경될 재고 :");
	String stock = sc.nextLine();
	
	pc.updataProduct(updataId,product_id,p_name,Integer.parseInt(price) ,description,Integer.parseInt(stock));
}
/**
 * 4. 제품삭제
 */
private void deleteProduct() {
	System.out.println("==== 제품 삭제 ====");
	System.out.print("삭제하실 제품의 제품번호 : ");
	String deleteId = sc.nextLine();
	System.out.println("정말 "+deleteId+" 제품을 삭제하시겠습니까? (Y/N) : ");
	String yn = sc.nextLine();
	
	if(yn.equals("Y") ||yn.equals("y") ) {
		pc.deleteProduct(deleteId);
	}else {
		System.out.println("메뉴화면으로 돌아갑니다.");
		return;
	}
	
}
/**5. 상품검색
 * 
 */
private void selectProduct() {
	System.out.println("==== 제품 검색 ====");
	System.out.print("검색하실 제품의 제품번호 : ");
	String selectId = sc.nextLine();
	
	pc.selectProduct(selectId);
	
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
	/** 2.
	 *신규등록
	 * 성공화면 
	 * @param string
	 */
	public void done(String string) {
		System.out.println("서비스 성공 : "+string);
	}
	/**2.
	 * 실패화면
	 * @param string
	 */
	public void fail(String string) {
		System.out.println("서비스 실패 : "+string);
	}



	/**
	 * 5. 상품검색
	 * @param p
	 */
	public void donePhone(Product p) {
		System.out.println("검색하신 제품의 정보 : ");
		System.out.println(p);
		
	}	

	
	
}
