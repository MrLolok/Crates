package me.lolok.crates.views.view.impl;

import com.cryptomorin.xseries.XMaterial;
import com.cryptomorin.xseries.XSound;
import lombok.RequiredArgsConstructor;
import me.lolok.crates.CratesPlugin;
import me.lolok.crates.configurations.messages.Message;
import me.lolok.crates.crates.crate.objects.Crate;
import me.lolok.crates.items.builder.ItemBuilder;
import me.lolok.crates.views.view.IView;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class OpeningCrateView extends View {
    private final static List<Material> GLASSES = CratesPlugin.getInstance().getConfig().getStringList("animation.glasses").stream().map(Material::getMaterial).collect(Collectors.toList());
    private final BukkitTask task;

    public OpeningCrateView(Crate crate) {
        super(String.format("§l» §8Opening crate %s...", crate.getName()), 5);

        ItemStack prize = crate.getRandomPrize();
        this.task = new OpeningTask(this, prize).runTaskTimerAsynchronously(CratesPlugin.getInstance(), 0L, 10L);

        // Skip animation
        setCloseListener((event, view) -> {
            if (!task.isCancelled() && prize != null)
                event.getPlayer().getInventory().addItem(prize);
        });
    }

    @RequiredArgsConstructor
    public static class OpeningTask extends BukkitRunnable {
        private final IView view;
        private final ItemStack prize;
        private int index = 0;

        @Override
        public void run() {
            if (index == 22) {
                ItemStack item = prize == null ? new ItemBuilder().material(XMaterial.BARRIER.parseMaterial()).name("§cNothing").build() : prize;
                view.getContentHandler().setItem(22, item);

                Message message = prize == null ? Message.CRATE_LOST : Message.CRATE_WON;
                message.send(view.getViewer());
                view.getViewer().playSound(view.getViewer().getLocation(), XSound.ENTITY_PLAYER_LEVELUP.parseSound(), 1F, index / 10F);

                cancel();
                return;
            }

            ItemStack item = new ItemBuilder().material(GLASSES.get(new Random().nextInt(GLASSES.size()))).name(" ").build();
            view.getContentHandler().setItem(index++, item);
            view.getContentHandler().setItem(45 - index, item);
            view.getViewer().playSound(view.getViewer().getLocation(), XSound.BLOCK_NOTE_BLOCK_PLING.parseSound(), 1F, index / 10F);
        }
    }
}
