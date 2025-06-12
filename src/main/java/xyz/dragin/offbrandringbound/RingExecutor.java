package xyz.dragin.offbrandringbound;

import org.bukkit.event.entity.EntityDamageByEntityEvent;

public interface RingExecutor {
    void onDamage(EntityDamageByEntityEvent event);
}
