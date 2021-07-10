package me.lolok.crates.database.subscribers.impl;

import lombok.RequiredArgsConstructor;
import me.lolok.crates.crates.crate.ICrateService;
import me.lolok.crates.crates.crate.objects.DefaultCrate;
import me.lolok.crates.crates.crate.objects.Crate;
import me.lolok.crates.database.subscribers.OperationSubscriber;
import me.lolok.crates.items.ItemParser;
import me.lolok.crates.items.objects.DefaultCrateItem;
import me.lolok.crates.items.objects.CrateItem;
import org.bson.Document;
import org.bukkit.inventory.ItemStack;

import java.util.LinkedList;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class CratesLoadingSubscriber extends OperationSubscriber<Document> {
    private final ICrateService service;

    @Override
    public void onNext(Document value) {
        String name = value.getString("name");
        ItemStack item = ItemParser.fromJSON(value.getString("item"));
        Set<CrateItem> prizes = new LinkedList<>(value.getList("prizes", Document.class)).stream()
                .map(document -> new DefaultCrateItem(ItemParser.fromJSON(document.getString("item")), document.getDouble("chance")))
                .collect(Collectors.toSet());

        Crate crate = new DefaultCrate(service, name, item, prizes);
        service.addCrate(crate);
    }
}
