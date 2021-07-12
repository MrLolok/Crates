package me.lolok.crates.views.view.impl;

import com.cryptomorin.xseries.XMaterial;
import me.lolok.crates.CratesPlugin;
import me.lolok.crates.crates.crate.objects.Crate;
import me.lolok.crates.items.builder.ItemBuilder;
import me.lolok.crates.views.components.Button;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ConfirmOpeningCrateView extends View {
    public ConfirmOpeningCrateView(Crate crate) {
        super(String.format("§l» §8Confirm opening of create %s?", crate.getName()), 5);

        this.contentHandler.setItem(13, crate.getItem());

        ItemStack confirm = new ItemBuilder().material(XMaterial.LIME_WOOL.parseMaterial()).name("§2§l» §aConfirm").build();
        this.contentHandler.setItem(20, Button.of(confirm, (event, view) -> CratesPlugin.getInstance().getCrateService().open(crate, (Player) event.getWhoClicked())));

        ItemStack cancel = new ItemBuilder().material(XMaterial.RED_WOOL.parseMaterial()).name("§4§l» §cCancel").build();
        this.contentHandler.setItem(24, Button.of(cancel, (event, view) -> event.getWhoClicked().closeInventory()));
    }
}
