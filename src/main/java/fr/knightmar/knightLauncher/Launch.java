package fr.knightmar.knightLauncher;

import fr.theshark34.openlauncherlib.LaunchException;
import fr.theshark34.openlauncherlib.external.ExternalLaunchProfile;
import fr.theshark34.openlauncherlib.external.ExternalLauncher;
import fr.theshark34.openlauncherlib.minecraft.*;

import java.util.Objects;

public class Launch {
    public static void launch(String version, String pseudo) throws LaunchException {
        if (Objects.equals(version, "1.12.2") || Objects.equals(version, "1.13.2") || Objects.equals(version, "1.16.5")) {

            GameInfos infos = new GameInfos("knightLauncher" + "/" + version, new GameVersion(version, GameType.V1_8_HIGHER), new GameTweak[]{});
            AuthInfos authInfos = new AuthInfos(pseudo, "", " ");

            ExternalLaunchProfile profile = MinecraftLauncher.createExternalProfile(infos, GameFolder.FLOW_UPDATER, authInfos);
            ExternalLauncher launcher = new ExternalLauncher(profile);

            launcher.launch();
        }
    }
}
