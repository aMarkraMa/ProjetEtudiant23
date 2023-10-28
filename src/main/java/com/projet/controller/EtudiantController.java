package com.projet.controller;

import com.projet.Main;
import com.projet.entity.Etudiant;
import com.projet.mapper.EtudiantMapper;
import com.projet.utils.MyBatisUtils;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.Optional;

public class EtudiantController {
    @FXML
    private TableColumn<Etudiant, String> nom_formation;

    @FXML
    private TableColumn<Etudiant, String> nom_etudiant;

    @FXML
    private TableColumn<Etudiant, String> id_etudiant;

    @FXML
    private TableColumn<Etudiant, Void> boutons;

    @FXML
    private TableColumn<Etudiant, String> prenom_etudiant;

    @FXML
    private TableView<Etudiant> tableview_etudiant;


    public void initialize() {
        id_etudiant = new TableColumn<>("id");
        id_etudiant.setCellValueFactory(new PropertyValueFactory<>("idEtudiant"));
        nom_etudiant = new TableColumn<>("Nom");
        nom_etudiant.setCellValueFactory(new PropertyValueFactory<>("nomEtudiant"));
        prenom_etudiant = new TableColumn<>("Prenom");
        prenom_etudiant.setCellValueFactory(new PropertyValueFactory<>("prenomEtudiant"));
        nom_formation = new TableColumn<>("Formation");
        nom_formation.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Etudiant, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Etudiant, String> etudiant) {
                return new SimpleStringProperty(etudiant.getValue().getFormation().getNomFormation());
            }
        });

        boutons = new TableColumn<>("Operation");
        boutons.setCellFactory(new Callback<TableColumn<Etudiant, Void>, TableCell<Etudiant, Void>>() {
            @Override
            public TableCell<Etudiant, Void> call(TableColumn<Etudiant, Void> etudiantVoidTableColumn) {
                return new TableCell<>() {
                    private final Button modifier = new Button("Modifier");
                    private final Button supprimer = new Button("Supprimer");

                    private HBox pane = new HBox(modifier, supprimer);

                    {
                        modifier.setOnAction((ActionEvent event) -> {


                        });

                        supprimer.setOnAction((ActionEvent event) -> {
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Supprimer un etudiant");
                            alert.setHeaderText("Confirmez votre choix");
                            alert.setContentText("Vous allez supprimer cet etudiant. Etes-vous sur?");
                            Optional<ButtonType> resultat = alert.showAndWait();
                            if (resultat.isPresent() && resultat.get() == ButtonType.OK) {
                                Etudiant etudiant = getTableView().getItems().get(getIndex());
                                System.out.println(etudiant);
                                Integer idEtudiant = etudiant.getIdEtudiant();
                                SqlSession sqlSession = MyBatisUtils.getSqlSession();
                                EtudiantMapper mapper = sqlSession.getMapper(EtudiantMapper.class);
                                mapper.deleteById(idEtudiant);
                                ObservableList<Etudiant> data = FXCollections.observableArrayList();
                                List<Etudiant> etudiants = mapper.selectAll();
                                data.addAll(etudiants);
                                tableview_etudiant.setItems(data);
                            }


                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(pane);
                        }
                    }
                };

            }


        });

        tableview_etudiant.getColumns().add(id_etudiant);
        tableview_etudiant.getColumns().add(nom_etudiant);
        tableview_etudiant.getColumns().add(prenom_etudiant);
        tableview_etudiant.getColumns().add(nom_formation);
        tableview_etudiant.getColumns().add(boutons);

        ObservableList<Etudiant> data = FXCollections.observableArrayList();
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        EtudiantMapper mapper = sqlSession.getMapper(EtudiantMapper.class);
        List<Etudiant> etudiants = mapper.selectAll();
        data.addAll(etudiants);
        tableview_etudiant.setItems(data);
    }


    public void retour(ActionEvent actionEvent) {
        Main.changeView("Main.fxml");
    }

    public void ajoutEtudiant(ActionEvent actionEvent) {

    }

    public void searchEtudiant(ActionEvent actionEvent) {
    }
}
