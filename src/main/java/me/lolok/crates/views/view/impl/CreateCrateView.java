package me.lolok.crates.views.view.impl;

import com.cryptomorin.xseries.XMaterial;
import me.lolok.crates.CratesPlugin;
import me.lolok.crates.views.components.Button;
import me.lolok.crates.items.builder.ItemBuilder;
import org.bukkit.inventory.ItemStack;

public class CreateCrateView extends View {
    public CreateCrateView(String crateName) {
        super(String.format("§l» §8Confirm creation of create %s?", crateName), 3);

        ItemStack confirm = new ItemBuilder().material(XMaterial.LIME_WOOL.parseMaterial()).name("§2§l» §aConfirm").build();
        this.contentHandler.setItem(12, Button.of(confirm, (event, view) -> CratesPlugin.getInstance().getCrateService().create(crateName, event.getWhoClicked().getInventory().getItemInMainHand())));

        ItemStack cancel = new ItemBuilder().material(XMaterial.RED_WOOL.parseMaterial()).name("§4§l» §cCancel").build();
        this.contentHandler.setItem(14, Button.of(cancel, (event, view) -> event.getWhoClicked().closeInventory()));
    }
}
