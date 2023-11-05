package com.projet.controller;

import com.projet.Main;
import com.projet.entity.Etudiant;
import com.projet.entity.Formation;
import com.projet.mapper.EtudiantMapper;
import com.projet.mapper.FormationMapper;
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

import java.util.List;
import java.util.Optional;

public class ShowFormationController {
	@FXML
	private TableColumn<Formation, String> nom_formation;
	
	@FXML
	private TableColumn<Formation, String> promotion;
	
	@FXML
	private TableColumn<Formation, String> id_formation;
	
	@FXML
	private TableColumn<Formation, Void> boutons;
	
	@FXML
	private TableView<Formation> tableview_formation;
	
	@FXML
	private Button toAjouterFor;
	
	@FXML
	private Button refresh_formation;
	
	@FXML
	private Button search_formation;
	
	@FXML
	private TextField textfieldNomFormation;
	
	@FXML
	private TextField textfieldPromotion;
	
	@FXML
	public void initialize() {
		
		id_formation = new TableColumn<>("id");
		id_formation.setCellValueFactory(new PropertyValueFactory<>("idFormation"));
		id_formation.setPrefWidth(40);
		nom_formation = new TableColumn<>("Formation");
		nom_formation.setCellValueFactory(new PropertyValueFactory<>("nomFormation"));
		nom_formation.setPrefWidth(112);
		promotion = new TableColumn<>("Promotion");
		promotion.setCellValueFactory(new PropertyValueFactory<>("promotion"));
		promotion.setPrefWidth(112);
		
		boutons = new TableColumn<>("Operation");
		boutons.setCellFactory(new Callback<TableColumn<Formation, Void>, TableCell<Formation, Void>>() {
			@Override
			public TableCell<Formation, Void> call(TableColumn<Formation, Void> formationVoidTableColumn) {
				return new TableCell<>() {
					private final Button modifier = new Button("Modifier");
					private final Button supprimer = new Button("Supprimer");
					
					private HBox pane = new HBox(modifier, supprimer);
					
					{
						modifier.setOnAction((ActionEvent event) -> {
							Formation formation = getTableView().getItems().get(getIndex());
							UpdateFormationServiceImpl.formationToUpdate = formation;
							Main.addView("/com/projet/UpdateFormation.fxml");
							
						});
						
						supprimer.setOnAction((ActionEvent event) -> {
							Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
							alert.setTitle("Supprimer un formation");
							alert.setHeaderText("Confirmez votre choix");
							alert.setContentText("Vous allez supprimer ce formation. Etes-vous sur?");
							Optional<ButtonType> resultat = alert.showAndWait();
							if (resultat.isPresent() && resultat.get() == ButtonType.OK) {
								Formation formation = getTableView().getItems().get(getIndex());
								System.out.println(formation);
								Integer idFormation = formation.getIdFormation();
								SqlSession sqlSession = MyBatisUtils.getSqlSession();
								FormationMapper mapper = sqlSession.getMapper(FormationMapper.class);
								mapper.deleteById(idFormation);
								ObservableList<Formation> data = FXCollections.observableArrayList();
								List<Formation> formations = mapper.selectAll();
								data.addAll(formations);
								tableview_formation.setItems(data);
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
		
		tableview_formation.getColumns().add(id_formation);
		tableview_formation.getColumns().add(nom_formation);
		tableview_formation.getColumns().add(promotion);
		tableview_formation.getColumns().add(boutons);
		
		
		SqlSession sqlSession = MyBatisUtils.getSqlSession();
		EtudiantMapper mapper = sqlSession.getMapper(EtudiantMapper.class);
		List<Etudiant> etudiants = mapper.selectAll();
		refreshTable(etudiants);
		
		Image refresh = new Image("refresh.png");
		ImageView refreshImageView = new ImageView(refresh);
		refreshImageView.setFitHeight(20);
		refreshImageView.setFitWidth(20);
		refresh_formation.setGraphic(refreshImageView);
		Image search = new Image("search.png");
		ImageView searchImageView = new ImageView(search);
		searchImageView.setFitWidth(20);
		searchImageView.setFitHeight(20);
		search_formation.setGraphic(searchImageView);
		
		
	}
	
	public void refreshTable(List newData) {
		ObservableList<Formation> data = FXCollections.observableArrayList();
		data.addAll(newData);
		tableview_formation.setItems(data);
	}
	
	
	public void retour(ActionEvent actionEvent) {
		Main.changeView("/com/projet/Main.fxml");
	}
	
	public void toFormation(ActionEvent actionEvent) {
		Main.changeView("/com/projet/ShowEtudiant.fxml");
	}
	
	public void toAjouterFormation(ActionEvent actionEvent) {
		Main.addView("/com/projet/AddEtudiant.fxml");
	}
	
	public void searchFormation(ActionEvent actionEvent) {
		SqlSession sqlSession = MyBatisUtils.getSqlSession();
		FormationMapper mapper = sqlSession.getMapper(FormationMapper.class);
		Formation formation = new Formation();
		formation.setNomFormation(textfieldNomFormation.getText());
		formation.setPromotion(textfieldPromotion.getText());
		List<Formation> formations = mapper.selectByCondition(formation);
		ObservableList<Formation> data = FXCollections.observableArrayList();
		data.addAll(formation);
		tableview_formation.setItems(data);
	}
	
	public void refreshTable(ActionEvent actionEvent) {
		SqlSession sqlSession = MyBatisUtils.getSqlSession();
		FormationMapper mapper = sqlSession.getMapper(FormationMapper.class);
		List<Formation> formations = mapper.selectAll();
		ObservableList<Formation> data = FXCollections.observableArrayList();
		data.addAll(formations);
		tableview_formation.setItems(data);
		
		textfieldNomFormation.setText("");
		textfieldPromotion.setText("");
		tableview_formation.refresh();
	}
}