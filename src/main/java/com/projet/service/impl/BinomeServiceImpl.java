package com.projet.service.impl;

import com.projet.entity.Binome;
import com.projet.mapper.BinomeMapper;
import com.projet.mapper.NoteMapper;
import com.projet.service.BinomeService;
import com.projet.utils.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class BinomeServiceImpl implements BinomeService {
	
	public static Binome binomeToUpdate;
	
	// Récupère la liste des binômes associés à un projet spécifique
	@Override
	public List<Binome> getBinomesByIdProjet(Integer idProjet) {
		SqlSession sqlSession = null;
		try {
			sqlSession = MyBatisUtils.getSqlSession();
			BinomeMapper binomeMapper = sqlSession.getMapper(BinomeMapper.class);
			List<Binome> binomes = binomeMapper.getBinomesByIdProjet(idProjet);
			return binomes;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
	}
	
	// Sélectionne un binôme spécifique par identifiant de binôme et de projet
	@Override
	public Binome selectByIdBinomeAndIdProjet(Binome binome) {
		SqlSession sqlSession = null;
		try {
			sqlSession = MyBatisUtils.getSqlSession();
			BinomeMapper binomeMapper = sqlSession.getMapper(BinomeMapper.class);
			Binome binomeDB = binomeMapper.selectByIdBinomeAndIdProjet(binome);
			return binomeDB;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
	}
	
	// Supprime un binôme par ses identifiants et ceux des étudiants associés
	@Override
	public void deleteBinome(Integer idBinome, Integer idProjet, Integer idEtudiant1, Integer idEtudiant2) {
		SqlSession sqlSession = null;
		try {
			sqlSession = MyBatisUtils.getNonAutoCommittingSqlSession();
			BinomeMapper binomeMapper = sqlSession.getMapper(BinomeMapper.class);
			binomeMapper.deleteBinome(idBinome, idProjet);
			binomeMapper.deleteAppartenir(idBinome, idProjet);
			binomeMapper.deleteNotesSoutenance(idEtudiant1, idProjet);
			if (idEtudiant2 != null) {
				binomeMapper.deleteNotesSoutenance(idEtudiant2, idProjet);
			}
			sqlSession.commit();
		} catch (Exception e) {
			if (sqlSession != null) {
				sqlSession.rollback();
			}
			e.printStackTrace();
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
	}
	
	// Sélectionne des binômes selon des conditions spécifiques
	@Override
	public List<Binome> selectByCondition(String nomMatiere, String sujet, Integer idProjet) {
		SqlSession sqlSession = null;
		try {
			sqlSession = MyBatisUtils.getSqlSession();
			BinomeMapper binomeMapper = sqlSession.getMapper(BinomeMapper.class);
			List<Binome> binomes = binomeMapper.selectByCondition(nomMatiere, sujet, idProjet);
			return binomes;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
	}
	
	// Récupère un binôme par identifiant de projet et d'étudiant
	@Override
	public Binome getBinomeByIdProjetAndIdEtudiant(Integer idEtudiant, Integer idProjet) {
		SqlSession sqlSession = null;
		try {
			sqlSession = MyBatisUtils.getSqlSession();
			BinomeMapper binomeMapper = sqlSession.getMapper(BinomeMapper.class);
			Binome binomeDB = binomeMapper.getBinomeByIdProjetAndIdEtudiant(idEtudiant, idProjet);
			return binomeDB;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
	}
	
	// Ajoute un nouveau binôme
	@Override
	public void addBinome(Binome binome) {
		SqlSession sqlSession = null;
		try {
			sqlSession = MyBatisUtils.getNonAutoCommittingSqlSession();
			BinomeMapper binomeMapper = sqlSession.getMapper(BinomeMapper.class);
			binomeMapper.insertBinomeStep1(binome);
			binomeMapper.insertBinomeStep2OrUpdateBinomeStep1(binome);
			if (binome.getEtudiants().size() > 1) {
				binomeMapper.insertBinomeStep3OrUpdateBinomeStep2(binome);
			}
			binomeMapper.insertOrUpdateBinomeStep4AndStep5(binome.getProjet().getIdProjet(), binome.getEtudiants().get(0).getIdEtudiant(), -1.0);
			if (binome.getEtudiants().size() > 1) {
				binomeMapper.insertNoteSoutenance(binome.getProjet().getIdProjet(), binome.getEtudiants().get(1).getIdEtudiant(), -1.0);
			}
			sqlSession.commit();
		} catch (Exception e) {
			if (sqlSession != null) {
				sqlSession.rollback();
			}
			e.printStackTrace();
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
	}
	
	// Supprime une note de soutenance par identifiant d'étudiant et de projet
	@Override
	public void deleteNoteSoutenance(Integer idEtudiant, Integer idProjet) {
		SqlSession sqlSession = null;
		try {
			sqlSession = MyBatisUtils.getSqlSession();
			BinomeMapper binomeMapper = sqlSession.getMapper(BinomeMapper.class);
			binomeMapper.deleteNotesSoutenance(idEtudiant, idProjet);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
	}
	
	// Insère une note de soutenance
	@Override
	public void insertNoteSoutenance(Integer idEtudiant, Integer idProjet, Double noteSoutenance) {
		SqlSession sqlSession = null;
		try {
			sqlSession = MyBatisUtils.getSqlSession();
			BinomeMapper binomeMapper = sqlSession.getMapper(BinomeMapper.class);
			binomeMapper.insertNoteSoutenance(idEtudiant, idProjet, noteSoutenance);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
	}
	
	// Met à jour un binôme
	@Override
	public void updateBinome(Binome binome) {
		SqlSession sqlSession = null;
		try {
			sqlSession = MyBatisUtils.getNonAutoCommittingSqlSession();
			BinomeMapper binomeMapper = sqlSession.getMapper(BinomeMapper.class);
			NoteMapper noteMapper = sqlSession.getMapper(NoteMapper.class);
			binomeMapper.deleteAppartenir(binome.getIdBinome(), binome.getProjet().getIdProjet());
			binomeMapper.insertBinomeStep2OrUpdateBinomeStep1(binome);
			if (binome.getEtudiants().size() > 1) {
				binomeMapper.insertBinomeStep3OrUpdateBinomeStep2(binome);
			}
			Double noteSoutenanceEtudiant1 = noteMapper.getNotesByIdProjetAndIdEtudiant(binome.getProjet().getIdProjet(), binome.getEtudiants().get(0).getIdEtudiant()).getNoteSoutenance();
			Double noteSoutenanceEtudiant2 = null;
			if (binome.getEtudiants().size() > 1) {
				noteSoutenanceEtudiant2 = noteMapper.getNotesByIdProjetAndIdEtudiant(binome.getProjet().getIdProjet(), binome.getEtudiants().get(1).getIdEtudiant()).getNoteSoutenance();
			}
			if (binome.getNoteRapport() != null || binome.getDateReelleRemise() != null) {
				binomeMapper.updateBinomeStep3(binome.getNoteRapport(), binome.getDateReelleRemise(), binome.getIdBinome(), binome.getProjet().getIdProjet());
			}
			binomeMapper.deleteNotesSoutenance(binome.getEtudiants().get(0).getIdEtudiant(), binome.getProjet().getIdProjet());
			if (binome.getEtudiants().size() > 1) {
				binomeMapper.deleteNotesSoutenance(binome.getEtudiants().get(1).getIdEtudiant(), binome.getProjet().getIdProjet());
			}
			binomeMapper.insertOrUpdateBinomeStep4AndStep5(binome.getProjet().getIdProjet(), binome.getEtudiants().get(0).getIdEtudiant(), noteSoutenanceEtudiant1);
			if (binome.getEtudiants().size() > 1) {
				if (noteSoutenanceEtudiant2 != null) {
					binomeMapper.insertNoteSoutenance(binome.getProjet().getIdProjet(), binome.getEtudiants().get(1).getIdEtudiant(), noteSoutenanceEtudiant2);
				} else {
					binomeMapper.insertNoteSoutenance(binome.getProjet().getIdProjet(), binome.getEtudiants().get(1).getIdEtudiant(), -1.0);
				}
			}
			sqlSession.commit();
		} catch (Exception e) {
			if (sqlSession != null) {
				sqlSession.rollback();
			}
			e.printStackTrace();
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
	}
	
	@Override
	public Integer getNbBinomeByIdEtudiant(Integer idEtudiant) {
		SqlSession sqlSession = null;
		try {
			sqlSession = MyBatisUtils.getSqlSession();
			BinomeMapper binomeMapper = sqlSession.getMapper(BinomeMapper.class);
			Integer nbBinome = binomeMapper.getNbBinomeByIdEtudiant(idEtudiant);
			return nbBinome;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
	}
}
