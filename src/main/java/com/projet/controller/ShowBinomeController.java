package com.projet.controller;

import com.projet.Main;
import com.projet.entity.Binome;
import com.projet.entity.Etudiant;
import com.projet.mapper.BinomeMapper;
import com.projet.mapper.EtudiantMapper;
import com.projet.service.impl.UpdateBinomeServiceImpl;
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

import java.util.List;
import java.util.Optional;

public class ShowBinomeController {
	@FXML
	private TableColumn<Binome, String> idBinomes;
	@FXML
	private TableColumn<Binome, String> nomMatiere;
	@FXML
	private TableColumn<Binome, String> sujet;
	
	@FXML
	private TableColumn<Binome, String> etudiant1;
	
	@FXML
	private TableColumn<Binome, String> etudiant2;
	
	
	@FXML
	private TableColumn<Binome, Void> boutons;
	
	
	@FXML
	private TableView<Binome> tableviewBinome;
	
	@FXML
	private Button toAjouterBi;
	
	@FXML
	private Button refreshBinome;
	
	@FXML
	private Button searchBinome;
	
	@FXML
	private TextField textfieldNomEtudiant;
	
	@FXML
	private TextField textfieldPrenomEtudiant;
	
	@FXML
	private TextField textfieldNomProjet;
	
	@FXML
	private MenuItem returnBinome;
	@FXML
	private MenuItem toEtudiant;
	
	@FXML
	private MenuItem toFormations;
	
