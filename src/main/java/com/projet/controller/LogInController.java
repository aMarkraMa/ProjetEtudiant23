package com.projet.controller;

import com.projet.Main;
import com.projet.entity.Enseigant;
import com.projet.mapper.EnseignantMapper;
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
	
	
	public void initialize() {
		Image imageB = new Image("com/projet/img/login.png");
		Image imageL = new Image("com/projet/img/dauphine.png");
		
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
		// Properties properties = new Properties();
		// properties.put("mail.smtp.host", "smtp.126.com");
		// properties.put("mail.smtp.port", 465);
		// properties.put("mail.smtp.auth", true);
		// properties.put("mail.smtp.ssl.enable", true);
		//
		// Session session = Session.getInstance(properties, new Authenticator() {
		// 	@Override
		// 	protected PasswordAuthentication getPasswordAuthentication() {
		// 		return new PasswordAuthentication("yli08666@gmail.com","Liyingxuan2001.");
		// 	}
		// });
		//
		// try {
		// 	MimeMessage message = new MimeMessage(session);
		// 	message.setFrom(new InternetAddress("yli08666@gmail.com"));
		// 	message.addRecipient(Message.RecipientType.TO, new InternetAddress("liyingxuanfr@126.com"));
		// 	message.setSubject("Votre nouveau mot de passe");
		// 	message.setText("Votre nouveau mot de passe est Asd123..");
		// 	Transport.send(message);
		// } catch (MessagingException e) {
		// 	e.printStackTrace();
		// }
		
	}
	
	public void inscrire(ActionEvent actionEvent) {
		Main.addView("/com/projet/view/Inscrire.fxml");
		
	}
	
	public void connecter(ActionEvent actionEvent) {
		SqlSession sqlSession = null;
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
			sqlSession = MyBatisUtils.getSqlSession();
			EnseignantMapper enseignantMapper = sqlSession.getMapper(EnseignantMapper.class);
			Enseigant enseigant = new Enseigant();
			enseigant.setNumeroEnseignant(Integer.parseInt(idEn.getText()));
			Enseigant enseignantDB = enseignantMapper.getEnseignant(enseigant);
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
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
		
	}
}
