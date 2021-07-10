package me.lolok.crates.views.listeners;

import me.lolok.crates.views.view.IView;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.InventoryHolder;

/**
 * Represents a dispatcher listener for the views.
 * Every event is passed to the correct listener, stored inside the view instances.
 * @see ClickListener
 * @see CloseListener
 * @see DragListener
 * @see OpenListener
 * @see ViewListener
 */
public class ViewDispatcherListener implements Listener {

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event) {
        InventoryHolder holder = event.getInventory().getHolder();
        if (!(holder instanceof IView))
            return;

        IView view = (IView) holder;
        view.setViewer((Player) event.getPlayer());
        view.getOpenListener().ifPresent(listener -> listener.accept(event, view));
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        InventoryHolder holder = event.getInventory().getHolder();
        if (!(holder instanceof IView))
            return;

        IView view = (IView) holder;
        view.getDragListener().ifPresent(listener -> listener.accept(event, view));
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        InventoryHolder holder = event.getInventory().getHolder();
        if (!(holder instanceof IView))
            return;

        IView view = (IView) holder;
        view.getCloseListener().ifPresent(listener -> listener.accept(event, view));
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        InventoryHolder holder = event.getInventory().getHolder();
        if (!(holder instanceof IView))
            return;

        IView view = (IView) holder;
        view.getContentHandler().getListener(event.getSlot()).ifPresent(listener -> listener.accept(event, view));
    }
    
}
