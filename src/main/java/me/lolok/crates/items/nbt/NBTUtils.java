package me.lolok.crates.items.nbt;

import com.google.common.base.Preconditions;
import me.lolok.crates.reflections.ReflectionsUtils;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.InvocationTargetException;

public class NBTUtils {

    /**
     * Convert a normal {@link ItemStack} to an NMS ItemStack
     * @param item to convert
     * @return NMS ItemStack
     */
    @Nullable
    public static Object getNMSItemStack(ItemStack item) {
        try {
            return ReflectionsUtils.getOBCClass("CraftItemStack").getMethod("asNMSCopy", ItemStack.class).invoke(null, item);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException("Cannot obtain an NMS ItemStack from a classic ItemStack", e);
        }
    }

    /**
     * Convert an NMS ItemStack to a normal {@link ItemStack}
     * @param NMSItem to convert
     * @return Bukkit's ItemStack
     */
    @Nullable
    public static ItemStack getBukkitItemStack(Object NMSItem) {
        try {
            return (ItemStack) ReflectionsUtils.getOBCClass("CraftItemStack").getMethod("asBukkitCopy", NMSItem.getClass()).invoke(null, NMSItem);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException("Cannot obtain a classic ItemStack from an NMS ItemStack", e);
        }
    }

    /**
     * Create a new NBTTagCompound
     * @return a new compound
     */
    private static Object newNBTCompound() {
        try {
            return ReflectionsUtils.getNMSClass("NBTTagCompound").newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException("Cannot create a new NBTTagCompound", e);
        }
    }

    /**
     * Check if an {@link ItemStack} has an NBTTagCompound by getting
     * his NMS version and checking if has a tag on it
     * @param item to check
     * @return true if has a tag on it
     */
    private static boolean hasNBTCompound(ItemStack item) {
        Object NMSItem = getNMSItemStack(item);
        Preconditions.checkNotNull(NMSItem, "NMS ItemStack is null");

        try {
            return (boolean) NMSItem.getClass().getMethod("hasTag").invoke(NMSItem);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException("Cannot check if the ItemStack has a tag", e);
        }
    }

    /**
     * Gets the NBTTagCompound from an {@link ItemStack} by getting his NMS
     * version and getting the tag from it.
     * If the item has no tag return a new compound invoking {@link NBTUtils#newNBTCompound()}
     * @param item to get the tag from
     * @return compound found
     */
    @Nullable
    protected static Object getTagCompound(ItemStack item) {
        Object NMSItem = getNMSItemStack(item);
        Preconditions.checkNotNull(NMSItem, "NMS ItemStack is null");

        try {
            return hasNBTCompound(item) ? NMSItem.getClass().getMethod("getTag").invoke(NMSItem) : newNBTCompound();
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException("Cannot obtain the NBT tags from an ItemStack", e);
        }
    }

    /**
     * Sets an NBTTagCompound to an {@link ItemStack} by getting his NMS
     * version and setting the tag on it
     * @param item to edit
     * @param compound to set
     * @return item with the new compound
     */
    @Nullable
    protected static ItemStack setNBTCompound(ItemStack item, Object compound) {
        Object NMSItem = getNMSItemStack(item);
        Preconditions.checkNotNull(NMSItem, "NMS ItemStack is null");

        try {
            NMSItem.getClass().getMethod("setTag", compound.getClass()).invoke(NMSItem, compound);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException("Cannot set the NBT tags to an ItemStack", e);
        }

        return getBukkitItemStack(NMSItem);
    }

}
