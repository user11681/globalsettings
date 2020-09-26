package user11681.globalsettings.asm.mixin;

import java.io.File;
import net.minecraft.client.options.GameOptions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(GameOptions.class)
public interface GameOptionsAccess {
    @Accessor("optionsFile")
    void setOptionFile(File file);
}
