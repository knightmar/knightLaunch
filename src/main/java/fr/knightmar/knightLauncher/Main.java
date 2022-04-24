package fr.knightmar.knightLauncher;

import fr.theshark34.openlauncherlib.minecraft.util.GameDirGenerator;

import java.nio.file.Path;

public class Main {

    public static void main(String[] args) {
        final Path launcherDir = GameDirGenerator.createGameDir("knightLauncher", true);
        Update.update(launcherDir, "1.12.2");
    }
}
