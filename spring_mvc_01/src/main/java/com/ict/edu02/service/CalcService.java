package com.ict.edu02.service;

import org.springframework.stereotype.Service;

import com.ict.edu02.vo.CalcVO;

@Service // => Spring에서 자동으로 서비스 클래스라고 인식하고 객체생성 해준다.
public class CalcService {
	
	public CalcService() {
		System.out.println("안녕나는 calcservice 기본생성자야");
	}
	
	// 정보를 받아서 계산하는 메소드를 만든다.
	public int getCalc(CalcVO cvo) {
		int result = 0 ;
		int su1 = Integer.parseInt(cvo.getS1());
		int su2 = Integer.parseInt(cvo.getS2());
		
		switch (cvo.getOp()) {
		case "+": result = su1 + su2 ; break;
		case "-": result = su1 - su2 ; break;
		case "*": result = su1 * su2 ; break;
		case "/": result = su1 / su2 ; break;
		}
		return result ;
	}
}
