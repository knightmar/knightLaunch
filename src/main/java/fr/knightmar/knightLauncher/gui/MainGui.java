package fr.knightmar.knightLauncher.gui;

import fr.flowarg.flowupdater.download.Step;
import fr.knightmar.knightLauncher.Update;
import fr.knightmar.knightLauncher.Utils;
import fr.litarvan.openauth.microsoft.MicrosoftAuthenticator;
import fr.theshark34.openlauncherlib.minecraft.util.GameDirGenerator;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.nio.file.Path;

public class MainGui extends Application {
    public static String ms_access_token = "";

    public static String ms_refresh_token = "";
    public static Stage stage;
    public static Label status_label = new Label("statuts");
    public static Label file_label = new Label("file");
    public static Label percent_label = new Label("percent");
    public static ProgressBar progressBar = new ProgressBar();
    public static TextField pseudoField = new TextField();
    public static final Path launcherDir = GameDirGenerator.createGameDir("knightLauncher", true);
    public static Button launch_button = new Button("Launch");
    public static Button update_button = new Button("Update");
    public static ComboBox<String> choice_versions;

    public static CheckBox checkBox = new CheckBox(" or play in crack mod");

    public static Button login_button_ms = new Button("Login with Microsoft");

    public static void close() throws InterruptedException {
        System.out.println("Closing");
        Thread.sleep(10000);
        getStage().close();
    }


    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        setUI();

        Pane root = new Pane();
        root.setMinSize(1000, 600);
        root.setId("stack-pane");
        root.getChildren().addAll(launch_button, choice_versions, status_label, file_label, progressBar, percent_label, pseudoField, update_button, checkBox, login_button_ms);
        Scene scene = new Scene(root, 1000, 600);

        try {

            root.getStylesheets().add("css/main.css");
        } catch (Exception e) {

            e.printStackTrace();
        }

        stage.centerOnScreen();
        stage.setResizable(false);
        stage.setTitle("knightLauncher");
        stage.setScene(scene);
        stage.show();
    }


    public static void setStatusLabelWithSteps(Step step) throws InterruptedException {
        String text;
        switch (step) {
            case DL_LIBS -> text = "Téléchargement des librairies en cours ...";
            case END -> {
                text = "Téléchargement terminé !";
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

    public static void setStatusLabel(String text) {
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

    public static void setUI() {
        ObservableList<String> versions = FXCollections.observableArrayList();


        if (Path.of(launcherDir.toAbsolutePath() + "/" + "1.12.2").toFile().exists()) {
            versions.add("✅" + "1.12.2");
            System.out.println("1.12.2 existe");
        } else {
            versions.add("1.12.2");
            System.out.print("1.12.2 n'existe pas");
        }
        if (Path.of(launcherDir.toAbsolutePath() + "/" + "1.13.2").toFile().exists()) {
            versions.add("✅" + "1.13.2");
            System.out.println("1.13.2 existe");
        } else {
            versions.add("1.13.2");
            System.out.print("1.13.2 n'existe pas");
        }
        if (Path.of(launcherDir.toAbsolutePath() + "/" + "1.16.5").toFile().exists()) {
            versions.add("✅" + "1.16.5");
            System.out.println("1.16.5 existe");
        } else {
            versions.add("1.16.5");
            System.out.print("1.16.5 n'existe pas");
        }

        checkBox.setTranslateX(700);
        checkBox.setTranslateY(570);

        ImageView view = new ImageView(new Image("images/ms.png"));
        view.setFitHeight(30d);
        view.setPreserveRatio(true);
        login_button_ms.setGraphic(view);
        login_button_ms.setId("login_button_ms");
        login_button_ms.setTranslateX(670);
        login_button_ms.setTranslateY(510);
        login_button_ms.setOnAction(event -> {
            authenticateMS();
        });


        launch_button.setDisable(true);
        launch_button.setTranslateX(900 - launch_button.getWidth() - 10);
        launch_button.setTranslateY(400 - launch_button.getHeight() - 10);


        choice_versions = new ComboBox<>(versions);
        choice_versions.getSelectionModel().selectFirst();
        choice_versions.setId("comboBox");
        choice_versions.setTranslateX(530 - choice_versions.getWidth() - 10);
        choice_versions.setTranslateY(520 - choice_versions.getHeight() - 10);


        update_button = new Button("Update");
        update_button.setTranslateX(900 - update_button.getWidth() - 10);
        update_button.setTranslateY(300 - update_button.getHeight() - 10);
        update_button.setDisable(choice_versions.getValue().contains("✅"));


        progressBar.setMinSize(500, 20);
        progressBar.setProgress(0);
        progressBar.setTranslateX(20 - progressBar.getWidth() - 10);
        progressBar.setTranslateY(550 - progressBar.getHeight() - 10);
        progressBar.setId("progressBar");


        pseudoField.setPromptText("Pseudo");
        pseudoField.setMaxWidth(100);
        pseudoField.setTranslateX(605 - 70 - pseudoField.getWidth() - 10);
        pseudoField.setTranslateY(570 - pseudoField.getHeight() - 10);


        pseudoField.textProperty().addListener((observable, oldValue, newValue) ->
                launch_button.setDisable(!Utils.checkPseudo(newValue)));

        choice_versions.valueProperty().addListener((observable, oldValue, newValue) -> {
            update_button.setDisable(newValue.contains("✅"));
        });


        update_button.setOnAction(event -> Update.update(launcherDir, choice_versions.getValue(), pseudoField.getText(), false));

        launch_button.setOnAction(event -> {
            Update.update(launcherDir, choice_versions.getValue(), pseudoField.getText(), true);
            launch_button.setDisable(true);
            System.out.println(choice_versions.getValue());

        });


        percent_label.setTranslateX(270 - percent_label.getWidth() - 10);
        percent_label.setTranslateY(530 - percent_label.getHeight() - 10);

        status_label.setTranslateX(350 - status_label.getWidth() - 10);
        status_label.setTranslateY(580 - status_label.getHeight() - 10);

        file_label.setTranslateX(30 - file_label.getWidth() - 10);
        file_label.setTranslateY(580 - file_label.getHeight() - 10);
    }

    public static void authenticateMS() {
        Thread loginThread = new Thread(() -> {
            MicrosoftAuthenticator authenticator = new MicrosoftAuthenticator();
            authenticator.loginWithAsyncWebview().whenComplete((response, error) -> {
                if (error != null) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur");
                    alert.setContentText(error.getMessage());
                    alert.show();
                    return;
                }
                ms_access_token = response.getAccessToken();
                ms_refresh_token = response.getRefreshToken();
            });
        });
        loginThread.setName("LoginThread");
        loginThread.start();
    }
}