	@FXML
	private MenuItem toProjets;
	
	
	@FXML
	private MenuItem toNotes;
	
	
	@FXML
	public void initialize() {
		
		idBinomes = new TableColumn<>("id");
		idBinomes.setCellValueFactory(new PropertyValueFactory<>("idBinome"));
		idBinomes.setPrefWidth(40);
		nomMatiere = new TableColumn<>("Nom matiere");
		nomMatiere.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Binome, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(TableColumn.CellDataFeatures<Binome, String> binome) {
				return new SimpleStringProperty(binome.getValue().getProjet().getNomMatiere());
			}
		});
		nomMatiere.setPrefWidth(112);
		
		sujet = new TableColumn<>("Sujet");
		sujet.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Binome, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(TableColumn.CellDataFeatures<Binome, String> binome) {
				return new SimpleStringProperty(binome.getValue().getProjet().getSujet());
			}
		});
		sujet.setPrefWidth(112);
		
		etudiant1 = new TableColumn<>("Etudiant 1");
		etudiant1.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Binome, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(TableColumn.CellDataFeatures<Binome, String> binome) {
				return new SimpleStringProperty(binome.getValue().getEtudiants().get(0).getNomEtudiant() + " " + binome.getValue().getEtudiants().get(0).getPrenomEtudiant());
			}
		});
		etudiant1.setPrefWidth(112);
		etudiant2 = new TableColumn<>("Etudiant 2");
		etudiant2.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Binome, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(TableColumn.CellDataFeatures<Binome, String> binome) {
				if (binome.getValue().getEtudiants().size() > 1) {
					return new SimpleStringProperty(binome.getValue().getEtudiants().get(1).getNomEtudiant() + " " + binome.getValue().getEtudiants().get(1).getPrenomEtudiant());
				} else {
					return new SimpleStringProperty("");
				}
			}
		});
		etudiant2.setPrefWidth(112);
		
		boutons = new TableColumn<>("Operation");
		boutons.setCellFactory(new Callback<TableColumn<Binome, Void>, TableCell<Binome, Void>>() {
			@Override
			public TableCell<Binome, Void> call(TableColumn<Binome, Void> binomeVoidTableColumn) {
				return new TableCell<>() {
					private final Button modifier = new Button("Modifier");
					private final Button supprimer = new Button("Supprimer");
					
					private HBox pane = new HBox(modifier, supprimer);
					
					{
						modifier.setOnAction((ActionEvent event) -> {
							Binome binome = getTableView().getItems().get(getIndex());
							UpdateBinomeServiceImpl.binomeToUpdate = binome;
							Main.addView("/com/projet/UpdateBinome.fxml");
						});
						
						supprimer.setOnAction((ActionEvent event) -> {
							Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
							alert.setTitle("Supprimer un binome");
							alert.setHeaderText("Confirmez votre choix");
							alert.setContentText("Vous allez supprimer ce binome. Etes-vous sur?");
							Optional<ButtonType> resultat = alert.showAndWait();
							if (resultat.isPresent() && resultat.get() == ButtonType.OK) {
								Binome binome = getTableView().getItems().get(getIndex());
								Integer idBinome = binome.getIdBinome();
								Integer idProjet = binome.getProjet().getIdProjet();
								SqlSession sqlSession = MyBatisUtils.getSqlSession();
								BinomeMapper mapper = sqlSession.getMapper(BinomeMapper.class);
								mapper.deleteById(idBinome,idProjet);
								ObservableList<Binome> data = FXCollections.observableArrayList();
								List<Binome> binomes = mapper.selectAll();
								data.addAll(binomes);
								tableviewBinome.setItems(data);
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
		
		tableviewBinome.getColumns().add(idBinomes);
		tableviewBinome.getColumns().add(nomMatiere);
		tableviewBinome.getColumns().add(sujet);
		tableviewBinome.getColumns().add(etudiant1);
		tableviewBinome.getColumns().add(etudiant2);
		tableviewBinome.getColumns().add(boutons);
		
		
		SqlSession sqlSession = MyBatisUtils.getSqlSession();
		BinomeMapper mapper = sqlSession.getMapper(BinomeMapper.class);
		List<Binome> binomes = mapper.selectAll();
		refreshTable(binomes);
		
		Image refresh = new Image("/com/projet/img/refresh.png");
		ImageView refreshImageView = new ImageView(refresh);
		refreshImageView.setFitHeight(20);
		refreshImageView.setFitWidth(20);
		refreshBinome.setGraphic(refreshImageView);
		Image search = new Image("/com/projet/img/search.png");
		ImageView searchImageView = new ImageView(search);
		searchImageView.setFitWidth(20);
		searchImageView.setFitHeight(20);
		searchBinome.setGraphic(searchImageView);
		
		
	}
	
	public void refreshTable(List newData) {
		ObservableList<Binome> data = FXCollections.observableArrayList();
		data.addAll(newData);
		tableviewBinome.setItems(data);
	}
	
	
	public void retour(ActionEvent actionEvent) {
		Main.changeView("/com/projet/Main.fxml");
	}
	
	public void toFormations(ActionEvent actionEvent) {
		Main.changeView("/com/projet/ShowFormation.fxml");
	}
	
	public void toAjouterBinome(ActionEvent actionEvent) {
		Main.addView("/com/projet/view/AddBinome.fxml");
	}
	
	public void searchBinome(ActionEvent actionEvent) {
		// SqlSession sqlSession = MyBatisUtils.getSqlSession();
		// BinomeMapper mapper = sqlSession.getMapper(BinomeMapper.class);
		// Binome binome = new Binome();
		//binome.setNomEtudiant("%" + textfieldNomEtudiant.getText() + "%");
		//binome.setPrenomEtudiant("%" + textfieldPrenomEtudiant.getText() + "%");
		//Formation formation = new Formation();
		//formation.setNomFormation("%" + textfieldNomProjet.getText() + "%");//TODO
		//etudiant.setFormation(formation);
		// List<Binome> binomes = mapper.selectByCondition(binome);
		// ObservableList<Binome> data = FXCollections.observableArrayList();
		// data.addAll(binomes);
		// tableviewBinome.setItems(data);
	}
	
	public void refreshTable(ActionEvent actionEvent) {
		SqlSession sqlSession = MyBatisUtils.getSqlSession();
		BinomeMapper mapper = sqlSession.getMapper(BinomeMapper.class);
		List<Binome> binomes = mapper.selectAll();
		ObservableList<Binome> data = FXCollections.observableArrayList();
		data.addAll(binomes);
		tableviewBinome.setItems(data);
		
		textfieldNomEtudiant.setText("");
		textfieldNomEtudiant.setPromptText("Nom");
		textfieldPrenomEtudiant.setText("");
		textfieldPrenomEtudiant.setPromptText("Prenom");
		// textfieldNomFormation.setText("");
		// textfieldNomFormation.setPromptText("Formation");
		tableviewBinome.refresh();
	}
	
	public void toProjets(ActionEvent actionEvent) {
		Main.changeView("/com/projet/ShowProjet.fxml");
	}
	
	public void toEtudiants(ActionEvent actionEvent) {Main.changeView("/com/projet/ShowEtudiant.fxml");}
	
	public void toNotes(ActionEvent actionEvent) {
	
	}
}
