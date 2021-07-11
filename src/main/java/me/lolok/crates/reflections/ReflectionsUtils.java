package me.lolok.crates.reflections;

import org.bukkit.Bukkit;

/**
 * This is a class utils to obtain NMS and OBC classes.
 * Useful to support all server versions.
 */
public class ReflectionsUtils {
    /**
     * Server version, Eg. 1_16_R1
     */
    public static final String VERSION = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];

    /**
     * Numerical server version, Eg. 16
     */
    public static final int NUMERICAL_VERSION = Integer.parseInt(VERSION.split("_")[1]);

    /**
     * Gets an NMS class from net.minecraft.server package
     * @param name of the class to get
     * @return class found
     */
    public static Class<?> getNMSClass(String name) {
        try {
            return Class.forName("net.minecraft.server." + VERSION + "." + name);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(String.format("Unable to find NMS class %s", name), e);
        }
    }

    /**
     * Gets an OBC class from org.bukkit.craftbukkit package
     * @param name of the class to get
     * @return class found
     */
    public static Class<?> getOBCClass(String name) {
        try {
            return Class.forName("org.bukkit.craftbukkit." + VERSION + "." + name);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(String.format("Unable to find OBC class %s", name), e);
        }
    }
}
