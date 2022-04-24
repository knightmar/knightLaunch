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
import java.util.Objects;

public class Update {
    public static void update(Path launcherDir, String version) {

        Thread t = new Thread(() -> {
            if (Objects.equals(version, "1.12.2") || Objects.equals(version, "1.13.2") || Objects.equals(version, "1.16.5")) {
                VanillaVersion vanillaVersion = new VanillaVersion.VanillaVersionBuilder()
                        .withName(version)
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
                            MainGui.setStatusLabel(String.valueOf(step));
                        });
                    }

                    public void onFileDownloaded(Path path) {
                        Platform.runLater(() -> {
                            MainGui.setFileLabel(path.getFileName().toString());
                        });
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
                    updater.update(launcherDir);
                    Launch.launch(version);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
    }
}
