package me.lolok.crates.views.listeners;

import me.lolok.crates.views.view.IView;
import org.bukkit.event.inventory.InventoryEvent;

import java.util.function.BiConsumer;

/**
 * Represents a generic callback listener which deals with IView's interactions.
 * Every implementation should complete without throwing exceptions.
 * @param <T> the {@link InventoryEvent}'s type
 */
@FunctionalInterface
public interface ViewListener<T extends InventoryEvent> extends BiConsumer<T, IView> {

    /**
     * Gets called every time a view-related event is fired.
     * @param inventoryEvent the fired event
     * @param view the view that the user has opened
     */
    @Override
    void accept(T inventoryEvent, IView view);
}
