package com.projet;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main extends Application {

    private static Stage stage;
    
    // Point d'entrée principal de l'application JavaFX
    public static void main(String[] args) {
        Application.launch(args);
    }
    
    // Démarre l'interface utilisateur principale de l'application
    @Override
    public void start(Stage stage) throws Exception {
        Main.stage = stage;
        stage.setTitle("Système de gestion de projets des étudiants"); // Titre de la fenêtre
        stage.getIcons().add(new Image("/com/projet/img/logo.png")); // Icône de la fenêtre
        stage.setResizable(true); // Permet de redimensionner la fenêtre
        stage.initStyle(StageStyle.DECORATED); // Style de la fenêtre
        stage.setWidth(1200);
        stage.setHeight(600);
        stage.setMinWidth(1200); // Largeur minimale
        stage.setMinHeight(600); // Hauteur minimale
        // startView("/com/projet/view/Main.fxml");
        startView("/com/projet/view/LogIn.fxml"); // Vue initiale
        stage.show(); // Affiche la fenêtre
    }
    
    // Démarre une vue spécifique
    public static void startView(String fxml){
        Parent root = null;
        try{
            root = FXMLLoader.load(Main.class.getResource(fxml));
            Scene scene = new Scene(root);
            scene.getStylesheets().add(Main.class.getResource("/flat-style.css").toExternalForm());
            stage.setScene(scene);
            stage.setResizable(false); // Fenêtre non redimensionnable
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
    
    // Change la vue actuelle
    public static void changeView(String fxml){
        Parent root = null;
        try{
            root = FXMLLoader.load(Main.class.getResource(fxml));
            Scene scene = new Scene(root);
            scene.getStylesheets().add(Main.class.getResource("/flat-style.css").toExternalForm());
            
            stage.setScene(scene);
            stage.setResizable(true); // Fenêtre redimensionnable
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
    
    // Ajoute une nouvelle vue dans une nouvelle fenêtre
    public static void addView(String fxml){
        Stage stage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(Main.class.getResource(fxml));
            Scene scene = new Scene(root);
            scene.getStylesheets().add(Main.class.getResource("/flat-style.css").toExternalForm());

            stage.setScene(scene);
            stage.setResizable(false); // Fenêtre non redimensionnable
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.show(); // Affiche la nouvelle fenêtre
    }
}