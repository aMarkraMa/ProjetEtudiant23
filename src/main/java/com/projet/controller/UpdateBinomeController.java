package com.projet.controller;

import com.projet.entity.Binome;
import com.projet.entity.Etudiant;
import com.projet.entity.Projet;
import com.projet.mapper.BinomeMapper;
import com.projet.mapper.EtudiantMapper;
import com.projet.mapper.NoteMapper;
import com.projet.mapper.ProjetMapper;
import com.projet.service.BinomeService;
import com.projet.service.EtudiantService;
import com.projet.service.ProjetService;
import com.projet.service.impl.BinomeServiceImpl;
import com.projet.service.impl.EtudiantServiceImpl;
import com.projet.service.impl.ProjetServiceImpl;
import com.projet.utils.MyBatisUtils;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.ibatis.session.SqlSession;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UpdateBinomeController {
	
	@FXML
	private TextField idBinome;
	
	@FXML
	private ChoiceBox<String> projet;
	
	@FXML
	private ChoiceBox<String> etudiant1;
	
	@FXML
	private ChoiceBox<String> etudiant2;
	
	@FXML
	private DatePicker dateRelleRemise;
	
	@FXML
	private TextField noteRapport;
	
	@FXML
	private Button updateBinome;
	
	
	private BinomeService binomeService = new BinomeServiceImpl();
	
	private ProjetService projetService = new ProjetServiceImpl();
	
	private EtudiantService etudiantService = new EtudiantServiceImpl();
	
	@FXML
	public void initialize() {
		
		
		updateBinome.setOnAction(new EventHandler<ActionEvent>() {
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
					updateBinome(actionEvent);
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
		
		idBinome.setDisable(true);
		projet.setDisable(true);
		
		Binome binomeToUpdate = BinomeServiceImpl.binomeToUpdate;
		
		idBinome.setText(binomeToUpdate.getIdBinome().toString());
		projet.setValue(binomeToUpdate.getProjet().getIdProjet() + " " + binomeToUpdate.getProjet().getNomMatiere() + " " + binomeToUpdate.getProjet().getSujet());
		
		etudiant1.setValue(binomeToUpdate.getEtudiants().get(0).getIdEtudiant().toString() + " " + binomeToUpdate.getEtudiants().get(0).getNomEtudiant() + " " + binomeToUpdate.getEtudiants().get(0).getPrenomEtudiant());
		
		if (binomeToUpdate.getEtudiants().size() > 1) {
			etudiant2.setValue(binomeToUpdate.getEtudiants().get(1).getIdEtudiant().toString() + " " + binomeToUpdate.getEtudiants().get(1).getNomEtudiant() + " " + binomeToUpdate.getEtudiants().get(1).getPrenomEtudiant());
		}
		
		if (binomeToUpdate.getNoteRapport() == -1.0) {
			noteRapport.setText("");
		} else {
			noteRapport.setText(binomeToUpdate.getNoteRapport().toString());
		}
		
		if (binomeToUpdate.getDateReelleRemise().equals(LocalDate.of(1111, 11, 11))) {
			dateRelleRemise.setValue(null);
		} else {
			dateRelleRemise.setValue(binomeToUpdate.getDateReelleRemise());
		}
		
		etudiant1.valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				noteRapport.setDisable(true);
				noteRapport.setText(null);
				dateRelleRemise.setDisable(true);
				dateRelleRemise.setValue(null);
				if (oldValue != null && !"".equals(oldValue)) {
					binomeService.deleteNoteSoutenance(Integer.parseInt(String.valueOf(oldValue.charAt(0))), Integer.parseInt(String.valueOf(projet.getValue().charAt(0))));
				}
				if (newValue != null && !"".equals(newValue)) {
					binomeService.insertNoteSoutenance(Integer.parseInt(String.valueOf(projet.getValue().charAt(0))), Integer.parseInt(String.valueOf(newValue.charAt(0))),-1.0);
				}
				if (etudiant2.getValue() != null && !"".equals(etudiant2.getValue())) {
					binomeService.deleteNoteSoutenance(Integer.parseInt(String.valueOf(etudiant2.getValue().charAt(0))), Integer.parseInt(String.valueOf(projet.getValue().charAt(0))));
					binomeService.insertNoteSoutenance(Integer.parseInt(String.valueOf(projet.getValue().charAt(0))), Integer.parseInt(String.valueOf(etudiant2.getValue().charAt(0))), -1.0);
				}
				
				
			}
		});
		
		etudiant2.valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				noteRapport.setDisable(true);
				noteRapport.setText(null);
				dateRelleRemise.setDisable(true);
				dateRelleRemise.setValue(null);
				if (oldValue != null && !"".equals(oldValue)) {
					binomeService.deleteNoteSoutenance(Integer.parseInt(String.valueOf(oldValue.charAt(0))), Integer.parseInt(String.valueOf(projet.getValue().charAt(0))));
				}
				if (newValue != null && !"".equals(newValue)) {
					binomeService.insertNoteSoutenance(Integer.parseInt(String.valueOf(projet.getValue().charAt(0))), Integer.parseInt(String.valueOf(newValue.charAt(0))), -1.0);
				}
				if (etudiant1.getValue() != null && !"".equals(etudiant1.getValue())) {
					binomeService.deleteNoteSoutenance(Integer.parseInt(String.valueOf(etudiant1.getValue().charAt(0))), Integer.parseInt(String.valueOf(projet.getValue().charAt(0))));
					binomeService.insertNoteSoutenance(Integer.parseInt(String.valueOf(projet.getValue().charAt(0))), Integer.parseInt(String.valueOf(etudiant1.getValue().charAt(0))), -1.0);
				}
			}
		});
		
		noteRapport.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (newValue != null) {
					etudiant1.setDisable(true);
					etudiant2.setDisable(true);
				}
			}
		});
		
		dateRelleRemise.valueProperty().addListener(new ChangeListener<LocalDate>() {
			@Override
			public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue) {
				if (newValue != null) {
					etudiant1.setDisable(true);
					etudiant2.setDisable(true);
				}
			}
		});
		
		dateRelleRemise.setOnMouseClicked(event -> {
			if (dateRelleRemise.getValue() == null || dateRelleRemise.getValue().isEqual(LocalDate.of(1111, 11, 11))) {
				dateRelleRemise.setValue(LocalDate.now());
			}
		});
	}
	
	
	public void updateBinome(ActionEvent actionEvent) {
		if (etudiant1.getValue().equals(etudiant2.getValue())) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("ERREUR: Vous avez choisi deux fois le meme etudiant!");
			alert.setHeaderText("Vous avez choisi deux fois le meme etudiant!");
			alert.setContentText("Vous avez choisi deux fois le meme etudiant, veuillez choisir deux etudiants differents pour ajouter un binome.");
			alert.getDialogPane().setPrefWidth(800);
			alert.show();
			return;
		}
		Binome binome = new Binome();
		
		if (!NumberUtils.isParsable(idBinome.getText().trim())) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("ERREUR: Vous n'avez pas saisi un nombre dans le champ \"idBinome\"!");
			alert.setHeaderText("Vous n'avez pas saisi un nombre dans le champ \"idBinome\"!");
			alert.setContentText("Vous n'avez pas saisi un nombre dans le champ \"idBinome\", veuillez recommencer.");
			alert.getDialogPane().setPrefWidth(800);
			alert.show();
			return;
		}
		binome.setIdBinome(Integer.parseInt(idBinome.getText().trim()));
		Projet projetP = new Projet();
		String[] infoProjet = projet.getValue().split(" ");
		projetP.setIdProjet(Integer.parseInt(infoProjet[0]));
		projetP.setNomMatiere(infoProjet[1]);
		projetP.setSujet(infoProjet[2]);
		
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
		
		if (noteRapport.getText() != null && !"".equals(noteRapport.getText().trim())) {
			if (!NumberUtils.isParsable(noteRapport.getText().trim()) || Double.parseDouble(noteRapport.getText().trim()) > 20 || Double.parseDouble(noteRapport.getText().trim()) < -1) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("ERREUR: La note de rapport saisie n'est pas valide!");
				alert.setHeaderText("La note de rapport saisie n'est pas valide!");
				alert.setContentText("La note de rapport saisie n'est pas valide, veuillez saisir un nombre entre 0 et 20 inclus");
				alert.getDialogPane().setPrefWidth(800);
				alert.show();
				return;
			}
			binome.setNoteRapport(Double.parseDouble(noteRapport.getText().trim()));
		} else {
			binome.setNoteRapport(-1.0);
		}
		if (dateRelleRemise.getValue() != null) {
			binome.setDateReelleRemise(dateRelleRemise.getValue());
		} else {
			binome.setDateReelleRemise(LocalDate.of(1111, 11, 11));
		}
		try {
			Binome binomeDB1 = binomeService.getBinomeByIdProjetAndIdEtudiant(etudiant1P.getIdEtudiant(), projetP.getIdProjet());
			Binome binomeDB2 = null;
			if (etudiant2P != null) {
				binomeDB2 = binomeService.getBinomeByIdProjetAndIdEtudiant(etudiant2P.getIdEtudiant(), projetP.getIdProjet());
			}
			
			if (binomeDB1 != null) {
				if (binomeDB1.getIdBinome() != Integer.parseInt(idBinome.getText().trim())) {
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setTitle("ERREUR: L'etudiant 1 que vous avez choisi est deja dans un binome de ce projet!");
					alert.setHeaderText("L'etudiant 1 que vous avez choisi est deja dans un binome de ce projet!");
					alert.setContentText("L'etudiant 1 que vous avez choisi est deja dans un binome de ce projet, veuillez changer d'etudiant.");
					alert.getDialogPane().setPrefWidth(800);
					alert.show();
					return;
				}
			}
			if (binomeDB2 != null) {
				if (binomeDB2.getIdBinome() != Integer.parseInt(idBinome.getText().trim())) {
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setTitle("ERREUR: L'etudiant 2 que vous avez choisi est deja dans un binome de ce projet!");
					alert.setHeaderText("L'etudiant 2 que vous avez choisi est deja dans un binome de ce projet!");
					alert.setContentText("L'etudiant 2 que vous avez choisi est deja dans un binome de ce projet, veuillez changer d'etudiant.");
					alert.getDialogPane().setPrefWidth(800);
					alert.show();
					return;
				}
			}
			binomeService.updateBinome(binome);
			
			Stage stage = (Stage) updateBinome.getScene().getWindow();
			stage.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}


