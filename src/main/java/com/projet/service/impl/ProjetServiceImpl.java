package com.projet.service.impl;

import com.projet.entity.Projet;
import com.projet.mapper.ProjetMapper;
import com.projet.service.ProjetService;
import com.projet.utils.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class ProjetServiceImpl implements ProjetService {
	
	// Sélectionne des projets selon des conditions spécifiques
	@Override
	public List<Projet> selectByCondition(Projet projet) {
		SqlSession sqlSession = null;
		try {
			sqlSession = MyBatisUtils.getSqlSession();
			ProjetMapper projetMapper = sqlSession.getMapper(ProjetMapper.class);
			List<Projet> projets = projetMapper.selectByCondition(projet);
			return projets;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
	}
	
	// Met à jour un projet
	@Override
	public void updateProjet(Projet projet) {
		SqlSession sqlSession = null;
		try {
			sqlSession = MyBatisUtils.getSqlSession();
			ProjetMapper projetMapper = sqlSession.getMapper(ProjetMapper.class);
			projetMapper.updateProjet(projet);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
	}
	
	// Supprime un projet par son identifiant
	@Override
	public void deleteById(Integer idProjet) {
		SqlSession sqlSession = null;
		try {
			sqlSession = MyBatisUtils.getSqlSession();
			ProjetMapper projetMapper = sqlSession.getMapper(ProjetMapper.class);
			projetMapper.deleteById(idProjet);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
	}
	
	// Sélectionne tous les projets
	@Override
	public List<Projet> selectAll() {
		SqlSession sqlSession = null;
		try {
			sqlSession = MyBatisUtils.getSqlSession();
			ProjetMapper projetMapper = sqlSession.getMapper(ProjetMapper.class);
			List<Projet> projets = projetMapper.selectAll();
			return projets;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
	}
	
	// Récupère les identifiants de tous les projets
	@Override
	public List<Integer> getIdsProjet() {
		SqlSession sqlSession = null;
		try {
			sqlSession = MyBatisUtils.getSqlSession();
			ProjetMapper projetMapper = sqlSession.getMapper(ProjetMapper.class);
			List<Integer> idsProjet = projetMapper.getIdsProjet();
			return idsProjet;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
	}
	
	// Récupère les identifiants des projets selon des conditions spécifiques
	@Override
	public List<Integer> getIdsProjetByCondition(String nomMatiere, String sujet) {
		SqlSession sqlSession = null;
		try {
			sqlSession = MyBatisUtils.getSqlSession();
			ProjetMapper projetMapper = sqlSession.getMapper(ProjetMapper.class);
			List<Integer> idsProjet = projetMapper.getIdsProjetByCondition(nomMatiere, sujet);
			return idsProjet;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
	}
	
	// Ajoute un nouveau projet
	@Override
	public void addProjet(Projet projet) {
		SqlSession sqlSession = null;
		try {
			sqlSession = MyBatisUtils.getSqlSession();
			ProjetMapper projetMapper = sqlSession.getMapper(ProjetMapper.class);
			projetMapper.insertProjet(projet);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
	}
}
