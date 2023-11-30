package com.projet.controller;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.projet.Main;
import com.projet.entity.*;
import com.projet.mapper.EtudiantMapper;
import com.projet.mapper.NoteMapper;
import com.projet.mapper.ProjetMapper;
import com.projet.service.EtudiantService;
import com.projet.service.NoteService;
import com.projet.service.ProjetService;
import com.projet.service.impl.EtudiantServiceImpl;
import com.projet.service.impl.NoteServiceImpl;
import com.projet.service.impl.ProjetServiceImpl;
import com.projet.utils.MyBatisUtils;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.converter.DoubleStringConverter;
import org.apache.ibatis.session.SqlSession;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class ShowNoteController {
	@FXML
	private TableColumn<Note, String> idProjet;
	
	@FXML
	private TableColumn<Note, String> nomMatiere;
	
	@FXML
	private TableColumn<Note, String> sujet;
	
	@FXML
	private TableColumn<Note, String> idEtudiant;
	
	@FXML
	private TableColumn<Note, String> nomEtudiant;
	
	@FXML
	private TableColumn<Note, String> prenomEtudiant;
	
	
	@FXML
	private TableColumn<Note, Double> noteSoutenance;
	
	@FXML
	private TableColumn<Note, String> noteRapport;
	
	@FXML
	private TableColumn<Note, String> noteFinale;
	
	
	@FXML
	private TableView<Note> tableViewNote;
	
	@FXML
	private TextField textFieldNomEtudiant;
	
	@FXML
	private TextField textFieldPrenomEtudiant;
	
	@FXML
	private TextField textFieldNomMatiere;
	
	@FXML
	private TextField textFieldSujet;
	
	@FXML
	private Button refreshNote;
	
	@FXML
	private Button searchNote;
	
	@FXML
	private Label error;
	
	private ProjetService projetService = new ProjetServiceImpl();
	
	private EtudiantService etudiantService = new EtudiantServiceImpl();
	
	private NoteService noteService = new NoteServiceImpl();
	
	
	// Initialisation du contrôleur
	@FXML
	public void initialize() {
		// Configuration des colonnes du tableau
		tableViewNote.setEditable(true);
		nomMatiere.setCellValueFactory(note -> new SimpleStringProperty(note.getValue().getProjet().getNomMatiere()));
		idProjet.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Note, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(TableColumn.CellDataFeatures<Note, String> note) {
				return new SimpleStringProperty(note.getValue().getProjet().getIdProjet().toString());
			}
		});
		idEtudiant.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Note, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(TableColumn.CellDataFeatures<Note, String> note) {
				return new SimpleStringProperty(note.getValue().getEtudiant().getIdEtudiant().toString());
			}
		});
		sujet.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Note, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(TableColumn.CellDataFeatures<Note, String> note) {
				return new SimpleStringProperty(note.getValue().getProjet().getSujet());
			}
		});
		nomEtudiant.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Note, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(TableColumn.CellDataFeatures<Note, String> note) {
				return new SimpleStringProperty(note.getValue().getEtudiant().getNomEtudiant());
			}
		});
		prenomEtudiant.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Note, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(TableColumn.CellDataFeatures<Note, String> note) {
				return new SimpleStringProperty(note.getValue().getEtudiant().getPrenomEtudiant());
			}
		});
		noteSoutenance.setCellValueFactory(new PropertyValueFactory<>("noteSoutenance"));
		noteSoutenance.setCellFactory(column -> {
			// Créer un nouveau TextFieldTableCell avec le convertisseur DoubleStringConverter personnalisé
			return new TextFieldTableCell<Note, Double>(new DoubleStringConverter() {
				@Override
				public Double fromString(String value) {
					// Surcharge la méthode fromString pour qu'elle renvoie null si la conversion échoue.
					try {
						double doubleValue = Double.parseDouble(value);
						if (doubleValue == -1) {
							return null;
						} else {
							return (doubleValue >= 0 && doubleValue <= 20) ? doubleValue : null;
						}
					} catch (NumberFormatException e) {
						return null;
					}
				}
				
				@Override
				public String toString(Double value) {
					return (value == null || value == -1) ? "" : value.toString();
				}
			}) {
				@Override
				public void startEdit() {
					super.startEdit();
					// Obtenir le champ de texte dans la cellule actuelle.
					TextField textField = (TextField) getGraphic();
					// Définir le format de texte pour restreindre la saisie
					textField.setTextFormatter(new TextFormatter<>(new DoubleStringConverter(), getItem(), c -> {
						if (c.getControlNewText().isEmpty()) {
							return c; // Les valeurs nulles sont autorisées et l'utilisateur peut effacer le texte pour saisir une nouvelle valeur.
						}
						try {
							double value = Double.parseDouble(c.getControlNewText());
							if (value >= 0 && value <= 20) { // Plage de valeurs de contrôle
								return c;
							}
						} catch (NumberFormatException exception) {
							exception.printStackTrace();
						}
						error.setVisible(true);
						return null; // L'entrée n'est pas acceptée car elle n'est pas comprise entre 0 et 100 ou n'est pas un nombre.
					}));
				}
			};
		});
		noteSoutenance.setOnEditCommit(
				(TableColumn.CellEditEvent<Note, Double> t) -> {
					Note note = t.getTableView().getItems().get(t.getTablePosition().getRow());
					System.out.println(note.toString());
					updateSoutenance(note.getProjet().getIdProjet(), note.getEtudiant().getIdEtudiant(), t.getNewValue());
				});
		noteRapport.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Note, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(TableColumn.CellDataFeatures<Note, String> note) {
				if (note.getValue().getNoteRapport() == -1.0) {
					return new SimpleStringProperty("");
				} else {
					return new SimpleStringProperty(note.getValue().getNoteRapport().toString());
				}
			}
		});
		
		noteFinale.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Note, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(TableColumn.CellDataFeatures<Note, String> note) {
				if (note.getValue().getNoteRapport().doubleValue() == -1.0 || note.getValue().getNoteSoutenance().doubleValue() == -1.0 || note.getValue().getDateReeleRemise().equals(LocalDate.of(1111, 11, 11))) {
					return new SimpleStringProperty("");
				} else {
					Double noteRapportE = note.getValue().getNoteRapport();
					Double noteSoutenanceE = note.getValue().getNoteSoutenance();
					Integer pourcentageSoutenance = note.getValue().getProjet().getPourcentageSoutenance();
					Integer pourcentageRapport = 100 - pourcentageSoutenance;
					Double noteFinale = noteRapportE * pourcentageRapport * 0.01 + noteSoutenanceE * pourcentageSoutenance * 0.01;
					if (note.getValue().getDateReeleRemise().isAfter(note.getValue().getProjet().getDatePrevueRemise())) {
						long dateDelay = ChronoUnit.DAYS.between(note.getValue().getDateReeleRemise(), note.getValue().getProjet().getDatePrevueRemise());
						if(noteFinale - Math.abs(dateDelay) * 0.01 >= 0){
							noteFinale = noteFinale - Math.abs(dateDelay) * 0.01;
						}else{
							noteFinale = 0.0;
						}
					}
					return new SimpleStringProperty(noteFinale.toString());
				}
			}
		});
		
		// Ajout des colonnes au TableView
		tableViewNote.widthProperty().addListener((observable, oldValue, newValue) -> {
			double tableWidth = newValue.doubleValue();
			nomMatiere.setPrefWidth((tableWidth - 80) / 7);
			sujet.setPrefWidth((tableWidth - 80) / 7);
			idProjet.setPrefWidth(40);
			idEtudiant.setPrefWidth(40);
			nomEtudiant.setPrefWidth((tableWidth - 80) / 7);
			prenomEtudiant.setPrefWidth((tableWidth - 80) / 7);
			noteSoutenance.setPrefWidth((tableWidth - 80) / 7);
			noteRapport.setPrefWidth((tableWidth - 80) / 7);
			noteFinale.setPrefWidth((tableWidth - 80) / 7);
		});
		
		// Chargement et affichage des données des notes
		refreshTable();
		// Configuration des icônes de boutons
		initializeImg();
	}
	
	// Configuration des icônes de boutons
	public void initializeImg() {
		Image search = new Image("/com/projet/img/search.png");
		ImageView searchImageView = new ImageView(search);
		searchImageView.setFitWidth(18);
		searchImageView.setFitHeight(18);
		searchNote.setGraphic(searchImageView);
		
		Image refresh = new Image("/com/projet/img/refresh.png");
		ImageView refreshImageView = new ImageView(refresh);
		refreshImageView.setFitWidth(18);
		refreshImageView.setFitHeight(18);
		refreshNote.setGraphic(refreshImageView);
	}
	
	// Recherche des notes
	public void searchNote(ActionEvent actionEvent) {
		String nomEtudiant = null;
		if (textFieldNomEtudiant.getText() != null && !"".equals(textFieldNomEtudiant.getText().trim())) {
			nomEtudiant = "%" + textFieldNomEtudiant.getText() + "%";
			
		}
		String prenomEtudiant = null;
		if (textFieldPrenomEtudiant.getText() != null && !"".equals(textFieldPrenomEtudiant.getText().trim())) {
			prenomEtudiant = "%" + textFieldPrenomEtudiant.getText() + "%";
		}
		String sujet = null;
		if (textFieldSujet.getText() != null && !"".equals(textFieldSujet.getText().trim())) {
			sujet = "%" + textFieldSujet.getText() + "%";
		}
		
		String nomMatiere = null;
		if (textFieldNomMatiere.getText() != null && !"".equals(textFieldNomMatiere.getText().trim())) {
			nomMatiere = "%" + textFieldNomMatiere.getText() + "%";
		}
		
		
		try {
			List<Integer> idsProjet = projetService.getIdsProjetByCondition(nomMatiere, sujet);
			List<Integer> idsEtudiant = etudiantService.getIdsEtudiantByCondition(nomEtudiant, prenomEtudiant);
			ObservableList<Note> data = FXCollections.observableArrayList();
			for (Integer idProjet : idsProjet) {
				for (Integer idEtudiant : idsEtudiant) {
					List<Note> notes = noteService.selectByCondition(idProjet, idEtudiant);
					data.addAll(notes);
				}
			}
			tableViewNote.setItems(data);
			tableViewNote.refresh();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	// Mise à jour de la note de soutenance
	public void updateSoutenance(Integer idProjet, Integer idEtudiant, Double noteSoutenance) {
		try {
			noteService.updateNoteSoutenance(idProjet, idEtudiant, noteSoutenance);
			System.out.println("idProjet : " + idProjet + " idEtudiant : " + idEtudiant + " noteSoutenance : " + noteSoutenance);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// Rafraîchissement des données du tableau
	public void refreshTable() {
		try {
			List<Integer> idsProjet = projetService.getIdsProjet();
			List<Integer> idsEtudiant = etudiantService.getIdsEtudiant();
			ObservableList<Note> data = FXCollections.observableArrayList();
			for (Integer idProjet : idsProjet) {
				for (Integer idEtudiant : idsEtudiant) {
					Note note = noteService.getNotesByIdProjetAndIdEtudiant(idProjet, idEtudiant);
					if (note != null) {
						data.add(note);
					}
				}
			}
			tableViewNote.setItems(data);
			tableViewNote.refresh();
		} catch (Exception e) {
			e.printStackTrace();
		}
		textFieldNomEtudiant.setText("");
		textFieldPrenomEtudiant.setText("");
		textFieldSujet.setText("");
		textFieldNomMatiere.setText("");
	}
	
	
	public void refreshTable(ActionEvent actionEvent) {
		refreshTable();
	}
	
	// Navigation vers différentes vues
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
		Main.changeView("/com/projet/view/ShowBinome.fxml");
	}
	
	public void toShowNote(ActionEvent actionEvent) {
		Main.changeView("/com/projet/view/ShowNote.fxml");
	}
	
	// Exportation des données en PDF
	public void toPDF(ActionEvent actionEvent) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save as PDF");
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
		
		Stage stage = (Stage) tableViewNote.getScene().getWindow();
		File file = fileChooser.showSaveDialog(stage);
		
		if (file != null) {
			Document document = new Document();
			try {
				PdfWriter.getInstance(document, new FileOutputStream(file));
				document.open();
				
				
				PdfPTable pdfTable = new PdfPTable(tableViewNote.getColumns().size() - 1);
				
				
				for (int i = 0; i < tableViewNote.getColumns().size() - 1; i++) {
					TableColumn<?, ?> col = (TableColumn<?, ?>) tableViewNote.getColumns().get(i);
					pdfTable.addCell(col.getText());
				}
				
				
				for (int i = 0; i < tableViewNote.getItems().size(); i++) {
					for (int j = 0; j < tableViewNote.getColumns().size() - 1; j++) {
						Object cellData = tableViewNote.getColumns().get(j).getCellData(tableViewNote.getItems().get(i));
						if (cellData != null) {
							pdfTable.addCell(cellData.toString());
						} else {
							pdfTable.addCell("");
						}
					}
				}
				
				
				document.add(pdfTable);
				
				document.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	// Exportation des données en Excel
	public void toExcel(ActionEvent actionEvent) {
		Stage stage = (Stage) tableViewNote.getScene().getWindow();
		
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save as Excel");
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel Files", "*.xlsx"));
		File file = fileChooser.showSaveDialog(stage);
		
		if (file != null) {
			try (Workbook workbook = new XSSFWorkbook(); FileOutputStream fileOut = new FileOutputStream(file)) {
				Sheet sheet = workbook.createSheet("Data");
				
				Row headerRow = sheet.createRow(0);
				
				for (int i = 0; i < tableViewNote.getColumns().size() - 1; i++) {
					org.apache.poi.ss.usermodel.Cell headerCell = headerRow.createCell(i);
					headerCell.setCellValue(tableViewNote.getColumns().get(i).getText());
				}
				
				
				for (int rowIndex = 0; rowIndex < tableViewNote.getItems().size(); rowIndex++) {
					Row dataRow = sheet.createRow(rowIndex + 1);
					for (int colIndex = 0; colIndex < tableViewNote.getColumns().size() - 1; colIndex++) {
						Cell cell = dataRow.createCell(colIndex);
						Object cellValue = ((TableColumn<?, ?>) tableViewNote.getColumns().get(colIndex)).getCellData(rowIndex);
						if (cellValue != null) {
							cell.setCellValue(cellValue.toString());
						}
					}
				}
				
				
				for (int i = 0; i < tableViewNote.getColumns().size() - 1; i++) {
					sheet.autoSizeColumn(i);
				}
				
				
				workbook.write(fileOut);
			} catch (Exception e) {
				e.printStackTrace();
				
			}
		}
	}
}
