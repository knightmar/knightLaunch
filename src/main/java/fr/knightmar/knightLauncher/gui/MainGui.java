package fr.knightmar.knightLauncher.gui;

import fr.flowarg.flowupdater.download.Step;
import fr.knightmar.knightLauncher.Update;
import fr.knightmar.knightLauncher.Utils;
import fr.theshark34.openlauncherlib.minecraft.util.GameDirGenerator;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.nio.file.Path;

public class MainGui extends Application {
    public static Stage stage;
    public static Label status_label = new Label("statuts");
    public static Label file_label = new Label("file");
    public static Label percent_label = new Label("percent");
    public static ProgressBar progressBar = new ProgressBar();
    public static TextField pseudoField = new TextField();
    public static final Path launcherDir = GameDirGenerator.createGameDir("knightLauncher", true);

    public static void close() {
        getStage().close();
    }


    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        ObservableList<String> options = FXCollections.observableArrayList();


        if (Path.of(launcherDir.toAbsolutePath() + "/" + "1.12.2").toFile().exists()) {
            options.add("✅" + "1.12.2");
            System.out.println("1.12.2 existe");
        } else {
            options.add("1.12.2");
            System.out.print("1.12.2 n'existe pas");
        }
        if (Path.of(launcherDir.toAbsolutePath() + "/" + "1.13.2").toFile().exists()) {
            options.add("✅" + "1.13.2");
            System.out.println("1.13.2 existe");
        } else {
            options.add("1.13.2");
            System.out.print("1.13.2 n'existe pas");
        }
        if (Path.of(launcherDir.toAbsolutePath() + "/" + "1.16.5").toFile().exists()) {
            options.add("✅" + "1.16.5");
            System.out.println("1.16.5 existe");
        } else {
            options.add("1.16.5");
            System.out.print("1.16.5 n'existe pas");
        }

        System.out.println(options);


        Line line = new Line(0, 0, 100, 100);


        Button launch_button = new Button("Launch the game");
        launch_button.setDisable(true);
        Button update_button = new Button("update the current version");


        ComboBox<String> comboBox = new ComboBox<>(options);
        comboBox.getSelectionModel().selectFirst();
        comboBox.setId("comboBox");

        comboBox.setTranslateX(150);


        status_label.setTranslateY(120);
        status_label.setTranslateX(50);

        file_label.setTranslateY(180);
        file_label.setTranslateX(-100);

        percent_label.setTranslateY(120);
        percent_label.setTranslateX(-250);


        progressBar.setTranslateY(150);
        progressBar.setMinSize(400, 20);
        progressBar.setProgress(0);
        progressBar.setTranslateX(-90);
        setProgressBar(0.75);

        launch_button.setTranslateY(150);
        launch_button.setTranslateX(200);

        pseudoField.setPromptText("Pseudo");
        pseudoField.setMaxWidth(100);
        pseudoField.setTranslateX(200);
        pseudoField.setTranslateY(100);
        pseudoField.textProperty().addListener((observable, oldValue, newValue) ->
                launch_button.setDisable(!Utils.checkPseudo(newValue)));

        comboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            update_button.setDisable(newValue.contains("✅"));
        });


        update_button.setOnAction(event -> Update.update(launcherDir, comboBox.getValue(), pseudoField.getText(), false));

        launch_button.setOnAction(event -> {
            Update.update(launcherDir, comboBox.getValue(), pseudoField.getText(), true);
            launch_button.setDisable(true);

        });


        StackPane root = new StackPane();
        root.setMinSize(1000, 600);
        root.setId("stack-pane");
        root.getChildren().addAll(launch_button, comboBox, status_label, file_label, progressBar, percent_label, pseudoField, update_button);
        Scene scene = new Scene(root, 1000, 600);


        try {

            root.getStylesheets().add("css/main.css");
        } catch (Exception e) {

            e.printStackTrace();
        }


        stage.setResizable(false);
        stage.setTitle("knightLauncher");
        stage.setScene(scene);
        stage.show();
    }


    public static void setStatusLabel(Step step) {
        String text;
        switch (step) {
            case DL_LIBS -> text = "Téléchargement des librairies en cours ...";
            case END -> {
                text = "Fin des téléchargements";
            }

            case READ -> text = "Lecture des fichiers";
            case MODS -> text = "Téléchargement des mods en cours";
            case MOD_PACK -> text = "Téléchargement des modspacks";
            case DL_ASSETS -> text = "Téléchargement des assets";
            case MOD_LOADER -> text = "Téléchargement du Modloader";
            case INTEGRATION -> text = "Intégration en cours";
            case EXTERNAL_FILES -> text = "Téléchargement des fichiers ecternes";
            case EXTRACT_NATIVES -> text = "Extraction des natives";
            case POST_EXECUTIONS -> text = "étapes finales";
            default -> text = "étape inconnue";
        }
        status_label.setText(text);
    }

    public static void setFileLabel(String text) {
        file_label.setText("Fichier en cours : " + text);
    }

    public static void setPercentLabel(String text) {
        percent_label.setText(text);
    }

    public static void setProgressBar(Double value) {
        progressBar.setProgress(value);
    }

    public static Stage getStage() {
        return stage;
    }


    public static void main(String[] args) {
        launch(args);
    }


}


