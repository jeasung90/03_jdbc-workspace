package com.kh.controller;

import java.util.ArrayList;

import com.kh.model.dao.MemberDao;
import com.kh.model.vo.Member;
import com.kh.view.MemberMenu;

// Controller : view를 통해서 사용자가 요청한 기능에 대해서 처리하는 담당
//				해당 메소드를 전달된 데이터 [가공처리 한 후] Dao로 전달하면서 호출
// 				Dao로 부터 반환받은 결과에 따라 성공인지 실패인지 판단 후 응답화면 결정
public class MemberContrller {

	/**
	 * 사용자의 회원 추가 요청을 처리해주는 메소드
	 * @param userId * @param userPwd * @param username * @param gender
	 * @param age * @param email * @param phone * @param address * @param hobby
	 *  : 사용자가 입력했던 정보들이 담겨있는 매개변수
	 */
	public void insertMember(String userId, String userPwd, String userName, String gender, String age, String email, String phone, String address, String hobby) {
		
		// 받은 값들을 데이터를 직접적으로 처리해주는 DAO로 넘기고
		// => 어딘가에 주섬주섬 담아서 전달!
		// 어딘가 ? => vo객체 => Memver 객체!
		
		// 방법 1) 기본생성자로 생성한 후 각 필드에 setter 메소드 통해서 일일히 담는 방법 => 이건 매개변수가 몇개 없을 때...
		// 방법 2) 아싸리 매개변수 생성자를 통해서 생성과 동시에 담는 방법
		//Member m = new Member(userId,userPwd,userName,gender,Integer.parseInt(age),email, phone, address, hobby);
		Member m = new Member(userId,userPwd,userName,gender,Integer.parseInt(age), email, phone, address, hobby);
		// 여기서 나이를 반드시!! int 형으로 바꿔야함
		// String => int로 변경? parseInt();
		
		// 빨간줄 뜨는 이유? => 그 데이터 타입을 받는 매개변수 생성자와 달라서
		// System.out.println(m);
		
		int result = new MemberDao().insertMember(m);
		
		//System.out.println(result);
		
		// 팝업창 처럼 사용자에게 문구 보여주기
		if(result>0) {// 성공
			new MemberMenu().dispalySuccess("성공적으로 회원등록되었습니다.");
		}else { // 실패2
			new MemberMenu().displatfail("회원등록에 실패하였습니다.");

		}	
		
	}

	/**
	 *  사용자의 회원 전체 조회 요청을 처리해주는 메소드
	 */
	public void selectList() {
		ArrayList<Member> list = new MemberDao().selectList();
		
		// System.out.println(list);
		
		// 조회된 데이터가 있는지 판단 후 사용자가 보게될 응답화면 지정
		if(list.isEmpty()) { // 텅 비어있을 경우 == 조회된 데이터 없었을 경우
			new MemberMenu().displayNodata("전체 조회결과가 없습니다.");
		}else { // 데이터가 뭐라도 있을 경우
			new MemberMenu().displatMemberList(list);
		}
		
		
		
	}

	public void selectByUserId(String userId) {
	new MemberDao().selectByUserId(userId);
		
		
	}

	public void deleteMember(String delId, String delPwd) {
	int result = new MemberDao().deleteMember(delId,delPwd);
	
	if(result>0) {// 성공
		new MemberMenu().dispalyDelete("성공적으로 탈퇴되었습니다.");
	}else { // 실패2
		new MemberMenu().displatNotDelete("회원탈퇴에 실패하였습니다.\n아이디와 비밀번호가 일치하지 않습니다.");
	}

	

	}

	public void selectByUserName(String userName) {
	new MemberDao().selectByUserName(userName);
		
		
	}
}
