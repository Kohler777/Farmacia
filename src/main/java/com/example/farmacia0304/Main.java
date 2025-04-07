package com.example.farmacia0304;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/farmacia0304/MedicamentoView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setTitle("Sistema de Estoque de Medicamentos");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
