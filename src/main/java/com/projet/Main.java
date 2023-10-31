package com.projet;

import com.projet.entity.Etudiant;
import com.projet.entity.Formation;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main extends Application {

    public static void main(String[] args) {
        Application.launch(args);

    }

    @Override
    public void start(Stage stage) throws Exception {

        stage.setTitle("Système de gestion de projets des étudiants");
        stage.getIcons().add(new Image("logo.png"));
        stage.setResizable(true);
        stage.initStyle(StageStyle.DECORATED);
        stage.show();
    }
}