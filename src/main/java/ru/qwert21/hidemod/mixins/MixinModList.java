package ru.qwert21.hidemod.mixins;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.network.handshake.FMLHandshakeMessage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.qwert21.hidemod.ModMain;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Mixin(value = FMLHandshakeMessage.ModList.class, remap = false)
public class MixinModList extends FMLHandshakeMessage {
  @Shadow
  private Map<String, String> modTags;

  @Inject(
          method = "<init>(Ljava/util/List;)V",
          at = @At("RETURN"),
          require = 1,
          remap = false
  )
  public void filterMods(List<ModContainer> mods, CallbackInfo ci) {
    if (Minecraft.getMinecraft().isSingleplayer()) return;

    Set<String> keys = new HashSet<>(modTags.keySet());
    for (String key : keys) {
      if (!ModMain.config.getState(key)) {
        modTags.remove(key);
      }
    }

    ModMain.logger.info("Visible mods: " + modTags);
  }
}
