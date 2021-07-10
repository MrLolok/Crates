package me.lolok.crates.items.builder;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemBuilder {

    private Material material;
    private String name;
    private List<String> lore;
    private short data = 0;
    private int amount = 1;

    /**
     * This method sets the item material
     *
     * @param material The material
     * @return The ItemBuilder object
     */
    public ItemBuilder material(Material material) {
        this.material = material;
        return this;
    }

    /**
     * This method sets the item data
     *
     * @param data The data
     * @return The ItemBuilder object
     */
    public ItemBuilder data(int data) {
        this.data = (short) data;
        return this;
    }

    /**
     * This method sets the item material
     *
     * @param material The material
     * @return The ItemBuilder object
     */
    public ItemBuilder material(String material) {
        this.material = Material.valueOf(material);
        return this;
    }

    /**
     * This method sets the item name
     *
     * @param name The name
     * @return The ItemBuilder object
     */
    public ItemBuilder name(String name) {
        this.name = ChatColor.translateAlternateColorCodes('&', name);
        return this;
    }

    /**
     * This method sets the item lore
     *
     * @param lore The lore
     * @return The ItemBuilder object
     */
    public ItemBuilder lore(String... lore) {
        this.lore = new ArrayList<>();
        for (String s : lore) {
            this.lore.add(ChatColor.translateAlternateColorCodes('&', s));
        }
        return this;
    }

    /**
     * This method sets the item lore
     *
     * @param lore The lore
     * @return The ItemBuilder object
     */
    public ItemBuilder lore(List<String> lore) {
        return lore(lore.toArray(new String[0]));
    }

    /**
     * This method sets the item amount
     *
     * @param amount The amount
     * @return The ItemBuilder object
     */
    public ItemBuilder amount(int amount) {
        this.amount = amount;
        return this;
    }

    /**
     * This method use all the information given to return the ItemStack object
     *
     * @return The ItemStack object
     */
    public ItemStack build() {
        ItemStack item = new ItemStack(material, amount, data);
        ItemMeta meta = item.getItemMeta();

        if (name != null)
            meta.setDisplayName(name);

        if (lore != null)
            meta.setLore(lore);

        item.setItemMeta(meta);
        return item;
    }

    /**
     * This method create a new ItemBuilder object
     *
     * @return The ItemBuilder object
     */
    public static ItemBuilder create() {
        return new ItemBuilder();
    }

}
