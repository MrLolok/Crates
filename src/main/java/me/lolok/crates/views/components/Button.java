package me.lolok.crates.views.components;

import me.lolok.crates.views.listeners.ClickListener;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

/**
 * Represents a view button.
 * A listener may be linked to a button instance.
 * @see ClickListener
 */
public class Button {

    @Getter
    private final ItemStack item;

    @Nullable
    private ClickListener listener;

    public Button(ItemStack item) {
        this.item = item;
    }

    public Button(ItemStack item, @Nullable ClickListener listener) {
        this.item = item;
        this.listener = listener;
    }

    public static Button of(ItemStack builder, ClickListener listener) {
        return new Button(builder, listener);
    }

    public static Button of(ItemStack builder) {
        return new Button(builder);
    }

    public Optional<ClickListener> getListener() {
        return Optional.ofNullable(listener);
    }
}
