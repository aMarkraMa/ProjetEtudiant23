package com.projet.controller;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.projet.Main;
import com.projet.entity.*;
import com.projet.mapper.BinomeMapper;
import com.projet.mapper.EtudiantMapper;
import com.projet.mapper.NoteMapper;
import com.projet.mapper.ProjetMapper;
import com.projet.service.impl.UpdateNoteServiceImpl;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
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
import java.util.Optional;

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
	private Button addNote;

	@FXML
	private Button searchNote;

	@FXML
	private Button deleteNote;
	
	
	@FXML
	public void initialize() {

		nomMatiere.setCellValueFactory(new PropertyValueFactory<>("nomMatiere"));
		sujet.setCellValueFactory(new PropertyValueFactory<>("sujet"));
		nomEtudiant.setCellValueFactory(new PropertyValueFactory<>("nomEtudiant"));
		prenomeEtudiant.setCellValueFactory(new PropertyValueFactory<>("prenomEtudiant"));
		noteSoutenance.setCellValueFactory(new PropertyValueFactory<>("noteSoutenance"));
		noteRapport.setCellValueFactory(new PropertyValueFactory<>("noteRapport"));
		noteFinale.setCellValueFactory(new PropertyValueFactory<>("noteFinale"));
		refreshTable();
		initializeImg();
	}

	public void initializeImg() {
		Image search = new Image("com/projet/img/search.png");
		ImageView searchImageView = new ImageView(search);
		searchImageView.setFitWidth(18);
		searchImageView.setFitHeight(18);
		searchNote.setGraphic(searchImageView);

		Image add = new Image("com/projet/img/add.png");
		ImageView addImageView = new ImageView(add);
		addImageView.setFitWidth(18);
		addImageView.setFitHeight(18);
		addNote.setGraphic(addImageView);

		Image delete = new Image("com/projet/img/delete.png");
		ImageView deleteImageView = new ImageView(delete);
		deleteImageView.setFitWidth(18);
		deleteImageView.setFitHeight(18);
		deleteNote.setGraphic(deleteImageView);

		Image refresh = new Image("com/projet/img/refresh.png");
		ImageView refreshImageView = new ImageView(refresh);
		refreshImageView.setFitWidth(18);
		refreshImageView.setFitHeight(18);
		refreshNote.setGraphic(refreshImageView);
	}

	public void searchNote(ActionEvent actionEvent) {
		SqlSession sqlSession = MyBatisUtils.getSqlSession();
		NoteMapper mapper = sqlSession.getMapper(NoteMapper.class);
		Note note = new Note();
		Etudiant etudiant = new Etudiant();
		etudiant.setNomEtudiant("%" + textFieldNomEtudiant.getText() + "%");
		etudiant.setPrenomEtudiant("%" + textFieldPrenomEtudiant.getText() + "%");

		Projet projet = new Projet();
		projet.setSujet("%" + textFieldSujet + "%");
		projet.setNomMatiere("%" + textFieldNomMatiere + "%");

		Binome binome = new Binome();
		binome.setProjet(projet);

		note.setEtudiant(etudiant);
		note.setBinome(binome);

		List<Note> notes = mapper.selectByCondition(note);
		ObservableList<Note> data = FXCollections.observableArrayList();
		data.addAll(notes);
		tableViewNote.setItems(data);
	}

	public void deleteNote(ActionEvent actionEvent){

	}


	
	public void refreshTable() {


		SqlSession sqlSession = MyBatisUtils.getSqlSession();
		NoteMapper mapper = sqlSession.getMapper(NoteMapper.class);

		List<Note> notes = mapper.selectAll();
		for(int i = 0;i < notes.size();i++){
			System.out.println(notes.get(i).getIdProjet() + " " + notes.get(i).getIdBinome() + " " + notes.get(i).getIdEtudiant());
			System.out.println(notes.get(i));
		}

		ObservableList<Note> data = FXCollections.observableArrayList();
		data.addAll(notes);

		tableViewNote.setItems(data);

		sqlSession.close();
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
