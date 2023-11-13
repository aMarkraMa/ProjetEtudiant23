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
import javafx.scene.input.KeyCode;
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
    private Button refreshProjet;

    @FXML
    private Button addProjet;

    @FXML
    private Button searchProjet;

    @FXML
    private Button deleteProjet;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField textFieldNomMatiere;

    @FXML
    private TextField textFieldSujet;

    @FXML
    public void initialize() {

        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        ProjetMapper mapper = sqlSession.getMapper(ProjetMapper.class);

        tableViewProjet.setEditable(true);

        nomMatiere.setCellValueFactory(new PropertyValueFactory<>("nomMatiere"));
        sujet.setCellValueFactory(new PropertyValueFactory<>("sujet"));
        datePrevueRemise.setCellValueFactory(new PropertyValueFactory<>("datePrevueRemise"));

        nomMatiere.setCellFactory(TextFieldTableCell.forTableColumn());
        sujet.setCellFactory(TextFieldTableCell.forTableColumn());
        datePrevueRemise.setCellFactory(column -> new TableCell<Projet, LocalDate>() {
            private final DatePicker datePicker = new DatePicker();
            {
                datePicker.setOnAction(event -> {
                    if(datePicker.getValue() != null) {
                        commitEdit(datePicker.getValue());
                    } else {
                        cancelEdit();
                    }
                });
            }

            @Override
            protected void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    // 如果是当前行是正在编辑的行，则显示DatePicker
                    if (isEditing()) {
                        datePicker.setValue(item);
                        setGraphic(datePicker);
                        System.out.println("id editing");
                    } else {
                        setText(getItem() == null ? "" : getItem().toString());
                        setGraphic(null);
                    }
                }
            }

            @Override
            public void startEdit() {
                super.startEdit();
                if (!isEmpty()) {
                    datePicker.setValue(getItem());
                    setGraphic(datePicker);
                    setText(null);
                }
                System.out.println("start edit");
            }

            @Override
            public void cancelEdit() {
                super.cancelEdit();
                setText(getItem().toString());
                setGraphic(null);
                System.out.println("cancel edit");
            }

            // 当DatePicker的值改变时，提交编辑
            {
                datePicker.valueProperty().addListener((observable, oldValue, newValue) -> {
                    if(isEditing()) {
                        commitEdit(newValue);
                    }
                });
            }

            @Override
            public void commitEdit(LocalDate newValue) {
                super.commitEdit(newValue);
                Projet projet = getTableView().getItems().get(getIndex());
                // 更新项目的日期
                projet.setDatePrevueRemise(newValue);
                // 使用外部定义的方法更新数据库和刷新TableView
                updateProjet(projet);
                System.out.println("commit edit");

                setText(newValue.toString());
                setGraphic(null);
            }

        });

        initializeImg();

        nomMatiere.setOnEditCommit(
                (TableColumn.CellEditEvent<Projet, String> t) -> {
                    Projet projet = t.getTableView().getItems().get(t.getTablePosition().getRow());
                    projet.setNomMatiere(t.getNewValue());
                    // mise à jour BDD
                    updateProjet(projet);
                });

        sujet.setOnEditCommit(
                (TableColumn.CellEditEvent<Projet, String> t) -> {
                    Projet projet = t.getTableView().getItems().get(t.getTablePosition().getRow());
                    projet.setSujet(t.getNewValue());
                    // mise à jour BDD
                    updateProjet(projet);
                });




        sqlSession.close();

        refreshTable();
    }

    public void initializeImg(){
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

        Image delete = new Image("com/projet/img/delete.png");
        ImageView deleteImageView = new ImageView(delete);
        deleteImageView.setFitWidth(18);
        deleteImageView.setFitHeight(18);
        deleteProjet.setGraphic(deleteImageView);

        Image refresh = new Image("com/projet/img/refresh.png");
        ImageView refreshImageView = new ImageView(refresh);
        refreshImageView.setFitWidth(18);
        refreshImageView.setFitHeight(18);
        refreshProjet.setGraphic(refreshImageView);
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

        sqlSession.close();
    }

    public void showAddView(){
        Main.addView("/com/projet/view/AddProjet.fxml");
    }

    public void updateProjet(Projet projet){
        try(SqlSession sqlSession = MyBatisUtils.getSqlSession()){
            ProjetMapper mapper = sqlSession.getMapper(ProjetMapper.class);

            mapper.updateProjet(projet);
            sqlSession.commit();

            refreshTable();
        }catch (Exception e){
            showErr("mise à jour projet non réussi");
        }


    }

    public void deleteProjet(ActionEvent actionEvent){

        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        ProjetMapper mapper = sqlSession.getMapper(ProjetMapper.class);

        Projet projet = tableViewProjet.getSelectionModel().getSelectedItem();

        if(projet != null){
            mapper.deleteById(projet.getIdProjet());
        }else{
            showErr("Pas de ligne selectioné");
        }
        sqlSession.close();
        refreshTable();
    }

    public void refreshTable() {
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        ProjetMapper mapper = sqlSession.getMapper(ProjetMapper.class);

        List<Projet> projets = mapper.selectAll();

        ObservableList<Projet> data = FXCollections.observableArrayList();
        data.addAll(projets);

        tableViewProjet.setItems(data);

        sqlSession.close();
    }

    public void refreshTable(ActionEvent actionEvent) {
        refreshTable();
    }
    public void showErr(String msg){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setHeaderText("Oups");
        alert.setContentText(msg);

        alert.showAndWait();
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
    
    public void retour(ActionEvent actionEvent) {
        Main.changeView("/com/projet/view/Main.fxml");
    }
}
