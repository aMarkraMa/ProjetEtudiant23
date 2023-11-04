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
        Main.changeView("/com/projet/ShowEtudiant.fxml");
    }

    public void gestionFormation(ActionEvent actionEvent) {
        Main.changeView("/com/projet/ShowFormation.fxml");
    }

    public void gestionProjet(ActionEvent actionEvent) {
    }

    public void gestionBinome(ActionEvent actionEvent) {
    }

    public void gestionNote(ActionEvent actionEvent) {
    }


//    public void

}
