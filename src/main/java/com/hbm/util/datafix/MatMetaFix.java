package com.hbm.util.datafix;

import com.hbm.inventory.material.Mats;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.datafix.IFixableData;
import org.jetbrains.annotations.NotNull;

public class MatMetaFix implements IFixableData {

    @Override
    public int getFixVersion() {
        return 1;
    }

    @Override
    public @NotNull NBTTagCompound fixTagCompound(NBTTagCompound tag) {
        if (tag.hasKey("id", 8)) {
            String id = tag.getString("id");

            if (id.startsWith("hbm:wire_") || id.startsWith("hbm:bolt_")) {
                String itemName = id.substring(0, 8);
                String matName = id.substring(9);
                matName = matName.substring(0, 1).toUpperCase() + matName.substring(1);
                if (matName.equals("Red_copper")) {
                    matName = "Mingrade";
                } else if (matName.equals("Advanced_alloy")) {
                    matName = "AdvancedAlloy";
                } else if (matName.equals("Magnetized_tungsten")) {
                    matName = "MagnetizedTungsten";
                } else if (matName.equals("Dura_steel")) {
                    matName = "DuraSteel";
                }
                try {
                    int newMeta = Mats.matByName.get(matName).id;
                    tag.setString("id", itemName);
                    tag.setShort("Damage", (short) newMeta);
                } catch (Exception e) {
                    System.out.println("Material not found: " + matName);
                }
            }
        }

        return tag;
    }
}
