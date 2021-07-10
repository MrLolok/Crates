package me.lolok.crates.reflections;

import org.bukkit.Bukkit;

public class ReflectionsUtils {
    public static final String VERSION = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
    public static final int NUMERICAL_VERSION = Integer.parseInt(VERSION.split("_")[1]);

    public static Class<?> getNMSClass(String name) {
        try {
            return Class.forName("net.minecraft.server." + VERSION + "." + name);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(String.format("Unable to find NMS class %s", name), e);
        }
    }

    public static Class<?> getOBCClass(String name) {
        try {
            return Class.forName("org.bukkit.craftbukkit." + VERSION + "." + name);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(String.format("Unable to find OBC class %s", name), e);
        }
    }
}
