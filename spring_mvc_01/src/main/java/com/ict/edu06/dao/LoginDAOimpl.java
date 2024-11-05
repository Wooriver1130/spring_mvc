package com.ict.edu06.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ict.edu06.vo.LoginVO;
@Repository
public class LoginDAOimpl  implements LoginDAO{

	@Autowired
	SqlSessionTemplate sqlSessionTemplate ;
	
	
	@Override
	public LoginVO LoginChk(String u_id) throws Exception {
		return sqlSessionTemplate.selectOne("login.selectOne", u_id);
	}

	@Override
	public int LoginJoin(LoginVO lvo) throws Exception {
		
		return sqlSessionTemplate.insert("login.getInsert", lvo);
	}

}
