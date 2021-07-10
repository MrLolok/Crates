package me.lolok.crates.crates.users.tasks;

import lombok.RequiredArgsConstructor;
import me.lolok.crates.crates.users.ICrateUsersService;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

@RequiredArgsConstructor
public class UsersClearTask extends BukkitRunnable {
    private final static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("HH:mm:ss");
    private final ICrateUsersService service;

    @Override
    public void run() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        if (DATE_FORMAT.format(timestamp).equals("00:00:00"))
            service.clear();
    }
}
