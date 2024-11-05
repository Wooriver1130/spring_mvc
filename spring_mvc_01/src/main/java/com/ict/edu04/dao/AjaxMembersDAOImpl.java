package com.ict.edu04.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ict.edu04.vo.MembersVO;

@Repository
public class AjaxMembersDAOImpl implements AjaxMembersDAO {

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	@Override
	public List<MembersVO> getMemberList() {
		
		return  sqlSessionTemplate.selectList("ajax_members.getMemberList");
	}
	@Override
	public String getMemberIdChk(String u_id) {
		int result = sqlSessionTemplate.selectOne("ajax_members.getMemberIdChk", u_id);
		return String.valueOf(result); 
//		if (result > 0) { // 사용할 아이디의 죽복유무 판단
//			return "1" ;
//		}else {
//			return "0";
//		}
	}

	@Override
	public String getMemberInsert(MembersVO uvo) {
		// 자동 커밋이다.
		int result = sqlSessionTemplate.insert("ajax_members.getMemberInsert", uvo);
		return String.valueOf(result);
	}
	@Override
	public String getMemberDelete(String u_idx) {
		int result = sqlSessionTemplate.delete("ajax_members.getMemberDelete", u_idx);
		return String.valueOf(result);
	}
	@Override
	public MembersVO getMemberDetail(String u_idx) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public String getMemberUpdate(MembersVO uvo) {
		// TODO Auto-generated method stub
		return null;
	}


}
