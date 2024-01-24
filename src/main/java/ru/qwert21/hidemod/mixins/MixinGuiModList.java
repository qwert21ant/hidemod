package ru.qwert21.hidemod.mixins;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import cpw.mods.fml.client.GuiModList;
import cpw.mods.fml.common.ModContainer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.qwert21.hidemod.ModMain;
import ru.qwert21.hidemod.gui.GuiButtonVisibility;

@Mixin(value = GuiModList.class)
public class MixinGuiModList extends GuiScreen {
  @Shadow(remap = false)
  private GuiButton configModButton;

  @Shadow(remap = false)
  private ModContainer selectedMod;

  GuiButtonVisibility visibilityModButton;

  @Inject(
          method = "initGui",
          at = @At("TAIL"),
          require = 1
  )
  public void initGui(CallbackInfo ci) {
    configModButton.width -= 22;
    visibilityModButton =
      new GuiButtonVisibility(configModButton.xPosition + configModButton.width + 2, configModButton.yPosition, false);
    this.buttonList.add(visibilityModButton);

    visibilityModButton.visible = false;
  }

  @Inject(
    method = "selectModIndex",
    at = @At("TAIL"),
    remap = false
  )
  public void selectModIndex(int var1, CallbackInfo ci) {
    visibilityModButton.visible = true;
    visibilityModButton.stateVisibility = ModMain.config.getState(selectedMod.getModId());
  }

  @Inject(
          method = "actionPerformed",
          at = @At("HEAD"),
          require = 1
  )
  private void actionPerformed(GuiButton button, CallbackInfo ci) {
    if (!(button instanceof GuiButtonVisibility)) return;
    if (!button.enabled) return;
    if (selectedMod == null) return;

    GuiButtonVisibility visibilityModButton = (GuiButtonVisibility) button;

    visibilityModButton.stateVisibility = !visibilityModButton.stateVisibility;
    ModMain.config.setState(selectedMod.getModId(), visibilityModButton.stateVisibility);
  }
}
