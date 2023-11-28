package com.projet.service.impl;

import com.projet.entity.Formation;
import com.projet.mapper.FormationMapper;
import com.projet.service.FormationService;
import com.projet.utils.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class FormationServiceImpl implements FormationService {
	
	public static Formation formationToUpdate;
	
	// Récupère une formation par son identifiant
	@Override
	public Formation getFormationById(Formation formation) {
		SqlSession sqlSession = MyBatisUtils.getSqlSession();
		FormationMapper formationMapper = sqlSession.getMapper(FormationMapper.class);
		Formation formationDB = formationMapper.selectById(formation.getIdFormation());
		return formationDB;
	}
	
	// Supprime une formation par son identifiant
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
	
	// Sélectionne toutes les formations
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
	
	// Sélectionne des formations selon des conditions spécifiques
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
	
	// Récupère les noms de toutes les formations
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
	
	// Récupère toutes les promotions
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
	
	// Ajoute une nouvelle formation
	@Override
	public void addFormation(Formation formation) {
		SqlSession sqlSession = null;
		try {
			sqlSession = MyBatisUtils.getSqlSession();
			FormationMapper formationMapper = sqlSession.getMapper(FormationMapper.class);
			formationMapper.insertFormation(formation);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
	}
	
	// Met à jour une formation
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
