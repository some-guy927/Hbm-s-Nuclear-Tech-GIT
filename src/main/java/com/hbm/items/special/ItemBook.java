package com.hbm.items.special;

import java.util.List;

import com.hbm.inventory.container.ContainerBook;
import com.hbm.inventory.gui.GUIBook;
import com.hbm.items.ModItems;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.IGUIProvider;
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

public class ItemBook extends Item implements IGUIProvider {

	public ItemBook(String s) {
		this.setTranslationKey(s);
		this.setRegistryName(s);
		
		ModItems.ALL_ITEMS.add(this);
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add("Edition 4, gold lined pages");
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		if(!world.isRemote)
			player.openGui(MainRegistry.instance, 0, world, 0, 0, 0);

		return super.onItemRightClick(world, player, hand);
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerBook(player.inventory);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIBook(player.inventory);
	}
}
