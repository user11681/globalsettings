package user11681.globalsettings.config;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import org.apache.commons.io.IOUtils;
import user11681.globalsettings.asm.mixin.GameOptionsAccess;

public class GlobalSettingsConfiguration {
    private static transient final File configurationFile = FabricLoader.getInstance().getConfigDir().resolve("globalsettings.txt").toFile();

    private static transient File file;

    public static File getFile() {
        return file;
    }

    public static void setPath(final String path) {
        file = new File(path);

        write();

        ((GameOptionsAccess) MinecraftClient.getInstance().options).setOptionFile(file);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void ensureFileExists() {
        file.getParentFile().mkdirs();

        try {
            file.createNewFile();
        } catch (final IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    private static void write() {
        try {
            IOUtils.write(file.getPath().getBytes(StandardCharsets.UTF_8), new FileOutputStream(configurationFile));
        } catch (final IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    static {
        try {
            if (configurationFile.exists()) {
                file = new File(new String(IOUtils.toByteArray(configurationFile.toURI())));
            } else {
                file = new File(System.getProperty("user.home"), ".config/minecraft/options.txt");

                write();
            }

            ensureFileExists();
        } catch (final Throwable throwable) {
            throw new RuntimeException(throwable);
        }
    }
}
