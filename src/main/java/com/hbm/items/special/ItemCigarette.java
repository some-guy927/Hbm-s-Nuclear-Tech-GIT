package com.hbm.items.special;

import java.util.List;

import com.hbm.capability.HbmLivingProps;
import com.hbm.items.ModItems;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.util.*;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class ItemCigarette extends ItemFood {

	public ItemCigarette(String s) {
        super(0, false);
        this.setTranslationKey(s);
		this.setRegistryName(s);
		this.setAlwaysEdible();

		ModItems.ALL_ITEMS.add(this);
	}

	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		return EnumAction.BOW;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return 30;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		player.setActiveHand(hand);
		return super.onItemRightClick(world, player, hand);
	}

	@Override
	public void onFoodEaten(ItemStack stack, World world, EntityPlayer player) {
		//stack.shrink(1);

		if(!world.isRemote) {

			if(this == ModItems.cigarette) {
				HbmLivingProps.incrementBlackLung(player, 2000);
				HbmLivingProps.incrementAsbestos(player, 2000);
				HbmLivingProps.incrementRadiation(player, 100F);
			}

			world.playSound(player, player.posX, player.posY, player.posZ,
					SoundEvent.REGISTRY.getObject(new ResourceLocation("hbm:player.cough")),
					SoundCategory.PLAYERS, 1.0F, 1.0F);

			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setString("type", "vomit");
			nbt.setString("mode", "smoke");
			nbt.setInteger("count", 30);
			nbt.setInteger("entity", player.getEntityId());
		}

	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, World worldIn, List<String> list, ITooltipFlag flagIn) {

		if(this == ModItems.cigarette) {
			list.add(TextFormatting.RED + "✓ Asbestos filter");
			list.add(TextFormatting.RED + "✓ High in tar");
			list.add(TextFormatting.RED + "✓ Tobacco contains 100% Polonium-210");
			list.add(TextFormatting.RED + "✓ Yum");
		} else {
			String[] colors = new String[] {
					TextFormatting.RED + "",
					TextFormatting.GOLD + "",
					TextFormatting.YELLOW + "",
					TextFormatting.GREEN + "",
					TextFormatting.AQUA + "",
					TextFormatting.BLUE + "",
					TextFormatting.DARK_PURPLE + "",
					TextFormatting.LIGHT_PURPLE + "",
			};
			int len = 2000;
			list.add("This can't be good for me, but I feel " + colors[(int)(System.currentTimeMillis() % len * colors.length / len)] + "GREAT");
		}
	}
}
