package com.hbm.util.datafix;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.datafix.IFixableData;
import org.jetbrains.annotations.NotNull;

public class CircuitMetaFix implements IFixableData {

    @Override
    public int getFixVersion() {
        return 2;
    }

    @Override
    public @NotNull NBTTagCompound fixTagCompound(NBTTagCompound tag) {

        if (tag.hasKey("inventory")) {
            NBTTagCompound inventory = tag.getCompoundTag("inventory");

            if (inventory.hasKey("Items", 9)) {
                NBTTagList items = inventory.getTagList("Items", 10);

                for (int i = 0; i < items.tagCount(); i++) {

                    NBTTagCompound stack = items.getCompoundTagAt(i);
                    String id = stack.getString("id");

                    if (id.startsWith("hbm:circuit_")) {
                        updateMeta(stack, id);
                    }
                }
            }
        } else if (tag.hasKey("id", 8)) {
            String id = tag.getString("id");

            if (id.startsWith("hbm:circuit_")) {
                updateMeta(tag, id);
            }

        }
        return tag;
    }

    public void updateMeta(NBTTagCompound tag, String id) {
        int newMeta = 14;
        String itemName = id.substring(0, 11);
        String type = id.substring(12);

        if (type.equals("raw") || type.equals("aluminium") || type.endsWith("1")) {
            newMeta = 4;
        } else if (type.equals("copper") || type.endsWith("2")) {
            newMeta = 8;
        } else if (type.equals("red_copper") || type.endsWith("3")) {
            newMeta = 10;
        } else if (type.equals("gold") || type.endsWith("4")) {
            newMeta = 11;
        } else if (type.equals("schrabidium") || type.endsWith("5")) {
            newMeta = 13;
        } else if (type.endsWith("6")) {
            newMeta = 15;
        }

        try {
            tag.setString("id", itemName);
            tag.setShort("Damage", (short) newMeta);
        } catch (Exception e) {
            System.out.println("Circuit not found: " + type);
        }
    }
}
