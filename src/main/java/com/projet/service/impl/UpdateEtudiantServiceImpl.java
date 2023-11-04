package com.projet.service.impl;

import com.projet.entity.Etudiant;
import com.projet.mapper.EtudiantMapper;
import com.projet.service.UpdateEtudiantService;
import com.projet.utils.MyBatisUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class UpdateEtudiantServiceImpl implements UpdateEtudiantService {
	
	public static Etudiant etudiantToUpdate;
	
	
	@Override
	public Etudiant getEtudiantById(Etudiant etudiant) {
		SqlSession sqlSession = MyBatisUtils.getSqlSession();
		EtudiantMapper etudiantMapper = sqlSession.getMapper(EtudiantMapper.class);
		Etudiant etudiantDB = etudiantMapper.selectById(etudiant);
		return etudiantDB;
	}

	
}
