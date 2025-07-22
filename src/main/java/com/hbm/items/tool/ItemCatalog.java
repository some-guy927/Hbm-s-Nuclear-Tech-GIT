package com.hbm.items.tool;

import com.hbm.handler.BobmazonOfferFactory;
import com.hbm.inventory.gui.GUIScreenBobmazon;
import com.hbm.items.ModItems;
import com.hbm.main.MainRegistry;

import com.hbm.tileentity.IGUIProvider;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemCatalog extends Item implements IGUIProvider {

	public ItemCatalog(String s) {
		this.setTranslationKey(s);
		this.setRegistryName(s);
		
		ModItems.ALL_ITEMS.add(this);
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
		if (BobmazonOfferFactory.getOffers(player.getHeldItemMainhand()) != null || BobmazonOfferFactory.getOffers(player.getHeldItemOffhand()) != null)
			return new GUIScreenBobmazon(player, BobmazonOfferFactory.getOffers(player.getHeldItemMainhand()));
		return null;
	}
}
