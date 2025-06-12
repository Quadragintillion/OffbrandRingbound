package xyz.dragin.offbrandringbound;

import org.bukkit.*;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;
import java.util.Objects;

public enum Ring {
    POINTY(new ShapedRecipe(
            new NamespacedKey(Offbrandringbound.getInstance(), "pointy_ring"),
            buildRing(ChatColor.RED + "Pointy Ring", "it sharp :o (doubles ur damage)"))
            .shape("DG ", "G G", " G ")
            .setIngredient('G', Material.GOLD_NUGGET)
            .setIngredient('D', Material.POINTED_DRIPSTONE),
            (event) -> {
                event.setDamage(event.getFinalDamage()*2);
            }),
    BLINDNESS(new ShapedRecipe(
            new NamespacedKey(Offbrandringbound.getInstance(), "blindness_ring"),
            buildRing(ChatColor.BLACK + "Blindness Ring", "makes the opponent see what my basement looks like"))
            .shape("SG ", "G G", " G ")
            .setIngredient('G', Material.GOLD_NUGGET)
            .setIngredient('S', Material.SPIDER_EYE),
            (event) -> {
                if (event.getEntity() instanceof LivingEntity entity) {
                    entity.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100, 0));
                }
            }),
    SONIC(new ShapedRecipe(
            new NamespacedKey(Offbrandringbound.getInstance(), "sonic_ring"),
            buildRing(ChatColor.YELLOW + "Sonic Ring", "makes u sped when attack ppl"))
            .shape("GGG", "G G", "GGG")
            .setIngredient('G', Material.GOLD_NUGGET),
            (event) -> {
                if (event.getDamager() instanceof LivingEntity entity) {
                    entity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100, 2));
                }
            }),
    RING(new ShapedRecipe(
            new NamespacedKey(Offbrandringbound.getInstance(), "ring_ring"),
            buildRing(ChatColor.BLACK + "Ring Ring", "spoopy"))
            .shape("BG ", "G G", " G ")
            .setIngredient('G', Material.GOLD_NUGGET)
            .setIngredient('B', Material.BLACK_WOOL),
            (event) -> {
                if (event.getEntity() instanceof LivingEntity entity) {
                    entity.getEquipment().setHelmet(Util.getHead("MHF_Herobrine"));
                }
            }),
    SUS(new ShapedRecipe(
            new NamespacedKey(Offbrandringbound.getInstance(), "sus_ring"),
            buildRing(ChatColor.RED + "Sus Ring", "SUSU SAMONGUSNSU"))
            .shape("RG ", "G G", " G ")
            .setIngredient('G', Material.GOLD_NUGGET)
            .setIngredient('R', Material.RED_WOOL),
            (event) -> {
                if (event.getEntity() instanceof LivingEntity entity) {
                    entity.getEquipment().setHelmet(Util.getHead("anantdev694"));
                }
            }),
    TRUMP(new ShapedRecipe(
            new NamespacedKey(Offbrandringbound.getInstance(), "trump_ring"),
    buildRing(ChatColor.GOLD + "Trump Ring", "builds a wall"))
            .shape("OG ", "G G", " G ")
            .setIngredient('G', Material.GOLD_NUGGET)
            .setIngredient('O', Material.ORANGE_WOOL),
        (event) -> {
        int baseX = event.getEntity().getLocation().getBlockX();
        int baseY = event.getEntity().getLocation().getBlockY();
            for (int x = baseX - 4; x <= baseX + 4; x++) {
                for (int y = baseY; y <= baseY + 4; y++) {
                    Bukkit.getLogger().info("x: " + x + "Y: " + y);
                    event.getEntity().getWorld().getBlockAt(new Location(event.getEntity().getWorld(), x, y, event.getEntity().getLocation().getBlockZ())).setType(Material.STONE_BRICKS);
                }
            }
    });

    private final Recipe recipe;
    public Recipe getRecipe() { return recipe; }
    public ItemStack getItem() { return recipe.getResult(); }
    private final RingExecutor executor;
    public void use(EntityDamageByEntityEvent event) { executor.onDamage(event);}

    Ring(Recipe recipe, RingExecutor executor) {
        this.recipe = recipe;
        this.executor = executor;
    }

    public static Ring getFromItem(ItemStack item) {
        if (item.getType().equals(Material.GOLD_NUGGET)) {
            ItemMeta meta = item.getItemMeta();
            if (meta == null) return null;

            for (Ring ring : values()) {
                if (Objects.requireNonNull(ring.getItem().getItemMeta()).getDisplayName().equals(meta.getDisplayName())) return ring;
            }
        }
        return null;
    }

    private static ItemStack buildRing(String displayName, String lore) {
        ItemStack item = new ItemStack(Material.GOLD_NUGGET);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setMaxStackSize(1);
        meta.setDisplayName(displayName);
        meta.setLore(List.of(ChatColor.GRAY + lore));
        meta.setEnchantmentGlintOverride(true);
        item.setItemMeta(meta);
        return item;
    }
}
