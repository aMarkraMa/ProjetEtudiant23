package com.projet.test;

import com.projet.entity.Enseigant;
import com.projet.mapper.EnseignantMapper;
import com.projet.utils.MyBatisUtils;
import com.projet.utils.ProjetStringUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

public class EnseignantMapperTest {
	@Test
	public void testLogIn(){
		SqlSession sqlSession = null;
		try {
			Integer numero = 12345678;
			String motDePasseSaisie = "Ab-12345";
			Enseigant enseigant = new Enseigant();
			enseigant.setNumeroEnseignant(numero);
			enseigant.setMotDePasseEnseignant(motDePasseSaisie);
			motDePasseSaisie = ProjetStringUtils.sha256(motDePasseSaisie);
			sqlSession = MyBatisUtils.getSqlSession();
			EnseignantMapper enseignantMapper = sqlSession.getMapper(EnseignantMapper.class);
			Enseigant enseignant = enseignantMapper.getEnseignant(enseigant);
			if (enseignant.getMotDePasseEnseignant().equals(motDePasseSaisie)){
				System.out.println("登录成功");
			} else {
				System.out.println("登录失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
	}
	
	
	@Test
	public void testInsert(){
		SqlSession sqlSession = null;
		try {
			Integer numero = 12345678;
			String motDePasseOriginal = "Ab-12345";
			String email = "1321913193@qq.com";
			String motDePasse = ProjetStringUtils.sha256(motDePasseOriginal);
			Enseigant enseigant = new Enseigant();
			enseigant.setNumeroEnseignant(numero);
			enseigant.setMotDePasseEnseignant(motDePasse);
			enseigant.setEmailEnseignant(email);
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
}
