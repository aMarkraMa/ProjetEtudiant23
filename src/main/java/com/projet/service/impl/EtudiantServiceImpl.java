package com.projet.service.impl;

import com.projet.entity.Etudiant;
import com.projet.entity.Formation;
import com.projet.mapper.EtudiantMapper;
import com.projet.service.EtudiantService;
import com.projet.utils.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class EtudiantServiceImpl implements EtudiantService {
	
	public static Etudiant etudiantToUpdate;
	
	// Récupère un étudiant par son identifiant
	@Override
	public Etudiant getEtudiantById(Etudiant etudiant) {
		SqlSession sqlSession = MyBatisUtils.getSqlSession();
		EtudiantMapper etudiantMapper = sqlSession.getMapper(EtudiantMapper.class);
		Etudiant etudiantDB = etudiantMapper.selectById(etudiant);
		return etudiantDB;
	}
	
	// Récupère la liste des étudiants associés à une formation spécifique
	@Override
	public List<Etudiant> getEtudiantsByIdFormation(Formation formation) {
		SqlSession sqlSession = null;
		try {
			sqlSession = MyBatisUtils.getSqlSession();
			EtudiantMapper etudiantMapper = sqlSession.getMapper(EtudiantMapper.class);
			Etudiant etudiant = new Etudiant();
			etudiant.setFormation(formation);
			List<Etudiant> etudiants = etudiantMapper.getEtudiantsByIdFormation(etudiant);
			return etudiants;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
	}
	
	// Supprime un étudiant par son identifiant
	@Override
	public void deleteById(Integer idEtudiant) {
		SqlSession sqlSession = null;
		try {
			sqlSession = MyBatisUtils.getSqlSession();
			EtudiantMapper etudiantMapper = sqlSession.getMapper(EtudiantMapper.class);
			etudiantMapper.deleteById(idEtudiant);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
	}
	
	// Sélectionne tous les étudiants
	@Override
	public List<Etudiant> selectAll() {
		SqlSession sqlSession = null;
		try {
			sqlSession = MyBatisUtils.getSqlSession();
			EtudiantMapper etudiantMapper = sqlSession.getMapper(EtudiantMapper.class);
			List<Etudiant> etudiants = etudiantMapper.selectAll();
			return etudiants;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
	}
	
	// Sélectionne des étudiants selon des conditions spécifiques
	@Override
	public List<Etudiant> selectByCondition(Etudiant etudiant) {
		SqlSession sqlSession = null;
		try {
			sqlSession = MyBatisUtils.getSqlSession();
			EtudiantMapper etudiantMapper = sqlSession.getMapper(EtudiantMapper.class);
			List<Etudiant> etudiants = etudiantMapper.selectByCondition(etudiant);
			return etudiants;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
	}
	
	// Récupère les identifiants de tous les étudiants
	@Override
	public List<Integer> getIdsEtudiant() {
		SqlSession sqlSession = null;
		try {
			sqlSession = MyBatisUtils.getSqlSession();
			EtudiantMapper etudiantMapper = sqlSession.getMapper(EtudiantMapper.class);
			List<Integer> idsEtudiant = etudiantMapper.getIdsEtudiant();
			return idsEtudiant;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
	}
	
	// Récupère les identifiants des étudiants selon des conditions spécifiques
	@Override
	public List<Integer> getIdsEtudiantByCondition(String nomEtudiant, String prenomEtudiant) {
		SqlSession sqlSession = null;
		try {
			sqlSession = MyBatisUtils.getSqlSession();
			EtudiantMapper etudiantMapper = sqlSession.getMapper(EtudiantMapper.class);
			List<Integer> idsEtudiant = etudiantMapper.getIdsEtudiantByCondition(nomEtudiant, prenomEtudiant);
			return idsEtudiant;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
	}
	
	// Ajoute un nouvel étudiant
	@Override
	public void addEtudiant(Etudiant etudiant) {
		SqlSession sqlSession = null;
		try {
			sqlSession = MyBatisUtils.getSqlSession();
			EtudiantMapper etudiantMapper = sqlSession.getMapper(EtudiantMapper.class);
			etudiantMapper.insertEtudiant(etudiant);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
	}
	
	// Récupère les noms de tous les étudiants
	@Override
	public List<String> getNomsEtudiant() {
		SqlSession sqlSession = null;
		try {
			sqlSession = MyBatisUtils.getSqlSession();
			EtudiantMapper etudiantMapper = sqlSession.getMapper(EtudiantMapper.class);
			List<String> nomsEtudiant = etudiantMapper.getNomsEtudiant();
			return nomsEtudiant;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
	}
	
	// Récupère les prénoms de tous les étudiants
	@Override
	public List<String> getPrenomsEtudiant() {
		SqlSession sqlSession = null;
		try {
			sqlSession = MyBatisUtils.getSqlSession();
			EtudiantMapper etudiantMapper = sqlSession.getMapper(EtudiantMapper.class);
			List<String> prenomsEtudiant = etudiantMapper.getPrenomsEtudiant();
			return prenomsEtudiant;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
	}
	
	// Met à jour un étudiant
	@Override
	public void updateEtudiant(Etudiant etudiant) {
		SqlSession sqlSession = null;
		try {
			sqlSession = MyBatisUtils.getSqlSession();
			EtudiantMapper etudiantMapper = sqlSession.getMapper(EtudiantMapper.class);
			etudiantMapper.updateEtudiant(etudiant);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
	}
}
