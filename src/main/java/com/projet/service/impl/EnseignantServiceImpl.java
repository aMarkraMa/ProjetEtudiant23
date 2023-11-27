package com.projet.service.impl;

import com.projet.entity.Binome;
import com.projet.entity.Enseigant;
import com.projet.mapper.BinomeMapper;
import com.projet.mapper.EnseignantMapper;
import com.projet.service.EnseignantService;
import com.projet.utils.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class EnseignantServiceImpl implements EnseignantService {
	
	@Override
	public Enseigant getEnseignant(Enseigant enseigant) {
		SqlSession sqlSession = null;
		try {
			sqlSession = MyBatisUtils.getSqlSession();
			EnseignantMapper enseignantMapper = sqlSession.getMapper(EnseignantMapper.class);
			Enseigant enseignantDB = enseignantMapper.getEnseignant(enseigant);
			return enseignantDB;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
	}
	
	@Override
	public List<String> getQuestions() {
		SqlSession sqlSession = null;
		try {
			sqlSession = MyBatisUtils.getSqlSession();
			EnseignantMapper enseignantMapper = sqlSession.getMapper(EnseignantMapper.class);
			List<String> questions = enseignantMapper.getQuestions();
			return questions;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
	}
	
	@Override
	public List<Integer> getNumerosEnseignant() {
		SqlSession sqlSession = null;
		try {
			sqlSession = MyBatisUtils.getSqlSession();
			EnseignantMapper enseignantMapper = sqlSession.getMapper(EnseignantMapper.class);
			List<Integer> numerosEnseignant = enseignantMapper.getNumerosEnseignant();
			return numerosEnseignant;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
	}
	
	@Override
	public void insertEnseignant(Enseigant enseigant) {
		SqlSession sqlSession = null;
		try {
			sqlSession = MyBatisUtils.getSqlSession();
			EnseignantMapper enseignantMapper = sqlSession.getMapper(EnseignantMapper.class);
			enseignantMapper.insertEnseignant(enseigant);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
	}
	
	@Override
	public String getQuestionByNumEnseignant(Integer numEnseignant) {
		SqlSession sqlSession = null;
		try {
			sqlSession = MyBatisUtils.getSqlSession();
			EnseignantMapper enseignantMapper = sqlSession.getMapper(EnseignantMapper.class);
			String questionDB = enseignantMapper.getQuestionByNumEnseignant(numEnseignant);
			return questionDB;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
	}
	
	@Override
	public String getReponseByNumeroEnseignant(Integer numEnseignant) {
		SqlSession sqlSession = null;
		try {
			sqlSession = MyBatisUtils.getSqlSession();
			EnseignantMapper enseignantMapper = sqlSession.getMapper(EnseignantMapper.class);
			String reponseDB = enseignantMapper.getReponseByNumeroEnseignant(numEnseignant);
			return reponseDB;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
	}
	
	@Override
	public void updateMotDePasseByNumeroEnseignant(Integer numEnseignant, String newPassword) {
		SqlSession sqlSession = null;
		try {
			sqlSession = MyBatisUtils.getSqlSession();
			EnseignantMapper enseignantMapper = sqlSession.getMapper(EnseignantMapper.class);
			enseignantMapper.updateMotDePasseByNumeroEnseignant(numEnseignant, newPassword);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
	}
	
	@Override
	public String getEmailEnseignantByNumeroEnseignant(Integer numEnseignant) {
		SqlSession sqlSession = null;
		try {
			sqlSession = MyBatisUtils.getSqlSession();
			EnseignantMapper enseignantMapper = sqlSession.getMapper(EnseignantMapper.class);
			String emailEnseignant = enseignantMapper.getEmailEnseignantByNumeroEnseignant(numEnseignant);
			return emailEnseignant;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
	}
}
