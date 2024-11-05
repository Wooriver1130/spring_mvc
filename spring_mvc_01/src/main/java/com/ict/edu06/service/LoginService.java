package com.ict.edu06.service;

import com.ict.edu06.vo.LoginVO;

public interface LoginService {
	
	// 로그인
	public LoginVO LoginChk(String u_id) throws Exception;
	
	// 회원가입
	public int LoginJoin(LoginVO lvo) throws Exception;
}
