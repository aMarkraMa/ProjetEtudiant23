package com.projet.controller;

import com.projet.mapper.FormationMapper;
import com.projet.utils.MyBatisUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class ModifyEtudiantController {
    @FXML
    private TextField prenomEtu;

    @FXML
    private ChoiceBox<String> nomFormation;

    @FXML
    private TextField nomEtu;

    @FXML
    private Button modifyEtudiant;

    @FXML
    private ChoiceBox<String> promotion;

    public void initialize() {
        nomFormation.getItems().clear();
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        FormationMapper mapper = sqlSession.getMapper(FormationMapper.class);
        List nomsFormation = mapper.getNomsFormation();
        nomFormation.getItems().addAll(nomsFormation);

        promotion.getItems().clear();
        List promotions = mapper.getPromotions();
        promotion.getItems().addAll(promotions);

    }


    public void modifyEtudiant(ActionEvent actionEvent) {

    }
}
