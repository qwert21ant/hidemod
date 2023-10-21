package ru.qwert21.hidemod.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import ru.qwert21.hidemod.ModMain;

public class GuiButtonVisibility extends GuiButton {
  public static final int ID = 876;
  private static final ResourceLocation TEXTURE = new ResourceLocation(ModMain.MODID, "eye.png");

  public boolean stateVisibility;

  public GuiButtonVisibility(int x, int y, boolean visibility) {
    super(ID, x, y, 20, 20, "");
    stateVisibility = visibility;
  }

  @Override
  public void drawButton(Minecraft mc, int mouseX, int mouseY) {
    if (!this.visible) return;

    this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
    int i = getHoverState(this.hovered);

    GlStateManager.color(1.0F, 1.0F, 1.0F);
    mc.getTextureManager().bindTexture(buttonTextures);
    this.drawTexturedModalRect(this.xPosition, this.yPosition, 0, 46 + i * 20, this.width / 2, this.height);
    this.drawTexturedModalRect(this.xPosition + this.width / 2, this.yPosition, 200 - this.width / 2, 46 + i * 20, this.width / 2, this.height);

    mc.getTextureManager().bindTexture(TEXTURE);
    Gui.drawModalRectWithCustomSizedTexture(this.xPosition, this.yPosition, stateVisibility ? 0 : 20, 0, this.width, this.height, 40, 20);
  }
}
