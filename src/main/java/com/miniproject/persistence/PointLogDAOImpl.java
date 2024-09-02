package com.miniproject.persistence;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.miniproject.model.PointLogDTO;

@Repository
public class PointLogDAOImpl implements PointLogDAO{

	@Inject
	private SqlSession ses;
	
	private static String ns = "com.miniproject.mappers.pointlogmepper.";
	
	@Override
	public int insertPointLog(PointLogDTO pointLogDTO) throws Exception {

		return ses.insert(ns + "insertPointLog", pointLogDTO);
	}

}
