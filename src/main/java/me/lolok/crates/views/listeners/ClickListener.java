package me.lolok.crates.views.listeners;

import me.lolok.crates.views.view.IView;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.function.BiConsumer;

/**
 * Represents a listener called when a view button is clicked by a player.
 */
@FunctionalInterface
public interface ClickListener extends BiConsumer<InventoryClickEvent, IView> {

    /**
     * Performs the implemented action when the GUI component gets an interaction.
     * @param event the {@link InventoryClickEvent}
     * @param view the {@link IView}
     */
    void accept(InventoryClickEvent event, IView view);
}
