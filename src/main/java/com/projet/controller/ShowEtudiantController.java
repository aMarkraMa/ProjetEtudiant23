package com.projet.controller;

import com.projet.Main;
import com.projet.entity.Etudiant;
import com.projet.entity.Formation;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import org.apache.ibatis.session.SqlSession;
import com.projet.service.UpdateEtudiantService;
import com.projet.service.impl.UpdateEtudiantServiceImpl;
import java.util.List;
import java.util.Optional;

public class ShowEtudiantController {
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
	
	@FXML
	private Button toAjouterEtu;
	
	@FXML
	private Button refresh_etudiant;
	
	@FXML
	private Button search_etudiant;
	
	@FXML
	private TextField textfieldNomEtudiant;
	
	@FXML
	private TextField textfieldPrenomEtudiant;
	
	@FXML
	private TextField textfieldNomFormation;
	
	@FXML
	public void initialize() {
		
		id_etudiant = new TableColumn<>("id");
		id_etudiant.setCellValueFactory(new PropertyValueFactory<>("idEtudiant"));
		id_etudiant.setPrefWidth(40);
		nom_etudiant = new TableColumn<>("Nom");
		nom_etudiant.setCellValueFactory(new PropertyValueFactory<>("nomEtudiant"));
		nom_etudiant.setPrefWidth(112);
		prenom_etudiant = new TableColumn<>("Prenom");
		prenom_etudiant.setCellValueFactory(new PropertyValueFactory<>("prenomEtudiant"));
		prenom_etudiant.setPrefWidth(112);
		nom_formation = new TableColumn<>("Formation");
		nom_formation.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Etudiant, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(TableColumn.CellDataFeatures<Etudiant, String> etudiant) {
				return new SimpleStringProperty(etudiant.getValue().getFormation().getNomFormation());
			}
		});
		nom_formation.setPrefWidth(112);
		
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
							Etudiant etudiant = getTableView().getItems().get(getIndex());
							UpdateEtudiantServiceImpl.etudiantToUpdate = etudiant;
							Main.addView("/com/projet/UpdateEtudiant.fxml");
							
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
		
		boutons.setPrefWidth(224);
		
		tableview_etudiant.getColumns().add(id_etudiant);
		tableview_etudiant.getColumns().add(nom_etudiant);
		tableview_etudiant.getColumns().add(prenom_etudiant);
		tableview_etudiant.getColumns().add(nom_formation);
		tableview_etudiant.getColumns().add(boutons);
		
		
		SqlSession sqlSession = MyBatisUtils.getSqlSession();
		EtudiantMapper mapper = sqlSession.getMapper(EtudiantMapper.class);
		List<Etudiant> etudiants = mapper.selectAll();
		refreshTable(etudiants);
		
		Image refresh = new Image("refresh.png");
		ImageView refreshImageView = new ImageView(refresh);
		refreshImageView.setFitHeight(20);
		refreshImageView.setFitWidth(20);
		refresh_etudiant.setGraphic(refreshImageView);
		Image search = new Image("search.png");
		ImageView searchImageView = new ImageView(search);
		searchImageView.setFitWidth(20);
		searchImageView.setFitHeight(20);
		search_etudiant.setGraphic(searchImageView);
		
		
	}
	
	public void refreshTable(List newData) {
		ObservableList<Etudiant> data = FXCollections.observableArrayList();
		data.addAll(newData);
		tableview_etudiant.setItems(data);
	}
	
	
	public void retour(ActionEvent actionEvent) {
		Main.changeView("/com/projet/Main.fxml");
	}
	
	public void toFormation(ActionEvent actionEvent) {
		Main.changeView("/com/projet/ShowFormation.fxml");
	}
	
	public void toAjouterEtudiant(ActionEvent actionEvent) {
		Main.addView("/com/projet/AddEtudiant.fxml");
	}
	
	public void searchEtudiant(ActionEvent actionEvent) {
		SqlSession sqlSession = MyBatisUtils.getSqlSession();
		EtudiantMapper mapper = sqlSession.getMapper(EtudiantMapper.class);
		Etudiant etudiant = new Etudiant();
		etudiant.setNomEtudiant(textfieldNomEtudiant.getText());
		etudiant.setPrenomEtudiant(textfieldPrenomEtudiant.getText());
		Formation formation = new Formation();
		formation.setNomFormation(textfieldNomFormation.getText());
		etudiant.setFormation(formation);
		List<Etudiant> etudiants = mapper.selectByCondition(etudiant);
		ObservableList<Etudiant> data = FXCollections.observableArrayList();
		data.addAll(etudiants);
		tableview_etudiant.setItems(data);
	}
	
	public void refreshTable(ActionEvent actionEvent) {
		SqlSession sqlSession = MyBatisUtils.getSqlSession();
		EtudiantMapper mapper = sqlSession.getMapper(EtudiantMapper.class);
		List<Etudiant> etudiants = mapper.selectAll();
		ObservableList<Etudiant> data = FXCollections.observableArrayList();
		data.addAll(etudiants);
		tableview_etudiant.setItems(data);
		
		textfieldNomEtudiant.setText("");
		textfieldPrenomEtudiant.setText("");
		textfieldNomFormation.setText("");
		tableview_etudiant.refresh();
	}
}
