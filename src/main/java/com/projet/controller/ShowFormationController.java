package com.projet.controller;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.projet.Main;
import com.projet.entity.Etudiant;
import com.projet.entity.Formation;
import com.projet.mapper.EtudiantMapper;
import com.projet.mapper.FormationMapper;
import com.projet.service.EtudiantService;
import com.projet.service.FormationService;
import com.projet.service.impl.EtudiantServiceImpl;
import com.projet.service.impl.FormationServiceImpl;
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
import java.util.List;
import java.util.Optional;

public class ShowFormationController {
	@FXML
	private TableColumn<Formation, String> nomFormation;
	
	@FXML
	private TableColumn<Formation, String> promotion;
	
	@FXML
	private TableColumn<Formation, String> idFormation;
	
	@FXML
	private TableColumn<Formation, Void> boutons;
	
	@FXML
	private TableView<Formation> tableviewFormation;
	
	@FXML
	private Button toAjouterFor;
	
	@FXML
	private Button refreshFormation;
	
	@FXML
	private Button searchFormation;
	
	@FXML
	private TextField textfieldNomFormation;
	
	@FXML
	private TextField textfieldPromotion;
	
	@FXML
	private MenuItem toEtudiants;
	
	@FXML
	private MenuItem toProjets;
	
	@FXML
	private MenuItem toBinomes;
	
	@FXML
	private MenuItem toNotes;
	
	private EtudiantService etudiantService = new EtudiantServiceImpl();
	
	private FormationService formationService = new FormationServiceImpl();
	
