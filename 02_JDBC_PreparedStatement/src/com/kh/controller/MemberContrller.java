package com.kh.controller;

import java.util.ArrayList;

import com.kh.model.dao.MemberDao;
import com.kh.model.vo.Member;
import com.kh.view.MemberMenu;

// Controller : view를 통해서 사용자가 요청한 기능에 대해서 처리하는 담당
//				해당 메소드를 전달된 데이터 [가공처리 한 후] Dao로 전달하면서 호출
// 				Dao로 부터 반환받은 결과에 따라 성공인지 실패인지 판단 후 응답화면 결정
public class MemberContrller {

	// private MemberMenu mm = new MemberMenu();
	
	/**
	 * 1. 사용자의 회원 추가 요청을 처리해주는 메소드
	 * @param userId * @param userPwd * @param username * @param gender
	 * @param age * @param email * @param phone * @param address * @param hobby
	 *  : 사용자가 입력했던 정보들이 담겨있는 매개변수
	 */
	public void insertMember(String userId, String userPwd, String userName, String gender, String age, String email, String phone, String address, String hobby) {
		Member m = new Member(userId, userPwd, userName, gender, Integer.parseInt(age), email, phone, address, hobby);
		
		int result = new MemberDao().insertMember(m);
		
		if(result > 0 ) {
			new MemberMenu().dispalySuccess("성공적으로 회원 추가 되었습니다.");
		}else {
			new MemberMenu().displatfail("회원등록에 실패하였습니다.");
		}
		
	}

	/**
	 *  2. 사용자의 회원 전체 조회 요청을 처리해주는 메소드
	 */
	public void selectList() {
		
		ArrayList<Member> list = new MemberDao().selectList();
		
		if (list.isEmpty()) {
			new MemberMenu().displayNodata("조회된 결과가 없습니다.");
		}else {
			new MemberMenu().displatMemberList(list);
		}
			
	}

	/** 3. id 서치
	 * 유저ID로 정보 찾는 메소드
	 * @param userId
	 */
	public void selectByUserId(String userId) {	
		Member m = new MemberDao().selectByUserId(userId);
		
		if(m == null) {
			new MemberMenu().displayNodata(userId+"에 해당하는 검색결과가 없습니다.");
		}else {
			 new MemberMenu().dispalyMember(m);
		}
		
		
	}
	
	/** 4. 키워드로 검색
	 * 이름으로 키워드 검색 요청시 처리해주는 메소드
	 * @param userName
	 */
	public void selectByUserName(String userName) {
		
		ArrayList<Member> list = new MemberDao().selectByUserName(userName);
		
		if(list.isEmpty()) {
			new MemberMenu().displayNodata(userName+"에 해당하는 검색결과가 없습니다.");
		}else {
			 new MemberMenu().displatMemberList(list);
		}
	}

	/**
	 * 5. 정보 변경
	 * 정보를 변경하는 메소드
	 * @param userId
	 * @param userPwd
	 * @param email
	 * @param phone
	 * @param address
	 * @param address2 
	 */
	public void updateMember(String userId, String userPwd1,  String userPwd2,String email, String phone, String address) {
		
		int result = new MemberDao().updateMember(userId,userPwd1,userPwd2,email,phone,address);
		
		if(result > 0 ) {
			new MemberMenu().dispalySuccess("성공적으로 회원정보가 수정 되었습니다.");
		}else {
			new MemberMenu().displatfail("회원정보 수정에 실패하였습니다.");
		}
		
	}
	
	/** 6. 계정 삭제
	 * 아이디와 비번이 맞으면 정보 지워주는 메소드
	 * @param delId
	 * @param delPwd
	 */
	/**
	 * @param delId
	 * @param delPwd
	 */
	public void deleteMember(String delId, String delPwd) {
		int result = new MemberDao().deleteMember(delId,delPwd);

		if(result > 0 ) {
			new MemberMenu().dispalySuccess("성공적으로 회원정보가 삭제 되었습니다.");
		}else {
			new MemberMenu().displatfail("회원정보 삭제에 실패하였습니다.\n아이디와 비밀번호를 확인하세요.");
		}
		
		
		
	}
}
