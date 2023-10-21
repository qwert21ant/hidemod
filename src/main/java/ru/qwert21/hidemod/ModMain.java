package ru.qwert21.hidemod;

import net.minecraftforge.fml.client.GuiModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;
import org.lwjgl.Sys;

import java.lang.reflect.Method;

@Mod(
        modid = ModMain.MODID,
        name = ModMain.NAME,
        version = ModMain.VERSION
)
public class ModMain {
  public static final String MODID = "hidemod";
  public static final String NAME = "Hide Mod";
  public static final String VERSION = "1.0";

  public static Logger logger;
  public static Config config;

  @EventHandler
  public void preInit(FMLPreInitializationEvent event) {
    logger = event.getModLog();
    config = new Config(event.getSuggestedConfigurationFile());

    config.init();

    Method[] methods = GuiModList.class.getDeclaredMethods();
    for(Method m : methods) {
      logger.info(m.getName());
      logger.info(m);
    }
  }
}
