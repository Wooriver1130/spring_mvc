package com.ict.edu04.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ict.edu04.vo.MembersVO;

public interface  AjaxMembersDAO {
	
	public List<MembersVO> getMemberList();
	public String getMemberIdChk(String u_id);
	public MembersVO getMemberDetail(String u_idx);
	public String getMemberInsert(MembersVO uvo);
	public String getMemberDelete(String u_idx);
	public String getMemberUpdate(MembersVO uvo);
}

