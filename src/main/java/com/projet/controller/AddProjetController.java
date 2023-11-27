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
	
	@FXML
	private TextField nomMatiere;
	@FXML
	private TextField sujet;
	@FXML
	private DatePicker datePicker;
	@FXML
	private TextField pourcentageSoutenance;
	@FXML
	private Button addProjet;
	
	private ProjetService projetService = new ProjetServiceImpl();
	
	public void initialize() {
		pourcentageSoutenance.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue.matches("\\d+")) {
				pourcentageSoutenance.setText(newValue.replaceAll("[^\\d]+", ""));
			}
		});
	}
	
	
	public void addProjet() {
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
	
	public void showErr(String msg) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("Error Dialog");
		alert.setHeaderText("Oups");
		alert.setContentText(msg);
		alert.show();
	}
}
