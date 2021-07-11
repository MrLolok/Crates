package me.lolok.crates.crates.crate.animations;

import com.cryptomorin.xseries.XMaterial;
import com.cryptomorin.xseries.XSound;
import lombok.RequiredArgsConstructor;
import me.lolok.crates.CratesPlugin;
import me.lolok.crates.configurations.messages.Message;
import me.lolok.crates.crates.crate.objects.Crate;
import me.lolok.crates.items.builder.ItemBuilder;
import me.lolok.crates.views.view.IView;
import me.lolok.crates.views.view.impl.View;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.Objects;
import java.util.Random;

public class GlassesRotationAnimation extends Animation {
    private BukkitTask task;

    public GlassesRotationAnimation(Crate crate) {
        super(crate);
    }

    @Override
    public void start(Player player) {
        IView view = new View(String.format("§l» §8Opening crate %s...", crate.getName()), 5);
        // Skip animation
        view.setCloseListener((event, gui) -> end(player));

        this.task = new OpeningTask(view, prize.getItem()).runTaskTimerAsynchronously(CratesPlugin.getInstance(), 0L, ANIMATION_SPEED);
    }

    @Override
    public void end(Player player) {
        if (task != null) {
            if (task.isCancelled()) return;
            task.cancel();
        }

        Message message = prize == null ? Message.CRATE_LOST : Message.CRATE_WON;
        message.send(player, "item_name", prize != null ? prize.getItem().getItemMeta().hasDisplayName() ? prize.getItem().getItemMeta().getDisplayName() : prize.getItem().getType().name() : "Nothing", "item_chance", prize != null ? String.valueOf(prize.getChance()) : "");
        player.playSound(player.getLocation(), Objects.requireNonNull(XSound.ENTITY_PLAYER_LEVELUP.parseSound()), 1F, 2F);

        if (prize != null)
            player.getInventory().addItem(prize.getItem());
    }

    @RequiredArgsConstructor
    public class OpeningTask extends BukkitRunnable {
        private final IView view;
        private final ItemStack prize;
        private int index = 0;

        @Override
        public void run() {
            if (index < 22) {
                setGlasses();
                return;
            }

            ItemStack item = prize == null ? new ItemBuilder().material(XMaterial.BARRIER.parseMaterial()).name("§cNothing").build() : prize;
            view.getContentHandler().setItem(22, item);

            Bukkit.getScheduler().runTaskLater(CratesPlugin.getInstance(), () -> view.getViewer().closeInventory(), 60L);
            end(view.getViewer());
        }

        private void setGlasses() {
            ItemStack item = new ItemBuilder().material(GLASSES.get(new Random().nextInt(GLASSES.size()))).name(" ").build();
            view.getContentHandler().setItem(index++, item);
            view.getContentHandler().setItem(45 - index, item);
            view.getViewer().playSound(view.getViewer().getLocation(), Objects.requireNonNull(XSound.BLOCK_NOTE_BLOCK_PLING.parseSound()), 1F, index / 10F);
        }
    }
}
