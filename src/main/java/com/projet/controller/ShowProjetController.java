package com.projet.controller;import com.itextpdf.text.Document;import com.itextpdf.text.pdf.PdfPTable;import com.itextpdf.text.pdf.PdfWriter;import com.projet.Main;import com.projet.entity.Binome;import com.projet.entity.Note;import com.projet.entity.Projet;import com.projet.mapper.BinomeMapper;import com.projet.mapper.NoteMapper;import com.projet.mapper.ProjetMapper;import com.projet.service.BinomeService;import com.projet.service.ProjetService;import com.projet.service.impl.BinomeServiceImpl;import com.projet.service.impl.ProjetServiceImpl;import com.projet.utils.MyBatisUtils;import javafx.beans.property.SimpleStringProperty;import javafx.beans.value.ObservableValue;import javafx.collections.FXCollections;import javafx.collections.ObservableList;import javafx.event.ActionEvent;import javafx.fxml.FXML;import javafx.fxml.Initializable;import javafx.scene.control.*;import javafx.scene.control.cell.PropertyValueFactory;import javafx.scene.control.cell.TextFieldTableCell;import javafx.scene.image.Image;import javafx.scene.image.ImageView;import javafx.scene.control.TextFormatter;import javafx.stage.FileChooser;import javafx.stage.Stage;import javafx.util.Callback;import javafx.util.converter.IntegerStringConverter;import org.apache.ibatis.session.SqlSession;import org.apache.poi.ss.usermodel.Cell;import org.apache.poi.ss.usermodel.Row;import org.apache.poi.ss.usermodel.Sheet;import org.apache.poi.ss.usermodel.Workbook;import org.apache.poi.xssf.usermodel.XSSFWorkbook;import java.io.File;import java.io.FileOutputStream;import java.net.URL;import java.time.LocalDate;import java.time.temporal.ChronoUnit;import java.util.List;import java.util.ResourceBundle;public class ShowProjetController {		@FXML	private TableColumn<Projet, String> nomMatiere;		@FXML	private TableColumn<Projet, String> sujet;		@FXML	private TableColumn<Projet, LocalDate> datePrevueRemise;		@FXML	private TableColumn<Projet, Integer> pourcentageSoutenance;		@FXML	private TableColumn<Projet, String> pourcentageRapport;		@FXML	private TableView<Projet> tableViewProjet;		@FXML	private Button refreshProjet;		@FXML	private Button addProjet;		@FXML	private Button searchProjet;		@FXML	private Button deleteProjet;		@FXML	private DatePicker datePicker;		@FXML	private TextField textFieldNomMatiere;		@FXML	private TextField textFieldSujet;		@FXML	private Label error;		private ProjetService projetService = new ProjetServiceImpl();		private BinomeService binomeService = new BinomeServiceImpl();		// Initialisation du contrôleur	@FXML	public void initialize() {				tableViewProjet.setEditable(true);				// Configuration des colonnes du tableau		nomMatiere.setCellValueFactory(new PropertyValueFactory<>("nomMatiere"));		sujet.setCellValueFactory(new PropertyValueFactory<>("sujet"));		datePrevueRemise.setCellValueFactory(new PropertyValueFactory<>("datePrevueRemise"));		pourcentageSoutenance.setCellValueFactory(new PropertyValueFactory<>("pourcentageSoutenance"));				nomMatiere.setCellFactory(TextFieldTableCell.forTableColumn());		sujet.setCellFactory(TextFieldTableCell.forTableColumn());		pourcentageRapport.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Projet, String>, ObservableValue<String>>() {			@Override			public ObservableValue<String> call(TableColumn.CellDataFeatures<Projet, String> projet) {				return new SimpleStringProperty(String.valueOf(100 - projet.getValue().getPourcentageSoutenance().intValue()));			}		});				pourcentageSoutenance.setCellFactory(column -> {			// Créer un nouveau TextFieldTableCell avec le convertisseur IntegerStringConverter personnalisé			return new TextFieldTableCell<Projet, Integer>(new IntegerStringConverter() {				@Override				public Integer fromString(String value) {					// Surcharge la méthode fromString pour qu'elle renvoie null si la conversion échoue.					try {						int intValue = Integer.parseInt(value);						return (intValue >= 0 && intValue <= 100) ? intValue : null;					} catch (NumberFormatException e) {						return null;					}				}			}) {				@Override				public void startEdit() {					super.startEdit();					// Obtenir le champ de texte dans la cellule actuelle.					TextField textField = (TextField) getGraphic();					// Définir le format de texte pour restreindre la saisie					textField.setTextFormatter(new TextFormatter<>(new IntegerStringConverter(), getItem(), c -> {						if (c.getControlNewText().isEmpty()) {							return c; // Les valeurs nulles sont autorisées et l'utilisateur peut effacer le texte pour saisir une nouvelle valeur.						}						try {							int value = Integer.parseInt(c.getControlNewText());							if (value >= 0 && value <= 100) { // Plage de valeurs de contrôle								return c;							}						} catch (NumberFormatException exception) {							exception.printStackTrace();						}						error.setVisible(true);						return null; // L'entrée n'est pas acceptée car elle n'est pas comprise entre 0 et 100 ou n'est pas un nombre.					}));				}			};		});				datePrevueRemise.setCellFactory(column -> new TableCell<Projet, LocalDate>() {			private final DatePicker datePicker = new DatePicker();						{				datePicker.setEditable(false);				datePicker.setOnAction(event -> {					if (datePicker.getValue() != null) {						commitEdit(datePicker.getValue());					} else {						cancelEdit();					}				});			}						@Override			protected void updateItem(LocalDate item, boolean empty) {				super.updateItem(item, empty);				if (empty) {					setText(null);					setGraphic(null);				} else {					// Si la ligne courante est celle qui est en cours d'édition, le sélecteur de date est affiché.					if (isEditing()) {						datePicker.setValue(item);						setGraphic(datePicker);						System.out.println("id editing");					} else {						setText(getItem() == null ? "" : getItem().toString());						setGraphic(null);					}				}			}						@Override			public void startEdit() {				super.startEdit();				if (!isEmpty()) {					datePicker.setValue(getItem());					setGraphic(datePicker);					setText(null);				}				System.out.println("start edit");			}						@Override			public void cancelEdit() {				super.cancelEdit();				setText(getItem().toString());				setGraphic(null);				System.out.println("cancel edit");			}						// Valider les modifications lorsque la valeur du DatePicker change			{				datePicker.valueProperty().addListener((observable, oldValue, newValue) -> {					if (isEditing()) {						commitEdit(newValue);					}				});			}						@Override			public void commitEdit(LocalDate newValue) {				super.commitEdit(newValue);				Projet projet = getTableView().getItems().get(getIndex());				// Date de mise à jour du projet				projet.setDatePrevueRemise(newValue);				// Mise à jour de la base de données et rafraîchissement du TableView à l'aide de méthodes définies en externe				updateProjet(projet);				System.out.println("commit edit");								setText(newValue.toString());				setGraphic(null);			}					});				initializeImg();				nomMatiere.setOnEditCommit(				(TableColumn.CellEditEvent<Projet, String> t) -> {					Projet projet = t.getTableView().getItems().get(t.getTablePosition().getRow());					projet.setNomMatiere(t.getNewValue());					// mise à jour BDD					updateProjet(projet);				});				sujet.setOnEditCommit(				(TableColumn.CellEditEvent<Projet, String> t) -> {					Projet projet = t.getTableView().getItems().get(t.getTablePosition().getRow());					projet.setSujet(t.getNewValue());					// mise à jour BDD					updateProjet(projet);				});						pourcentageSoutenance.setOnEditCommit(				(TableColumn.CellEditEvent<Projet, Integer> t) -> {					Projet projet = t.getTableView().getItems().get(t.getTablePosition().getRow());					projet.setPourcentageSoutenance(t.getNewValue());					updateProjet(projet);				});						tableViewProjet.widthProperty().addListener((observable, oldValue, newValue) -> {			double tableWidth = newValue.doubleValue();			nomMatiere.setPrefWidth(tableWidth / 5);			sujet.setPrefWidth(tableWidth / 5);			datePrevueRemise.setPrefWidth(tableWidth / 5);			pourcentageSoutenance.setPrefWidth(tableWidth / 5);			pourcentageRapport.setPrefWidth(tableWidth / 5);		});				// Chargement et affichage des données des projets		refreshTable();	}		// Initialisation des icônes des boutons	public void initializeImg() {		Image search = new Image("/com/projet/img/search.png");		ImageView searchImageView = new ImageView(search);		searchImageView.setFitWidth(18);		searchImageView.setFitHeight(18);		searchProjet.setGraphic(searchImageView);				Image add = new Image("/com/projet/img/add.png");		ImageView addImageView = new ImageView(add);		addImageView.setFitWidth(18);		addImageView.setFitHeight(18);		addProjet.setGraphic(addImageView);				Image delete = new Image("/com/projet/img/delete.png");		ImageView deleteImageView = new ImageView(delete);		deleteImageView.setFitWidth(18);		deleteImageView.setFitHeight(18);		deleteProjet.setGraphic(deleteImageView);				Image refresh = new Image("/com/projet/img/refresh.png");		ImageView refreshImageView = new ImageView(refresh);		refreshImageView.setFitWidth(18);		refreshImageView.setFitHeight(18);		refreshProjet.setGraphic(refreshImageView);	}		// Recherche des projets	public void searchProjet(ActionEvent actionEvent) {		try {			Projet projet = new Projet();			projet.setNomMatiere("%" + textFieldNomMatiere.getText().trim() + "%");			projet.setSujet("%" + textFieldSujet.getText().trim() + "%");			projet.setDatePrevueRemise(datePicker.getValue());			List<Projet> projets = projetService.selectByCondition(projet);			ObservableList<Projet> data = FXCollections.observableArrayList();			data.addAll(projets);			tableViewProjet.setItems(data);			tableViewProjet.refresh();		} catch (Exception e) {			e.printStackTrace();		}	}		// Affichage de la vue pour ajouter un projet	public void showAddView() {		Main.addView("/com/projet/view/AddProjet.fxml");	}		// Mise à jour d'un projet	public void updateProjet(Projet projet) {		try {			projetService.updateProjet(projet);			refreshTable();		} catch (Exception e) {			showErr("mise à jour projet non réussi");		}					}		// Suppression d'un projet	public void deleteProjet(ActionEvent actionEvent) {				Projet projet = tableViewProjet.getSelectionModel().getSelectedItem();		if (projet == null) {			showErr("Pas de ligne selectioné");		} else {			List<Binome> binomes = binomeService.getBinomesByIdProjet(projet.getIdProjet());			if (binomes.size() > 0) {				showErr("Avant de supprimer le projet, veuillez supprimer tous les binomes faisant ce projet.");				return;			}			projetService.deleteById(projet.getIdProjet());		}						refreshTable();	}		// Rafraîchissement des données du tableau	public void refreshTable() {		try {			List<Projet> projets = projetService.selectAll();			ObservableList<Projet> data = FXCollections.observableArrayList();			data.addAll(projets);						tableViewProjet.setItems(data);			tableViewProjet.refresh();		} catch (Exception e) {			e.printStackTrace();		}	}		public void refreshTable(ActionEvent actionEvent) {		refreshTable();	}		// Affichage d'une erreur	public void showErr(String msg) {		Alert alert = new Alert(Alert.AlertType.ERROR);		alert.setTitle("Error Dialog");		alert.setHeaderText("Oups");		alert.setContentText(msg);				alert.showAndWait();	}		// Navigation vers différentes vues	public void toShowFormation(ActionEvent actionEvent) {		Main.changeView("/com/projet/view/ShowFormation.fxml");	}		public void toShowEtudiant(ActionEvent actionEvent) {		Main.changeView("/com/projet/view/ShowEtudiant.fxml");	}		public void toShowProjet(ActionEvent actionEvent) {		Main.changeView("/com/projet/view/ShowProjet.fxml");	}		public void toShowBinome(ActionEvent actionEvent) {		Main.changeView("/com/projet/view/ShowBinome.fxml");	}		public void toShowNote(ActionEvent actionEvent) {		Main.changeView("/com/projet/view/ShowNote.fxml");	}		// Exportation des données en PDF	public void toPDF(ActionEvent actionEvent) {		FileChooser fileChooser = new FileChooser();		fileChooser.setTitle("Save as PDF");		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));				Stage stage = (Stage) tableViewProjet.getScene().getWindow();		File file = fileChooser.showSaveDialog(stage);				if (file != null) {			Document document = new Document();			try {				PdfWriter.getInstance(document, new FileOutputStream(file));				document.open();								PdfPTable pdfTable = new PdfPTable(tableViewProjet.getColumns().size() - 1);								for (int i = 0; i < tableViewProjet.getColumns().size() - 1; i++) {					TableColumn<?, ?> col = (TableColumn<?, ?>) tableViewProjet.getColumns().get(i);					pdfTable.addCell(col.getText());				}								for (int i = 0; i < tableViewProjet.getItems().size(); i++) {					for (int j = 0; j < tableViewProjet.getColumns().size() - 1; j++) {						Object cellData = tableViewProjet.getColumns().get(j).getCellData(tableViewProjet.getItems().get(i));						if (cellData != null) {							pdfTable.addCell(cellData.toString());						} else {							pdfTable.addCell("");						}					}				}								document.add(pdfTable);								document.close();			} catch (Exception e) {				e.printStackTrace();			}		}	}		// Exportation des données en Excel	public void toExcel(ActionEvent actionEvent) {		Stage stage = (Stage) tableViewProjet.getScene().getWindow();				FileChooser fileChooser = new FileChooser();		fileChooser.setTitle("Save as Excel");		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel Files", "*.xlsx"));		File file = fileChooser.showSaveDialog(stage);				if (file != null) {			try (Workbook workbook = new XSSFWorkbook(); FileOutputStream fileOut = new FileOutputStream(file)) {				Sheet sheet = workbook.createSheet("Data");								Row headerRow = sheet.createRow(0);				for (int i = 0; i < tableViewProjet.getColumns().size() - 1; i++) {					org.apache.poi.ss.usermodel.Cell headerCell = headerRow.createCell(i);					headerCell.setCellValue(tableViewProjet.getColumns().get(i).getText());				}								for (int rowIndex = 0; rowIndex < tableViewProjet.getItems().size(); rowIndex++) {					Row dataRow = sheet.createRow(rowIndex + 1);					for (int colIndex = 0; colIndex < tableViewProjet.getColumns().size() - 1; colIndex++) {						Cell cell = dataRow.createCell(colIndex);						Object cellValue = ((TableColumn<?, ?>) tableViewProjet.getColumns().get(colIndex)).getCellData(rowIndex);						if (cellValue != null) {							cell.setCellValue(cellValue.toString());						}					}				}								for (int i = 0; i < tableViewProjet.getColumns().size() - 1; i++) {					sheet.autoSizeColumn(i);				}								workbook.write(fileOut);			} catch (Exception e) {				e.printStackTrace();			}		}	}}