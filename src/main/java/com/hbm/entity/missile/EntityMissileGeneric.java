package com.hbm.entity.missile;

import java.util.ArrayList;
import java.util.List;

import api.hbm.entity.IRadarDetectable;
import api.hbm.entity.IRadarDetectableNT;
import com.hbm.explosion.ExplosionLarge;
import com.hbm.items.ModItems;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityMissileGeneric extends EntityMissileBaseAdvanced implements IRadarDetectable {

	public EntityMissileGeneric(World worldIn) {
		super(worldIn);
		this.setSize(1F, 6F);
	}
	
	public EntityMissileGeneric(World world, float x, float y, float z, int a, int b) {
		super(world, x, y, z, a, b);
		this.setSize(1F, 6F);
	}

	@Override
	public void onImpact() {
		ExplosionLarge.explode(world, posX, posY, posZ, 15.0F, true, true, true);
	}

	@Override
	public RadarTargetType getTargetType() {
		return RadarTargetType.MISSILE_TIER1;
	}

	@Override
	public List<ItemStack> getDebris() {
		List<ItemStack> list = new ArrayList<ItemStack>();

		list.add(new ItemStack(ModItems.plate_titanium, 4));
		list.add(new ItemStack(ModItems.thruster_small, 1));
		
		return list;
	}

	@Override
	public ItemStack getDebrisRareDrop() {
		return new ItemStack(ModItems.warhead_generic_small);
	}

	@Override
	public String getTranslationKey() {
		return "radar.target.tier1";
	}

	@Override
	public int getBlipLevel() {
		return IRadarDetectableNT.TIER1;
	}

	@Override
	public boolean canBeSeenBy(Object radar) {
		return true;
	}

	@Override
	public boolean paramsApplicable(RadarScanParams params) {
		return params.scanMissiles;
	}

	@Override
	public boolean suppliesRedstone(RadarScanParams params) {
		return !params.smartMode || !(this.motionY >= 0);
	}
}
