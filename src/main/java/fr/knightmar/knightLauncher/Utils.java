package fr.knightmar.knightLauncher;

public class Utils {
    public static boolean checkPseudo(String pseudo) {
        return pseudo.length() > 3 && !pseudo.contains(" ") && pseudo.length() < 15;

    }
}
