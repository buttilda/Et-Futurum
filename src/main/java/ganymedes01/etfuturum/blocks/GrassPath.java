package ganymedes01.etfuturum.blocks;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.IConfigurable;
import ganymedes01.etfuturum.core.utils.Utils;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class GrassPath extends Block implements IConfigurable {

	@SideOnly(Side.CLIENT)
	private IIcon sideIcon;

	public GrassPath() {
		super(Material.grass);
		setHardness(0.6F);
		setLightOpacity(255);
		setHarvestLevel("shovel", 0);
		useNeighborBrightness = true;
		setStepSound(soundTypeGrass);
		setBlockTextureName("grass_path");
		setBlockName(Utils.getUnlocalisedName("grass_path"));
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.9375F, 1.0F);
		setCreativeTab(EtFuturum.enableGrassPath ? EtFuturum.creativeTab : null);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		return AxisAlignedBB.getBoundingBox(x, y, z, x + 1, y + 1, z + 1);
	}

	@Override
	public Item getItemDropped(int meta, Random rand, int fortune) {
		return Blocks.dirt.getItemDropped(meta, rand, fortune);
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
		if (world.getBlock(x, y + 1, z).getMaterial().isSolid())
			world.setBlock(x, y, z, Blocks.dirt);
	}

	@Override
	public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side) {
		return side == ForgeDirection.DOWN;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return side == 0 ? Blocks.dirt.getIcon(side, 0) : side == 1 ? blockIcon : sideIcon;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		blockIcon = reg.registerIcon(getTextureName() + "_top");
		sideIcon = reg.registerIcon(getTextureName() + "_side");
	}

	@Override
	public boolean isEnabled() {
		return EtFuturum.enableGrassPath;
	}
}