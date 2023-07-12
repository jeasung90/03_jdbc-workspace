package com.kh.view;

import java.util.ArrayList;
import java.util.Scanner;

import com.kh.contriller.PhoneBookController;
import com.kh.model.vo.PhoneBook;

public class PhoneBookMenu {

	private Scanner sc = new Scanner(System.in);
	
	private PhoneBookController pc = new PhoneBookController();

	public void mainMenu() {
	
		while(true) {
			System.out.println("==================");
			System.out.println("전화번호부 v1.0");
			System.out.println("-------------------");
			System.out.println("1.전화번호등록");
			System.out.println("2.전화번호검색");
			System.out.println("3.전화번호모두보기");
			System.out.println("4.종료");
		
			System.out.print(">> 메뉴 선택 : ");
			int menu = sc.nextInt();
			sc.nextLine();
		
			switch(menu) {
			
			case 1 :
				inputPhone();
				break;
			case 2 :
				inputname();
				break;
			case 3 :
				pc.allPhone();
				break;
			case 4 :
				System.out.println("이용해주셔서 감사합니다. 프로그램을 종료합니다.");
				return;
			}
		}
	}


	//----------------------------------------------------------------
	/**
	 * 1. 전번 등록
	 * 
	 */
	private void inputPhone() {
		System.out.println("==전화번호등록==");
		System.out.print("이름 : ");
		String name = sc.nextLine();
		System.out.print("나이 :"); 
		String age = sc.nextLine();
		System.out.print("주소 :"); 
		String address = sc.nextLine();
		System.out.print("전화번호(-제외) :"); 
		String phone = sc.nextLine();
		
		pc.inputPhone(name,age,address,phone);
	}

	/**
	 * 2. 이름 받기
	 */
	private void inputname() {
		System.out.println("==전화번호검색==");
		System.out.print("검색하실 번호의 이름 : ");
		String name = sc.nextLine();
		
		pc.selectPhone(name);
	}
	// ---------------------- 디스플레이 구문 ------------------------------------
	/**1.
	 * 성공했을 때
	 * @param string
	 */
	public void dispalySuccess(String message) {
		System.out.println("\n서비스 요청 성공 : "+message);
	}

	/**1.
	 * 실패했을 때
	 * @param string
	 */
	public void displatfail(String message) {
		System.out.println("\n 서비스 요청 실패 : "+message);
	}

	/**3.
	 * 조회된 회원 없을때
	 * @param string
	 */
	public void displayNodata(String message) {
		System.out.println(message + "\n조회된 회원이 없습니다.");

	}

	/**3.
	 * 조회된 결과가 여러줄일때
	 * @param list
	 */
	public void displatPhoneList(ArrayList<PhoneBook> list) {
		System.out.println("\n 조회된 데이터는 다음과 같습니다.");
		
		for(PhoneBook p : list) {
			System.out.println(p);
		}
	}


	/**2.
	 * 조회된 결과가 한줄일때
	 * @param p
	 */
	public void displatPhone(PhoneBook p) {
		System.out.println("\n 조회된 데이터는 다음과 같습니다.");
		System.out.println(p);
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
