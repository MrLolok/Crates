package me.lolok.crates.views.listeners;

import me.lolok.crates.views.view.IView;
import org.bukkit.event.inventory.InventoryCloseEvent;

/**
 * Represents a listener called when a view is closed by a player.
 */
@FunctionalInterface
public interface CloseListener extends ViewListener<InventoryCloseEvent> {

    /**
     * Gets called when the targeted view is closed.
     * @param event the {@link InventoryCloseEvent}
     * @param view the view that the user has closed
     */
    @Override
    void accept(InventoryCloseEvent event, IView view);
}
