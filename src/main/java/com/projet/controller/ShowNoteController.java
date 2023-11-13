package com.projet.controller;

import com.projet.Main;
import com.projet.entity.Note;
import com.projet.mapper.NoteMapper;
import com.projet.utils.MyBatisUtils;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import org.apache.ibatis.session.SqlSession;

import java.time.temporal.ChronoUnit;
import java.util.List;

public class ShowNoteController {
	
	@FXML
	private TableColumn<Note, String> nomMatiere;
	@FXML
	private TableColumn<Note, String> sujet;
	
	@FXML
	private TableColumn<Note, String> nomEtudiant;
	
	@FXML
	private TableColumn<Note, String> prenomeEtudiant;
	
	
	@FXML
	private TableColumn<Note, String> noteSoutenance;
	
	@FXML
	private TableColumn<Note, String> noteRapport;
	
	@FXML
	private TableColumn<Note, String> noteFinale;
	
	
	@FXML
	private TableColumn<Note, Void> boutons;
	
	
	@FXML
	private TableView<Note> tableviewNote;
	
	@FXML
	private Button toAjouterNote;
	
	@FXML
	private Button refreshNote;
	
	@FXML
	private Button searchNote;
	
	@FXML
	private TextField textfieldNomEtudiant;
	
	@FXML
	private TextField textfieldPrenomEtudiant;
	
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
	private MenuItem toBinomes;
	
	
	@FXML
	public void initialize() {
		nomMatiere = new TableColumn<>("Nom matiere");
		nomMatiere.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Note, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(TableColumn.CellDataFeatures<Note, String> note) {
				return new SimpleStringProperty(note.getValue().getProjet().getNomMatiere());
			}
		});
		
		sujet = new TableColumn<>("Sujet");
		sujet.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Note, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(TableColumn.CellDataFeatures<Note, String> note) {
				return new SimpleStringProperty(note.getValue().getProjet().getSujet());
			}
		});
		
		nomEtudiant = new TableColumn<>("NomEtudanit");
		nomEtudiant.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Note, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(TableColumn.CellDataFeatures<Note, String> note) {
				return new SimpleStringProperty(note.getValue().getEtudiant().getNomEtudiant());
			}
		});
		
		prenomeEtudiant = new TableColumn<>("prenomeEtudaint");
		prenomeEtudiant.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Note, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(TableColumn.CellDataFeatures<Note, String> note) {
				return new SimpleStringProperty(note.getValue().getEtudiant().getPrenomEtudiant());
			}
		});
		
		noteRapport = new TableColumn<>("NoteRapport");
		noteRapport.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Note, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(TableColumn.CellDataFeatures<Note, String> note) {
				if (note.getValue().getBinome().getNoteRapport() == 0.0) {
					return new SimpleStringProperty("");
				} else {
					return new SimpleStringProperty(note.getValue().getBinome().getNoteRapport().toString());
				}
			}
		});
		
		noteSoutenance = new TableColumn<>("NoteSoutenance");
		noteSoutenance.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Note, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(TableColumn.CellDataFeatures<Note, String> note) {
				if (note.getValue().getNoteSoutenance() == 0.0) {
					return new SimpleStringProperty("");
				} else {
					return new SimpleStringProperty(note.getValue().getNoteSoutenance().toString());
				}
			}
		});
		
		noteFinale = new TableColumn<>("NoteFinale");
		noteFinale.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Note, String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(TableColumn.CellDataFeatures<Note, String> note) {
				if ((note.getValue().getBinome().getNoteRapport() * (1 - note.getValue().getProjet().getPourcentageSoutenance()) * 0.01 + note.getValue().getNoteSoutenance() * note.getValue().getProjet().getPourcentageSoutenance() * 0.01) == 0.0) {
					return new SimpleStringProperty("");
				} else {
					Double noteFinale = note.getValue().getBinome().getNoteRapport() * (1 - note.getValue().getProjet().getPourcentageSoutenance()) * 0.01 + note.getValue().getNoteSoutenance() * note.getValue().getProjet().getPourcentageSoutenance() * 0.01;
					if (note.getValue().getBinome().getDateReelleRemise().isAfter(note.getValue().getProjet().getDatePrevueRemise())) {
						noteFinale -= ChronoUnit.DAYS.between(note.getValue().getBinome().getDateReelleRemise(), note.getValue().getProjet().getDatePrevueRemise()) * 0.1;
					}
					return new SimpleStringProperty(note.getValue().getNoteSoutenance().toString());
				}
			}
		});
		
		boutons = new TableColumn<>("Operation");
		boutons.setCellFactory(new Callback<TableColumn<Note, Void>, TableCell<Note, Void>>() {
			@Override
			public TableCell<Note, Void> call(TableColumn<Note, Void> binomeVoidTableColumn) {
				return new TableCell<>() {
					private final Button modifier = new Button("Modifier");
					private final Button supprimer = new Button("Supprimer");
					//
					// 			private HBox pane = new HBox(modifier, supprimer);
					//
					// 			{
					// 				modifier.setOnAction((ActionEvent event) -> {
					// 					Note note = getTableView().getItems().get(getIndex());
					// 					SqlSession sqlSession = MyBatisUtils.getSqlSession();
					// 					NoteMapper noteMapper = sqlSession.getMapper(NoteMapper.class);
					// 					//UpdateNoteServiceImpl.binomeToUpdate = noteMapper.selectByIdBinomeAndIdProjet(note);
					// 					//Main.addView("/com/projet/view/UpdateNote.fxml");
					// 				});
					//
					// 				// supprimer.setOnAction((ActionEvent event) -> {
					// 				// 	Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
					// 				// 	alert.setTitle("Supprimer un binome");
					// 				// 	alert.setHeaderText("Confirmez votre choix");
					// 				// 	alert.setContentText("Vous allez supprimer ce binome. Etes-vous sur?");
					// 				// 	Optional<ButtonType> resultat = alert.showAndWait();
					// 				// 	if (resultat.isPresent() && resultat.get() == ButtonType.OK) {
					// 				// 		Binome binome = getTableView().getItems().get(getIndex());
					// 				// 		Integer idBinome = binome.getIdBinome();
					// 				// 		Integer idProjet = binome.getProjet().getIdProjet();
					// 				// 		SqlSession sqlSession = null;
					// 				// 		try {
					// 				// 			sqlSession = MyBatisUtils.getNonAutoCommittingSqlSession();
					// 				// 			BinomeMapper mapper = sqlSession.getMapper(BinomeMapper.class);
					// 				// 			mapper.deleteBinome(idBinome,idProjet);
					// 				// 			mapper.deleteAppartenir(idBinome,idProjet);
					// 				// 			sqlSession.commit();
					// 				// 			ObservableList<Binome> data = FXCollections.observableArrayList();
					// 				// 			List<Binome> binomes = mapper.selectAll();
					// 				// 			data.addAll(binomes);
					// 				// 			tableviewBinome.setItems(data);
					// 				// 		} catch (Exception e) {
					// 				// 			if (sqlSession != null){
					// 				// 				sqlSession.rollback();
					// 				// 			}
					// 				// 			e.printStackTrace();
					// 				// 		} finally {
					// 				// 			if (sqlSession != null) {
					// 				// 				sqlSession.close();
					// 				// 			}
					// 				// 		}
					// 				// 	}
					//
					//
					// 				});
					// 			}
					//
					// 			// @Override
					// 			// protected void updateItem(Void item, boolean empty) {
					// 			// 	super.updateItem(item, empty);
					// 			// 	if (empty) {
					// 			// 		setGraphic(null);
					// 			// 	} else {
					// 			// 		setGraphic(pane);
					// 			// 	}
					// 			// }
				};
				
			}
			
			
		});
		
		tableviewNote.getColumns().add(nomMatiere);
		tableviewNote.getColumns().add(sujet);
		tableviewNote.getColumns().add(nomEtudiant);
		tableviewNote.getColumns().add(prenomeEtudiant);
		tableviewNote.getColumns().add(noteRapport);
		tableviewNote.getColumns().add(noteSoutenance);
		tableviewNote.getColumns().add(noteFinale);
		tableviewNote.getColumns().add(boutons);
		
		
		SqlSession sqlSession = MyBatisUtils.getSqlSession();
		NoteMapper mapper = sqlSession.getMapper(NoteMapper.class);
		List<Note> notes = mapper.selectAll();
		refreshTable(notes);
		
		Image refresh = new Image("/com/projet/img/refresh.png");
		ImageView refreshImageView = new ImageView(refresh);
		refreshImageView.setFitHeight(20);
		refreshImageView.setFitWidth(20);
		refreshNote.setGraphic(refreshImageView);
		Image search = new Image("/com/projet/img/search.png");
		ImageView searchImageView = new ImageView(search);
		searchImageView.setFitWidth(20);
		searchImageView.setFitHeight(20);
		searchNote.setGraphic(searchImageView);
		
		
	}
	
	public void refreshTable(List newData) {
		ObservableList<Note> data = FXCollections.observableArrayList();
		data.addAll(newData);
		tableviewNote.setItems(data);
	}
	
	public void refreshTable(ActionEvent actionEvent) {
	}
	
	
	public void toFormations(ActionEvent actionEvent) {
		Main.changeView("/com/projet/view/ShowFormation.fxml");
	}
	
	public void toAjouterNote(ActionEvent actionEvent) {
		Main.addView("/com/projet/view/AddNote.fxml");
	}
	
	public void searchNote(ActionEvent actionEvent) {
		SqlSession sqlSession = MyBatisUtils.getSqlSession();
		NoteMapper mapper = sqlSession.getMapper(NoteMapper.class);
	}
	
	// public void refreshTable(ActionEvent actionEvent) {
	// 	SqlSession sqlSession = MyBatisUtils.getSqlSession();
	// 	BinomeMapper mapper = sqlSession.getMapper(NoteMapper.class);
	// 	List<Binome> notes = mapper.selectAll();
	// 	ObservableList<Note> data = FXCollections.observableArrayList();
	// 	data.addAll(notes);
	// 	tableviewNote.setItems(data);
	//
	// 	textfieldNomEtudiant.setText("");
	// 	textfieldNomEtudiant.setPromptText("Nom");
	// 	textfieldPrenomEtudiant.setText("");
	// 	textfieldPrenomEtudiant.setPromptText("Prenom");
	// 	textfieldNomMatiere.setText("");
	// 	textfieldNomMatiere.setPromptText("Nom Matiere");
	// 	textfieldSujet.setText("");
	// 	textfieldSujet.setPromptText("Sujet");
	//
	// 	tableviewNote.refresh();
	// }
	
	public void toProjets(ActionEvent actionEvent) {
		Main.changeView("/com/projet/view/ShowProjet.fxml");
	}
	
	public void toEtudiants(ActionEvent actionEvent) {
		Main.changeView("/com/projet/view/ShowEtudiant.fxml");
	}
	
	public void toBinomes(ActionEvent actionEvent) {
		Main.changeView("/com/projet/view/ShowBinome.fxml");
		
	}
	
	
}
