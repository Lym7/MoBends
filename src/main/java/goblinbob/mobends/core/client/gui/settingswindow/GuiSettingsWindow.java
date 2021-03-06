package goblinbob.mobends.core.client.gui.settingswindow;

import goblinbob.mobends.core.bender.EntityBender;
import goblinbob.mobends.core.bender.EntityBenderRegistry;
import goblinbob.mobends.core.client.event.DataUpdateHandler;
import goblinbob.mobends.core.client.gui.GuiBendsMenu;
import goblinbob.mobends.core.util.Draw;
import goblinbob.mobends.core.util.GuiHelper;
import goblinbob.mobends.standard.main.ModStatics;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;

import java.io.IOException;

public class GuiSettingsWindow extends GuiScreen
{

    public static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(ModStatics.MODID,
            "textures/gui/pack_window.png");
    public static final int EDITOR_WIDTH = 280;
    public static final int EDITOR_HEIGHT = 177;
    private static final int BUTTON_BACK = 0;

    private int x, y;

    private final GuiBenderList bendsSettingsListUI = new GuiBenderList(0, 0, EDITOR_WIDTH - 10, EDITOR_HEIGHT - 10);

    public GuiSettingsWindow()
    {
        super();

        for (final EntityBender<?> bender : EntityBenderRegistry.instance.getRegistered())
        {
            bendsSettingsListUI.addElement(new GuiBenderSettings(bender));
        }
    }

    @Override
    public void initGui()
    {
        super.initGui();

        this.x = (this.width - EDITOR_WIDTH) / 2;
        this.y = (this.height - EDITOR_HEIGHT) / 2;

        buttonList.clear();
        buttonList.add(new GuiButton(BUTTON_BACK, 10, height - 30, 60, 20, "Back"));
        bendsSettingsListUI.initGui(this.x + 9, this.y + 9);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();

        Minecraft.getMinecraft().getTextureManager().bindTexture(BACKGROUND_TEXTURE);
        // Container
        Draw.borderBox(x + 4, y + 4, EDITOR_WIDTH, EDITOR_HEIGHT, 4, 36, 126);
        // Title background
        Draw.texturedModalRect(x, y - 13, 101, 0, 4, 16);
        Draw.texturedModalRect(x + 4, y - 13, EDITOR_WIDTH - 16, 16, 105, 0, 1, 16);
        Draw.texturedModalRect(x + EDITOR_WIDTH - 17, y - 13, 106, 0, 19, 16);

        bendsSettingsListUI.draw(DataUpdateHandler.partialTicks);

        mc.fontRenderer.drawStringWithShadow("Settings", this.x + 6, this.y - 9, 0xffffff);

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void updateScreen()
    {
        super.updateScreen();

        final int mouseX = Mouse.getEventX() * this.width / this.mc.displayWidth;
        final int mouseY = this.height - Mouse.getEventY() * this.height / this.mc.displayHeight - 1;

        bendsSettingsListUI.update(mouseX, mouseY);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
        super.mouseClicked(mouseX, mouseY, mouseButton);

        bendsSettingsListUI.handleMouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int mouseButton)
    {
        super.mouseReleased(mouseX, mouseY, mouseButton);

        bendsSettingsListUI.handleMouseReleased(mouseX, mouseY, mouseButton);
    }

    @Override
    public void handleMouseInput() throws IOException
    {
        super.handleMouseInput();

        bendsSettingsListUI.handleMouseInput();
    }

    @Override
    public void keyTyped(char typedChar, int keyCode)
    {
        switch (keyCode)
        {
            case 1:
                GuiHelper.closeGui();
                break;
        }
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException
    {
        super.actionPerformed(button);

        switch (button.id)
        {
            case BUTTON_BACK:
                goBack();
                break;
        }
    }

    private void goBack()
    {
        this.mc.displayGuiScreen(new GuiBendsMenu());
    }

    public boolean doesGuiPauseGame()
    {
        return false;
    }

}
