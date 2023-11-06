package com.projet.controller;

import com.projet.entity.Formation;
import com.projet.mapper.FormationMapper;
import com.projet.service.UpdateFormationService;
import com.projet.service.impl.UpdateFormationServiceImpl;
import com.projet.utils.MyBatisUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.ibatis.session.SqlSession;

public class AddFormationController {
	
	@FXML
	private TextField nomFormation;
	@FXML
	private TextField promotion;
	
	@FXML
	private Button ajouterFor;
	
	
	private UpdateFormationService updateFormationService = new UpdateFormationServiceImpl();
	
	
	@FXML
	public void initialize() {
	}
	
	private String[] transformerStr(String nomFormation, String promotion) {
		String nomFormationUpperCase = nomFormation.toUpperCase();
		String promotionUpperCase = promotion.toUpperCase();
		return new String[]{nomFormationUpperCase, promotionUpperCase};
	}
	
	@FXML
	void ajouterFormation(ActionEvent event) {
		Formation formation = new Formation();
		String nomFormation = this.nomFormation.getText();
		String promotion = this.promotion.getText();
		formation.setNomFormation(nomFormation);
		formation.setPromotion(promotion);
		SqlSession sqlSession = MyBatisUtils.getSqlSession();
		FormationMapper mapper = sqlSession.getMapper(FormationMapper.class);
		mapper.addFormation(formation);
		Stage stage = (Stage) ajouterFor.getScene().getWindow();
		stage.close();
	}
	
}