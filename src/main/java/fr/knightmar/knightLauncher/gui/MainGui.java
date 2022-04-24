package fr.knightmar.knightLauncher.gui;

import fr.knightmar.knightLauncher.Update;
import fr.theshark34.openlauncherlib.minecraft.util.GameDirGenerator;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;

public class MainGui extends Application {
    public static Label status_label = new Label("statuts");
    public static Label file_label = new Label("file");
    public static Label percent_label = new Label("percent");
    public static ProgressBar progressBar = new ProgressBar();
    public static TextField pseudoField = new TextField("Pseudo");


    @Override
    public void start(Stage primaryStage) throws Exception {
        ObservableList<String> options =
                FXCollections.observableArrayList(
                        "1.12.2",
                        "1.13.2",
                        "1.16.5"
                );

        Line line = new Line(0, 0, 100, 100);
        Button btn1 = new Button("Launch the game");
        ComboBox comboBox = new ComboBox(options);
        status_label.setTranslateY(120);
        status_label.setTranslateX(50);

        file_label.setTranslateY(120);
        file_label.setTranslateX(-90);

        percent_label.setTranslateY(120);
        percent_label.setTranslateX(-250);



        progressBar.setTranslateY(150);
        progressBar.setMinSize(400, 20);
        progressBar.setProgress(0);
        progressBar.setTranslateX(-90);
        setProgressBar(0.75);

        btn1.setTranslateY(150);
        btn1.setTranslateX(200);


        comboBox.setValue(options.get(0));


        pseudoField.setOnAction(arg -> {

        });

        btn1.setOnAction(arg0 -> {
            // TODO Auto-generated method stub
            final Path launcherDir = GameDirGenerator.createGameDir("knightLauncher", true);
            try {
                Update.update(launcherDir, comboBox.getValue().toString());

            } catch (Exception e) {
                e.printStackTrace();
            }

            btn1.setDisable(true);

        });
        StackPane root = new StackPane();
        root.setMinSize(600,400);
        root.autosize();
        root.getChildren().addAll(btn1, comboBox, status_label, file_label, progressBar, percent_label, line);
        Scene scene = new Scene(root, 600, 400);


        try {

            root.getStylesheets().add("css/main.css");
        } catch (Exception e) {

            e.printStackTrace();
        }


        primaryStage.setResizable(false);
        primaryStage.setTitle("knightLauncher");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void setStatusLabel(String text) {
        status_label.setText(text);
    }

    public static void setFileLabel(String text) {
        file_label.setText(text);
    }

    public static void setPercentLabel(String text) {
        percent_label.setText(text);
    }

    public static void setProgressBar(Double value) {
        progressBar.setProgress(value);
    }


    public static void main(String[] args) {
        launch(args);
    }


}


