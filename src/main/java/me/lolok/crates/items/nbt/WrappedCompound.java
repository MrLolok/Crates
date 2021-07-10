package me.lolok.crates.items.nbt;

import me.lolok.crates.reflections.ReflectionsUtils;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

public class WrappedCompound {
    private final Object compound;

    public static WrappedCompound of(@NotNull ItemStack item) {
        return new WrappedCompound(item);
    }

    public WrappedCompound(@NotNull ItemStack item) {
        this.compound = NBTUtils.getTagCompound(item);
    }

    public WrappedCompound(Object compound) {
        this.compound = compound;
    }

    public String getString(String key) {
        return (String) get(key, NBTTagType.STRING);
    }

    public int getInt(String key) {
        return (int) get(key, NBTTagType.INTEGER);
    }

    public double getDouble(String key) {
        return (double) get(key, NBTTagType.DOUBLE);
    }

    public long getLong(String key) {
        return (long) get(key, NBTTagType.LONG);
    }

    public float getFloat(String key) {
        return (float) get(key, NBTTagType.FLOAT);
    }

    public boolean getBoolean(String key) {
        return (boolean) get(key, NBTTagType.BOOLEAN);
    }

    public WrappedCompound getCompound(String key) {
        return new WrappedCompound(get(key, NBTTagType.COMPOUND));
    }

    public Object get(String key, NBTTagType type) {
        try {
            return hasKey(key) ? compound.getClass().getMethod(type.getGetMethodName(), String.class).invoke(compound, key) : null;
        } catch (Exception e) {
            throw new RuntimeException(String.format("Unable to obtain value of %s", key), e);
        }
    }

    public WrappedCompound setString(String key, String value) {
        set(key, value, NBTTagType.STRING);
        return this;
    }

    public WrappedCompound setInt(String key, int value) {
        set(key, value, NBTTagType.INTEGER);
        return this;
    }

    public WrappedCompound setDouble(String key, double value) {
        set(key, value, NBTTagType.DOUBLE);
        return this;
    }

    public WrappedCompound setLong(String key, long value) {
        set(key, value, NBTTagType.LONG);
        return this;
    }

    public WrappedCompound setFloat(String key, float value) {
        set(key, value, NBTTagType.FLOAT);
        return this;
    }

    public WrappedCompound setBoolean(String key, boolean value) {
        set(key, value, NBTTagType.BOOLEAN);
        return this;
    }

    public WrappedCompound setCompound(String key, Object value) {
        set(key, value, NBTTagType.COMPOUND);
        return this;
    }

    public void set(String key, Object value, NBTTagType type) {
        try {
            Constructor<?> constructor = Objects.requireNonNull(ReflectionsUtils.getNMSClass(type.getClassName())).getDeclaredConstructors()[0];
            constructor.setAccessible(true);
            Object tag = constructor.newInstance(value);
            compound.getClass().getMethod("set", String.class, ReflectionsUtils.getNMSClass("NBTBase")).invoke(compound, key, tag);
        } catch (Exception e) {
            throw new RuntimeException(String.format("Unable to set value %s", key), e);
        }
    }

    public boolean hasKey(String key) {
        try {
            return (boolean) compound.getClass().getMethod("hasKey", String.class).invoke(compound, key);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }

        return false;
    }

    public ItemStack save(ItemStack item) {
        return NBTUtils.setNBTCompound(item, compound);
    }
}
