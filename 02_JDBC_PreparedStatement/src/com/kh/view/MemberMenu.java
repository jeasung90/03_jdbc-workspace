package com.kh.view;

import java.util.ArrayList;
import java.util.Scanner;

import com.kh.controller.MemberContrller;
import com.kh.model.vo.Member;

// view : 사용자가 보게될 시각적인 요소(화면) 출력 및 입력
public class MemberMenu {

	
	// Scanner 객체 생성 ( 전역으로 다 쓸 수 있도록 )
	private Scanner sc = new Scanner(System.in);
	
	// MemberController 객체 생성 (전역으로 요청 가능하게)
	private MemberContrller mc = new MemberContrller();
	
	/**
	 * 사용자가 보게될 첫 화면 (메인화면)
	 */
	public void mainMemu() {
		
		while(true)	{
			System.out.println("\n== 회원 관리 프로그램 ==");
			System.out.println("1. 회원 추가");
			System.out.println("2. 회원 전체조회");
			System.out.println("3. 회원 아이디 검색");
			System.out.println("4. 회원 이름으로 키워드 검색");
			System.out.println("5. 회원 정보 변경");
			System.out.println("6. 회원탈퇴");
			System.out.println("0. 프로그램 종료");
			System.out.println("9. 이름으로 정보 조회");
			
			System.out.print(">> 메뉴 선택 : ");
			int menu = sc.nextInt();
			sc.nextLine();
			
			switch(menu) {
			
			case 1 :
				inputMember();
				break;
			case 2 :
				mc.selectList();// 입력받을게 없으면 바로 controller
				break;
			case 3 :
				// String userId = inputMemberId();
				// 
				mc.selectByUserId(inputMemberId());
				break;
			case 4 :
				//String userName =selectByUserName();
				mc.selectByUserName(selectByUserName());
				break;
			case 5 :
				updateMember();
				break;
			case 6 :
				deleteMember();
				break;
			case 0 :
				System.out.println("이용해주셔서 감사합니다. 프로그램을 종료합니다.");
				return;
			case 9 :
				break;
			default : System.out.println("메뉴를 잘못 입력하셨습니다. 다시 입력해주세요.");
			
			
			}
			
			
		}
	
	}



	//---------------------------------------------------------------------------------------------

	/**
	 * 1. 회원 추가 창( 서브화면 )
	 * 즉, 추가하고자 하는 회원의 정보를 입력받아 회원 추가요정 하는 창
	 */
	private void inputMember() { 
		System.out.println("\n===== 회원추가 =====");
		// 아이디 ~ 취미 까지 입력 받기
		System.out.print("아이디 :");
		String userId = sc.nextLine();
		System.out.print("비번 :");
		String userPwd = sc.nextLine();
		System.out.print("이름 :");
		String userName = sc.nextLine();
		System.out.print("성별(M/F) :");
		String gender = sc.nextLine();
		System.out.print("나이 :"); // 이제부터는 모두 문자열로 받자 => 웹에서는 다 문자로 넘어옴
		String age = sc.nextLine();
		System.out.print("이메일 :"); 
		String email = sc.nextLine();
		System.out.print("전화번호(-제외) :"); 
		String phone = sc.nextLine();
		System.out.print("주소 :"); 
		String address = sc.nextLine();
		System.out.print("취미(,로 연이어 작성) :"); 
		String hobby = sc.nextLine();
		
		if(gender.equals("F")|| gender.equals("M")) {
			
			// 회원 추가 요청 == Controller 메소드 호출
			mc.insertMember(userId,userPwd,userName,gender,age,email,phone,address,hobby);
		}else {
			System.out.println("성별란을 잘못 입력하셨습니다. 양식에 맞게 다시 작성해 주세요");
			return;
		}
	}
	/**4. 회원 이름으로 키워드 검색
	 * 사용자에게 검색할 회원명(키워드) 입력받은 후 그때 입력된 값을 반환시켜주는 메소드
	 * @return 사용자가 입력한 회원명(키워드)
	 */
	private String selectByUserName() {
		System.out.print("\n 회원의 이름 입력(키워드 가능) : ");
		return sc.nextLine();
	}
	
