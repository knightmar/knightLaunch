package fr.knightmar.knightLauncher;

import fr.flowarg.mcmsal.AuthInfo;
import fr.knightmar.knightLauncher.gui.LoginMSWebview;

public class Tests {
    public static void main(String[] args) throws InterruptedException {
        AuthInfo authInfo = LoginMSWebview.login();
        if (authInfo != null){
            System.out.println(authInfo.getUsername());

        }else {
            System.out.print("Connection interompue");
        }
    }
}
