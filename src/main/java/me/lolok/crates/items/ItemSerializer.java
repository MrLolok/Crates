package me.lolok.crates.items;

import com.google.common.base.Preconditions;
import me.lolok.crates.items.nbt.NBTUtils;
import me.lolok.crates.reflections.ReflectionsUtils;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.InvocationTargetException;

/**
 * This is a class utils to parse or serialize {@link ItemStack} to/from JSON.
 * It's used to serialize and store items of crates to MongoDB
 */
public class ItemSerializer {

    /**
     * Serialize an {@link ItemStack} to JSON
     * @param item to serialize
     * @return JSON string
     */
    @Nullable
    public static String toJSON(ItemStack item) {
        Object NMSItem = NBTUtils.getNMSItemStack(item);
        Preconditions.checkNotNull(NMSItem, "NMS ItemStack is null");

        try {
            Object compound = ReflectionsUtils.getNMSClass("NBTTagCompound").newInstance();
            NMSItem.getClass().getMethod("save", compound.getClass()).invoke(NMSItem, compound);
            return compound.toString();
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new RuntimeException("Cannot serialize the ItemStack to JSON", e);
        }
    }

    /**
     * Parse an {@link ItemStack} from JSON
     * @param json to parse
     * @return item parsed
     */
    @Nullable
    public static ItemStack fromJSON(String json) {
        try {
            Object compound = ReflectionsUtils.getNMSClass("MojangsonParser").getMethod("parse", String.class).invoke(null, json);
            Class<?> itemStackClass = ReflectionsUtils.getNMSClass("ItemStack");
            Object NMSItem = ReflectionsUtils.NUMERICAL_VERSION < 13 ? itemStackClass.getConstructor(ReflectionsUtils.getNMSClass("NBTTagCompound")).newInstance(compound) : itemStackClass.getMethod("a", compound.getClass()).invoke(null, compound);
            return NBTUtils.getBukkitItemStack(NMSItem);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | InstantiationException e) {
            throw new RuntimeException("Cannot parse the ItemStack from JSON", e);
        }
    }

}
