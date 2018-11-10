package test.namiken.task;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FishHook;
import org.bukkit.scheduler.BukkitRunnable;

import test.namiken.SimplePluginMain;
import test.namiken.property.PluginProperty;

public class HookExplodeTask extends BukkitRunnable {

  /** target hook */
  private FishHook hook;

  private Location beforeLocation;

  /**
   *  constructor.
   *
   * @param hook target hook
   */
  public HookExplodeTask(FishHook hook) {
    this.hook = hook;
  }

  @Override
  public void run() {
    Location hookLocation = hook.getLocation();

    //test hook dont move
    if (beforeLocation != null && beforeLocation.distanceSquared(hookLocation) == 0) {
      executeExplosion(hook);
      cancel();
      return;
    }

    //update hook's location
    beforeLocation = hook.getLocation();

    //test hook is valid
    if (!hook.isValid()) {
      cancel();
      return;
    }

    //test hook is on ground
    if (hook.isOnGround()) {
      executeExplosion(hook);
      cancel();
      return;
    }

    //test hook is in block
    Material OverlapBlockType = hookLocation.getBlock()
        .getType();
    if (OverlapBlockType != Material.AIR && OverlapBlockType.isBlock()) {
      executeExplosion(hook);
      cancel();
      return;
    }
  }

  /**
   * execute explode at hook location
   * @param hook hook entity
   */
  private void executeExplosion(Entity hook) {
    Location location = hook.getLocation();

    //explode
    PluginProperty property = SimplePluginMain.getProperty();
    location.getWorld().createExplosion(location.getX(), location.getY(), location.getZ(), property.getExplosionPower(),
        false, false);

    //particle run
    location.getWorld().spawnParticle(Particle.LAVA, location.getX(), location.getY(), location.getZ(), 200, 1.2,
        1.2, 1.2, 0.1);
  }
}
