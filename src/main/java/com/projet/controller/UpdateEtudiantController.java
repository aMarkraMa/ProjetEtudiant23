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

public class UpdateEtudiantController {
	@FXML
	private TextField prenomEtu;
	
	@FXML
	private ChoiceBox<String> nomFormation;
	
	@FXML
	private TextField nomEtu;
	
	@FXML
	private Button updateEtudiant;
	
	@FXML
	private ChoiceBox<String> promotion;
	
	private EtudiantService etudiantService = new EtudiantServiceImpl();
	
	private FormationService formationService = new FormationServiceImpl();
	
	// Initialisation de la fenêtre de mise à jour
	public void initialize() {
		try {
			// Charger les noms de formation et les promotions dans les listes déroulantes
			nomFormation.getItems().clear();
			List<String> nomsFormation = formationService.getNomsFormation();
			nomFormation.getItems().addAll(nomsFormation);
			
			promotion.getItems().clear();
			List<String> promotions = formationService.getPromotions();
			promotion.getItems().addAll(promotions);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// Récupérer les informations de l'étudiant à modifier et les afficher dans les champs correspondants
		Etudiant etudiantToUpdate = EtudiantServiceImpl.etudiantToUpdate;
		
		nomEtu.setText(etudiantToUpdate.getNomEtudiant());
		prenomEtu.setText(etudiantToUpdate.getPrenomEtudiant());
		nomFormation.setValue(etudiantToUpdate.getFormation().getNomFormation());
		promotion.setValue(etudiantToUpdate.getFormation().getPromotion());
		
	}
	
	// Méthode pour transformer la première lettre du prénom en majuscule
	private String transformerStr(String prenom) {
		String premiereLettre = prenom.substring(0, 1);
		String autresLettres = prenom.substring(1);
		String premiereLettreUpperCase = premiereLettre.toUpperCase();
		String autresLettresLowerCase = autresLettres.toLowerCase();
		String prenomF = premiereLettreUpperCase.concat(autresLettresLowerCase);
		return prenomF;
	}
	
	// Méthode pour mettre à jour un étudiant
	public void updateEtudiant(ActionEvent actionEvent) {
		// Création d'un nouvel objet Etudiant avec les informations saisies
		Etudiant etudiant = new Etudiant();
		etudiant.setIdEtudiant(EtudiantServiceImpl.etudiantToUpdate.getIdEtudiant());
		etudiant.setNomEtudiant(nomEtu.getText().trim().toUpperCase());
		etudiant.setPrenomEtudiant(transformerStr(prenomEtu.getText().trim()));
		try {
			// Recherche de la formation sélectionnée dans la base de données
			Formation formation = new Formation();
			formation.setNomFormation(nomFormation.getValue());
			formation.setPromotion(promotion.getValue());
			List<Formation> formations = formationService.selectByCondition(formation);
			Formation formationDB;
			// Mise à jour de l'étudiant si la formation existe
			if (formations != null && formations.size() > 0) {
				formationDB = formations.get(0);
				etudiant.setFormation(formationDB);
				etudiantService.updateEtudiant(etudiant);
				Stage stage = (Stage) updateEtudiant.getScene().getWindow();
				stage.close();
			} else {
				// Afficher une alerte si la formation n'existe pas
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("ERREUR: Il n'y a pas cette formation");
				alert.setHeaderText("Il n'y a pas cette formation");
				alert.setContentText("La formation que vous choisissez n'existe pas. Veuillez l'ajouter dans la section \"Gestion de formations\".");
				alert.show();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
