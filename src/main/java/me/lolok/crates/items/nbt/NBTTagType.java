package me.lolok.crates.items.nbt;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum NBTTagType {
    STRING("NBTTagString", "getString", "setString"),
    BOOLEAN("NBTTagBoolean", "getBoolean", "setBoolean"),
    DOUBLE("NBTTagDouble", "getDouble", "setDouble"),
    INTEGER("NBTTagInt", "getInt", "setInt"),
    LONG("NBTTagLong", "getLong", "setLong"),
    FLOAT("NBTTagFloat", "getFloat", "setFloat"),
    COMPOUND("NBTTagCompound", "getCompound", "setCompund");

    @Getter
    private final String className, getMethodName, setMethodName;
}
