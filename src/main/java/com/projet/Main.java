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

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Main.stage = stage;
       // FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("ShowEtudiant.fxml"));
        //Scene scene = new Scene(fxmlLoader.load(), 600, 420);
        stage.setTitle("Système de gestion de projets des étudiants");
       // stage.setScene(scene);
        stage.getIcons().add(new Image("com/projet/img/logo.png"));
        stage.setResizable(true);
        stage.initStyle(StageStyle.DECORATED);
        stage.setMinWidth(600);
        stage.setMinHeight(400);
        changeView("/com/projet/view/Main.fxml");
        //startView("/com/projet/view/LogIn.fxml");
        stage.show();
    }
    
    public static void startView(String fxml){
        Parent root = null;
        try{
            root = FXMLLoader.load(Main.class.getResource(fxml));
            stage.setScene(new Scene(root));
            stage.setResizable(false);
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public static void changeView(String fxml){
        Parent root = null;
        try{
            root = FXMLLoader.load(Main.class.getResource(fxml));
            stage.setScene(new Scene(root));
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public static void addView(String fxml){
        Stage stage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(Main.class.getResource(fxml));
            stage.setScene(new Scene(root));
            stage.setResizable(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.show();
    }
}