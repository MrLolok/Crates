package me.lolok.crates.items.nbt;

import com.google.common.base.Preconditions;
import me.lolok.crates.reflections.ReflectionsUtils;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.InvocationTargetException;

public class NBTUtils {
    @Nullable
    public static Object getNMSItemStack(ItemStack item) {
        try {
            return ReflectionsUtils.getOBCClass("CraftItemStack").getMethod("asNMSCopy", ItemStack.class).invoke(null, item);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException("Cannot obtain an NMS ItemStack from a classic ItemStack", e);
        }
    }

    @Nullable
    public static ItemStack getBukkitItemStack(Object NMSItem) {
        try {
            return (ItemStack) ReflectionsUtils.getOBCClass("CraftItemStack").getMethod("asBukkitCopy", NMSItem.getClass()).invoke(null, NMSItem);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException("Cannot obtain a classic ItemStack from an NMS ItemStack", e);
        }
    }

    private static Object newNBTCompound() {
        try {
            return ReflectionsUtils.getNMSClass("NBTTagCompound").newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException("Cannot create a new NBTTagCompound", e);
        }
    }

    private static boolean hasNBTCompound(ItemStack item) {
        Object NMSItem = getNMSItemStack(item);
        Preconditions.checkNotNull(NMSItem, "NMS ItemStack is null");

        try {
            return (boolean) NMSItem.getClass().getMethod("hasTag").invoke(NMSItem);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException("Cannot check if the ItemStack has a tag", e);
        }
    }

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
