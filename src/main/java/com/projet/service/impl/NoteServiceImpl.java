package com.projet.service.impl;

import com.projet.entity.Binome;
import com.projet.entity.Etudiant;
import com.projet.entity.Note;
import com.projet.mapper.BinomeMapper;
import com.projet.mapper.EtudiantMapper;
import com.projet.mapper.NoteMapper;
import com.projet.service.NoteService;
import com.projet.utils.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class NoteServiceImpl implements NoteService {
	
	// Sélectionne des notes selon des conditions spécifiques, comme l'identifiant du projet et de l'étudiant
	@Override
	public List<Note> selectByCondition(Integer idProjet, Integer idEtudiant) {
		SqlSession sqlSession = null;
		try {
			sqlSession = MyBatisUtils.getSqlSession();
			NoteMapper noteMapper = sqlSession.getMapper(NoteMapper.class);
			List<Note> notes = noteMapper.selectByCondition(idProjet, idEtudiant);
			return notes;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
	}
	
	// Met à jour la note de soutenance pour un étudiant et un projet spécifiques
	@Override
	public void updateNoteSoutenance(Integer idProjet, Integer idEtudiant, Double noteSoutenance) {
		SqlSession sqlSession = null;
		try {
			sqlSession = MyBatisUtils.getSqlSession();
			NoteMapper noteMapper = sqlSession.getMapper(NoteMapper.class);
			noteMapper.updateNoteSoutenance(idProjet, idEtudiant, noteSoutenance);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
		
	}
	
	// Obtient les notes d'un étudiant pour un projet spécifique
	@Override
	public Note getNotesByIdProjetAndIdEtudiant(Integer idProjet, Integer idEtudiant) {
		SqlSession sqlSession = null;
		try {
			sqlSession = MyBatisUtils.getSqlSession();
			NoteMapper noteMapper = sqlSession.getMapper(NoteMapper.class);
			Note note = noteMapper.getNotesByIdProjetAndIdEtudiant(idProjet, idEtudiant);
			return note;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
	}
}
