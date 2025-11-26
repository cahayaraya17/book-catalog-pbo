package com.bookcatalog;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/views/main.fxml"));
        primaryStage.setTitle("Book Catalog App - PBO Project");  // Ganti "PRO" jadi "PBO"
        primaryStage.setScene(new Scene(root, 1000, 600));       // Ganti "000" jadi "600"
        primaryStage.show();
    }

    public static void main(String[] args) {  // Perbaiki "string" jadi "String"
        launch(args);
    }
}
