package com.projet.controller;

import com.projet.Main;
import com.projet.entity.Projet;
import com.projet.mapper.ProjetMapper;
import com.projet.utils.MyBatisUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.apache.ibatis.session.SqlSession;

import java.time.LocalDate;
import java.util.List;

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
    private DatePicker datePicker;

    @FXML
    private TextField textFieldNomMatiere;

    @FXML
    private TextField textFieldSujet;

    @FXML
    public void initialize() {
        tableViewProjet.setEditable(true);

        nomMatiere.setCellValueFactory(new PropertyValueFactory<>("nomMatiere"));
        sujet.setCellValueFactory(new PropertyValueFactory<>("sujet"));
        datePrevueRemise.setCellValueFactory(new PropertyValueFactory<>("datePrevueRemise"));

        nomMatiere.setCellFactory(TextFieldTableCell.forTableColumn());
        sujet.setCellFactory(TextFieldTableCell.forTableColumn());


        nomMatiere.setOnEditCommit(
                (TableColumn.CellEditEvent<Projet, String> t) -> {
                    Projet projet = t.getTableView().getItems().get(t.getTablePosition().getRow());
                    projet.setNomMatiere(t.getNewValue()); // mise à jour BDD

                    // 更新数据库
                    updateProjet(projet);
                });

        Image search = new Image("com/projet/img/search.png");
        ImageView searchImageView = new ImageView(search);
        searchImageView.setFitWidth(18);
        searchImageView.setFitHeight(18);
        searchProjet.setGraphic(searchImageView);

        Image add = new Image("com/projet/img/add.png");
        ImageView addImageView = new ImageView(add);
        addImageView.setFitWidth(18);
        addImageView.setFitHeight(18);
        addProjet.setGraphic(addImageView);
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

    public void showAddView(){
        Main.addView("/com/projet/view/AddProjet.fxml");
    }

    public void updateProjet(Projet projet){
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        ProjetMapper mapper = sqlSession.getMapper(ProjetMapper.class);
        System.out.println(projet.toString());
        mapper.updateProjet(projet);
        sqlSession.commit();
    }

    public void refreshTable(List newData) {
        ObservableList<Projet> data = FXCollections.observableArrayList();
        data.addAll(newData);
        tableViewProjet.setItems(data);
    }

    public void toShowFormation(ActionEvent actionEvent) {
        Main.changeView("/com/projet/view/ShowFormation.fxml");
    }

    public void toShowEtudiant(ActionEvent actionEvent) {
        Main.changeView("/com/projet/view/ShowEtudiant.fxml");
    }

    public void toShowProjet(ActionEvent actionEvent) {
        Main.changeView("/com/projet/view/ShowProjet.fxml");
    }

    public void toShowBinome(ActionEvent actionEvent) {
        Main.changeView("/com/projet/view/ShowProjet.fxml");
    }

    public void toShowNote(ActionEvent actionEvent) {
        Main.changeView("/com/projet/view/ShowProjet.fxml");
    }
}
