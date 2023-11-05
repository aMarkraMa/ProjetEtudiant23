package com.projet.controller;

import com.projet.Main;
import com.projet.entity.Etudiant;
import com.projet.entity.Formation;
import com.projet.entity.Projet;
import com.projet.mapper.EtudiantMapper;
import com.projet.mapper.FormationMapper;
import com.projet.mapper.ProjetMapper;
import com.projet.service.impl.UpdateFormationServiceImpl;
import com.projet.utils.MyBatisUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import org.apache.ibatis.session.SqlSession;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class ShowProjetController {
    @FXML
    private TableColumn<Projet, String> nomMatiere;

    @FXML
    private TableColumn<Projet, String> sujet;

    @FXML
    private TableColumn<Projet, LocalDate> datePrevueRemise;

    @FXML
    private TableView<Projet> tableViewProjet;

    @FXML
    private Button returnProjet;

    @FXML
    private Button addProjet;

    @FXML
    private Button searchProjet;

    @FXML
    private Button toEtudiant;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField textFieldNomMatiere;

    @FXML
    private TextField textFieldSujet;

    @FXML
    public void initialize() {
        nomMatiere.setCellValueFactory(new PropertyValueFactory<>("nomMatiere"));
        sujet.setCellValueFactory(new PropertyValueFactory<>("sujet"));
        datePrevueRemise.setCellValueFactory(new PropertyValueFactory<>("datePrevueRemise"));

    }

    public void searchProjet(ActionEvent actionEvent) {
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        ProjetMapper mapper = sqlSession.getMapper(ProjetMapper.class);
        Projet projet = new Projet();
        projet.setNomMatiere("%" + textFieldNomMatiere.getText() + "%");
        projet.setSujet("%" + textFieldSujet.getText() + "%");
        projet.setDatePrevueRemise(datePicker.getValue());
        List<Projet> projets = mapper.selectByCondition(projet);
        ObservableList<Projet> data = FXCollections.observableArrayList();
        data.addAll(projets);
        tableViewProjet.setItems(data);
    }

}
