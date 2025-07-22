package com.hbm.items.tool;

import java.util.List;

import com.hbm.inventory.gui.GUIScreenDesignator;
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
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemDesignatorManual extends Item implements IGUIProvider {

	public ItemDesignatorManual(String s) {
		this.setTranslationKey(s);
		this.setRegistryName(s);
		this.setCreativeTab(MainRegistry.missileTab);
		
		ModItems.ALL_ITEMS.add(this);
	}
	
	@Override
	public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn) {
		stack.setTagCompound(new NBTTagCompound());
		stack.getTagCompound().setInteger("xCoord", 0);
		stack.getTagCompound().setInteger("zCoord", 0);
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		if(stack.getTagCompound() != null)
		{
			tooltip.add(TextFormatting.GREEN + I18nUtil.resolveKey("desc.targetcoord")+"§r");
			tooltip.add("§aX: " + stack.getTagCompound().getInteger("xCoord") +"§r");
			tooltip.add("§aZ: " + stack.getTagCompound().getInteger("zCoord") +"§r");
		} else {
			tooltip.add(TextFormatting.YELLOW + I18nUtil.resolveKey("desc.choosetarget2"));
		}
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		if(worldIn.isRemote)
			playerIn.openGui(MainRegistry.instance, 0, worldIn, handIn == EnumHand.MAIN_HAND ? 1 : 0, 0, 0);
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return null;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIScreenDesignator(player);
	}
}
