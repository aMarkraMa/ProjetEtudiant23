package com.projet.controller;

import com.projet.entity.Binome;
import com.projet.entity.Etudiant;
import com.projet.entity.Projet;
import com.projet.service.BinomeService;
import com.projet.service.EtudiantService;
import com.projet.service.ProjetService;
import com.projet.service.impl.BinomeServiceImpl;
import com.projet.service.impl.EtudiantServiceImpl;
import com.projet.service.impl.ProjetServiceImpl;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.ArrayList;
import java.util.List;

public class AddBinomeController {
	
	// Liste déroulante pour sélectionner un projet
	@FXML
	private ChoiceBox<String> projet;
	
	// Liste déroulante pour sélectionner le premier étudiant
	@FXML
	private ChoiceBox<String> etudiant1;
	
	// Liste déroulante pour sélectionner le deuxième étudiant
	@FXML
	private ChoiceBox<String> etudiant2;
	
	// Bouton pour ajouter un binôme
	@FXML
	private Button ajouterBi;
	
	// Champ de texte pour l'identifiant du binôme
	@FXML
	private TextField idBinome;
	
	// Initialisation de l'interface utilisateur
	private ProjetService projetService = new ProjetServiceImpl();
	
	private EtudiantService etudiantService = new EtudiantServiceImpl();
	
