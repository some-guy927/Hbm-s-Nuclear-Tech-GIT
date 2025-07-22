package com.hbm.items.machine;

import java.util.List;

import com.hbm.inventory.gui.GUIScreenGuide;
import com.hbm.inventory.gui.GUIScreenTemplateFolder;
import com.hbm.items.ModItems;
import com.hbm.main.MainRegistry;

import com.hbm.tileentity.IGUIProvider;
import com.hbm.util.I18nUtil;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemTemplateFolder extends Item implements IGUIProvider {

	public ItemTemplateFolder(String s) {
		this.setTranslationKey(s);
		this.setRegistryName(s);
		
		ModItems.ALL_ITEMS.add(this);
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add(I18nUtil.resolveKey("desc.templfolder1"));
		tooltip.add(I18nUtil.resolveKey("desc.templfolder2"));
		tooltip.add(I18nUtil.resolveKey("desc.templfolder3"));
		tooltip.add(I18nUtil.resolveKey("desc.templfolder4"));
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		if(worldIn.isRemote)
			playerIn.openGui(MainRegistry.instance, 0, worldIn, 0, 0, 0);
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return null;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIScreenTemplateFolder(player);
	}
}
