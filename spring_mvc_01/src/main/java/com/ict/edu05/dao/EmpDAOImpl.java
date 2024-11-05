package com.ict.edu05.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ict.edu05.vo.EmpVO;

@Repository
public class EmpDAOImpl implements EmpDAO{
	
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;

	@Override
	public List<EmpVO> getList() throws Exception {
		
		return sqlSessionTemplate.selectList("emp.selectAll");  
	}

	@Override
	public List<EmpVO> getSearch(String deptno) throws Exception {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList("emp.search", deptno);
	}

	// Mybatis에서 파라미터 자리는 없거나 하나만 존재한다.
	// 그래서 두개 이상일 때에는 vo를 사용하는 방법과 Map을 사용하는 방법이 있다.
	
	// vo 사용 
	@Override
	public List<EmpVO> getSearch(EmpVO empvo) throws Exception {
		
		return sqlSessionTemplate.selectList("emp.dynamic", empvo);
	}

	@Override
	public List<EmpVO> getSearch(String idx, String keyword) throws Exception {
		// Map 사용
		Map<String, String> map = new HashMap<String, String>();
		map.put("idx", idx);
		map.put("keyword", keyword);
		return  sqlSessionTemplate.selectList("emp.dynamic2", map)  ;
	}

}
