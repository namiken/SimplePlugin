package test.namiken.listener;

import org.bukkit.entity.FishHook;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerFishEvent.State;

import test.namiken.SimplePluginMain;
import test.namiken.property.PluginProperty;
import test.namiken.task.HookExplodeTask;

public class FishExplosionListener implements Listener {

  /**
   * if fishing hook is on grond or dont move, explode at hook location.
   *
   * @param event PlayerFishEvent
   */
  @EventHandler
  public void onPlayerFilshEvent(PlayerFishEvent event) {
    PluginProperty property = SimplePluginMain.getProperty();
    if (!property.isEnable()) {
      return;
    }

    if (event.getState() != State.FISHING) {
      return;
    }

    //start hook monitoring
    FishHook hook = event.getHook();
    new HookExplodeTask(hook).runTaskTimer(SimplePluginMain.getPlugin(), 2, 1);
  }

}
