package com.hbm.blocks.generic;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.gui.GUIScreenBobble;
import com.hbm.items.special.ItemPlasticScrap.ScrapType;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.world.gen.INBTTileEntityTransformable;
import com.hbm.world.gen.INBTTransformable;

import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraftforge.fml.common.network.internal.FMLNetworkHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.Random;
import javax.annotation.Nullable;

public class BlockBobble extends BlockContainer implements INBTTransformable {

	public static final PropertyInteger ROTATION = PropertyInteger.create("rotation", 0, 15);

	public BlockBobble(String s) {
		super(Material.IRON);
		this.setTranslationKey(s);
		this.setRegistryName(s);
		this.setHarvestLevel("pickaxe", 0);

		ModBlocks.ALL_BLOCKS.add(this);
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isNormalCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isNormalCube(IBlockState state, IBlockAccess world, BlockPos pos) {
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isBlockNormalCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isFullBlock(IBlockState state)
	{
		return false;
	}

	@Override
	public int getLightOpacity(IBlockState state)
	{
		return 0;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public float getAmbientOcclusionLightValue(IBlockState state)
	{
		return 1.0F;
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int j) {
		return Items.AIR;
	}

	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {

		TileEntityBobble entity = (TileEntityBobble) world.getTileEntity(pos);

		if(entity != null) {
			return new ItemStack(this, 1, entity.type.ordinal());
		}

		return super.getPickBlock(state, target, world, pos, player);
	}

	@Override
	public void onBlockHarvested(World world, BlockPos pos, IBlockState state, EntityPlayer player) {

		if(!player.capabilities.isCreativeMode) {
			harvesters.set(player);
			if(!world.isRemote) {
				TileEntity tile = world.getTileEntity(pos);
				if(tile instanceof TileEntityBobble) {
				  TileEntityBobble entity = (TileEntityBobble) tile;
				  ItemStack stack = new ItemStack(this, 1, entity.type.ordinal());
					EntityItem item = new EntityItem(world, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, stack);
					item.motionX = 0;
					item.motionY = 0;
					item.motionZ = 0;
					world.spawnEntity(item);
				}
			}
			harvesters.set(null);
		}
	}

	@Override
	public void harvestBlock(World world, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity te, ItemStack stack) {
		player.addStat(StatList.getBlockStats(this));
		player.addExhaustion(0.025F);
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {

		if(world.isRemote) {
			FMLNetworkHandler.openGui(player, MainRegistry.instance, 0, world, pos.getX(), pos.getY(), pos.getZ());
			return true;

		} else {
			return true;
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> items) {

		for(int i = 1; i < BobbleType.values().length; i++)
			items.add(new ItemStack(this, 1, i));
	}

	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase player, ItemStack stack) {
		int meta = MathHelper.floor((double)((player.rotationYaw + 180.0F) * 16.0F / 360.0F) + 0.5D) & 15;
		world.setBlockState(pos, state.withProperty(ROTATION, meta), 2);

		TileEntityBobble bobble = (TileEntityBobble) world.getTileEntity(pos);
		bobble.type = BobbleType.values()[Math.abs(stack.getItemDamage()) % BobbleType.values().length];
		bobble.markDirty();
	}

	@Override
  	@Nullable
  	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
    	float f = 0.0625F;
    	return new AxisAlignedBB(
      	5.5F * f, 0.0F, 5.5F * f,
      	1.0F - 5.5F * f, 0.625F, 1.0F - 5.5F * f
    	);
  	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
		return getBoundingBox(state, world, pos);
	}

	@Override
	public int transformMeta(int meta, int coordBaseMode) {
		return (meta + coordBaseMode * 4) % 16;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityBobble();
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, ROTATION);
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(ROTATION, meta);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(ROTATION);
	}

	public static class TileEntityBobble extends TileEntity implements INBTTileEntityTransformable, IGUIProvider {

		public BobbleType type = BobbleType.NONE;

		@Override
		public int getBlockMetadata() {
			if (world != null && pos != null) {
				return getBlockType().getMetaFromState(world.getBlockState(pos));
			}
			return 0;
		}

		@Override
		public SPacketUpdateTileEntity getUpdatePacket() {
			NBTTagCompound nbt = new NBTTagCompound();
			this.writeToNBT(nbt);
			return new SPacketUpdateTileEntity(this.pos, 0, nbt);
		}

		@Override
		public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
			this.readFromNBT(pkt.getNbtCompound());
		}

		@Override
		public void readFromNBT(NBTTagCompound nbt) {
			super.readFromNBT(nbt);
			this.type = BobbleType.values()[Math.abs(nbt.getByte("type")) % BobbleType.values().length];
		}

		@Override
		public @NotNull NBTTagCompound writeToNBT(NBTTagCompound nbt) {
			super.writeToNBT(nbt);
			nbt.setByte("type", (byte) type.ordinal());
			return nbt;
		}

		@Override
		public NBTTagCompound getUpdateTag() {
			return writeToNBT(super.getUpdateTag());
		}

		@Override
		public void handleUpdateTag(NBTTagCompound tag) {
			readFromNBT(tag);
		}

		@Override
		public void transformTE(World world, int coordBaseMode) {
			type = BobbleType.values()[world.rand.nextInt(BobbleType.values().length - 1) + 1];
		}

		@Override
		public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
			return null;
		}

		@Override
		@SideOnly(Side.CLIENT)
		public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
			return new GUIScreenBobble(this);
		}
	}

	public static enum BobbleType {

		NONE(			"null",								"null",				null,														null,																								false,	ScrapType.BOARD_BLANK),
		STRENGTH(		"Strength",							"Strength",			null,														"It's essential to give your arguments impact.",													false,	ScrapType.BRIDGE_BIOS),
		PERCEPTION(		"Perception",						"Perception",		null,														"Only through observation will you perceive weakness.",												false,	ScrapType.BRIDGE_NORTH),
		ENDURANCE(		"Endurance",						"Endurance",		null,														"Always be ready to take one for the team.",														false,	ScrapType.BRIDGE_SOUTH),
		CHARISMA(		"Charisma",							"Charisma",			null,														"Nothing says pizzaz like a winning smile.",														false,	ScrapType.BRIDGE_IO),
		INTELLIGENCE(	"Intelligence",						"Intelligence",		null,														"It takes the smartest individuals to realize$there's always more to learn.",						false,	ScrapType.BRIDGE_BUS),
		AGILITY(		"Agility",							"Agility",			null,														"Never be afraid to dodge the sensitive issues.",													false,	ScrapType.BRIDGE_CHIPSET),
		LUCK(			"Luck",								"Luck",				null,														"There's only one way to give 110%.",																false,	ScrapType.BRIDGE_CMOS),
		BOB(			"Robert \"The Bobcat\" Katzinsky",	"HbMinecraft",		"Hbm's Nuclear Tech Mod",									"I know where you live, " + System.getProperty("user.name"),									false,	ScrapType.CPU_SOCKET),
		FRIZZLE(		"Frooz",							"Frooz",			"Weapon models",											"BLOOD IS FUEL",																					true,	ScrapType.CPU_CLOCK),
		PU238(			"Pu-238",							"Pu-238",			"Improved Tom impact mechanics",							null,																								false,	ScrapType.CPU_REGISTER),
		VT(				"VT-6/24",							"VT-6/24",			"Balefire warhead model and general texturework",			"You cannot unfuck a horse.",																		true,	ScrapType.CPU_EXT),
		DOC(			"The Doctor",						"Doctor17PH",		"Russian localization, lunar miner",						"Perhaps the moon rocks were too expensive",														true,	ScrapType.CPU_CACHE),
		BLUEHAT(		"The Blue Hat",						"The Blue Hat",		"Textures",													"payday 2's deagle freeaim champ of the year 2022",													true,	ScrapType.MEM_16K_A),
		PHEO(			"Pheo",								"Pheonix",			"Deuterium machines, tantalium textures, Reliant Rocket",	"RUN TO THE BEDROOM, ON THE SUITCASE ON THE LEFT,$YOU'LL FIND MY FAVORITE AXE",						true,	ScrapType.MEM_16K_B),
		ADAM29(			"Adam29",							"Adam29",			"Ethanol, liquid petroleum gas",							"You know, nukes are really quite beatiful.$It's like watching a star be born for a split second.",	true,	ScrapType.MEM_16K_C),
		UFFR(			"UFFR",								"UFFR",				"All sorts of things from his PR",							"fried shrimp",																						false,	ScrapType.MEM_SOCKET),
		VAER(			"vaer",								"vaer",				"ZIRNOX",													"taken de family out to the weekend cigarette festival",											true,	ScrapType.MEM_16K_D),
		NOS(			"Dr Nostalgia",						"Dr Nostalgia",		"SSG and Vortex models",									"Take a picture, I'ma pose, paparazzi$I've been drinking, moving like a zombie",					true,	ScrapType.BOARD_TRANSISTOR),
		DRILLGON(		"Drillgon200",						"Drillgon200",		"1.12 Port",												null,																								false,	ScrapType.CPU_LOGIC),
		CIRNO(			"Cirno",							"Cirno",			"the only multi layered skin i had",						"No brain. Head empty.",																			true,	ScrapType.BOARD_BLANK),
		MICROWAVE(		"Microwave",						"Microwave",		"OC Compatibility and massive RBMK/packet optimizations",	"they call me the food heater$john optimization",													true,	ScrapType.BOARD_CONVERTER),
		PEEP(			"Peep",								"LePeeperSauvage",	"Coilgun, Leadburster and Congo Lake models, BDCL QC",		"Fluffy ears can't hide in ash, nor snow.",															true,	ScrapType.CARD_BOARD),
		MELLOW(			"MELLOWARPEGGIATION",				"Mellow",			"NBT Structures, industrial lighting, animation tools",		"Make something cool now, ask for permission later.",												true,	ScrapType.CARD_PROCESSOR),
		ABEL(			"Abel1502", 						"Abel1502", 		"Abilities GUI, optimizations and many QoL improvements", 	"NANTO SUBARASHII",																				true,	ScrapType.CPU_REGISTER);

		public String name;			//the title of the tooltip
		public String label;		//the name engraved in the socket
		public String contribution;	//what contributions this person has made, if applicable
		public String inscription;	//the flavor text
		public boolean skinLayers;
		public ScrapType scrap;

		private BobbleType(String name, String label, String contribution, String inscription, boolean layers, ScrapType scrap) {
			this.name = name;
			this.label = label;
			this.contribution = contribution;
			this.inscription = inscription;
			this.skinLayers = layers;
			this.scrap = scrap;
		}
	}
}
