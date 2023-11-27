package com.projet.controller;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.projet.Main;
import com.projet.entity.Binome;
import com.projet.entity.Projet;
import com.projet.service.BinomeService;
import com.projet.service.ProjetService;
import com.projet.service.impl.BinomeServiceImpl;
import com.projet.service.impl.ProjetServiceImpl;
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
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDate;
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
	
	private ProjetService projetService = new ProjetServiceImpl();
	
	private BinomeService binomeService = new BinomeServiceImpl();
	
	
	@FXML
	public void initialize() {
	
		try {
			List<Integer> idsProjet = projetService.getIdsProjet();
			ObservableList<Binome> data = FXCollections.observableArrayList();
			for (Integer idProjet : idsProjet) {
				List<Binome> binomes = binomeService.getBinomesByIdProjet(idProjet);
				binomes.forEach(System.out::println);
				data.addAll(binomes);
			}
			tableviewBinome.setItems(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
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
				if (binome.getValue().getNoteRapport() == -1.0) {
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
							try {
								Binome binome = getTableView().getItems().get(getIndex());
								BinomeServiceImpl.binomeToUpdate = binomeService.selectByIdBinomeAndIdProjet(binome);
								Main.addView("/com/projet/view/UpdateBinome.fxml");
							} catch (Exception e) {
								e.printStackTrace();
							}
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
								try {
									if (binome.getEtudiants().size() > 1) {
										binomeService.deleteBinome(idBinome, idProjet, binome.getEtudiants().get(0).getIdEtudiant(), binome.getEtudiants().get(1).getIdEtudiant());
									} else {
										binomeService.deleteBinome(idBinome, idProjet, binome.getEtudiants().get(0).getIdEtudiant(), null);
									}
									List<Integer> idsProjet = projetService.getIdsProjet();
									ObservableList<Binome> data = FXCollections.observableArrayList();
									for (Integer idProjetDB : idsProjet) {
										List<Binome> binomes = binomeService.getBinomesByIdProjet(idProjetDB);
										binomes.forEach(System.out::println);
										data.addAll(binomes);
									}
									tableviewBinome.setItems(data);
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
		
		tableviewBinome.getColumns().add(idBinomes);
		tableviewBinome.getColumns().add(nomMatiere);
		tableviewBinome.getColumns().add(sujet);
		tableviewBinome.getColumns().add(etudiant1);
		tableviewBinome.getColumns().add(etudiant2);
		tableviewBinome.getColumns().add(noteRapport);
		tableviewBinome.getColumns().add(dateRelleRemise);
		tableviewBinome.getColumns().add(boutons);
		
		tableviewBinome.widthProperty().addListener((observable, oldValue, newValue) -> {
			double tableWidth = newValue.doubleValue();
			nomMatiere.setPrefWidth((tableWidth - 40) / 7);
			sujet.setPrefWidth((tableWidth - 40) / 7);
			etudiant1.setPrefWidth((tableWidth - 40) / 7);
			etudiant2.setPrefWidth((tableWidth - 40) / 7);
			noteRapport.setPrefWidth((tableWidth - 40) / 7);
			dateRelleRemise.setPrefWidth((tableWidth - 40) / 7);
			boutons.setPrefWidth((tableWidth - 40) / 7);
		});
		
		
		
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
	
	
	
	public void toFormations(ActionEvent actionEvent) {
		Main.changeView("/com/projet/view/ShowFormation.fxml");
	}
	
	public void toAjouterBinome(ActionEvent actionEvent) {
		Main.addView("/com/projet/view/AddBinome.fxml");
	}
	
	public void searchBinome(ActionEvent actionEvent) {
		
		try {
			Binome binome = new Binome();
			Projet projet = new Projet();
			projet.setNomMatiere("%" + textfieldNomMatiere.getText() + "%");
			projet.setSujet("%" + textfieldSujet.getText() + "%");
			binome.setProjet(projet);
			List<Binome> binomeDB = binomeService.selectByCondition(binome);
			ObservableList<Binome> data = FXCollections.observableArrayList();
			data.addAll(binomeDB);
			tableviewBinome.setItems(data);
			tableviewBinome.refresh();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void refreshTable(ActionEvent actionEvent) {
		
		try {
			List<Integer> idsProjet = projetService.getIdsProjet();
			ObservableList<Binome> data = FXCollections.observableArrayList();
			for (Integer idProjet : idsProjet) {
				List<Binome> binomes = binomeService.getBinomesByIdProjet(idProjet);
				binomes.forEach(System.out::println);
				data.addAll(binomes);
			}
			tableviewBinome.setItems(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
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
		Main.changeView("/com/projet/view/ShowNote.fxml");
		
	}
	
	public void toPDF(ActionEvent actionEvent) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save as PDF");
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
		
		Stage stage = (Stage) tableviewBinome.getScene().getWindow();
		File file = fileChooser.showSaveDialog(stage);
		
		if (file != null) {
			Document document = new Document();
			try {
				PdfWriter.getInstance(document, new FileOutputStream(file));
				document.open();
				
				PdfPTable pdfTable = new PdfPTable(tableviewBinome.getColumns().size() - 1);
				
				for (int i = 0; i < tableviewBinome.getColumns().size() - 1; i++) {
					TableColumn<?, ?> col = (TableColumn<?, ?>) tableviewBinome.getColumns().get(i);
					pdfTable.addCell(col.getText());
				}
				
				for (int i = 0; i < tableviewBinome.getItems().size(); i++) {
					for (int j = 0; j < tableviewBinome.getColumns().size() - 1; j++) {
						Object cellData = tableviewBinome.getColumns().get(j).getCellData(tableviewBinome.getItems().get(i));
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
		Stage stage = (Stage) tableviewBinome.getScene().getWindow();
		
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save as Excel");
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel Files", "*.xlsx"));
		File file = fileChooser.showSaveDialog(stage);
		
		if (file != null) {
			try (Workbook workbook = new XSSFWorkbook(); FileOutputStream fileOut = new FileOutputStream(file)) {
				Sheet sheet = workbook.createSheet("Data");
				
				Row headerRow = sheet.createRow(0);
				for (int i = 0; i < tableviewBinome.getColumns().size() - 1; i++) {
					org.apache.poi.ss.usermodel.Cell headerCell = headerRow.createCell(i);
					headerCell.setCellValue(tableviewBinome.getColumns().get(i).getText());
				}
				
				for (int rowIndex = 0; rowIndex < tableviewBinome.getItems().size(); rowIndex++) {
					Row dataRow = sheet.createRow(rowIndex + 1);
					for (int colIndex = 0; colIndex < tableviewBinome.getColumns().size() - 1; colIndex++) {
						Cell cell = dataRow.createCell(colIndex);
						Object cellValue = ((TableColumn<?, ?>) tableviewBinome.getColumns().get(colIndex)).getCellData(rowIndex);
						if (cellValue != null) {
							cell.setCellValue(cellValue.toString());
						}
					}
				}
				
				for (int i = 0; i < tableviewBinome.getColumns().size() - 1; i++) {
					sheet.autoSizeColumn(i);
				}
				
				workbook.write(fileOut);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
}
