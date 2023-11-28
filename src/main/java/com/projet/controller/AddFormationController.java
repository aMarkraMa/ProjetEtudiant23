package com.projet.controller;

import com.projet.entity.Formation;
import com.projet.mapper.FormationMapper;
import com.projet.service.FormationService;
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

public class AddFormationController {
	
	// Champ de texte pour le nom de la formation
	@FXML
	private TextField nomFormation;
	
	// Liste déroulante pour la promotion
	@FXML
	private ChoiceBox<String> promotion;
	
	// Bouton pour ajouter la formation
	@FXML
	private Button ajouterFor;
	
	private FormationService formationService = new FormationServiceImpl();
	
	// Initialisation de l'interface utilisateur
	@FXML
	public void initialize() {
		// Chargement des options de promotion dans la liste déroulante
		promotion.getItems().clear();
		promotion.getItems().add("Initiale");
		promotion.getItems().add("En Alternance");
		promotion.getItems().add("Formation Continue");
	}
	
	// Méthode pour transformer les données en majuscules ou format approprié
	private String[] transformerStr(String nomFormation, String promotion) {
		// Transformation du nom de la formation en majuscules
		String nomFormationUpperCase = nomFormation.toUpperCase();
		return new String[]{nomFormationUpperCase, promotion};
	}
	
	// Méthode pour ajouter une formation
	@FXML
	void ajouterFormation(ActionEvent event) {
		// Logique pour ajouter une formation avec gestion des erreurs et validation
		if (nomFormation.getText() == null || "".equals(nomFormation.getText().trim()) || promotion.getValue() == null || "".equals(promotion.getValue())) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("ERREUR: Tous les champs sont obligatoires!");
			alert.setHeaderText("Tous les champs sont obligatoires!");
			alert.setContentText("Tous les champs sont obligatoires, veuillez les remplir.");
			alert.getDialogPane().setPrefWidth(800);
			alert.show();
			return;
		}
		Formation formation = new Formation();
		String nomFormation = this.nomFormation.getText().trim();
		String promotion = this.promotion.getValue().trim();
		formation.setNomFormation(nomFormation);
		formation.setPromotion(promotion);
		try {
			List<Formation> formationsDB = formationService.selectByCondition(formation);
			if (formationsDB.size() > 0) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("ERREUR: Cette formation existe deja!");
				alert.setHeaderText("Cette formation existe deja!");
				alert.setContentText("Cette formation existe deja, veuillez verifier votre saisie.");
				alert.getDialogPane().setPrefWidth(800);
				alert.show();
				return;
			}
			formationService.addFormation(formation);
			Stage stage = (Stage) ajouterFor.getScene().getWindow();
			stage.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}