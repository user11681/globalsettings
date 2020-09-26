package user11681.globalsettings.asm.mixin;

import java.io.File;
import net.minecraft.client.options.GameOptions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import user11681.globalsettings.config.GlobalSettingsConfiguration;

@Mixin(GameOptions.class)
abstract class GameOptionsMixin {
    @Redirect(method = "<init>",
              at = @At(value = "NEW",
                       target = "java/io/File"))
    public File useCustomOptions(final File parent, final String child) {
        return GlobalSettingsConfiguration.getFile();
    }

    static {
        try {
            Class.forName(GlobalSettingsConfiguration.class.getName());
        } catch (final Throwable throwable) {
            throw new RuntimeException(throwable);
        }
    }
}
