package com.projet.controller;

import com.projet.entity.Etudiant;
import com.projet.entity.Formation;
import com.projet.mapper.EtudiantMapper;
import com.projet.mapper.FormationMapper;
import com.projet.service.EtudiantService;
import com.projet.service.FormationService;
import com.projet.service.impl.EtudiantServiceImpl;
import com.projet.service.impl.FormationServiceImpl;
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
	
	private EtudiantService etudiantService = new EtudiantServiceImpl();
	
	private FormationService formationService = new FormationServiceImpl();
	
	
	@FXML
	public void initialize() {
		try {
			nomFormation.getItems().clear();
			List<String> nomsFormation = formationService.getNomsFormation();
			nomFormation.getItems().addAll(nomsFormation);
			
			promotion.getItems().clear();
			List<String> promotions = formationService.getPromotions();
			promotion.getItems().addAll(promotions);
		} catch (Exception e) {
			e.printStackTrace();
		}
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
		if (nomEtu.getText() == null || "".equals(nomEtu.getText().trim()) || prenomEtu.getText() == null || "".equals(prenomEtu.getText().trim()) || nomFormation.getValue() == null || "".equals(nomFormation.getValue()) || promotion.getValue() == null || "".equals(promotion.getValue())) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("ERREUR: Tous les champs sont obligatoires!");
			alert.setHeaderText("Tous les champs sont obligatoires!");
			alert.setContentText("Tous les champs sont obligatoires, veuillez les remplir.");
			alert.getDialogPane().setPrefWidth(800);
			alert.show();
			return;
		}
		Etudiant etudiant = new Etudiant();
		etudiant.setNomEtudiant(nomEtu.getText().trim().toUpperCase());
		etudiant.setPrenomEtudiant(transformerStr(prenomEtu.getText().trim()));
		
		try {
			Formation formation = new Formation();
			formation.setNomFormation(nomFormation.getValue().trim());
			formation.setPromotion(promotion.getValue().trim());
			List<Formation> formations = formationService.selectByCondition(formation);
			Formation formationDB;
			if (formations != null && formations.size() > 0) {
				formationDB = formations.get(0);
				etudiant.setFormation(formationDB);
				etudiantService.addEtudiant(etudiant);
				Stage stage = (Stage) ajouterEtu.getScene().getWindow();
				stage.close();
			} else {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("ERREUR: Il n'y a pas cette formation");
				alert.setHeaderText("Il n'y a pas cette formation");
				alert.setContentText("La formation que vous choisissez n'existe pas. Veuillez l'ajouter dans la section \"Gestion de formations\".");
				alert.getDialogPane().setPrefWidth(800);
				alert.show();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}