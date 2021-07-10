package me.lolok.crates.views.view;

import me.lolok.crates.views.listeners.CloseListener;
import me.lolok.crates.views.listeners.DragListener;
import me.lolok.crates.views.listeners.OpenListener;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryHolder;

import java.util.Optional;

/**
 * Represents the root of all GUIs.
 * This interface extends InventoryHolder
 */
public interface IView extends InventoryHolder {

    String getTitle();

    Object getSize();

    Player getViewer();

    void setViewer(Player player);

    ViewContentHandler getContentHandler();

    Optional<OpenListener> getOpenListener();

    Optional<DragListener> getDragListener();

    Optional<CloseListener> getCloseListener();

    void setOpenListener(OpenListener listener);

    void setDragListener(DragListener listener);

    void setCloseListener(CloseListener listener);

    default void open(Player player) {
        player.openInventory(getContentHandler().getInventory());
    }

}
