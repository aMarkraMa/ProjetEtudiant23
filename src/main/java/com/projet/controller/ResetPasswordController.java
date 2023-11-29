package com.projet.controller;

import com.projet.mapper.EnseignantMapper;
import com.projet.service.EnseignantService;
import com.projet.service.impl.EnseignantServiceImpl;
import com.projet.utils.MyBatisUtils;
import com.projet.utils.ProjetStringUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
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
	private TextField numEn; // Champ de texte pour le numéro de l'enseignant
	
	@FXML
	private ChoiceBox<String> question; // Liste déroulante pour la question de sécurité
	
	@FXML
	private TextField reponse; // Champ de texte pour la réponse à la question de sécurité
	
	@FXML
	private Button reinitialiser; // Bouton pour réinitialiser le mot de passe
	
	private EnseignantService enseignantService = new EnseignantServiceImpl();
	
	// Initialisation de l'interface utilisateur
	@FXML
	public void initialize() {
		// Chargement des questions de sécurité et configuration de l'interface
		try {
			question.getItems().clear();
			List<String> questions = enseignantService.getQuestions();
			question.getItems().addAll(questions);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// Méthode pour réinitialiser le mot de passe
	public void reinitialiser(ActionEvent actionEvent) {
		// Logique pour réinitialiser le mot de passe avec gestion des erreurs et validation
		try {
			if (numEn.getText() == null || numEn.getText().trim().equals("") || question.getValue() == null || question.getValue().equals("") || reponse.getText() == null || reponse.getText().trim().equals("")) {
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
			Integer numEnseignant = Integer.parseInt(numEn.getText().trim());
			List<Integer> numerosEnseignant = enseignantService.getNumerosEnseignant();
			if (!numerosEnseignant.contains(numEnseignant)) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("ERREUR: Ce numero enseignant n'existe pas!");
				alert.setHeaderText("Ce numero enseignant n'existe pas!");
				alert.setContentText("Ce numero enseignant n'existe pas, veuillez vous inscrire.");
				alert.getDialogPane().setPrefWidth(800);
				alert.show();
				return;
			}
			String questionDB = enseignantService.getQuestionByNumEnseignant(numEnseignant);
			if (!questionDB.equals(question.getValue())) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("ERREUR: Vous n'avez pas choisi la bonne question!");
				alert.setHeaderText("Vous n'avez pas choisi la bonne question!");
				alert.setContentText("Vous n'avez pas choisi la bonne question, veuillez choisir la question que vous avez choisi lors de votre inscription.");
				alert.getDialogPane().setPrefWidth(800);
				alert.show();
				return;
			}
			String reponseDB = enseignantService.getReponseByNumeroEnseignant(numEnseignant);
			if (!reponse.getText().trim().toLowerCase().equals(reponseDB)) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("ERREUR: Vous n'avez pas saisi la bonne reponse!");
				alert.setHeaderText("Vous n'avez pas saisi la bonne reponse!");
				alert.setContentText("Vous n'avez pas saisi la bonne reponse, veuillez reessayer.");
				alert.getDialogPane().setPrefWidth(800);
				alert.show();
				return;
			}
			String newPassword = generatePassword();
			enseignantService.updateMotDePasseByNumeroEnseignant(numEnseignant, ProjetStringUtils.sha256(newPassword));
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("Votre nouveau mot de passe est dans votre email");
			alert.setHeaderText("Votre nouveau mot de passe est dans votre email");
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
			String emailEnseignant = enseignantService.getEmailEnseignantByNumeroEnseignant(numEnseignant);
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
		}
	}
	
	// Génère un mot de passe aléatoire
	private String generatePassword() {
		// Génération d'un mot de passe aléatoire
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