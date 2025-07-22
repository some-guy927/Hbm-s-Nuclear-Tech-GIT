package com.hbm.tileentity;

import api.hbm.energy.ILoadedTile;
import com.hbm.handler.threading.PacketThreading;
import com.hbm.packet.BufPacket;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import org.jetbrains.annotations.NotNull;

public class TileEntityLoadedBase extends TileEntity implements ILoadedTile, IBufPacketReceiver {
	
	public boolean isLoaded = false;
	
	@Override
	public boolean isLoaded() {
		return isLoaded;
	}

	@Override
	public void onChunkUnload() {
		super.onChunkUnload();
		this.isLoaded = false;
	}

    @Override
    public void onLoad() {
        super.onLoad();
        this.isLoaded = true;
    }
}
