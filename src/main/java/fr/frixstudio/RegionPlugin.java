package fr.frixstudio;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

/** Local portfolio example: one configured region, private plain-text entry notice. */
public class RegionPlugin extends JavaPlugin implements Listener {
    private RegionBounds bounds;
    private final Map<UUID, Boolean> inside = new HashMap<>();
    private final Map<UUID, Long> notified = new HashMap<>();
    @Override public void onEnable() {
        saveDefaultConfig(); loadBounds();
        getServer().getPluginManager().registerEvents(this, this);
    }
    private void loadBounds() {
        try {
            bounds = new RegionBounds(getConfig().getString("world", "world"),
                    getConfig().getInt("min-x", -5), getConfig().getInt("max-x", 5),
                    getConfig().getInt("min-z", -5), getConfig().getInt("max-z", 5));
        } catch (IllegalArgumentException e) { bounds = null; getLogger().warning("Invalid bounds; notices disabled."); }
        inside.clear();
    }
    @EventHandler public void onMove(PlayerMoveEvent event) {
        var to = event.getTo(); var from = event.getFrom();
        if (bounds == null || to == null || to.getWorld() == null) return;
        if (from.getWorld() == to.getWorld() && from.getBlockX() == to.getBlockX()
                && from.getBlockZ() == to.getBlockZ()) return;
        var id = event.getPlayer().getUniqueId();
        boolean present = bounds.contains(to.getWorld().getName(), to.getX(), to.getZ());
        boolean was = inside.getOrDefault(id, false); inside.put(id, present);
        long now = System.currentTimeMillis();
        if (!present || was || now - notified.getOrDefault(id, 0L) < 300_000) return;
        notified.entrySet().removeIf(entry -> now - entry.getValue() > 300_000);
        if (notified.size() >= 1024 && !notified.containsKey(id)) return;
        notified.put(id, now);
        String text = getConfig().getString("message", "You entered the example region.");
        event.getPlayer().sendMessage(text.substring(0, Math.min(240, text.length())));
    }
    @EventHandler public void onQuit(PlayerQuitEvent event) { inside.remove(event.getPlayer().getUniqueId()); }
    @Override public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("frixregion.admin")) { sender.sendMessage("Permission denied."); return true; }
        if (args.length == 1 && args[0].equalsIgnoreCase("reload")) { reloadConfig(); loadBounds(); }
        sender.sendMessage(bounds == null ? "Example region disabled: check bounds." : "Frix region example enabled.");
        return true;
    }
    @Override public void onDisable() { inside.clear(); notified.clear(); }
}
