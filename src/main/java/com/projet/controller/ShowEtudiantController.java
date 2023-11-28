package com.projet.controller;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.projet.Main;
import com.projet.entity.Etudiant;
import com.projet.entity.Formation;
import com.projet.mapper.EtudiantMapper;
import com.projet.service.EtudiantService;
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
import com.projet.service.impl.EtudiantServiceImpl;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Optional;

public class ShowEtudiantController {
	@FXML
	private TableColumn<Etudiant, String> nomFormation;
	
	@FXML
	private TableColumn<Etudiant, String> nomEtudiant;
	
	@FXML
	private TableColumn<Etudiant, String> idEtudiant;
	
	@FXML
	private TableColumn<Etudiant, Void> boutons;
	
	@FXML
	private TableColumn<Etudiant, String> prenomEtudiant;
	
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
	
	private EtudiantService etudiantService = new EtudiantServiceImpl();
	
	// Initialisation du contrôleur et configuration du tableau
	@FXML
	public void initialize() {
		// Initialisation de chaque colonne du tableau
		idEtudiant = new TableColumn<>("id");
		idEtudiant.setCellValueFactory(new PropertyValueFactory<>("idEtudiant"));
		idEtudiant.setPrefWidth(40);
		nomEtudiant = new TableColumn<>("Nom");
		nomEtudiant.setCellValueFactory(new PropertyValueFactory<>("nomEtudiant"));
		prenomEtudiant = new TableColumn<>("Prenom");
		prenomEtudiant.setCellValueFactory(new PropertyValueFactory<>("prenomEtudiant"));
		nomFormation = new TableColumn<>("Formation");
		nomFormation.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Etudiant, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(TableColumn.CellDataFeatures<Etudiant, String> etudiant) {
				return new SimpleStringProperty(etudiant.getValue().getFormation().getNomFormation() + "--" + etudiant.getValue().getFormation().getPromotion());
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
							EtudiantServiceImpl.etudiantToUpdate = etudiant;
							Main.addView("/com/projet/view/UpdateEtudiant.fxml");
							
						});
						
						supprimer.setOnAction((ActionEvent event) -> {
							Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
							alert.setTitle("Supprimer un etudiant");
							alert.setHeaderText("Confirmez votre choix");
							alert.setContentText("Vous allez supprimer cet etudiant. Etes-vous sur?");
							Optional<ButtonType> resultat = alert.showAndWait();
							if (resultat.isPresent() && resultat.get() == ButtonType.OK) {
								try {
									Etudiant etudiant = getTableView().getItems().get(getIndex());
									Integer idEtudiant = etudiant.getIdEtudiant();
									etudiantService.deleteById(idEtudiant);
									ObservableList<Etudiant> data = FXCollections.observableArrayList();
									List<Etudiant> etudiants = etudiantService.selectAll();
									data.addAll(etudiants);
									tableviewEtudiant.setItems(data);
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
		
		// Ajout des colonnes à la vue du tableau
		tableviewEtudiant.getColumns().add(idEtudiant);
		tableviewEtudiant.getColumns().add(nomEtudiant);
		tableviewEtudiant.getColumns().add(prenomEtudiant);
		tableviewEtudiant.getColumns().add(nomFormation);
		tableviewEtudiant.getColumns().add(boutons);
		
		// Ajustement de la largeur des colonnes du tableau
		tableviewEtudiant.widthProperty().addListener((observable, oldValue, newValue) -> {
			double tableWidth = newValue.doubleValue();
			nomEtudiant.setPrefWidth((tableWidth - 40) / 4);
			prenomEtudiant.setPrefWidth((tableWidth - 40) / 4);
			nomFormation.setPrefWidth((tableWidth - 40) / 4);
			boutons.setPrefWidth((tableWidth - 40) / 4);
		});
		
		// Tentative de chargement des données des étudiants et rafraîchissement du tableau
		try {
			List<Etudiant> etudiants = etudiantService.selectAll();
			refreshTable(etudiants);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// Configuration des icônes des boutons
		Image refresh = new Image("/com/projet/img/refresh.png");
		ImageView refreshImageView = new ImageView(refresh);
		refreshImageView.setFitHeight(20);
		refreshImageView.setFitWidth(20);
		refreshEtudiant.setGraphic(refreshImageView);
		Image search = new Image("/com/projet/img/search.png");
		ImageView searchImageView = new ImageView(search);
		searchImageView.setFitWidth(20);
		searchImageView.setFitHeight(20);
		searchEtudiant.setGraphic(searchImageView);
		Image add = new Image("/com/projet/img/add.png");
		ImageView addImageView = new ImageView(add);
		addImageView.setFitWidth(18);
		addImageView.setFitHeight(18);
		toAjouterEtu.setGraphic(addImageView);
		
		
	}
	
	// Rafraîchissement des données du tableau
	public void refreshTable(List newData) {
		ObservableList<Etudiant> data = FXCollections.observableArrayList();
		data.addAll(newData);
		tableviewEtudiant.setItems(data);
	}
	
	
	// Navigation vers différentes vues
	public void toFormations(ActionEvent actionEvent) {
		Main.changeView("/com/projet/view/ShowFormation.fxml");
	}
	
	public void toAjouterEtudiant(ActionEvent actionEvent) {
		Main.addView("/com/projet/view/AddEtudiant.fxml");
	}
	
	// Recherche d'étudiants
	public void searchEtudiant(ActionEvent actionEvent) {
		try {
			Etudiant etudiant = new Etudiant();
			etudiant.setNomEtudiant("%" + textfieldNomEtudiant.getText().trim() + "%");
			etudiant.setPrenomEtudiant("%" + textfieldPrenomEtudiant.getText().trim() + "%");
			Formation formation = new Formation();
			formation.setNomFormation("%" + textfieldNomFormation.getText().trim() + "%");
			etudiant.setFormation(formation);
			List<Etudiant> etudiants = etudiantService.selectByCondition(etudiant);
			ObservableList<Etudiant> data = FXCollections.observableArrayList();
			data.addAll(etudiants);
			tableviewEtudiant.setItems(data);
			tableviewEtudiant.refresh();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// Rafraîchissement des données du tableau
	public void refreshTable(ActionEvent actionEvent) {
		
		try {
			List<Etudiant> etudiants = etudiantService.selectAll();
			ObservableList<Etudiant> data = FXCollections.observableArrayList();
			data.addAll(etudiants);
			tableviewEtudiant.setItems(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// Réinitialisation des champs de recherche
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
	
	// Exportation des données en PDF
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
	
	// Exportation des données en Excel
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
	
	public void addExcel(ActionEvent actionEvent) {
		// Créer un objet FileChooser
		FileChooser fileChooser = new FileChooser();
		
		// Configurer un filtre de fichier pour autoriser uniquement les fichiers Excel
		FileChooser.ExtensionFilter extFilter =
				new FileChooser.ExtensionFilter("Excel files (*.xlsx, *.xls)", "*.xlsx", "*.xls");
		fileChooser.getExtensionFilters().add(extFilter);
		
		
		Stage stage = (Stage) tableviewEtudiant.getScene().getWindow();
		
		// Afficher la boîte de dialogue d'ouverture de fichier
		File file = fileChooser.showOpenDialog(stage);
		
		// Vérifier si l'utilisateur a sélectionné un fichier
		if (file != null) {
			try {
				etudiantService.importEtudiants(file);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
