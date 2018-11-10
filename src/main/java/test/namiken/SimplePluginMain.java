package test.namiken;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import test.namiken.listener.FishExplosionListener;
import test.namiken.property.PluginProperty;

public class SimplePluginMain extends JavaPlugin {

  private static SimplePluginMain plugin;

  private static PluginProperty property;

  @Override
  public void onEnable() {
    plugin = this;
    saveDefaultConfig();

    //load config.yml
    loadConfiguration();

    //register event listener
    Bukkit.getPluginManager().registerEvents(new FishExplosionListener(), this);
  }

  public static SimplePluginMain getPlugin() {
    return plugin;
  }

  public static PluginProperty getProperty() {
    return property;
  }

  /**
   * Load config.yml.
   */
  private synchronized void loadConfiguration() {
    property = new PluginProperty();
    property.setEnable(getConfig().getBoolean("enable", true));
    property.setExplosionPower((float) getConfig().getDouble("explosionPower", 3.0));
  }

}
