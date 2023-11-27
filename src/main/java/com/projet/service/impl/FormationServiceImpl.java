package com.projet.service.impl;

import com.projet.entity.Formation;
import com.projet.mapper.FormationMapper;
import com.projet.service.FormationService;
import com.projet.utils.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class FormationServiceImpl implements FormationService {
	
	public static Formation formationToUpdate;
	
	
	@Override
	public Formation getFormationById(Formation formation) {
		SqlSession sqlSession = MyBatisUtils.getSqlSession();
		FormationMapper formationMapper = sqlSession.getMapper(FormationMapper.class);
		Formation formationDB = formationMapper.selectById(formation.getIdFormation());
		return formationDB;
	}
	
	@Override
	public void deleteById(Integer idFormation) {
		SqlSession sqlSession = null;
		try {
			sqlSession = MyBatisUtils.getSqlSession();
			FormationMapper formationMapper = sqlSession.getMapper(FormationMapper.class);
			formationMapper.deleteById(idFormation);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
	}
	
	@Override
	public List<Formation> selectAll() {
		SqlSession sqlSession = null;
		try {
			sqlSession = MyBatisUtils.getSqlSession();
			FormationMapper formationMapper = sqlSession.getMapper(FormationMapper.class);
			List<Formation> formations = formationMapper.selectAll();
			return formations;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
	}
	
	@Override
	public List<Formation> selectByCondition(Formation formation) {
		SqlSession sqlSession = null;
		try {
			sqlSession = MyBatisUtils.getSqlSession();
			FormationMapper formationMapper = sqlSession.getMapper(FormationMapper.class);
			List<Formation> formations = formationMapper.selectByCondition(formation);
			return formations;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
	}
	
	@Override
	public List<String> getNomsFormation() {
		SqlSession sqlSession = null;
		try {
			sqlSession = MyBatisUtils.getSqlSession();
			FormationMapper formationMapper = sqlSession.getMapper(FormationMapper.class);
			List<String> nomsFormation = formationMapper.getNomsFormation();
			return nomsFormation;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
	}
	
	@Override
	public List<String> getPromotions() {
		SqlSession sqlSession = null;
		try {
			sqlSession = MyBatisUtils.getSqlSession();
			FormationMapper formationMapper = sqlSession.getMapper(FormationMapper.class);
			List<String> promotions = formationMapper.getPromotions();
			return promotions;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
	}
	
	@Override
	public void addFormation(Formation formation) {
		SqlSession sqlSession = null;
		try {
			sqlSession = MyBatisUtils.getSqlSession();
			FormationMapper formationMapper = sqlSession.getMapper(FormationMapper.class);
			formationMapper.addFormation(formation);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
	}
	
	@Override
	public void updateFormation(Formation formation) {
		SqlSession sqlSession = null;
		try {
			sqlSession = MyBatisUtils.getSqlSession();
			FormationMapper formationMapper = sqlSession.getMapper(FormationMapper.class);
			formationMapper.updateFormation(formation);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
	}
}
