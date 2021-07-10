package me.lolok.crates.views.listeners;

import me.lolok.crates.views.view.IView;
import org.bukkit.event.inventory.InventoryDragEvent;

import java.util.function.BiConsumer;

/**
 * Represents a listener called when a view button is dragged inside the view by a player.
 */
@FunctionalInterface
public interface DragListener extends BiConsumer<InventoryDragEvent, IView> {

    /**
     * Gets called when a button is dragged inside the view.
     * @param event the {@link InventoryDragEvent}
     * @param view the {@link IView}
     */
    @Override
    void accept(InventoryDragEvent event, IView view);
}
