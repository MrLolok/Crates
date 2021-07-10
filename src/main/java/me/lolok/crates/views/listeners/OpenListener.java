package me.lolok.crates.views.listeners;

import me.lolok.crates.views.view.IView;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

import java.util.function.BiConsumer;

/**
 * Represents a listener called when a view is opened by a player.
 */
@FunctionalInterface
public interface OpenListener extends BiConsumer<InventoryOpenEvent, IView> {

    /**
     * Gets called when the targeted view is opened.
     * @param event the {@link InventoryCloseEvent}
     * @param view the view that the user has opened
     */
    @Override
    void accept(InventoryOpenEvent event, IView view);
}
