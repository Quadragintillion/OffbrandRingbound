package xyz.dragin.offbrandringbound;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public final class Offbrandringbound extends JavaPlugin implements Listener {
    private static Offbrandringbound instance;
    public static Offbrandringbound getInstance() { return instance; }

    @Override
    public void onEnable() {
        instance = this;
        Bukkit.getPluginManager().registerEvents(this, this);

        for (Ring ring : Ring.values()) {
            Bukkit.addRecipe(ring.getRecipe());
        }
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player player) {
            for (ItemStack itemStack : player.getInventory()) {
                if (itemStack == null) continue;
                Ring ring = Ring.getFromItem(itemStack);
                if (ring == null) continue;
                ring.use(event);
            }
        }
    }
}
