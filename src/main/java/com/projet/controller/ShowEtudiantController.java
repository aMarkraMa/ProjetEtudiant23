package com.projet.controller;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.projet.Main;
import com.projet.entity.Etudiant;
import com.projet.entity.Formation;
import com.projet.mapper.EtudiantMapper;
import com.projet.utils.MyBatisUtils;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Callback;
import org.apache.ibatis.session.SqlSession;
import com.projet.service.impl.UpdateEtudiantServiceImpl;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
	private TableView<Etudiant> tableviewEtudiant;
	
	@FXML
	private Button toAjouterEtu;
	
	@FXML
	private Button refreshEtudiant;
	
	@FXML
	private Button searchEtudiant;
	
	@FXML
	private TextField textfieldNomEtudiant;
	
	@FXML
	private TextField textfieldPrenomEtudiant;
	
	@FXML
	private TextField textfieldNomFormation;
	
	@FXML
	private MenuItem toFormations;
	
	@FXML
	private MenuItem toProjets;
	
	@FXML
	private MenuItem toBinomes;
	
	@FXML
	private MenuItem toNotes;
	
	
	@FXML
	public void initialize() {
		
		id_etudiant = new TableColumn<>("id");
		id_etudiant.setCellValueFactory(new PropertyValueFactory<>("idEtudiant"));
		id_etudiant.setPrefWidth(40);
		nom_etudiant = new TableColumn<>("Nom");
		nom_etudiant.setCellValueFactory(new PropertyValueFactory<>("nomEtudiant"));
		prenom_etudiant = new TableColumn<>("Prenom");
		prenom_etudiant.setCellValueFactory(new PropertyValueFactory<>("prenomEtudiant"));
		nom_formation = new TableColumn<>("Formation");
		nom_formation.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Etudiant, String>, ObservableValue<String>>() {
			// 接收一个TableColumn.CellDataFeatures<T, U> 对象作为参数，并返回一个ObservableValue<U>对象
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
							Etudiant etudiant = getTableView().getItems().get(getIndex());
							UpdateEtudiantServiceImpl.etudiantToUpdate = etudiant;
							Main.addView("/com/projet/view/UpdateEtudiant.fxml");
							
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
								tableviewEtudiant.setItems(data);
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
		
		
		tableviewEtudiant.getColumns().add(id_etudiant);
		tableviewEtudiant.getColumns().add(nom_etudiant);
		tableviewEtudiant.getColumns().add(prenom_etudiant);
		tableviewEtudiant.getColumns().add(nom_formation);
		tableviewEtudiant.getColumns().add(boutons);
		
		tableviewEtudiant.widthProperty().addListener((observable, oldValue, newValue) -> {
			double tableWidth = newValue.doubleValue();
			nom_etudiant.setPrefWidth((tableWidth - 40) / 4);
			prenom_etudiant.setPrefWidth((tableWidth - 40) / 4);
			nom_formation.setPrefWidth((tableWidth - 40) / 4);
			boutons.setPrefWidth((tableWidth - 40) / 4);
		});
		
		
		SqlSession sqlSession = MyBatisUtils.getSqlSession();
		EtudiantMapper mapper = sqlSession.getMapper(EtudiantMapper.class);
		List<Etudiant> etudiants = mapper.selectAll();
		refreshTable(etudiants);
		
		Image refresh = new Image("com/projet/img/refresh.png");
		ImageView refreshImageView = new ImageView(refresh);
		refreshImageView.setFitHeight(20);
		refreshImageView.setFitWidth(20);
		refreshEtudiant.setGraphic(refreshImageView);
		Image search = new Image("com/projet/img/search.png");
		ImageView searchImageView = new ImageView(search);
		searchImageView.setFitWidth(20);
		searchImageView.setFitHeight(20);
		searchEtudiant.setGraphic(searchImageView);
		
		
	}
	
	public void refreshTable(List newData) {
		ObservableList<Etudiant> data = FXCollections.observableArrayList();
		data.addAll(newData);
		tableviewEtudiant.setItems(data);
	}
	
	
	public void retour(ActionEvent actionEvent) {
		Main.changeView("/com/projet/view/Main.fxml");
	}
	
	public void toFormations(ActionEvent actionEvent) {
		Main.changeView("/com/projet/view/ShowFormation.fxml");
	}
	
	public void toAjouterEtudiant(ActionEvent actionEvent) {
		Main.addView("/com/projet/view/AddEtudiant.fxml");
	}
	
	public void searchEtudiant(ActionEvent actionEvent) {
		SqlSession sqlSession = MyBatisUtils.getSqlSession();
		EtudiantMapper mapper = sqlSession.getMapper(EtudiantMapper.class);
		Etudiant etudiant = new Etudiant();
		etudiant.setNomEtudiant("%" + textfieldNomEtudiant.getText() + "%");
		etudiant.setPrenomEtudiant("%" + textfieldPrenomEtudiant.getText() + "%");
		Formation formation = new Formation();
		formation.setNomFormation("%" + textfieldNomFormation.getText() + "%");
		etudiant.setFormation(formation);
		List<Etudiant> etudiants = mapper.selectByCondition(etudiant);
		ObservableList<Etudiant> data = FXCollections.observableArrayList();
		data.addAll(etudiants);
		tableviewEtudiant.setItems(data);
	}
	
	public void refreshTable(ActionEvent actionEvent) {
		SqlSession sqlSession = MyBatisUtils.getSqlSession();
		EtudiantMapper mapper = sqlSession.getMapper(EtudiantMapper.class);
		List<Etudiant> etudiants = mapper.selectAll();
		ObservableList<Etudiant> data = FXCollections.observableArrayList();
		data.addAll(etudiants);
		tableviewEtudiant.setItems(data);
		
		textfieldNomEtudiant.setText("");
		textfieldNomEtudiant.setPromptText("Nom");
		textfieldPrenomEtudiant.setText("");
		textfieldPrenomEtudiant.setPromptText("Prenom");
		textfieldNomFormation.setText("");
		textfieldNomFormation.setPromptText("Formation");
		tableviewEtudiant.refresh();
	}
	
	public void toProjets(ActionEvent actionEvent) {
		Main.changeView("/com/projet/view/ShowProjet.fxml");
	}
	
	public void toBinomes(ActionEvent actionEvent) {
		Main.changeView("/com/projet/view/ShowBinome.fxml");
	}
	
	public void toNotes(ActionEvent actionEvent) {
		Main.changeView("/com/projet/view/ShowNote.fxml");
		
	}
	
	public void toPDF(ActionEvent actionEvent) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save as PDF");
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
		
		Stage stage = (Stage) tableviewEtudiant.getScene().getWindow();
		File file = fileChooser.showSaveDialog(stage);
		
		if (file != null) {
			Document document = new Document();
			try {
				PdfWriter.getInstance(document, new FileOutputStream(file));
				document.open();
				
				// Créez une table PDF, avec le même nombre de colonnes que la TableView
				PdfPTable pdfTable = new PdfPTable(tableviewEtudiant.getColumns().size() - 1);
				
				// Ajoutez un en-tête
				for (int i = 0; i < tableviewEtudiant.getColumns().size() - 1; i++) {
					TableColumn<?, ?> col = (TableColumn<?, ?>) tableviewEtudiant.getColumns().get(i);
					pdfTable.addCell(col.getText());
				}
				
				// Ajoutez des données de ligne
				for (int i = 0; i < tableviewEtudiant.getItems().size(); i++) {
					for (int j = 0; j < tableviewEtudiant.getColumns().size() - 1; j++) {
						Object cellData = tableviewEtudiant.getColumns().get(j).getCellData(tableviewEtudiant.getItems().get(i));
						if (cellData != null) {
							pdfTable.addCell(cellData.toString());
						} else {
							pdfTable.addCell("");
						}
					}
				}
				
				// Ajoutez une table à votre document
				document.add(pdfTable);
				
				document.close(); // Fermez le document
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void toExcel(ActionEvent actionEvent) {
		Stage stage = (Stage) tableviewEtudiant.getScene().getWindow(); // Obtenir la fenêtre actuelle pour afficher le sélecteur de fichiers
		
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save as Excel");
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel Files", "*.xlsx"));
		File file = fileChooser.showSaveDialog(stage);
		
		if (file != null) {
			try (Workbook workbook = new XSSFWorkbook(); FileOutputStream fileOut = new FileOutputStream(file)) {
				Sheet sheet = workbook.createSheet("Data");
				
				Row headerRow = sheet.createRow(0);
				// Titres de colonne
				for (int i = 0; i < tableviewEtudiant.getColumns().size() - 1; i++) {
					Cell headerCell = headerRow.createCell(i);
					headerCell.setCellValue(tableviewEtudiant.getColumns().get(i).getText());
				}
				
				// Données de ligne
				for (int rowIndex = 0; rowIndex < tableviewEtudiant.getItems().size(); rowIndex++) {
					Row dataRow = sheet.createRow(rowIndex + 1);
					for (int colIndex = 0; colIndex < tableviewEtudiant.getColumns().size() - 1; colIndex++) {
						Cell cell = dataRow.createCell(colIndex);
						Object cellValue = ((TableColumn<?, ?>) tableviewEtudiant.getColumns().get(colIndex)).getCellData(rowIndex);
						if (cellValue != null) {
							cell.setCellValue(cellValue.toString()); // Convertir les données en fonction des besoins
						}
					}
				}
				
				// Automatiquement ajuster la largeur de toutes les colonnes
				for (int i = 0; i < tableviewEtudiant.getColumns().size() - 1; i++) {
					sheet.autoSizeColumn(i);
				}
				
				// Écrire dans le fichier
				workbook.write(fileOut);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
}
