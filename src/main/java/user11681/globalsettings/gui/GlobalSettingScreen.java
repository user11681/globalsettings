package user11681.globalsettings.gui;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.options.GameOptions;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;
import user11681.globalsettings.config.GlobalSettingsConfiguration;

@SuppressWarnings("ConstantConditions")
@Environment(EnvType.CLIENT)
public class GlobalSettingScreen extends Screen {
    private static final GameOptions options = MinecraftClient.getInstance().options;

    private static final int textWidth = 100;
    private static final int textHeight = 20;

    private final Screen parent;

    private int startX;
    private int startY;

    private TextFieldWidget optionPathField;
    private ButtonWidget apply;
    private ButtonWidget reset;

    public GlobalSettingScreen(final Screen parent) {
        super(new LiteralText("global settings"));

        this.parent = parent;
    }

    @Override
    protected void init() {
        super.init();

        this.startY = this.height / 2;
        this.startX = this.width / 2 - 50;

        this.optionPathField = new TextFieldWidget(this.textRenderer, this.startX - 75, this.startY, textWidth + 150, textHeight, LiteralText.EMPTY);

        this.optionPathField.setMaxLength(Integer.MAX_VALUE);
        this.optionPathField.setText(GlobalSettingsConfiguration.getFile().getPath());

        this.reset = new ButtonWidget(this.startX - 75, this.startY + 30, textWidth, textHeight, new TranslatableText("globalsettings.reset"), (final ButtonWidget reset) -> {
            this.optionPathField.setText(GlobalSettingsConfiguration.getFile().getPath());

            this.setActive(false);
        });

        this.apply = new ButtonWidget(this.startX + 75, this.startY + 30, textWidth, textHeight, new TranslatableText("globalsettings.apply"), (final ButtonWidget apply) -> {
            GlobalSettingsConfiguration.setPath(this.optionPathField.getText());

            if (GlobalSettingsConfiguration.getFile().exists()) {
                options.load();

                this.client.onResolutionChanged();
            } else {
                GlobalSettingsConfiguration.ensureFileExists();

                options.write();
            }

            this.setActive(false);
        });

        this.setActive(false);

        this.addButton(this.optionPathField);
        this.addButton(this.apply);
        this.addButton(this.reset);
        this.addButton(new ButtonWidget(this.startX, this.height - 30, 100, 20, new TranslatableText("mco.selectServer.close"), (final ButtonWidget button) -> this.onClose()));
    }

    @Override
    public void render(final MatrixStack matrices, final int mouseX, final int mouseY, final float delta) {
        this.renderBackground(matrices);

        super.render(matrices, mouseX, mouseY, delta);

        drawCenteredText(matrices, this.textRenderer, new TranslatableText("globalsettings.path"), this.width / 2, this.startY - 10, 0xFFFFFF);
    }

    @Override
    public boolean keyPressed(final int keyCode, final int scanCode, final int modifiers) {
        final boolean handled = super.keyPressed(keyCode, scanCode, modifiers);

        this.setActive(!this.optionPathField.getText().equals(GlobalSettingsConfiguration.getFile().getPath()));

        return handled;
    }

    @Override
    public boolean charTyped(final char character, final int keyCode) {
        final boolean handled =  super.charTyped(character, keyCode);

        this.setActive(!this.optionPathField.getText().equals(GlobalSettingsConfiguration.getFile().getPath()));

        return handled;
    }

    @Override
    public void onClose() {
        GlobalSettingsConfiguration.setPath(this.optionPathField.getText());

        this.client.openScreen(this.parent);
    }

    public void setActive(final boolean active) {
        this.reset.active = this.apply.active = active;
    }
}
