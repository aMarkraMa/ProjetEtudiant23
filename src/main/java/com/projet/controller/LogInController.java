package com.projet.controller;

import com.projet.Main;
import com.projet.entity.Enseigant;
import com.projet.mapper.EnseignantMapper;
import com.projet.service.EnseignantService;
import com.projet.service.impl.EnseignantServiceImpl;
import com.projet.utils.MyBatisUtils;
import com.projet.utils.ProjetStringUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;
import javafx.scene.control.TextField;
import javafx.scene.control.Hyperlink;
import org.apache.ibatis.session.SqlSession;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class LogInController {
	@FXML
	private TextField idMot;
	
	@FXML
	private Pane paneB;
	
	@FXML
	private TextField idEn;
	
	@FXML
	private Pane paneL;
	
	@FXML
	private Button connecter;
	
	@FXML
	private Hyperlink reset;
	
	@FXML
	private Hyperlink inscrire;
	
	private EnseignantService enseignantService = new EnseignantServiceImpl();
	
	
	public void initialize() {
		Image imageB = new Image("/com/projet/img/login.png");
		Image imageL = new Image("/com/projet/img/dauphine.png");
		
		ImageView imageViewB = new ImageView(imageB);
		imageViewB.setFitWidth(600);
		imageViewB.setFitHeight(400);
		
		ImageView imageViewL = new ImageView(imageL);
		imageViewL.setFitWidth(394);
		imageViewL.setFitHeight(98);
		
		paneB.getChildren().add(imageViewB);
		paneL.getChildren().add(imageViewL);
		
	}
	
	
	public void resetPassword(ActionEvent actionEvent) {
		Main.addView("/com/projet/view/ResetPassword.fxml");
		
	}
	
	public void inscrire(ActionEvent actionEvent) {
		Main.addView("/com/projet/view/Inscrire.fxml");
	}
	
	public void connecter(ActionEvent actionEvent) {
		try {
			if (idEn.getText() == null || idEn.getText().trim().equals("") || idMot.getText() == null || idMot.getText().trim().equals("")) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("ERREUR: Tous les champs sont obligatoires!");
				alert.setHeaderText("Tous les champs sont obligatoires!");
				alert.setContentText("Tous les champs sont obligatoires, veuillez les remplir.");
				alert.getDialogPane().setPrefWidth(800);
				alert.show();
				return;
			}
			Enseigant enseigant = new Enseigant();
			enseigant.setNumeroEnseignant(Integer.parseInt(idEn.getText()));
			Enseigant enseignantDB = enseignantService.getEnseignant(enseigant);
			if (enseignantDB == null) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("ERREUR: L'identifiant ou le mot de passe n'existe pas!");
				alert.setHeaderText("L'identifiant ou le mot de passe n'existe pas!");
				alert.setContentText("L'identifiant ou le mot de passe n'existe pas, veuillez recommencer.");
				alert.getDialogPane().setPrefWidth(800);
				alert.show();
				return;
			} else {
				if (ProjetStringUtils.sha256(idMot.getText()).equals(enseignantDB.getMotDePasseEnseignant())) {
					Main.changeView("/com/projet/view/Main.fxml");
				} else {
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setTitle("ERREUR: L'identifiant ou le mot de passe n'existe pas!");
					alert.setHeaderText("L'identifiant ou le mot de passe n'existe pas!");
					alert.setContentText("L'identifiant ou le mot de passe n'existe pas, veuillez recommencer.");
					alert.getDialogPane().setPrefWidth(800);
					alert.show();
					return;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
