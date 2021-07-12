package me.lolok.crates.crates.crate.animations;

import com.cryptomorin.xseries.XMaterial;
import com.cryptomorin.xseries.XSound;
import lombok.RequiredArgsConstructor;
import me.lolok.crates.CratesPlugin;
import me.lolok.crates.configurations.messages.Message;
import me.lolok.crates.crates.crate.objects.Crate;
import me.lolok.crates.crates.crate.prizes.CratePrize;
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
    private CratePrize prize;
    private BukkitTask task;

    public GlassesRotationAnimation() {
        super("GlassesRotation");
    }

    @Override
    public void start(Crate crate, Player player) {
        IView view = new View(String.format("§l» §8Opening crate %s...", crate.getName()), 5);
        // Skip animation
        view.setCloseListener((event, gui) -> end(player));

        this.prize = crate.getRandomPrize();
        this.task = new OpeningTask(view).runTaskTimerAsynchronously(CratesPlugin.getInstance(), 0L, ANIMATION_SPEED);
    }

    private void end(Player player) {
        if (task != null) {
            if (task.isCancelled()) return;
            task.cancel();
        }

        sendPrize(prize, player);
    }

    @RequiredArgsConstructor
    public class OpeningTask extends BukkitRunnable {
        private final IView view;
        private int index = 0;

        @Override
        public void run() {
            if (index < 22) {
                setGlasses();
                return;
            }

            ItemStack item = prize == null ? new ItemBuilder().material(XMaterial.BARRIER.parseMaterial()).name(Message.EMPTY_PRIZE.getMessage()).build() : prize.getItem();
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
