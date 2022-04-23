package fr.knightmar.knightLauncher.gui;

import fr.knightmar.knightLauncher.Launch;
import fr.knightmar.knightLauncher.Update;
import fr.theshark34.openlauncherlib.LaunchException;
import fr.theshark34.openlauncherlib.minecraft.util.GameDirGenerator;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.nio.file.Path;

public class MainGui extends Application {
    public static Label status_label = new Label("statuts");
    public static Label file_label = new Label("file");
    public static Label percent_label = new Label("percent");
    public static ProgressBar progressBar = new ProgressBar();




    @Override
    public void start(Stage primaryStage) throws Exception {
        ObservableList<String> options =
                FXCollections.observableArrayList(
                        "1.12.2",
                        "1.13.2",
                        "1.16.5"
                );


        Button btn1 = new Button("Launch the game");
        ComboBox comboBox = new ComboBox(options);
        status_label.setTranslateY(-100);
        file_label.setTranslateY(-50);
        percent_label.setTranslateY(100);
        progressBar.setTranslateY(150);

        progressBar.setMinSize(300, 10);
        progressBar.setProgress(0);
        setProgressBar(0.75);

        btn1.setTranslateY(50);






        comboBox.setValue(options.get(0));

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
        root.getChildren().addAll(btn1, comboBox, status_label, file_label, progressBar, percent_label);
        Scene scene = new Scene(root, 600, 400);
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


