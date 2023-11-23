package com.projet.controller;

import com.projet.entity.Projet;
import com.projet.mapper.ProjetMapper;
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

    public void initialize(){
        pourcentageSoutenance.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d+")) {
                pourcentageSoutenance.setText(newValue.replaceAll("[^\\d]+", ""));
            }
        });
    }


    public void addProjet(){
        SqlSession sqlSession = null;
        try {
            sqlSession = MyBatisUtils.getSqlSession();
            ProjetMapper projetMapper = sqlSession.getMapper(ProjetMapper.class);
            
            Projet projet = new Projet();
            
            
            if(nomMatiere.getText() != null && !nomMatiere.getText().isEmpty()){
                projet.setNomMatiere(nomMatiere.getText());
            }else{
                showErr("Nom Matière ne peux pas être vide");
                return;
            }
            if(sujet.getText() != null && !sujet.getText().isEmpty()){
                projet.setSujet(sujet.getText());
            }else{
                showErr("Sujet ne peux pas être vide");
                return;
            }
            if(datePicker.getValue() != null){
                projet.setDatePrevueRemise(datePicker.getValue());
            }else{
                showErr("DatePrevueRemise ne peux pas être vide");
                return;
            }
            
            if(pourcentageSoutenance.getText() != null && !pourcentageSoutenance.getText().isEmpty()){
                if(Integer.valueOf(pourcentageSoutenance.getText()) >= 0 && Integer.valueOf(pourcentageSoutenance.getText()) <= 100){
                    projet.setPourcentageSoutenance(Integer.valueOf(pourcentageSoutenance.getText()));
                }else{
                    showErr("taux de note doit etre entre 0 - 100");
                    return;
                }
    
            }else{
                showErr("pourcentageSoutenance ne peux pas être vide");
                return;
            }
            
            Projet p = new Projet();
            p.setNomMatiere(projet.getNomMatiere());
            p.setSujet(projet.getSujet());
            List<Projet> ps = projetMapper.selectByCondition(p);
            if(ps.isEmpty()){
                projetMapper.addProjet(projet);
            }else {
                showErr("Projet : " + nomMatiere.getText() + "-" + sujet.getText() + " existe déjà");
                return;
            }
            
            Stage stage = (Stage)addProjet.getScene().getWindow();
            stage.close();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
        }
        
        
        

        


    }

    public void showErr(String msg){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setHeaderText("Oups");
        alert.setContentText(msg);

        alert.showAndWait();
    }
}
