package com.projet.controller;

import com.projet.entity.Enseigant;
import com.projet.mapper.EnseignantMapper;
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
	@FXML
	private TextField numEn;
	
	@FXML
	private PasswordField motEn;
	
	@FXML
	private PasswordField veriferMot;
	
	
	@FXML
	private TextField email;
	
	@FXML
	private Button validerEn;
	
	@FXML
	private ChoiceBox<String> question;
	
	@FXML
	private TextField reponse;
	
	@FXML
	private Label text;
	
	
	@FXML
	public void initialize() {
		question.getItems().clear();
		SqlSession sqlSession = MyBatisUtils.getSqlSession();
		EnseignantMapper enseignantMapper = sqlSession.getMapper(EnseignantMapper.class);
		List<String> questions = enseignantMapper.getQuestions();
		question.getItems().addAll(questions);
		text.setText("* Pour le mot de passe, veuillez saisir 8 caractères, \ncomprenant au moins une lettre majuscule, \nune lettre minuscule, un chiffre et un caractère spécial.");
		text.setStyle("-fx-text-fill: red;");
	}
	
	
	@FXML
	void ajouterEnseignant(ActionEvent event) {
		SqlSession sqlSession = null;
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
			if (numEn.getText().length() != 8 || !NumberUtils.isParsable(numEn.getText())) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("ERREUR: Numero enseignant invalide!");
				alert.setHeaderText("Numero enseignant invalide!");
				alert.setContentText("Numero enseignant invalide, veuillez saisir un numero de 8 chiffres.");
				alert.getDialogPane().setPrefWidth(800);
				alert.show();
				return;
			}
			sqlSession = MyBatisUtils.getSqlSession();
			EnseignantMapper enseignantMapper = sqlSession.getMapper(EnseignantMapper.class);
			List<Integer> numerosEnseignant = enseignantMapper.getNumerosEnseignant();
			for (Integer numero : numerosEnseignant) {
				if (Integer.parseInt(numEn.getText()) == numero) {
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setTitle("ERREUR: Ce numero enseignant existe deja!");
					alert.setHeaderText("Ce numero enseignant existe deja!");
					alert.setContentText("Ce numero enseignant existe deja, veuillez verifier votre saisie.");
					alert.getDialogPane().setPrefWidth(800);
					alert.show();
					return;
				}
			}
			if (!ProjetStringUtils.isValidPassword(motEn.getText())) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("ERREUR: Mot de passe invalide!");
				alert.setHeaderText("Mot de passe invalide!");
				alert.setContentText("Mot de passe invalide, veuillez saisir un autre mot de passe.");
				alert.getDialogPane().setPrefWidth(800);
				alert.show();
				return;
			}
			if (!motEn.getText().equals(veriferMot.getText())) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("ERREUR: Les deux mots de passe saisis ne sont pas identiques!");
				alert.setHeaderText("Les deux mots de passe saisis ne sont pas identiques!");
				alert.setContentText("Les deux mots de passe saisis ne sont pas identiques, veuillez verifier votre saisie.");
				alert.getDialogPane().setPrefWidth(800);
				alert.show();
				return;
			}
			
			if (!ProjetStringUtils.isValidEmail(email.getText())) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("ERREUR: Email invalide!");
				alert.setHeaderText("Email invalide!");
				alert.setContentText("Email invalide, veuillez verifier votre saisie.");
				alert.getDialogPane().setPrefWidth(800);
				alert.show();
				return;
			}
			
			
			
			Enseigant enseigant = new Enseigant();
			enseigant.setNumeroEnseignant(Integer.parseInt(numEn.getText()));
			enseigant.setMotDePasseEnseignant(ProjetStringUtils.sha256(motEn.getText()));
			enseigant.setEmailEnseignant(email.getText());
			enseigant.setQuestion(question.getValue());
			enseigant.setReponse(reponse.getText().toLowerCase());
			enseignantMapper.insertEnseignant(enseigant);
			
			Stage stage = (Stage) validerEn.getScene().getWindow();
			stage.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
		
		
	}
	
}