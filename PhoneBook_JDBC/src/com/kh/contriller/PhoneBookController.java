package com.kh.contriller;

import java.util.ArrayList;

import com.kh.model.service.PhoneBookService;
import com.kh.model.vo.PhoneBook;
import com.kh.view.PhoneBookMenu;

public class PhoneBookController {

	/**
	 * 1. 전번등록
	 * @param name
	 * @param age
	 * @param address
	 * @param phone
	 */
	public void inputPhone(String name, String age, String address, String phone) {
		
		PhoneBook m = new PhoneBook(name,Integer.parseInt(age),address,phone);
		
		int result = new PhoneBookService().inputPhone(m);
		
		if(result > 0 ) {
			new PhoneBookMenu().dispalySuccess("성공적으로 회원 추가 되었습니다.");
		}else {
			new PhoneBookMenu().displatfail("회원등록에 실패하였습니다.");
		}
	}

	/**
	 * 2. 전번검색
	 * @param name 
	 */
	public void selectPhone(String name) {
		
		PhoneBook p = new PhoneBookService().selectPhone(name);
		
		if (p == null) {
			new PhoneBookMenu().displayNodata("조회된 결과가 없습니다.");
		}else {
			new PhoneBookMenu().displatPhone(p);
		}
	}

	/**
	 * 3. 모두 검색
	 */
	public void allPhone() {

		ArrayList<PhoneBook> list = new PhoneBookService().allPhone();
		
		if (list.isEmpty()) {
			new PhoneBookMenu().displayNodata("조회된 결과가 없습니다.");
		}else {
			new PhoneBookMenu().displatPhoneList(list);
		}
		
	}


}
