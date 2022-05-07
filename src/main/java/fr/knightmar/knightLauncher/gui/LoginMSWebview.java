package fr.knightmar.knightLauncher.gui;

import fr.flowarg.mcmsal.AuthInfo;
import fr.flowarg.mcmsal.JFXAuth;
import fr.flowarg.mcmsal.MCMSALException;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.util.function.Consumer;

public class LoginMSWebview {
    public static AuthInfo authInfo;
    private static final Stage login_stage = new Stage();
    private static Pane root;
    private static JFXAuth.JFXAuthCallback callback;


    public static void login() {
        root = new Pane();
        Scene anotherScene = new Scene(root, 600, 600);
        root.setMaxSize(600, 600);
        login_stage.setScene(anotherScene);
        login_stage.show();

        try {
            showLogin();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void showLogin() throws InterruptedException {
        System.out.println("showLogin");
        callback = new JFXAuth.JFXAuthCallback() {

            @Override
            public void beforeAuth(WebView webView) {
                root.getChildren().add(webView);
            }

            @Override
            public void webViewCanBeClosed(WebView webView) {
                root.getChildren().remove(webView);
                login_stage.close();
            }

            @Override
            public Consumer<AuthInfo> onAuthFinished() {
                return (info) -> {
                    authInfo = info;
                    MainGui.setAuthInfo(authInfo);
                    MainGui.setFocus();
                };
            }

            @Override
            public void exceptionCaught(MCMSALException e) {
                e.printStackTrace();
            }

            @Override
            public double prefWidth() {
                return 600;
            }

            @Override
            public double prefHeight() {
                return 600;
            }
        };
        JFXAuth.authenticateWithWebView(callback);
    }
}
