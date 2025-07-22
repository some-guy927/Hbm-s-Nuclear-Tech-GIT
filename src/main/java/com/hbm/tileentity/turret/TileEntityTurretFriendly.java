package com.hbm.tileentity.turret;

import java.util.ArrayList;
import java.util.List;

import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.inventory.gui.GUITurretFriendly;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityTurretFriendly extends TileEntityTurretChekhov {

	static List<Integer> configs = new ArrayList<>();
	
	static {
		configs.add(BulletConfigSyncingUtil.R5_NORMAL);
		configs.add(BulletConfigSyncingUtil.R5_EXPLOSIVE);
		configs.add(BulletConfigSyncingUtil.R5_DU);
		configs.add(BulletConfigSyncingUtil.R5_STAR);
		configs.add(BulletConfigSyncingUtil.CHL_R5);
	}
	
	@Override
	protected List<Integer> getAmmoList() {
		return configs;
	}

	@Override
	public String getName() {
		return "container.turretFriendly";
	}

	@Override
	public int getDelay() {
		return 5;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUITurretFriendly(player.inventory, this);
	}
}
