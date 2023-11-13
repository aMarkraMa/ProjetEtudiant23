package com.projet.controller;

import com.projet.Main;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;

public class MainController {
    private Button gestionEtudiant;
    private Button gestionFormation;
    private Button gestionProjet;
    private Button gestionBinome;
    private Button gestionNote;

    public void gestionEtudiant(ActionEvent actionEvent) {
        Main.changeView("/com/projet/view/ShowEtudiant.fxml");
    }

    public void gestionFormation(ActionEvent actionEvent) {
        Main.changeView("/com/projet/view/ShowFormation.fxml");
    }

    public void gestionProjet(ActionEvent actionEvent) {
        Main.changeView("/com/projet/view/ShowProjet.fxml");
    }

    public void gestionBinome(ActionEvent actionEvent) {
        Main.changeView("/com/projet/view/ShowBinome.fxml");
    }

    public void gestionNote(ActionEvent actionEvent) { Main.changeView("/com/projet/view/ShowNote.fxml");
    }
    
}


