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
    private static final Stage anotherStage = new Stage();
    private static Pane root;
    private static Thread current_thread;

    public static AuthInfo login() throws InterruptedException {
        root = new Pane();
        Scene anotherScene = new Scene(root);
        anotherStage.setScene(anotherScene);
        anotherStage.show();
        current_thread = Thread.currentThread();
        current_thread.setName("current thread");
        Thread login_thread = new Thread(() -> {
            try {
                showLogin();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        login_thread.start();
        System.out.println(current_thread.getName());

        System.out.println(authInfo.getUsername());
        return authInfo;


    }

    private static void showLogin() throws InterruptedException {
        current_thread.join();
        JFXAuth.authenticateWithWebView(new JFXAuth.JFXAuthCallback() {

            @Override
            public void beforeAuth(WebView webView) {
                root.getChildren().add(webView);
            }

            @Override
            public void webViewCanBeClosed(WebView webView) {
                root.getChildren().remove(webView);
            }

            @Override
            public Consumer<AuthInfo> onAuthFinished() {
                // another actions
                return (info) -> authInfo = info;
            }

            @Override
            public void exceptionCaught(MCMSALException e) {
                e.printStackTrace();
            }

            @Override
            public double prefWidth() {
                return 405;
            }

            @Override
            public double prefHeight() {
                return 410;
            }
        });
    }
}
