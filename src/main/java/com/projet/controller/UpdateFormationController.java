package com.projet.controller;

import com.projet.entity.Etudiant;
import com.projet.entity.Formation;
import com.projet.mapper.EtudiantMapper;
import com.projet.mapper.FormationMapper;
import com.projet.service.UpdateFormationService;
import com.projet.service.impl.UpdateEtudiantServiceImpl;
import com.projet.service.impl.UpdateFormationServiceImpl;
import com.projet.utils.MyBatisUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class UpdateFormationController {
	@FXML
	private TextField nomFormation;
	
	@FXML
	private TextField promotion;
	
	@FXML
	private Button updateFormation;
	
	private UpdateFormationService updateFormationService = new UpdateFormationServiceImpl();
	
	public void initialize() {
		Formation formationToUpdate = UpdateFormationServiceImpl.formationToUpdate;
		nomFormation.setText(formationToUpdate.getNomFormation());
		promotion.setText(formationToUpdate.getPromotion());
	}
	
	private String[] transformerStr(String nomFormation, String promotion) {
		String nomFormationUpperCase = nomFormation.toUpperCase();
		String promotionUpperCase = promotion.toUpperCase();
		return new String[]{nomFormationUpperCase, promotionUpperCase};
	}
	
	public void updateFormation(ActionEvent actionEvent) {
		Formation formation = new Formation();
		String nomFormation = this.nomFormation.getText();
		String promotion = this.promotion.getText();
		String[] nomFormationAndPromotion = transformerStr(nomFormation, promotion);
		formation.setNomFormation(nomFormationAndPromotion[0]);
		formation.setPromotion(nomFormationAndPromotion[1]);
		formation.setIdFormation(UpdateFormationServiceImpl.formationToUpdate.getIdFormation());
		SqlSession sqlSession = MyBatisUtils.getSqlSession();
		FormationMapper mapper = sqlSession.getMapper(FormationMapper.class);
		mapper.updateFormation(formation);
		Stage stage = (Stage) updateFormation.getScene().getWindow();
		stage.close();
	}
}