	/**
	 * 6. 회원탈퇴
	 */
	private void deleteMember() {
		System.out.print("탈퇴를 원하시는 계정의\n아이디 : ");
		String delId = sc.nextLine();
		
		System.out.print("비번 : ");
		String delPwd = sc.nextLine();
		
		mc.deleteMember(delId,delPwd);
	}
	//--------------------응답화면--------------------
	/**
	 * 3. 사용자에게 회원 아이디 입력 받은 후 그 때 입력된 값을 반환시켜주는 메소드
	 * 5. 정보 수정
	 * @return 사용자가 입력한 아이디값
	 */
	private String inputMemberId() { // 아이디로 찾는 메소드
		System.out.print("\n회원의 아이디 입력 : ");
		return sc.nextLine();

	}
	
	/**
	 * 서비스 요청 처리 후 성공했을 경우 사용자가 보게 될 응답화면
	 * @param message
	 * 1. 회원등록
	 */
	public void dispalySuccess(String message) { // 요청 성공 메소드
		System.out.println("\n 서비스 요청 성공 : "+message);
		
	}
	public void dispalyDelete(String message) { // 요청 성공 메소드
		System.out.println("\n 서비스 요청 성공 : "+message);
		
	}
	/**
	 * 서비스 요청 처리 후 실패했을 경우 사용자가 보게 될 응답화면
	 * @param message
	 * 1. 회원등록
	 */
	public void displatfail(String message) {
		System.out.println("\n 서비스 요청 실패 : "+message);
	}
	public void displatNotDelete(String message) {
		System.out.println("\n 서비스 요청 실패 : "+message);
	}
	/**
	 * 조회 서비스 요청시 조회의 결과가 없을 경우 사용자가 보게 될 응답화면
	 * @param message
	 * 2. 전체조회
	 * 3. 아이디로 조회
	 */
	public void displayNodata(String message) {
		System.out.println(message + "\n조회된 회원이 없습니다.");
	}

	/** ArrayList 로 출력하는.
	 * 조회 서비스 요청시 조회결과가 여러행일 경우 사용자가 보게될 응답화면
	 * @param list
	 * 2. 전체조회
	 * 
	 */
	public void displatMemberList(ArrayList<Member> list) {
		System.out.println("\n 조회된 데이터는 다음과 같습니다.");
		
		/*
		for(int i=0; i<list.size();i++) {
			System.out.println(list.get(i));
		}
		*/
		// 향상된 for문
		for(Member m : list) {
			System.out.println(m);
		}
		}


	/**
	 * 조회된 값 1개를 출력하는 화면
	 * 3. 아이디로 정보찾기
	 * @param m
	 */
	public void dispalyMember(Member m) { 
		System.out.println("\n 조회된 데이터는 다음과 같습니다.");
		System.out.println(m);
	}

	/**
	 * 5. 정보 수정
	 */
	private void updateMember() {
		System.out.println("\n ==== 회원정보 변경 ====");
		
		// 아이디 => 바꿀 내용(비번,이메일,전번,주소)
		//System.out.print("회원정보 변경할 회원 아이디 : ");
		//String userid = sc.nextLine();
		String userId = inputMemberId();
		
		System.out.print("회원의 비밀번호 입력 : ");
		String userPwd1 = sc.nextLine();
		System.out.print("변경하실 암호 : ");
		String userPwd2 = sc.nextLine();
		System.out.print("변경하실 이메일 : ");
		String email = sc.nextLine();
		System.out.print("변경하실 전화번호 : ");
		String phone = sc.nextLine();
		System.out.print("변경하실 주소 : ");
		String address = sc.nextLine();
		
		mc.updateMember(userId,userPwd1,userPwd2,email,phone,address);
	}
	
	}
	
	



