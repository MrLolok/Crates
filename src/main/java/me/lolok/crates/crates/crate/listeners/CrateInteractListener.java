package me.lolok.crates.crates.crate.listeners;

import lombok.RequiredArgsConstructor;
import me.lolok.crates.configurations.files.MessagesConfiguration;
import me.lolok.crates.configurations.messages.Message;
import me.lolok.crates.crates.crate.ICrateService;
import me.lolok.crates.crates.crate.objects.Crate;
import me.lolok.crates.crates.users.objects.CrateUser;
import me.lolok.crates.items.nbt.WrappedCompound;
import me.lolok.crates.views.view.impl.ConfirmOpeningCrateView;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

@RequiredArgsConstructor
public class CrateInteractListener implements Listener {
    private final ICrateService service;

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (e.getHand() != EquipmentSlot.HAND) return;

        Player player = e.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        if (!e.getAction().name().startsWith("RIGHT_CLICK")) return;

        WrappedCompound compound = WrappedCompound.of(item);
        if (!compound.hasKey("Crate")) return;

        e.setCancelled(true);
        CrateUser user = service.getUsersService().getUser(player.getUniqueId());
        if (!player.hasPermission("crates.bypass") && user != null) {
            if (user.getOpened() == ICrateService.MAX_PER_DAY) {
                Message.DAILY_MAX_REACHED.send(player);
                return;
            }

            int seconds = (int) ((System.currentTimeMillis() - user.getLastOpen()) / 1000);
            if (seconds < ICrateService.DELAY) {
                Message.OPEN_DELAY.send(player, "seconds", String.valueOf(ICrateService.DELAY - seconds));
                return;
            }
        }

        String name = compound.getString("Crate");
        Crate crate = service.getCrate(name);
        if (crate == null) return;
        new ConfirmOpeningCrateView(crate).open(player);
    }
}
