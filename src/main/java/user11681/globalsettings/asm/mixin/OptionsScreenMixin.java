package user11681.globalsettings.asm.mixin;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.options.OptionsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import user11681.globalsettings.gui.GlobalSettingScreen;

@Mixin(OptionsScreen.class)
public class OptionsScreenMixin extends Screen {
    private OptionsScreenMixin() {
        super(LiteralText.EMPTY);
    }

    @SuppressWarnings("ConstantConditions")
    @Inject(method = "init",
            at = @At("RETURN"))
    public void addMenuButton(final CallbackInfo info) {
        this.addButton(new ButtonWidget(this.width - 70, this.height - 30, 50, 20, new TranslatableText("globalsettings.button"), (final ButtonWidget button) -> this.client.openScreen(new GlobalSettingScreen(this))));
    }
}
