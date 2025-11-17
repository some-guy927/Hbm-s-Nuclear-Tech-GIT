package com.hbm.util.datafix;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.datafix.IFixableData;
import org.jetbrains.annotations.NotNull;

public class BedrockOreFix implements IFixableData {

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

                    if (id.startsWith("hbm:ore_bedrock") && stack.getShort("Damage") > 40) {
                        int newMeta = stack.getShort("Damage") - 5;
                        stack.setShort("Damage", (short) newMeta);
                    }
                }
            }
        } else if (tag.hasKey("id", 8)) {
            String id = tag.getString("id");

            if (id.startsWith("hbm:ore_bedrock") && tag.getShort("Damage") > 40) {
                int newMeta = tag.getShort("Damage") - 5;
                tag.setShort("Damage", (short) newMeta);
            }
        }
        return tag;
    }
}
