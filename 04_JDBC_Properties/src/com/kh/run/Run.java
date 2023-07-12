package com.kh.run;

import com.kh.view.MemberMenu;

public class Run {

	public static void main(String[] args) {
		
		/*
		 *  * MVC 패턴
		 *  M(Model) : 데이터 처리 담당 (데이터들을 담기위함 클래스(vo), 데이터들이 보관된 공간과 직접적으로 접근해서 데이터 주고받기 (dao))
		 *  v(View) : 화면을 담당 (지금은 콘솔창! 사용자가 보게되는 시각적인 요소, 출력 및 입력)
		 *  c(Controller) : 사용자의 요청을 처리해주는 담당 (사용자의 요청을 처리한 후 그에 해당하는 응답화면을 지정)
		 */
		
		new MemberMenu().mainMemu();
		
		
	}

}
