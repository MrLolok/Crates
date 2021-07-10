package me.lolok.crates.database.subscribers.impl;

import lombok.RequiredArgsConstructor;
import me.lolok.crates.crates.users.objects.DefaultCrateUser;
import me.lolok.crates.crates.users.objects.CrateUser;
import me.lolok.crates.crates.users.ICrateUsersService;
import me.lolok.crates.database.subscribers.OperationSubscriber;
import org.bson.Document;

import java.util.UUID;

@RequiredArgsConstructor
public class UsersLoadingSubscriber extends OperationSubscriber<Document> {
    private final ICrateUsersService service;

    @Override
    public void onNext(Document value) {
        CrateUser user = new DefaultCrateUser(UUID.fromString(value.getString("uuid")));
        user.setOpened(value.getInteger("opened"));
        user.setLastOpen(value.getLong("lastOpen"));
        service.addUser(user);
    }
}
