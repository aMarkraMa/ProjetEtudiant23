package com.projet.controller;

import com.projet.entity.Projet;
import com.projet.mapper.ProjetMapper;
import com.projet.service.ProjetService;
import com.projet.service.impl.ProjetServiceImpl;
import com.projet.utils.MyBatisUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class AddProjetController {
	
	// Champ de texte pour le nom de la matière
	@FXML
	private TextField nomMatiere;
	
	// Champ de texte pour le sujet du projet
	@FXML
	private TextField sujet;
	
	// Sélecteur de date pour la date prévue de remise
	@FXML
	private DatePicker datePicker;
	
	// Champ de texte pour le pourcentage de la soutenance
	@FXML
	private TextField pourcentageSoutenance;
	
	// Bouton pour ajouter le projet
	@FXML
	private Button addProjet;
	
	private ProjetService projetService = new ProjetServiceImpl();
	
	// Initialisation de l'interface utilisateur
	public void initialize() {
		// Ajout d'un écouteur pour s'assurer que le pourcentage est un nombre
		pourcentageSoutenance.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue.matches("\\d+")) {
				pourcentageSoutenance.setText(newValue.replaceAll("[^\\d]+", ""));
			}
		});
	}
	
	// Méthode pour ajouter un projet
	public void addProjet() {
		// Logique pour ajouter un projet avec gestion des erreurs et validation
		try {
			
			Projet projet = new Projet();
			if (nomMatiere.getText() != null && !nomMatiere.getText().trim().isEmpty()) {
				projet.setNomMatiere(nomMatiere.getText().trim());
			} else {
				showErr("Nom Matière ne peux pas être vide");
				return;
			}
			if (sujet.getText() != null && !sujet.getText().trim().isEmpty()) {
				projet.setSujet(sujet.getText().trim());
			} else {
				showErr("Sujet ne peux pas être vide");
				return;
			}
			if (datePicker.getValue() != null) {
				projet.setDatePrevueRemise(datePicker.getValue());
			} else {
				showErr("DatePrevueRemise ne peux pas être vide");
				return;
			}
			
			if (pourcentageSoutenance.getText() != null && !pourcentageSoutenance.getText().trim().isEmpty()) {
				if (Integer.valueOf(pourcentageSoutenance.getText().trim()) >= 0 && Integer.valueOf(pourcentageSoutenance.getText().trim()) <= 100) {
					projet.setPourcentageSoutenance(Integer.valueOf(pourcentageSoutenance.getText().trim()));
				} else {
					showErr("Taux de note doit etre entre 0 - 100");
					return;
				}
				
			} else {
				showErr("pourcentageSoutenance ne peux pas être vide");
				return;
			}
			
			Projet p = new Projet();
			p.setNomMatiere(projet.getNomMatiere());
			p.setSujet(projet.getSujet());
			List<Projet> ps = projetService.selectByCondition(p);
			if (ps.isEmpty()) {
				projetService.addProjet(projet);
			} else {
				showErr("Projet : " + nomMatiere.getText().trim() + "-" + sujet.getText().trim() + " existe déjà");
				return;
			}
			
			Stage stage = (Stage) addProjet.getScene().getWindow();
			stage.close();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}
	
	// Affiche une alerte en cas d'erreur
	public void showErr(String msg) {
		// Création et affichage d'une alerte d'erreur
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("Error Dialog");
		alert.setHeaderText("Oups");
		alert.setContentText(msg);
		alert.show();
	}
}
