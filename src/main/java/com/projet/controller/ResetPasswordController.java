package com.projet.controller;

import com.projet.entity.Etudiant;
import com.projet.entity.Formation;
import com.projet.mapper.EnseignantMapper;
import com.projet.mapper.EtudiantMapper;
import com.projet.mapper.FormationMapper;
import com.projet.service.UpdateEtudiantService;
import com.projet.service.impl.UpdateEtudiantServiceImpl;
import com.projet.utils.MyBatisUtils;
import com.projet.utils.ProjetStringUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.ibatis.session.SqlSession;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.Random;

public class ResetPasswordController {
	
	@FXML
	private TextField numEn;
	
	@FXML
	private ChoiceBox<String> question;
	
	@FXML
	private TextField reponse;
	
	@FXML
	private Button reinitialiser;
	
	
	@FXML
	public void initialize() {
		SqlSession sqlSession = null;
		try {
			question.getItems().clear();
			sqlSession = MyBatisUtils.getSqlSession();
			EnseignantMapper enseignantMapper = sqlSession.getMapper(EnseignantMapper.class);
			List<String> questions = enseignantMapper.getQuestions();
			question.getItems().addAll(questions);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
		
	}
	
	
	public void reinitialiser(ActionEvent actionEvent) {
		SqlSession sqlSession = null;
		try {
			if (numEn.getText() == null || numEn.getText().trim().equals("") || question.getValue() == null || question.getValue().equals("") || reponse.getText() == null || reponse.getText().equals("")) {
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
			Integer numEnseignant = Integer.parseInt(numEn.getText());
			List<Integer> numerosEnseignant = enseignantMapper.getNumerosEnseignant();
			if (!numerosEnseignant.contains(numEnseignant)) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("ERREUR: Ce numero enseignant n'existe pas!");
				alert.setHeaderText("Ce numero enseignant n'existe pas!");
				alert.setContentText("Ce numero enseignant n'existe pas, veuillez vous inscrire.");
				alert.getDialogPane().setPrefWidth(800);
				alert.show();
				return;
			}
			String questionDB = enseignantMapper.getQuestionByNumEnseignant(numEnseignant);
			if (!questionDB.equals(question.getValue())) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("ERREUR: Vous n'avez pas choisi la bonne question!");
				alert.setHeaderText("Vous n'avez pas choisi la bonne question!");
				alert.setContentText("Vous n'avez pas choisi la bonne question, veuillez choisir la question que vous avez choisi lors de votre inscription.");
				alert.getDialogPane().setPrefWidth(800);
				alert.show();
				return;
			}
			String reponseDB = enseignantMapper.getReponseByNumeroEnseignant(numEnseignant);
			if (!reponse.getText().toLowerCase().equals(reponseDB)) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("ERREUR: Vous n'avez pas saisi la bonne reponse!");
				alert.setHeaderText("Vous n'avez pas saisi la bonne reponse!");
				alert.setContentText("Vous n'avez pas saisi la bonne reponse, veuillez reessayer.");
				alert.getDialogPane().setPrefWidth(800);
				alert.show();
				return;
			}
			String newPassword = generatePassword();
			enseignantMapper.updateMotDePasseByNumeroEnseignant(numEnseignant, ProjetStringUtils.sha256(newPassword));
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("Votre nouveau mot de passe est dans votre email");
			alert.setHeaderText("Votre nouveau mot de passe dans votre email");
			alert.setContentText("Veuillez trouver votre nouveau mot de passe dans votre email");
			alert.getDialogPane().setPrefWidth(800);
			Optional<ButtonType> resultat = alert.showAndWait();
			if (resultat.isPresent() && resultat.get() == ButtonType.OK) {
				Stage stage = (Stage) reinitialiser.getScene().getWindow();
				stage.close();
			}
			Properties properties = new Properties();
			properties.put("mail.smtp.host", "smtp.gmail.com");
			properties.put("mail.smtp.port", 465);
			properties.put("mail.smtp.auth", true);
			properties.put("mail.smtp.ssl.enable", true);

			Session session = Session.getInstance(properties, new Authenticator() {
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("lyingxuan789@gmail.com","ebiq lulx tfsq bovo");
				}
			});
			String emailEnseignant = enseignantMapper.getEmailEnseignantByNumeroEnseignant(numEnseignant);
			try {
				
				MimeMessage message = new MimeMessage(session);
				message.setFrom(new InternetAddress("lyingxuan789@gmail.com"));
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(emailEnseignant));
				message.setSubject("Votre nouveau mot de passe");
				message.setText("Votre nouveau mot de passe est " + newPassword);
				Transport.send(message);
			} catch (MessagingException e) {
				e.printStackTrace();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
	}
	
	private String generatePassword() {
		String upperCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String lowerCaseLetters = "abcdefghijklmnopqrstuvwxyz";
		String numbers = "0123456789";
		String specialCharacters = "!@#$%^&*()_-+=<>?/{}~|";
		String combinedChars = upperCaseLetters + lowerCaseLetters + numbers + specialCharacters;
		Random random = new Random();
		StringBuilder password = new StringBuilder();
		password.append(upperCaseLetters.charAt(random.nextInt(upperCaseLetters.length())));
		password.append(lowerCaseLetters.charAt(random.nextInt(lowerCaseLetters.length())));
		password.append(numbers.charAt(random.nextInt(numbers.length())));
		password.append(specialCharacters.charAt(random.nextInt(specialCharacters.length())));
		for (int i = 4; i < 8; i++) {
			password.append(combinedChars.charAt(random.nextInt(combinedChars.length())));
		}
		return password.toString();
	}
}