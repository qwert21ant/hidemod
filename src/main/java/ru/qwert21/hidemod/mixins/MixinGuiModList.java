package ru.qwert21.hidemod.mixins;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.client.GuiModList;
import net.minecraftforge.fml.common.ModContainer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.qwert21.hidemod.ModMain;
import ru.qwert21.hidemod.gui.GuiButtonVisibility;

@Mixin(value = GuiModList.class, remap = false)
public class MixinGuiModList extends GuiScreen {
  @Shadow
  private GuiButton configModButton;

  @Shadow
  private ModContainer selectedMod;

  GuiButtonVisibility visibilityModButton;

  @Inject(
          method = "initGui",
          at = @At(
                  value = "INVOKE",
                  target = "Lnet/minecraftforge/fml/client/GuiModList;updateCache()V"
          ),
          require = 1,
          remap = true
  )
  public void initGui(CallbackInfo ci) {
    ModMain.logger.info("Init gui");
    configModButton.width -= 22;
    visibilityModButton =
      new GuiButtonVisibility(configModButton.x + configModButton.width + 2, configModButton.y, false);
    this.buttonList.add(visibilityModButton);
  }

  @Inject(
          method = "updateCache",
          at = @At("HEAD"),
          require = 1,
          remap = false
  )
  private void updateCache1(CallbackInfo ci) {
    ModMain.logger.info("Update cache 1");
    visibilityModButton.visible = false;
  }

  @Inject(
          method = "updateCache",
          at = @At("TAIL"),
          require = 1,
          remap = false
  )
  private void updateCache2(CallbackInfo ci) {
    ModMain.logger.info("Update cache 2");
    visibilityModButton.visible = true;
    visibilityModButton.stateVisibility = ModMain.config.getState(selectedMod.getModId());
  }

  @Inject(
          method = "actionPerformed",
          at = @At("HEAD"),
          require = 1,
          remap = true
  )
  private void actionPerformed(GuiButton button, CallbackInfo ci) {
    ModMain.logger.info("Action perform");
    if (!(button instanceof GuiButtonVisibility)) return;
    if (!button.enabled) return;
    if (selectedMod == null) return;

    GuiButtonVisibility visibilityModButton = (GuiButtonVisibility) button;

    visibilityModButton.stateVisibility = !visibilityModButton.stateVisibility;
    ModMain.config.setState(selectedMod.getModId(), visibilityModButton.stateVisibility);
  }
}
