package com.projet.controller;

import com.projet.entity.Enseigant;
import com.projet.mapper.EnseignantMapper;
import com.projet.service.EnseignantService;
import com.projet.service.impl.EnseignantServiceImpl;
import com.projet.utils.MyBatisUtils;
import com.projet.utils.ProjetStringUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class InscrireController {
	// Champ de texte pour le numéro de l'enseignant
	@FXML
	private TextField numEn;
	
	// Champ pour le mot de passe
	@FXML
	private PasswordField motEn;
	
	// Champ pour vérifier le mot de passe
	@FXML
	private PasswordField veriferMot;
	
	// Champ de texte pour l'email
	@FXML
	private TextField email;
	
	// Bouton pour valider l'inscription
	@FXML
	private Button validerEn;
	
	// Liste déroulante pour la question de sécurité
	@FXML
	private ChoiceBox<String> question;
	
	// Champ de texte pour la réponse à la question de sécurité
	@FXML
	private TextField reponse;
	
	// Étiquette pour les instructions
	@FXML
	private Label text;
	
	private EnseignantService enseignantService = new EnseignantServiceImpl();
	
	// Initialisation de l'interface utilisateur
	@FXML
	public void initialize() {
		// Chargement des questions de sécurité et paramétrage des champs
		question.getItems().clear();
		List<String> questions = enseignantService.getQuestions();
		question.getItems().addAll(questions);
		text.setText("* Pour le mot de passe, veuillez saisir 8 caractères, \ncomprenant au moins une lettre majuscule, \nune lettre minuscule, un chiffre et un caractère spécial.");
		text.setStyle("-fx-text-fill: red;");
	}
	
	// Méthode pour ajouter un enseignant
	@FXML
	void ajouterEnseignant(ActionEvent event) {
		// Logique pour ajouter un enseignant avec gestion des erreurs et validation
		try {
			if (numEn.getText() == null || numEn.getText().trim().equals("") || motEn.getText() == null || motEn.getText().trim().equals("") || veriferMot.getText() == null || veriferMot.getText().trim().equals("") || email.getText() == null || email.getText().trim().equals("") || question.getValue() == null || question.getValue().equals("") || reponse.getText() == null || reponse.getText().equals("")) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("ERREUR: Tous les champs sont obligatoires!");
				alert.setHeaderText("Tous les champs sont obligatoires!");
				alert.setContentText("Tous les champs sont obligatoires, veuillez les remplir.");
				alert.getDialogPane().setPrefWidth(800);
				alert.show();
				return;
			}
			
			if (numEn.getText().trim().length() != 8 || !NumberUtils.isParsable(numEn.getText().trim())) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("ERREUR: Numero enseignant invalide!");
				alert.setHeaderText("Numero enseignant invalide!");
				alert.setContentText("Numero enseignant invalide, veuillez saisir un numero de 8 chiffres.");
				alert.getDialogPane().setPrefWidth(800);
				alert.show();
				return;
			}
			List<Integer> numerosEnseignant = enseignantService.getNumerosEnseignant();
			for (Integer numero : numerosEnseignant) {
				if (Integer.parseInt(numEn.getText().trim()) == numero) {
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setTitle("ERREUR: Ce numero enseignant existe deja!");
					alert.setHeaderText("Ce numero enseignant existe deja!");
					alert.setContentText("Ce numero enseignant existe deja, veuillez verifier votre saisie.");
					alert.getDialogPane().setPrefWidth(800);
					alert.show();
					return;
				}
			}
			if (!ProjetStringUtils.isValidPassword(motEn.getText().trim())) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("ERREUR: Mot de passe invalide!");
				alert.setHeaderText("Mot de passe invalide!");
				alert.setContentText("Mot de passe invalide, veuillez saisir un autre mot de passe.");
				alert.getDialogPane().setPrefWidth(800);
				alert.show();
				return;
			}
			
			if (!motEn.getText().trim().equals(veriferMot.getText().trim())) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("ERREUR: Les deux mots de passe saisis ne sont pas identiques!");
				alert.setHeaderText("Les deux mots de passe saisis ne sont pas identiques!");
				alert.setContentText("Les deux mots de passe saisis ne sont pas identiques, veuillez verifier votre saisie.");
				alert.getDialogPane().setPrefWidth(800);
				alert.show();
				return;
			}
			
			if (!ProjetStringUtils.isValidEmail(email.getText().trim())) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("ERREUR: Email invalide!");
				alert.setHeaderText("Email invalide!");
				alert.setContentText("Email invalide, veuillez verifier votre saisie.");
				alert.getDialogPane().setPrefWidth(800);
				alert.show();
				return;
			}
			
			Enseigant enseigant = new Enseigant();
			enseigant.setNumeroEnseignant(Integer.parseInt(numEn.getText().trim()));
			enseigant.setMotDePasseEnseignant(ProjetStringUtils.sha256(motEn.getText().trim()));
			enseigant.setEmailEnseignant(email.getText().trim());
			enseigant.setQuestion(question.getValue().trim());
			enseigant.setReponse(reponse.getText().trim().toLowerCase());
			enseignantService.insertEnseignant(enseigant);
			
			Stage stage = (Stage) validerEn.getScene().getWindow();
			stage.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}