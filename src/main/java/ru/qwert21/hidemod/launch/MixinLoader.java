package ru.qwert21.hidemod.launch;

import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Mixins;
import org.spongepowered.tools.obfuscation.mcp.ObfuscationServiceMCP;

import java.util.Map;

public class MixinLoader implements IFMLLoadingPlugin {
  public MixinLoader() {
    MixinBootstrap.init();

    MixinEnvironment.getDefaultEnvironment().setSide(MixinEnvironment.Side.CLIENT);
    MixinEnvironment.getDefaultEnvironment().setObfuscationContext(ObfuscationServiceMCP.SEARGE);

    Mixins.addConfiguration("mixins.hidemod.json");
  }
  @Override
  public String[] getASMTransformerClass() {
    return new String[0];
  }

  @Override
  public String getModContainerClass() {
    return null;
  }

  @Override
  public String getSetupClass() {
    return null;
  }

  @Override
  public void injectData(Map<String, Object> data) {

  }

  @Override
  public String getAccessTransformerClass() {
    return null;
  }
}
