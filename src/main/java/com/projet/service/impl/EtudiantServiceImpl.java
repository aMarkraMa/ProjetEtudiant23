package com.projet.service.impl;

import com.projet.entity.Etudiant;
import com.projet.entity.Formation;
import com.projet.mapper.EtudiantMapper;
import com.projet.mapper.FormationMapper;
import com.projet.service.EtudiantService;
import com.projet.service.FormationService;
import com.projet.utils.MyBatisUtils;
import javafx.scene.control.Alert;
import org.apache.ibatis.session.SqlSession;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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
	
	@Override
	public void importEtudiants(File file) {
		FileInputStream fis = null;
		XSSFWorkbook excel = null;
		SqlSession sqlSession = null;
		try {
			fis = new FileInputStream(file);
			excel = new XSSFWorkbook(fis);
			sqlSession = MyBatisUtils.getNonAutoCommittingSqlSession();
			FormationMapper formationMapper = sqlSession.getMapper(FormationMapper.class);
			EtudiantMapper etudiantMapper = sqlSession.getMapper(EtudiantMapper.class);
			XSSFSheet sheet = excel.getSheetAt(0);
			int lastRowNum = sheet.getLastRowNum();
			for (int i = 1; i <= lastRowNum; i++) {
				XSSFRow row = sheet.getRow(i);
				String nomEtudiant = row.getCell(0).getStringCellValue().trim();
				String prenomEtudiant = row.getCell(1).getStringCellValue().trim();
				String nomFormation = row.getCell(2).getStringCellValue().trim();
				String promotion = row.getCell(3).getStringCellValue().trim();
				System.out.println(nomEtudiant + " " + prenomEtudiant + " " + nomFormation + " " + promotion);
				if (!"Initiale".equals(promotion) && !"En Alternance".equals(promotion) && !"Formation Continue".equals(promotion)) {
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setTitle("ERREUR: Une des valeurs dans la colonne \"Promotion\" n'est pas valide!");
					alert.setHeaderText("Une des valeurs dans la colonne \"Promotion\" n'est pas valide!");
					alert.setContentText("Une des valeurs dans la colonne \"Promotion\" n'est pas valide, veuillez verifier votre saisie.");
					alert.getDialogPane().setPrefWidth(800);
					alert.show();
					return;
				}
				String[] infosFormation = transformerStrFormation(nomFormation, promotion);
				Formation formation = new Formation();
				formation.setNomFormation(infosFormation[0]);
				formation.setPromotion(infosFormation[1]);
				List<Formation> formationsDB = formationMapper.selectByCondition(formation);
				if (formationsDB == null || formationsDB.size() == 0) {
					formationMapper.insertFormation(formation);
					System.out.println(formation.getIdFormation());
				} else {
					formation.setIdFormation(formationsDB.get(0).getIdFormation());
				}
				Etudiant etudiant = new Etudiant();
				etudiant.setNomEtudiant(nomEtudiant.toUpperCase());
				etudiant.setPrenomEtudiant(transformerStrEtudiant(prenomEtudiant));
				etudiant.setFormation(formation);
				etudiantMapper.insertEtudiant(etudiant);
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
			
			if (excel != null) {
				try {
					excel.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private String transformerStrEtudiant(String prenom) {
		// Logique pour transformer le prénom
		String premiereLettre = prenom.substring(0, 1);
		String autresLettres = prenom.substring(1);
		String premiereLettreUpperCase = premiereLettre.toUpperCase();
		String autresLettresLowerCase = autresLettres.toLowerCase();
		String prenomF = premiereLettreUpperCase.concat(autresLettresLowerCase);
		return prenomF;
	}
	
	private String[] transformerStrFormation(String nomFormation, String promotion) {
		// Transformation du nom de la formation en majuscules
		String nomFormationUpperCase = nomFormation.toUpperCase();
		return new String[]{nomFormationUpperCase, promotion};
	}
	
	
}
