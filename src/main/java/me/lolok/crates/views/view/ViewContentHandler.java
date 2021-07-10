package me.lolok.crates.views.view;

import me.lolok.crates.views.components.Button;
import me.lolok.crates.views.listeners.ClickListener;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Represents the content of a view. An element may be listener-linked.
 * If so, an action will be executed.
 * @see ClickListener
 */
@RequiredArgsConstructor
public class ViewContentHandler {

    @Getter
    private final Inventory inventory;
    private final Map<Integer, ClickListener> listeners = new HashMap<>();

    public Optional<ClickListener> getListener(int slot) {
        return Optional.ofNullable(listeners.get(slot));
    }

    /**
     * Sets the new button for the given slot.
     * Setting a new button will remove the listener linked to the previous button.
     * A new listener will be set if the given button has one.
     * @param slot the view slot
     * @param button the new button
     */
    public void setItem(int slot, Button button) {
        listeners.remove(slot);
        button.getListener().ifPresent(clickListener -> listeners.put(slot, clickListener));
        inventory.setItem(slot, button.getItem());
    }

    public void setItem(int slot, ItemStack item) {
        setItem(slot, Button.of(item));
    }
    
    public void removeItem(int slot) {
        listeners.remove(slot);
        inventory.setItem(slot, null);
    }

    /**
     * Clears the view and removes the listeners.
     */
    public void clear() {
        listeners.clear();
        inventory.clear();
    }
}
