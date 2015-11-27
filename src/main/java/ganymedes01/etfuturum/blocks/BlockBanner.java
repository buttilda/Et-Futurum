package ganymedes01.etfuturum.blocks;

import java.util.ArrayList;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.IConfigurable;
import ganymedes01.etfuturum.ModBlocks.ISubBlocksBlock;
import ganymedes01.etfuturum.core.utils.Utils;
import ganymedes01.etfuturum.items.block.ItemBanner;
import ganymedes01.etfuturum.tileentities.TileEntityBanner;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;

public class BlockBanner extends BlockContainer implements ISubBlocksBlock, IConfigurable {

	public BlockBanner() {
		super(Material.wood);
		disableStats();
		setHardness(1.0F);
		setStepSound(soundTypeWood);
		setBlockName(Utils.getUnlocalisedName("banner"));
		setCreativeTab(EtFuturum.enableBanners ? EtFuturum.creativeTab : null);

		float f = 0.25F;
		float f1 = 1.0F;
		setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f1, 0.5F + f);
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
		TileEntityBanner tile = Utils.getTileEntity(world, x, y, z, TileEntityBanner.class);
		if (tile == null)
			return;

		if (!tile.isStanding) {
			int meta = world.getBlockMetadata(x, y, z);
			float f = 0.0F;
			float f1 = 0.78125F;
			float f2 = 0.0F;
			float f3 = 1.0F;
			float f4 = 0.125F;

			setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);

			if (meta == 2)
				setBlockBounds(f2, f, 1.0F - f4, f3, f1, 1.0F);
			if (meta == 3)
				setBlockBounds(f2, f, 0.0F, f3, f1, f4);
			if (meta == 4)
				setBlockBounds(1.0F - f4, f, f2, 1.0F, f1, f3);
			if (meta == 5)
				setBlockBounds(0.0F, f, f2, f4, f1, f3);
		} else {
			float f = 0.25F;
			float f1 = 1.0F;
			setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f1, 0.5F + f);
		}
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block neighbour) {
		TileEntityBanner tile = Utils.getTileEntity(world, x, y, z, TileEntityBanner.class);
		if (tile == null)
			return;

		boolean flag = false;
		if (tile.isStanding) {
			if (!world.getBlock(x, y - 1, z).getMaterial().isSolid())
				flag = true;
		} else {
			int meta = world.getBlockMetadata(x, y, z);
			flag = true;
			if (meta == 2 && world.getBlock(x, y, z + 1).getMaterial().isSolid())
				flag = false;
			if (meta == 3 && world.getBlock(x, y, z - 1).getMaterial().isSolid())
				flag = false;
			if (meta == 4 && world.getBlock(x + 1, y, z).getMaterial().isSolid())
				flag = false;
			if (meta == 5 && world.getBlock(x - 1, y, z).getMaterial().isSolid())
				flag = false;
		}

		if (flag) {
			dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
			world.setBlockToAir(x, y, z);
		}

		super.onNeighborBlockChange(world, x, y, z, neighbour);
	}

	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z, EntityPlayer player) {
		TileEntityBanner banner = Utils.getTileEntity(world, x, y, z, TileEntityBanner.class);
		if (banner != null)
			return banner.createStack();

		return super.getPickBlock(target, world, x, y, z, player);
	}

	@Override
	public void onBlockHarvested(World world, int x, int y, int z, int meta, EntityPlayer player) {
		if (!player.capabilities.isCreativeMode) {
			ArrayList<ItemStack> drops = getDrops(world, x, y, z, meta, 0);
			if (ForgeEventFactory.fireBlockHarvesting(drops, world, this, x, y, z, meta, 0, 1.0F, false, player) > 0.0F)
				for (ItemStack stack : drops)
					dropBlockAsItem(world, x, y, z, stack);
		}
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
		ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
		TileEntityBanner banner = Utils.getTileEntity(world, x, y, z, TileEntityBanner.class);
		if (banner != null)
			ret.add(banner.createStack());
		return ret;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return Blocks.planks.getBlockTextureFromSide(side);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		return null;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z) {
		setBlockBoundsBasedOnState(world, x, y, z);
		return super.getSelectedBoundingBoxFromPool(world, x, y, z);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityBanner();
	}

	@Override
	public int getRenderType() {
		return -1;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean getBlocksMovement(IBlockAccess world, int x, int y, int z) {
		return true;
	}

	@Override
	public Class<? extends ItemBlock> getItemBlockClass() {
		return ItemBanner.class;
	}

	@Override
	public boolean isEnabled() {
		return EtFuturum.enableBanners;
	}
}