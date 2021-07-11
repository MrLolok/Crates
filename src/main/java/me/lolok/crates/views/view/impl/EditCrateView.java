package me.lolok.crates.views.view.impl;

import me.lolok.crates.CratesPlugin;
import me.lolok.crates.configurations.messages.Message;
import me.lolok.crates.crates.crate.ICrateService;
import me.lolok.crates.crates.crate.objects.Crate;
import me.lolok.crates.crates.crate.prizes.CratePrize;
import me.lolok.crates.crates.crate.prizes.DefaultCratePrize;
import me.lolok.crates.views.components.Button;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EditCrateView extends View {
    private final ICrateService service = CratesPlugin.getInstance().getCrateService();

    public EditCrateView(Crate crate) {
        super(String.format("§l» §8Editing crate %s", crate.getName()), Math.max(1, (int) Math.ceil(crate.getPrizes().size() / 9D)));
        setDragListener((event, view) -> crate.addPrize(new DefaultCratePrize(event.getOldCursor())));
        loadPrizes(crate);
    }

    private void loadPrizes(Crate crate) {
        int slot = 0;
        for (CratePrize prize : crate.getPrizes()) {
            ItemStack item = prize.getItem();
            ItemMeta meta = item.getItemMeta();
            List<String> lore = meta.hasLore() ? meta.getLore() : new ArrayList<>();
            lore.addAll(Arrays.asList("", "§6§l» §eChance: §f" + prize.getChance() + "%", "§f§lRIGHT CLICK §7Remove", "§f§lSHIFT CLICK §7Edit chance"));
            meta.setLore(lore);
            item.setItemMeta(meta);

            contentHandler.setItem(slot++, Button.of(item, ((event, view) -> {
                Player player = (Player) event.getWhoClicked();
                switch (event.getClick()) {
                    case RIGHT:
                        crate.removePrize(prize);
                        new EditCrateView(crate).open(player);
                        break;
                    case SHIFT_LEFT:
                    case SHIFT_RIGHT:
                        service.addEditingUser(player, prize);
                        player.sendTitle(Message.EDIT_CHANCE_TITLE.getMessage(), Message.EDIT_CHANCE_SUBTITLE.getMessage(), 0, Integer.MAX_VALUE, 0);
                        player.closeInventory();
                        break;
                }
            })));
        }
    }
}
