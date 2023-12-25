package io.github.zekerzhayard.forgewrapper.installer.detector;

import java.nio.file.Path;
import java.util.HashMap;

public class ColorMCFileDetector implements IFileDetector {
    protected Path libraryDir = null;
    protected Path installerJar = null;
    protected Path minecraftJar = null;

    @Override
    public String name() {
        return "ColorMC";
    }

    @Override
    public boolean enabled(HashMap<String, IFileDetector> others) {
        return others.isEmpty();
    }

    @Override
    public Path getLibraryDir() {
        if (this.libraryDir == null) {
            this.libraryDir = IFileDetector.super.getLibraryDir();
        }
        return this.libraryDir;
    }

    @Override
    public Path getInstallerJar(String forgeGroup, String forgeArtifact, String forgeFullVersion) {
        Path path = IFileDetector.super.getInstallerJar(forgeGroup, forgeArtifact, forgeFullVersion);
        if (path == null) {
            if (this.installerJar == null) {
                Path installerBase = this.getLibraryDir();
                for (String dir : forgeGroup.split("\\."))
                    installerBase = installerBase.resolve(dir);
                this.installerJar = installerBase.resolve(forgeArtifact).resolve(forgeFullVersion).resolve(forgeArtifact + "-" + forgeFullVersion + "-installer.jar").toAbsolutePath();
            }
            return this.installerJar;
        }
        return path;
    }

    @Override
    public Path getMinecraftJar(String mcVersion) {
        Path path = IFileDetector.super.getMinecraftJar(mcVersion);
        if (path == null) {
            return this.minecraftJar != null ? this.minecraftJar : (this.minecraftJar = this.getLibraryDir().resolve("net").resolve("minecraft").resolve("client").resolve(mcVersion).resolve("client-" + mcVersion + ".jar").toAbsolutePath());
        }
        return path;
    }
}
