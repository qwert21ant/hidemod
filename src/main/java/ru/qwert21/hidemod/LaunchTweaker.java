package ru.qwert21.hidemod;

import net.minecraft.launchwrapper.ITweaker;
import net.minecraft.launchwrapper.LaunchClassLoader;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Mixins;
import org.spongepowered.tools.obfuscation.mcp.ObfuscationServiceMCP;

import java.io.File;
import java.util.List;

public class LaunchTweaker implements ITweaker {
  @Override
  public void acceptOptions(List<String> args, File gameDir, File assetsDir, String profile) {

  }

  @Override
  public void injectIntoClassLoader(LaunchClassLoader classLoader) {
    MixinBootstrap.init();

    MixinEnvironment.getDefaultEnvironment().setSide(MixinEnvironment.Side.CLIENT);
    MixinEnvironment.getDefaultEnvironment().setObfuscationContext(ObfuscationServiceMCP.SEARGE);

    Mixins.addConfiguration("mixins.hidemod.json");
  }

  @Override
  public String getLaunchTarget() {
    return "net.minecraft.client.main.Main";
  }

  @Override
  public String[] getLaunchArguments() {
    return new String[0];
  }
}
