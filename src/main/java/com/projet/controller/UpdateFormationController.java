package com.projet.controller;

import com.projet.entity.Formation;
import com.projet.mapper.FormationMapper;
import com.projet.service.FormationService;
import com.projet.service.impl.FormationServiceImpl;
import com.projet.utils.MyBatisUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class UpdateFormationController {
	@FXML
	private TextField nomFormation;
	
	@FXML
	private ChoiceBox<String> promotion;
	
	@FXML
	private Button updateFormation;
	
	private FormationService formationService = new FormationServiceImpl();
	
	public void initialize() {
		promotion.getItems().clear();
		promotion.getItems().add("Initiale");
		promotion.getItems().add("En Alternance");
		promotion.getItems().add("Formation Continue");
		
		Formation formationToUpdate = FormationServiceImpl.formationToUpdate;
		nomFormation.setText(formationToUpdate.getNomFormation());
		promotion.setValue(formationToUpdate.getPromotion());
	}
	
	private String[] transformerStr(String nomFormation, String promotion) {
		String nomFormationUpperCase = nomFormation.toUpperCase();
		return new String[]{nomFormationUpperCase, promotion};
	}
	
	public void updateFormation(ActionEvent actionEvent) {
		Formation formation = new Formation();
		String nomFormation = this.nomFormation.getText().trim();
		String promotion = this.promotion.getValue();
		String[] nomFormationAndPromotion = transformerStr(nomFormation, promotion);
		formation.setNomFormation(nomFormationAndPromotion[0]);
		formation.setPromotion(nomFormationAndPromotion[1]);
		formation.setIdFormation(FormationServiceImpl.formationToUpdate.getIdFormation());
		try {
			List<Formation> formationsDB = formationService.selectByCondition(formation);
			if (formationsDB.size() > 0) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("ERREUR: Cette formation existe deja!");
				alert.setHeaderText("Cette formation existe deja!");
				alert.setContentText("Cette formation existe deja, veuillez verifier votre saisie.");
				alert.getDialogPane().setPrefWidth(800);
				alert.show();
				return;
			}
			formationService.updateFormation(formation);
			Stage stage = (Stage) updateFormation.getScene().getWindow();
			stage.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
