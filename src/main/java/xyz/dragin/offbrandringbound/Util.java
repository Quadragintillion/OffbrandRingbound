package xyz.dragin.offbrandringbound;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class Util {
    private Util() {}

    public static ItemStack getHead(String username) {
        ItemStack item = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) item.getItemMeta();
        meta.setOwner(username);
        item.setItemMeta(meta);
        return item;
    }
}
