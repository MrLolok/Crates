package me.lolok.crates.views.view.impl;

import me.lolok.crates.views.listeners.CloseListener;
import me.lolok.crates.views.listeners.DragListener;
import me.lolok.crates.views.listeners.OpenListener;
import me.lolok.crates.views.view.IView;
import me.lolok.crates.views.view.ViewContentHandler;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class View implements IView {

    @Getter
    protected final String title;
    @Getter
    protected final Object size;

    @Getter @Setter
    protected Player viewer;
    @Getter
    protected ViewContentHandler contentHandler;

    @Setter @Nullable private OpenListener openListener;
    @Setter @Nullable private CloseListener closeListener;
    @Setter @Nullable private DragListener dragListener;

    public View(String title, Object size) {
        this.title = title;
        this.size = size;
        setupGUIContentHandler();
    }

    @Override
    public Optional<OpenListener> getOpenListener() {
        return Optional.ofNullable(openListener);
    }

    @Override
    public Optional<DragListener> getDragListener() {
        return Optional.ofNullable(dragListener);
    }

    @Override
    public Optional<CloseListener> getCloseListener() {
        return Optional.ofNullable(closeListener);
    }

    private void setupGUIContentHandler() {
        Inventory inventory = size instanceof Integer ? Bukkit.createInventory(this, (int) size * 9, title) : Bukkit.createInventory(this, (InventoryType) size, title);
        this.contentHandler = new ViewContentHandler(inventory);
    }

    @Override
    public @NotNull Inventory getInventory() {
        return contentHandler.getInventory();
    }
}
