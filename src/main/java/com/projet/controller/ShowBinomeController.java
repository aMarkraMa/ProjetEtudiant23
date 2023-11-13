package com.projet.controller;

import com.projet.Main;
import com.projet.entity.Binome;
import com.projet.entity.Etudiant;
import com.projet.entity.Formation;
import com.projet.entity.Projet;
import com.projet.mapper.BinomeMapper;
import com.projet.service.impl.UpdateBinomeServiceImpl;
import com.projet.utils.MyBatisUtils;
import javafx.beans.property.SimpleDoubleProperty;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ShowBinomeController {
	@FXML
	private TableColumn<Binome, String> idBinomes;
	// @FXML
	// private TableColumn<Binome, String> idProjet;
	@FXML
	private TableColumn<Binome, String> nomMatiere;
	@FXML
	private TableColumn<Binome, String> sujet;
	
	@FXML
	private TableColumn<Binome, String> etudiant1;
	
	@FXML
	private TableColumn<Binome, String> etudiant2;
	
	@FXML
	private TableColumn<Binome, String> noteRapport;
	
	@FXML
	private TableColumn<Binome, String> dateRelleRemise;
	
	
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
	private TextField textfieldNomMatiere;
	
	@FXML
	private TextField textfieldSujet;
	
	@FXML
	private MenuItem toEtudiants;
	
	@FXML
	private MenuItem toFormations;
	
	@FXML
	private MenuItem toProjets;
	
	
	@FXML
	private MenuItem toNotes;
	
	
	@FXML
	public void initialize() {
		
		idBinomes = new TableColumn<>("id");
		idBinomes.setCellFactory(column -> {
			return new TableCell<>() {
				@Override
				protected void updateItem(String item, boolean empty) {
					super.updateItem(item, empty);
					
					if (this.getTableRow() != null && !empty) {
						setText(this.getTableRow().getIndex() + 1 + "");
					} else {
						setText("");
					}
				}
			};
		});
		
		// idProjet = new TableColumn<>("idProjet");
		// idProjet.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Binome, String>, ObservableValue<String>>() {
		// 	@Override
		// 	public ObservableValue<String> call(TableColumn.CellDataFeatures<Binome, String> binome) {
		// 		return new SimpleStringProperty(binome.getValue().getProjet().getIdProjet().toString());
		// 	}
		// });
		
		nomMatiere = new TableColumn<>("Nom matiere");
		nomMatiere.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Binome, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(TableColumn.CellDataFeatures<Binome, String> binome) {
				return new SimpleStringProperty(binome.getValue().getProjet().getNomMatiere());
			}
		});
		
		sujet = new TableColumn<>("Sujet");
		sujet.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Binome, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(TableColumn.CellDataFeatures<Binome, String> binome) {
				return new SimpleStringProperty(binome.getValue().getProjet().getSujet());
			}
		});
		
		etudiant1 = new TableColumn<>("Etudiant 1");
		etudiant1.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Binome, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(TableColumn.CellDataFeatures<Binome, String> binome) {
				return new SimpleStringProperty(binome.getValue().getEtudiants().get(0).getNomEtudiant() + " " + binome.getValue().getEtudiants().get(0).getPrenomEtudiant());
			}
		});
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
		
		noteRapport = new TableColumn<>("NoteRapport");
		noteRapport.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Binome, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(TableColumn.CellDataFeatures<Binome, String> binome) {
				if (binome.getValue().getNoteRapport() == 0.0) {
					return new SimpleStringProperty("");
				} else {
					return new SimpleStringProperty(binome.getValue().getNoteRapport().toString());
				}
			}
		});
		dateRelleRemise = new TableColumn<>("DateReelleRemise");
		dateRelleRemise.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Binome, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(TableColumn.CellDataFeatures<Binome, String> binome) {
				if (binome.getValue().getDateReelleRemise().equals(LocalDate.parse("1111-11-11"))) {
					return new SimpleStringProperty("");
				} else {
					return new SimpleStringProperty(binome.getValue().getDateReelleRemise().toString());
				}
			}
		});
		
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
							SqlSession sqlSession = MyBatisUtils.getSqlSession();
							BinomeMapper binomeMapper = sqlSession.getMapper(BinomeMapper.class);
							UpdateBinomeServiceImpl.binomeToUpdate = binomeMapper.selectByIdBinomeAndIdProjet(binome);
							Main.addView("/com/projet/view/UpdateBinome.fxml");
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
								SqlSession sqlSession = null;
								try {
									sqlSession = MyBatisUtils.getNonAutoCommittingSqlSession();
									BinomeMapper mapper = sqlSession.getMapper(BinomeMapper.class);
									mapper.deleteBinome(idBinome, idProjet);
									mapper.deleteAppartenir(idBinome, idProjet);
									sqlSession.commit();
									ObservableList<Binome> data = FXCollections.observableArrayList();
									List<Binome> binomes = mapper.selectAll();
									data.addAll(binomes);
									tableviewBinome.setItems(data);
								} catch (Exception e) {
									if (sqlSession != null) {
										sqlSession.rollback();
									}
									e.printStackTrace();
								} finally {
									if (sqlSession != null) {
										sqlSession.close();
									}
								}
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
		
		tableviewBinome.getColumns().add(idBinomes);
		//tableviewBinome.getColumns().add(idProjet);
		tableviewBinome.getColumns().add(nomMatiere);
		tableviewBinome.getColumns().add(sujet);
		tableviewBinome.getColumns().add(etudiant1);
		tableviewBinome.getColumns().add(etudiant2);
		tableviewBinome.getColumns().add(noteRapport);
		tableviewBinome.getColumns().add(dateRelleRemise);
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
		Main.changeView("/com/projet/view/ShowFormation.fxml");
	}
	
	public void toAjouterBinome(ActionEvent actionEvent) {
		Main.addView("/com/projet/view/AddBinome.fxml");
	}
	
	public void searchBinome(ActionEvent actionEvent) {
		SqlSession sqlSession = MyBatisUtils.getSqlSession();
		BinomeMapper mapper = sqlSession.getMapper(BinomeMapper.class);
		Binome binome = new Binome();
		Projet projet = new Projet();
		projet.setNomMatiere("%" + textfieldNomMatiere.getText() + "%");
		projet.setSujet("%" + textfieldSujet.getText() + "%");
		binome.setProjet(projet);
		List<Binome> binomeDB = mapper.selectByCondition(binome);
		ObservableList<Binome> data = FXCollections.observableArrayList();
		data.addAll(binomeDB);
		tableviewBinome.setItems(data);
	}
	
	public void refreshTable(ActionEvent actionEvent) {
		SqlSession sqlSession = MyBatisUtils.getSqlSession();
		BinomeMapper mapper = sqlSession.getMapper(BinomeMapper.class);
		List<Binome> binomes = mapper.selectAll();
		ObservableList<Binome> data = FXCollections.observableArrayList();
		data.addAll(binomes);
		tableviewBinome.setItems(data);
		
		textfieldNomMatiere.setText("");
		textfieldNomMatiere.setPromptText("Nom Matiere");
		textfieldSujet.setText("");
		textfieldSujet.setPromptText("Sujet");
		
		tableviewBinome.refresh();
	}
	
	public void toProjets(ActionEvent actionEvent) {
		Main.changeView("/com/projet/view/ShowProjet.fxml");
	}
	
	public void toEtudiants(ActionEvent actionEvent) {
		Main.changeView("/com/projet/view/ShowEtudiant.fxml");
	}
	
	public void toNotes(ActionEvent actionEvent) {
	
	}
}
