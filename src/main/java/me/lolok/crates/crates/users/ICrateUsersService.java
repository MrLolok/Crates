package me.lolok.crates.crates.users;

import me.lolok.crates.crates.Service;
import me.lolok.crates.crates.users.objects.CrateUser;
import org.jetbrains.annotations.Nullable;

import java.util.Set;
import java.util.UUID;

public interface ICrateUsersService extends Service {

    Set<CrateUser> getUsers();

    @Nullable CrateUser getUser(UUID uuid);

    void addUser(CrateUser user);

    void open(UUID uuid);

    void clear();

}
