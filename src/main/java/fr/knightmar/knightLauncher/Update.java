package fr.knightmar.knightLauncher;

import fr.flowarg.flowlogger.ILogger;
import fr.flowarg.flowupdater.FlowUpdater;
import fr.flowarg.flowupdater.download.DownloadList;
import fr.flowarg.flowupdater.download.IProgressCallback;
import fr.flowarg.flowupdater.download.Step;
import fr.flowarg.flowupdater.versions.VanillaVersion;
import fr.knightmar.knightLauncher.gui.MainGui;
import javafx.application.Platform;

import java.nio.file.Path;
import java.text.DecimalFormat;

public class Update {
    public static void update(Path launcherDir, String version, String pseudo, boolean launch) {
        String finalVersion;

        if (version.contains("1.12.2")) {
            finalVersion = "1.12.2";
        } else if (version.contains("1.13.2")) {
            finalVersion = "1.13.2";
        } else if (version.contains("1.16.5")) {
            finalVersion = "1.16.5";
        } else {
            finalVersion = "1.12.2";
        }
        launcherDir = Path.of(launcherDir.toAbsolutePath() + "/" + finalVersion);

        Path finalLauncherDir = launcherDir;


        Thread t = new Thread(() -> {


            System.out.println("finalVersion : " + finalVersion);


            VanillaVersion vanillaVersion = new VanillaVersion.VanillaVersionBuilder()
                    .withName(finalVersion)
                    .withSnapshot(false)
                    .build();


            IProgressCallback callback = new IProgressCallback() {
                private final DecimalFormat decimalFormat = new DecimalFormat("#.#");

                @Override
                public void init(ILogger logger) {
                }

                @Override
                public void step(Step step) {

                    Platform.runLater(() -> {
                        try {
                            MainGui.setStatusLabelWithSteps(step);
                            if ((step == Step.END && launch)) {
                                Platform.runLater(() -> {
                                    System.out.println("appel close");
                                    MainGui.setStatusLabel("Attentez le lancement du jeu ...");
                                    try {
                                        MainGui.close();
                                    } catch (InterruptedException e) {
                                        throw new RuntimeException(e);
                                    }
                                });
                            }
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    });
                }

                public void onFileDownloaded(Path path) {
                    Platform.runLater(() -> MainGui.setFileLabel(path.getFileName().toString()));
                }

                @Override
                public void update(DownloadList.DownloadInfo info) {
                    double progress = (double) info.getDownloadedBytes() / info.getTotalToDownloadBytes();

                    Platform.runLater(() -> {
                        MainGui.setPercentLabel(decimalFormat.format(progress * 100.0d) + "%");
                        MainGui.setProgressBar(progress);
                    });
                }
            };


            final FlowUpdater updater = new FlowUpdater.FlowUpdaterBuilder()
                    .withVanillaVersion(vanillaVersion)
                    .withProgressCallback(callback)
                    .build();

            try {
                updater.update(finalLauncherDir);
                if (launch) {
                    Launch.launch(finalVersion, pseudo);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        });
        t.start();
    }
}
