package com.projet.controller;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.projet.Main;
import com.projet.entity.*;
import com.projet.mapper.EtudiantMapper;
import com.projet.mapper.NoteMapper;
import com.projet.mapper.ProjetMapper;
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
	public void initialize() {
		
		tableViewNote.setEditable(true);
//		noteSoutenance.setEditable(true);
		nomMatiere.setCellValueFactory(note -> new SimpleStringProperty(note.getValue().getProjet().getNomMatiere()));
// 		idProjet.setCellValueFactory(new PropertyValueFactory<>("idProjet"));
// 		idEtudiant.setCellValueFactory(new PropertyValueFactory<>("idEtudiant"));
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
						return null; // L'entrée n'est pas acceptée car elle n'est pas comprise entre 0 et 100 ou n'est pas un nombre.
					}));
				}
			};
		});
		noteSoutenance.setOnEditCommit(
				(TableColumn.CellEditEvent<Note, Double> t) -> {
					Note note = t.getTableView().getItems().get(t.getTablePosition().getRow());
//					note.setNoteSoutenance(t.getNewValue());
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
		// noteFinale.setCellValueFactory(new PropertyValueFactory<>("noteFinale"));
		noteFinale.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Note, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(TableColumn.CellDataFeatures<Note, String> note) {
				if (note.getValue().getNoteRapport().doubleValue() == -1.0 || note.getValue().getNoteSoutenance().doubleValue() == -1.0) {
					return new SimpleStringProperty("");
				} else {
					Double noteRapportE = note.getValue().getNoteRapport();
					Double noteSoutenanceE = note.getValue().getNoteSoutenance();
					Integer pourcentageSoutenance = note.getValue().getProjet().getPourcentageSoutenance();
					Integer pourcentageRapport = 100 - pourcentageSoutenance;
					Double noteFinale = noteRapportE * pourcentageRapport * 0.01 + noteSoutenanceE * pourcentageSoutenance * 0.01;
					long dateDelay = ChronoUnit.DAYS.between(note.getValue().getDateReeleRemise(), note.getValue().getProjet().getDatePrevueRemise());
					noteFinale = noteFinale - dateDelay * 0.01;
					return new SimpleStringProperty(noteFinale.toString());
				}
				// Integer ip = note.getValue().getProjet().getIdProjet();
				// Integer ie = note.getValue().getEtudiant().getIdEtudiant();
				// Integer pctg = note.getValue().getProjet().getPourcentageSoutenance();
				// System.out.println("pctg:" + pctg);
				// System.out.println(ip + "," + ie + "dateDelay : " + dateDelay + "Note: " + note.getValue().getNoteFinale().toString());
				// if (note.getValue().getNoteSoutenance()==-1 || note.getValue().getNoteRapport()==-1){
				// 	return new SimpleStringProperty("");
				// }
				// else {
				// 	return new SimpleStringProperty(note.getValue().getNoteFinale().toString());
				// }
				
			}
		});
		
		
		refreshTable();
		initializeImg();
	}
	
	public void initializeImg() {
		Image search = new Image("com/projet/img/search.png");
		ImageView searchImageView = new ImageView(search);
		searchImageView.setFitWidth(18);
		searchImageView.setFitHeight(18);
		searchNote.setGraphic(searchImageView);
		
		Image refresh = new Image("com/projet/img/refresh.png");
		ImageView refreshImageView = new ImageView(refresh);
		refreshImageView.setFitWidth(18);
		refreshImageView.setFitHeight(18);
		refreshNote.setGraphic(refreshImageView);
	}
	
	public void searchNote(ActionEvent actionEvent) {
		
		String nomEtudiant = "%" + textFieldNomEtudiant.getText() + "%";
		String prenomEtudiant = "%" + textFieldPrenomEtudiant.getText() + "%";
		String sujet = "%" + textFieldSujet.getText() + "%";
		String nomMatiere = "%" + textFieldNomMatiere.getText() + "%";
		
		System.out.println(nomEtudiant);
		System.out.println(prenomEtudiant);
		System.out.println(sujet);
		System.out.println(nomMatiere);
		
		SqlSession sqlSession = null;
		try {
			sqlSession = MyBatisUtils.getSqlSession();
			NoteMapper noteMapper = sqlSession.getMapper(NoteMapper.class);
			ProjetMapper projetMapper = sqlSession.getMapper(ProjetMapper.class);
			EtudiantMapper etudiantMapper = sqlSession.getMapper(EtudiantMapper.class);
			List<Integer> idsProjet = projetMapper.getIdsProjet();
			List<Integer> idsEtudiant = etudiantMapper.getIdsEtudiant();
			ObservableList<Note> data = FXCollections.observableArrayList();
			for (Integer idProjet : idsProjet) {
				for (Integer idEtudiant : idsEtudiant) {
					List<Note> notes = noteMapper.selectByCondition(idProjet, idEtudiant, nomMatiere, sujet, nomEtudiant, prenomEtudiant);
					// for (Note note : notes) {
					// 	long dateDelay = ChronoUnit.DAYS.between(note.getDateReeleRemise(), note.getProjet().getDatePrevueRemise());
					// 	note.setNoteFinale((int)dateDelay);
					// }
					data.addAll(notes);
				}
			}
			
			tableViewNote.setItems(data);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
		
	}
	
	public void updateSoutenance(Integer idProjet, Integer idEtudiant, Double noteSoutenance) {
		SqlSession sqlSession = null;
		try {
			sqlSession = MyBatisUtils.getSqlSession();
			NoteMapper noteMapper = sqlSession.getMapper(NoteMapper.class);
			int i = noteMapper.updateNoteSoutenance(idProjet, idEtudiant, noteSoutenance);
			System.out.println("idProjet : " + idProjet + " idEtudiant : " + idEtudiant + " noteSoutenance : " + noteSoutenance);
			System.out.println("update Note Soutenance nb line: " + i);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
	}
	
	
	public void refreshTable() {
		
		SqlSession sqlSession = null;
		try {
			sqlSession = MyBatisUtils.getSqlSession();
			NoteMapper noteMapper = sqlSession.getMapper(NoteMapper.class);
			ProjetMapper projetMapper = sqlSession.getMapper(ProjetMapper.class);
			EtudiantMapper etudiantMapper = sqlSession.getMapper(EtudiantMapper.class);
			List<Integer> idsProjet = projetMapper.getIdsProjet();
			List<Integer> idsEtudiant = etudiantMapper.getIdsEtudiant();
			ObservableList<Note> data = FXCollections.observableArrayList();
			for (Integer idProjet : idsProjet) {
				for (Integer idEtudiant : idsEtudiant) {
					Note note = noteMapper.getNotesByIdProjetAndIdEtudiant(idProjet, idEtudiant);
					if (note != null) {
						data.add(note);
					}
				}
			}
			tableViewNote.setItems(data);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
		textFieldNomEtudiant.setText("");
		textFieldPrenomEtudiant.setText("");
		textFieldSujet.setText("");
		textFieldNomMatiere.setText("");
	}
	
	
	public void refreshTable(ActionEvent actionEvent) {
		refreshTable();
	}
	
	public void showAddView(ActionEvent actionEvent) {
		Main.addView("/com/projet/view/AddNote.fxml");
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
		Main.changeView("/com/projet/view/ShowBinome.fxml");
	}
	
	public void toShowNote(ActionEvent actionEvent) {
		Main.changeView("/com/projet/view/ShowNote.fxml");
	}
	
	public void retour(ActionEvent actionEvent) {
		Main.changeView("/com/projet/view/Main.fxml");
	}
	
	
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
