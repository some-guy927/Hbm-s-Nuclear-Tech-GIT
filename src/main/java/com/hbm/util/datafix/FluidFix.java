package com.hbm.util.datafix;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.datafix.IFixableData;
import org.jetbrains.annotations.NotNull;

public class FluidFix implements IFixableData {

    @Override
    public int getFixVersion() {
        return 2;
    }

    @Override
    public @NotNull NBTTagCompound fixTagCompound(NBTTagCompound tag) {

        if(tag.hasKey("FluidName")) {
            String name = tag.getString("FluidName");

            if(name.equals("watz")) {
                tag.setString("FluidName", "mud_fluid");
            }
        }

        return tag;
    }
}
