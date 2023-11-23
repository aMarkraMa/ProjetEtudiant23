package com.projet.controller;

import com.projet.entity.Etudiant;
import com.projet.entity.Formation;
import com.projet.mapper.EtudiantMapper;
import com.projet.mapper.FormationMapper;
import com.projet.service.UpdateEtudiantService;
import com.projet.service.impl.UpdateEtudiantServiceImpl;
import com.projet.utils.MyBatisUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class AddEtudiantController {
	
	@FXML
	private TextField prenomEtu;
	
	@FXML
	private ChoiceBox<String> nomFormation;
	
	@FXML
	private TextField nomEtu;
	
	@FXML
	private Button ajouterEtu;
	
	@FXML
	private ChoiceBox<String> promotion;
	
	private UpdateEtudiantService updateEtudiantService = new UpdateEtudiantServiceImpl();
	
	
	@FXML
	public void initialize() {
		nomFormation.getItems().clear();
		SqlSession sqlSession = MyBatisUtils.getSqlSession();
		FormationMapper mapper = sqlSession.getMapper(FormationMapper.class);
		List nomsFormation = mapper.getNomsFormation();
		nomFormation.getItems().addAll(nomsFormation);
		
		promotion.getItems().clear();
		List promotions = mapper.getPromotions();
		promotion.getItems().addAll(promotions);
	}
	
	private String transformerStr(String prenom) {
		String premiereLettre = prenom.substring(0, 1);
		String autresLettres = prenom.substring(1);
		String premiereLettreUpperCase = premiereLettre.toUpperCase();
		String autresLettresLowerCase = autresLettres.toLowerCase();
		String prenomF = premiereLettreUpperCase.concat(autresLettresLowerCase);
		return prenomF;
	}
	
	@FXML
	void ajouterEtudiant(ActionEvent event) {
		Etudiant etudiant = new Etudiant();
		etudiant.setNomEtudiant(nomEtu.getText().toUpperCase());
		etudiant.setPrenomEtudiant(transformerStr(prenomEtu.getText()));
		SqlSession sqlSession = null;
		try {
			sqlSession = MyBatisUtils.getSqlSession();
			FormationMapper formationMapper = sqlSession.getMapper(FormationMapper.class);
			Formation formation = new Formation();
			formation.setNomFormation(nomFormation.getValue());
			formation.setPromotion(promotion.getValue());
			List<Formation> formations = formationMapper.selectByCondition(formation);
			Formation formationDB;
			if (formations != null && formations.size() > 0) {
				formationDB = formations.get(0);
				etudiant.setFormation(formationDB);
				EtudiantMapper etudiantMapper = sqlSession.getMapper(EtudiantMapper.class);
				etudiantMapper.addEtudiant(etudiant);
				Stage stage = (Stage) ajouterEtu.getScene().getWindow();
				stage.close();
			} else {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("ERREUR: Il n'y a pas cette formation");
				alert.setHeaderText("Il n'y a pas cette formation");
				alert.setContentText("La formation que vous choisissez n'existe pas. Veuillez l'ajouter dans la section \"Gestion de formations\".");
				alert.show();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
		
	}
	
}