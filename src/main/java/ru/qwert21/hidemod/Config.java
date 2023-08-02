package ru.qwert21.hidemod;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;

import java.io.File;
import java.util.*;

public class Config {
  public static String PROPERTY_NAME = "hide";

  private final Configuration config;
  private final Map<String, Boolean> modStates;

  Config(File configFile) {
    config = new Configuration(configFile);
    modStates = new HashMap<>();
  }

  public void init() {
    for (ModContainer mod : Loader.instance().getModList()) {
      modStates.put(mod.getModId(), true);
    }

    String[] savedStates = getProperty().getStringList();
    for (String modId : savedStates) {
      if (modStates.containsKey(modId)) {
        modStates.put(modId, false);
      }
    }
  }

  private Property getProperty() {
    return config.get(Configuration.CATEGORY_GENERAL, PROPERTY_NAME, new String[0], "List of mod IDs to hide from server");
  }

  private void updateConfig() {
    List<String> modList = new ArrayList<>();

    for (Map.Entry<String, Boolean> entry : modStates.entrySet()) {
      if (!entry.getValue()) {
        modList.add(entry.getKey());
      }
    }

    getProperty().set(modList.toArray(new String[0]));
    config.save();
  }

  public void setState(String modId, boolean state) {
    Boolean ret = modStates.put(modId, state);
    if (ret != state)
      updateConfig();
  }

  public boolean getState(String modId) {
    return modStates.get(modId);
  }
}
