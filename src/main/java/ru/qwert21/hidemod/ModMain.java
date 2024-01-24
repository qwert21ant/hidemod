package ru.qwert21.hidemod;

import cpw.mods.fml.client.GuiModList;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Method;

@Mod(modid = ModMain.MODID)
public class ModMain {
  public static final String MODID = "hidemod";
  public static Logger logger;
  public static Config config;

  @EventHandler
  public void preInit(FMLPreInitializationEvent event) {
    logger = event.getModLog();
    config = new Config(event.getSuggestedConfigurationFile());

    config.init();
  }
}