	// Initialisation du contrôleur
	@FXML
	public void initialize() {
		// Configuration des colonnes du tableau
		idFormation = new TableColumn<>("id");
		idFormation.setCellValueFactory(new PropertyValueFactory<>("idFormation"));
		idFormation.setPrefWidth(40);
		nomFormation = new TableColumn<>("Formation");
		nomFormation.setCellValueFactory(new PropertyValueFactory<>("nomFormation"));
		promotion = new TableColumn<>("Promotion");
		promotion.setCellValueFactory(new PropertyValueFactory<>("promotion"));
		
		// Configuration des boutons d'action
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
							FormationServiceImpl.formationToUpdate = formation;
							Main.addView("/com/projet/view/UpdateFormation.fxml");
							
						});
						
						supprimer.setOnAction((ActionEvent event) -> {
							Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
							alert1.setTitle("Supprimer une formation");
							alert1.setHeaderText("Confirmez votre choix");
							alert1.setContentText("Vous allez supprimer cette formation. Etes-vous sur?");
							Optional<ButtonType> resultat = alert1.showAndWait();
							if (resultat.isPresent() && resultat.get() == ButtonType.OK) {
								try {
									Formation formation = getTableView().getItems().get(getIndex());
									List<Etudiant> etudiants = etudiantService.getEtudiantsByIdFormation(formation);
									if (etudiants != null && etudiants.size() != 0) {
										Alert alert2 = new Alert(Alert.AlertType.ERROR);
										alert2.setTitle("ERREUR: Il y a encore des etudiants dans cette formation!");
										alert2.setHeaderText("Il y a encore des etudiants dans cette formation!");
										alert2.setContentText("Il y a encore des etudiants dans cette formation, vous ne pouvez pas la supprimer.");
										alert2.getDialogPane().setPrefWidth(800);
										alert2.show();
									} else {
										Integer idFormation = formation.getIdFormation();
										formationService.deleteById(idFormation);
										ObservableList<Formation> data = FXCollections.observableArrayList();
										List<Formation> formations = formationService.selectAll();
										data.addAll(formations);
										tableviewFormation.setItems(data);
									}
								} catch (Exception e) {
									e.printStackTrace();
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
		
		// Ajout des colonnes au TableView
		tableviewFormation.getColumns().add(idFormation);
		tableviewFormation.getColumns().add(nomFormation);
		tableviewFormation.getColumns().add(promotion);
		tableviewFormation.getColumns().add(boutons);
		
		// Ajustement de la largeur des colonnes
		tableviewFormation.widthProperty().addListener((observable, oldValue, newValue) -> {
			double tableWidth = newValue.doubleValue();
			nomFormation.setPrefWidth((tableWidth - 40) / 3);
			promotion.setPrefWidth((tableWidth - 40) / 3);
			boutons.setPrefWidth((tableWidth - 40) / 3);
		});
		
		// Chargement et affichage des données des formations
		try {
			List<Formation> formations = formationService.selectAll();
			refreshTable(formations);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// Configuration des icônes de boutons
		Image refresh = new Image("com/projet/img/refresh.png");
		ImageView refreshImageView = new ImageView(refresh);
		refreshImageView.setFitHeight(20);
		refreshImageView.setFitWidth(20);
		refreshFormation.setGraphic(refreshImageView);
		Image search = new Image("com/projet/img/search.png");
		ImageView searchImageView = new ImageView(search);
		searchImageView.setFitWidth(20);
		searchImageView.setFitHeight(20);
		searchFormation.setGraphic(searchImageView);
		Image add = new Image("/com/projet/img/add.png");
		ImageView addImageView = new ImageView(add);
		addImageView.setFitWidth(18);
		addImageView.setFitHeight(18);
		toAjouterFor.setGraphic(addImageView);
		
		
	}
	
	// Rafraîchissement des données du tableau
	public void refreshTable(List newData) {
		ObservableList<Formation> data = FXCollections.observableArrayList();
		data.addAll(newData);
		tableviewFormation.setItems(data);
	}
	
	
	// Navigation vers différentes vues
	public void toEtudiants(ActionEvent actionEvent) {
		Main.changeView("/com/projet/view/ShowEtudiant.fxml");
	}
	
	public void toAjouterFormation(ActionEvent actionEvent) {
		Main.addView("/com/projet/view/AddFormation.fxml");
	}
	
	// Recherche des formations
	public void searchFormation(ActionEvent actionEvent) {
		
		try {
			Formation formation = new Formation();
			formation.setNomFormation("%" + textfieldNomFormation.getText().trim() + "%");
			formation.setPromotion("%" + textfieldPromotion.getText().trim() + "%");
			List<Formation> formations = formationService.selectByCondition(formation);
			ObservableList<Formation> data = FXCollections.observableArrayList();
			data.addAll(formations);
			tableviewFormation.setItems(data);
			tableviewFormation.refresh();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// Rafraîchissement du tableau avec les données actualisées
	public void refreshTable(ActionEvent actionEvent) {

		try {
			List<Formation> formations = formationService.selectAll();
			ObservableList<Formation> data = FXCollections.observableArrayList();
			data.addAll(formations);
			tableviewFormation.setItems(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		textfieldNomFormation.setText("");
		textfieldPromotion.setText("");
		tableviewFormation.refresh();
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
	
	// Exportation des données en PDF
	public void toPDF(ActionEvent actionEvent) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save as PDF");
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
		
		Stage stage = (Stage) tableviewFormation.getScene().getWindow();
		File file = fileChooser.showSaveDialog(stage);
		
		if (file != null) {
			Document document = new Document();
			try {
				PdfWriter.getInstance(document, new FileOutputStream(file));
				document.open();
				
				PdfPTable pdfTable = new PdfPTable(tableviewFormation.getColumns().size() - 1);
				
				for (int i = 0; i < tableviewFormation.getColumns().size() - 1; i++) {
					TableColumn<?, ?> col = (TableColumn<?, ?>) tableviewFormation.getColumns().get(i);
					pdfTable.addCell(col.getText());
				}
				
				for (int i = 0; i < tableviewFormation.getItems().size(); i++) {
					for (int j = 0; j < tableviewFormation.getColumns().size() - 1; j++) {
						Object cellData = tableviewFormation.getColumns().get(j).getCellData(tableviewFormation.getItems().get(i));
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
		Stage stage = (Stage) tableviewFormation.getScene().getWindow();
		
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save as Excel");
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel Files", "*.xlsx"));
		File file = fileChooser.showSaveDialog(stage);
		
		if (file != null) {
			try (Workbook workbook = new XSSFWorkbook(); FileOutputStream fileOut = new FileOutputStream(file)) {
				Sheet sheet = workbook.createSheet("Data");
				
				Row headerRow = sheet.createRow(0);
				
				for (int i = 0; i < tableviewFormation.getColumns().size() - 1; i++) {
					org.apache.poi.ss.usermodel.Cell headerCell = headerRow.createCell(i);
					headerCell.setCellValue(tableviewFormation.getColumns().get(i).getText());
				}
				
				for (int rowIndex = 0; rowIndex < tableviewFormation.getItems().size(); rowIndex++) {
					Row dataRow = sheet.createRow(rowIndex + 1);
					for (int colIndex = 0; colIndex < tableviewFormation.getColumns().size() - 1; colIndex++) {
						Cell cell = dataRow.createCell(colIndex);
						Object cellValue = ((TableColumn<?, ?>) tableviewFormation.getColumns().get(colIndex)).getCellData(rowIndex);
						if (cellValue != null) {
							cell.setCellValue(cellValue.toString());
						}
					}
				}
				
				for (int i = 0; i < tableviewFormation.getColumns().size() - 1; i++) {
					sheet.autoSizeColumn(i);
				}
				
				workbook.write(fileOut);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
}
