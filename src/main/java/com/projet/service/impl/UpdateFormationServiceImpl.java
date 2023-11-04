package com.projet.service.impl;

import com.projet.entity.Etudiant;
import com.projet.entity.Formation;
import com.projet.mapper.EtudiantMapper;
import com.projet.mapper.FormationMapper;
import com.projet.service.UpdateEtudiantService;
import com.projet.service.UpdateFormationService;
import com.projet.utils.MyBatisUtils;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.SqlSession;

import java.text.Format;

public class UpdateFormationServiceImpl implements UpdateFormationService {
	
	public static Formation formationToUpdate;
	
	
	@Override
	public Formation getFormationById(Formation formation) {
		SqlSession sqlSession = MyBatisUtils.getSqlSession();
		FormationMapper formationMapper = sqlSession.getMapper(FormationMapper.class);
		Formation formationDB = formationMapper.selectById(formation.getIdFormation());
		return formationDB;
	}

	
}
