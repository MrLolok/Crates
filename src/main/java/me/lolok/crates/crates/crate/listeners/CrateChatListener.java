package me.lolok.crates.crates.crate.listeners;

import lombok.RequiredArgsConstructor;
import me.lolok.crates.configurations.messages.Message;
import me.lolok.crates.crates.crate.ICrateService;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

@RequiredArgsConstructor
public class CrateChatListener implements Listener {
    private final ICrateService service;

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        Player player = e.getPlayer();
        if (!service.isEditing(player)) return;

        e.setCancelled(true);
        if (e.getMessage().equalsIgnoreCase("cancel")) {
            service.removeEditingUser(player);
            return;
        }

        double chance;
        try {
            chance = Double.parseDouble(e.getMessage());
        } catch (NumberFormatException ex) {
            Message.INVALID_CHANCE.send(player);
            return;
        }

        service.removeEditingUser(player).setChance(chance);
        Message.EDIT_CHANCE_MESSAGE.send(player, "chance", String.valueOf(chance));
        player.resetTitle();
    }
}
