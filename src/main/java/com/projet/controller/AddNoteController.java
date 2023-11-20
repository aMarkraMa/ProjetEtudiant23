package com.projet.controller;

import com.projet.entity.Etudiant;
import com.projet.entity.Projet;
import com.projet.mapper.EtudiantMapper;
import com.projet.mapper.ProjetMapper;
import com.projet.utils.MyBatisUtils;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.apache.ibatis.session.SqlSession;

public class AddNoteController {

    @FXML
    private ChoiceBox<Projet> projet;
    @FXML
    private ChoiceBox<Etudiant> etudiant;
    @FXML
    private TextField noteRapport;
    @FXML
    private TextField noteSoutenance;
    @FXML
    private Button addNote;

    public void initialize(){
        noteRapport.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d+")) {
                noteRapport.setText(newValue.replaceAll("[^\\d]+", ""));
            }
        });
        noteSoutenance.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d+")) {
                noteSoutenance.setText(newValue.replaceAll("[^\\d]+", ""));
            }
        });

        //band
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        ProjetMapper projetMapper = sqlSession.getMapper(ProjetMapper.class);
        EtudiantMapper etudiantMapper = sqlSession.getMapper(EtudiantMapper.class);
        projet.getItems().addAll(projetMapper.selectAll());
    }


    public void addNote(){

    }

    public void showErr(String msg){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setHeaderText("Oups");
        alert.setContentText(msg);

        alert.showAndWait();
    }
}