	private BinomeService binomeService = new BinomeServiceImpl();
	
	
	@FXML
	public void initialize() {
		// Configuration des actions et chargement des données dans les listes déroulantes
		ajouterBi.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				if (etudiant1.getValue() == null || "".equals(etudiant1.getValue())) {
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setTitle("ERREUR: Le choix de l'etudiant 1 est obligatoire!");
					alert.setHeaderText("Le choix de l'etudiant 1 est obligatoire!");
					alert.setContentText("Le choix de l'etudiant 1 est obligatoire, veuillez choisir un etudiant pour ce champ.");
					alert.getDialogPane().setPrefWidth(800);
					alert.show();
				} else {
					ajouterBinome(actionEvent);
				}
			}
		});
		projet.getItems().clear();
		etudiant1.getItems().clear();
		etudiant2.getItems().clear();
		try {
			
			List<Projet> projets = projetService.selectAll();
			List<String> infoProjets = new ArrayList<>();
			
			for (int i = 0; i < projets.size(); i++) {
				Projet infoProjet = projets.get(i);
				Integer idProjet = infoProjet.getIdProjet();
				String nomMatiere = infoProjet.getNomMatiere();
				String sujet = infoProjet.getSujet();
				infoProjets.add(idProjet + " " + nomMatiere + " " + sujet);
			}
			
			List<Integer> idsEtudiant = etudiantService.getIdsEtudiant();
			List<String> nomsEtudiant = etudiantService.getNomsEtudiant();
			List<String> prenomsEtudiant = etudiantService.getPrenomsEtudiant();
			
			List<String> infoEtudiants = new ArrayList<>();
			infoEtudiants.add("");
			for (int i = 0; i < idsEtudiant.size(); i++) {
				infoEtudiants.add(idsEtudiant.get(i) + " " + nomsEtudiant.get(i) + " " + prenomsEtudiant.get(i));
			}
			
			projet.getItems().addAll(infoProjets);
			etudiant1.getItems().addAll(infoEtudiants);
			etudiant2.getItems().addAll(infoEtudiants);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	// Méthode pour ajouter un binôme
	@FXML
	void ajouterBinome(ActionEvent event) {
		
		if (etudiant1.getValue().equals(etudiant2.getValue())) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("ERREUR: Vous avez choisi deux fois le meme etudiant!");
			alert.setHeaderText("Vous avez choisi deux fois le meme etudiant!");
			alert.setContentText("Vous avez choisi deux fois le meme etudiant, veuillez choisir deux etudiants differents pour ajouter un binome.");
			alert.getDialogPane().setPrefWidth(800);
			alert.show();
		} else {
			Binome binome = new Binome();
			
			if (!NumberUtils.isParsable(idBinome.getText().trim())) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("ERREUR: Vous n'avez pas saisi un nombre dans le champ \"idBinome\"!");
				alert.setHeaderText("Vous n'avez pas saisi un nombre dans le champ \"idBinome\"!");
				alert.setContentText("Vous n'avez pas saisi un nombre dans le champ \"idBinome\", veuillez recommencer.");
				alert.getDialogPane().setPrefWidth(800);
				alert.show();
			} else {
				binome.setIdBinome(Integer.parseInt(idBinome.getText().trim()));
				Projet projetP = new Projet();
				String[] infoProjet = projet.getValue().split(" ");
				projetP.setIdProjet(Integer.parseInt(infoProjet[0]));
				
				Etudiant etudiant1P = new Etudiant();
				String[] infoEtudiant1 = etudiant1.getValue().split(" ");
				etudiant1P.setIdEtudiant(Integer.parseInt(infoEtudiant1[0]));
				
				Etudiant etudiant2P = null;
				if (etudiant2.getValue() != null && !"".equals(etudiant2.getValue())) {
					etudiant2P = new Etudiant();
					String[] infoEtudiant2 = etudiant2.getValue().split(" ");
					etudiant2P.setIdEtudiant(Integer.parseInt(infoEtudiant2[0]));
				}
				
				List<Etudiant> etudiants = new ArrayList<>();
				etudiants.add(etudiant1P);
				if (etudiant2P != null) {
					etudiants.add(etudiant2P);
				}
				
				binome.setProjet(projetP);
				binome.setEtudiants(etudiants);
				
				try {
					Binome binomeDB1 = binomeService.selectByIdBinomeAndIdProjet(binome);
					Binome binomeDB2 = binomeService.getBinomeByIdProjetAndIdEtudiant(etudiant1P.getIdEtudiant(), projetP.getIdProjet());
					Binome binomeDB3 = null;
					if (etudiant2P != null) {
						binomeDB3 = binomeService.getBinomeByIdProjetAndIdEtudiant(etudiant2P.getIdEtudiant(), projetP.getIdProjet());
					}
					if (binomeDB1 != null) {
						Alert alert = new Alert(Alert.AlertType.ERROR);
						alert.setTitle("ERREUR: Il exite deja un binome avec cet id pour le projet choisi!");
						alert.setHeaderText("Il exite deja un binome avec cet id pour le projet choisi!");
						alert.setContentText("Il exite deja un binome avec cet id pour le projet choisi, veuillez verifier les informations saisies.");
						alert.getDialogPane().setPrefWidth(800);
						alert.show();
					} else {
						if (binomeDB2 != null) {
							Alert alert = new Alert(Alert.AlertType.ERROR);
							alert.setTitle("ERREUR: L'etudiant 1 que vous avez choisi est deja dans un binome de ce projet!");
							alert.setHeaderText("L'etudiant 1 que vous avez choisi est deja dans un binome de ce projet!");
							alert.setContentText("L'etudiant 1 que vous avez choisi est deja dans un binome de ce projet, veuillez changer d'etudiant.");
							alert.getDialogPane().setPrefWidth(800);
							alert.show();
						} else {
							if (binomeDB3 != null) {
								Alert alert = new Alert(Alert.AlertType.ERROR);
								alert.setTitle("ERREUR: L'etudiant 2 que vous avez choisi est deja dans un binome de ce projet!");
								alert.setHeaderText("L'etudiant 2 que vous avez choisi est deja dans un binome de ce projet!");
								alert.setContentText("L'etudiant 2 que vous avez choisi est deja dans un binome de ce projet, veuillez changer d'etudiant.");
								alert.getDialogPane().setPrefWidth(800);
								alert.show();
							} else {
								binomeService.addBinome(binome);
								Stage stage = (Stage) ajouterBi.getScene().getWindow();
								stage.close();
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		}
	}
}