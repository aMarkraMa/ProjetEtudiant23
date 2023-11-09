package com.projet.controller;

import com.projet.entity.Binome;
import com.projet.entity.Etudiant;
import com.projet.entity.Formation;
import com.projet.entity.Projet;
import com.projet.mapper.BinomeMapper;
import com.projet.mapper.EtudiantMapper;
import com.projet.mapper.FormationMapper;
import com.projet.mapper.ProjetMapper;
import com.projet.service.UpdateBinomeService;
import com.projet.service.UpdateEtudiantService;
import com.projet.service.impl.UpdateBinomeServiceImpl;
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

import java.util.ArrayList;
import java.util.List;

public class AddBinomeController {
	
	@FXML
	private TextField idBinome;
	
	@FXML
	private ChoiceBox<String> projet;
	
	@FXML
	private ChoiceBox<String> etudiant1;
	
	@FXML
	private Button ajouterEtu;
	
	@FXML
	private ChoiceBox<String> etudiant2;
	
	
	@FXML
	public void initialize() {
		projet.getItems().clear();
		etudiant1.getItems().clear();
		etudiant2.getItems().clear();
		SqlSession sqlSession = MyBatisUtils.getSqlSession();
		ProjetMapper projetMapper = sqlSession.getMapper(ProjetMapper.class);
		EtudiantMapper etudiantMapper = sqlSession.getMapper(EtudiantMapper.class);
		
		List<Integer> idsEtudiant = etudiantMapper.getIdsEtudiant();
		List<String> nomsEtudiant = etudiantMapper.getNomsEtudiant();
		List<String> prenomsEtudiant = etudiantMapper.getPrenomsEtudiant();
		
		List<String> etudiants = new ArrayList<>();
		
		for (int i = 0; i < idsEtudiant.size(); i++) {
			etudiants.add(idsEtudiant.get(i) + " " + nomsEtudiant.get(i) + " " + prenomsEtudiant.get(i));
		}
		
		etudiant1.getItems().addAll(etudiants);
		etudiant2.getItems().addAll(etudiants);
		
		
	}
	
	
	@FXML
	void ajouterBinome(ActionEvent event) {
		
		if (etudiant1.getValue().equals(etudiant2.getValue())) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("ERREUR: Vous avez choisi deux fois le meme etudiant!");
			alert.setHeaderText("Vous avez choisi deux fois le meme etudiant!");
			alert.setContentText("Vous avez choisi deux fois le meme etudiant, veuillez choisir deux etudiants differents pour ajouter un binome.");
			alert.getDialogPane().setPrefWidth(800);
			alert.show();
		} else {
			Binome binome = new Binome();
			
			Etudiant etudiant1P = new Etudiant();
			String[] infoEtudiant1 = etudiant1.getValue().split(" ");
			etudiant1P.setIdEtudiant(Integer.parseInt(infoEtudiant1[0]));
			
			Etudiant etudiant2P = null;
			if (etudiant2.getValue() != null) {
				etudiant2P = new Etudiant();
				String[] infoEtudiant2 = etudiant2.getValue().split(" ");
				etudiant2P.setIdEtudiant(Integer.parseInt(infoEtudiant2[0]));
			}
			
			List<Etudiant> etudiants = new ArrayList<>();
			etudiants.add(etudiant1P);
			if (etudiant2P != null) {
				etudiants.add(etudiant2P);
			}
			
			binome.setEtudiants(etudiants);
			
		}
		
		
		// SqlSession sqlSession = null;
		// try {
		// 	sqlSession = MyBatisUtils.getSqlSession();
		// 	FormationMapper formationMapper = sqlSession.getMapper(FormationMapper.class);
		// 	Formation formation = new Formation();
		// 	formation.setNomFormation(nomFormation.getValue());
		// 	formation.setPromotion(promotion.getValue());
		// 	List<Formation> formations = formationMapper.selectByCondition(formation);
		// 	Formation formationDB;
		// 	if (formations != null && formations.size() > 0) {
		// 		formationDB = formations.get(0);
		// 		etudiant.setFormation(formationDB);
		// 		EtudiantMapper etudiantMapper = sqlSession.getMapper(EtudiantMapper.class);
		// 		etudiantMapper.addEtudiant(etudiant);
		// 		Stage stage = (Stage) ajouterEtu.getScene().getWindow();
		// 		stage.close();
		// 	} else {
		// 		Alert alert = new Alert(Alert.AlertType.ERROR);
		// 		alert.setTitle("ERREUR: Il n'y a pas cette formation");
		// 		alert.setHeaderText("Il n'y a pas cette formation");
		// 		alert.setContentText("La formation que vous choisissez n'existe pas. Veuillez l'ajouter dans la section \"Gestion de formations\".");
		// 		alert.show();
		// 	}
		// } catch (Exception e) {
		// 	e.printStackTrace();
		// } finally {
		// 	if (sqlSession != null) {
		// 		sqlSession.close();
		// 	}
		// }
		
	}
	
}