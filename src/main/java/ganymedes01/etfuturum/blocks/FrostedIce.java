package ganymedes01.etfuturum.blocks;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockIce;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class FrostedIce extends BlockIce {

	@SideOnly(Side.CLIENT)
	private IIcon[] icons;

	public FrostedIce() {
		setHardness(0.5F);
		setLightOpacity(3);
		setCreativeTab(null);
		setStepSound(soundTypeGlass);
		setBlockTextureName("frosted_ice");
		setBlockName(Utils.getUnlocalisedName("frosted_ice"));
	}

	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		if (world.isRemote)
			return;

		world.scheduleBlockUpdate(x, y, z, this, 40 + world.rand.nextInt(40));
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {
		if (world.isRemote)
			return;

		int surroundingBlockCount = 0;
		for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
			Block block = world.getBlock(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ);
			if (block == this || block == Blocks.ice || block == Blocks.packed_ice)
				if (++surroundingBlockCount >= 4)
					break;
		}

		if (surroundingBlockCount < 4 || rand.nextInt(100) <= 33) {
			int meta = world.getBlockMetadata(x, y, z);
			if (meta < 3)
				world.setBlockMetadataWithNotify(x, y, z, meta + 1, 2);
			else
				world.setBlock(x, y, z, Blocks.water);
		}

		world.scheduleBlockUpdate(x, y, z, this, 40 + rand.nextInt(40));
	}

	@Override
	protected boolean canSilkHarvest() {
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		if (meta < 0 || meta >= icons.length)
			meta = 0;
		return icons[meta];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		icons = new IIcon[4];
		for (int i = 0; i < icons.length; i++)
			icons[i] = reg.registerIcon(getTextureName() + "_" + i);
	}
}