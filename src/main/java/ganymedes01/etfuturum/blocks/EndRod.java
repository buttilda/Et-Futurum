package ganymedes01.etfuturum.blocks;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.IConfigurable;
import ganymedes01.etfuturum.core.utils.Utils;
import ganymedes01.etfuturum.lib.RenderIDs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class EndRod extends Block implements IConfigurable {

	public EndRod() {
		super(Material.rock);
		setHardness(0);
		setLightLevel(0.9375F);
		setBlockTextureName("end_rod");
		setBlockName(Utils.getUnlocalisedName("end_rod"));
		setBlockBounds(0.375F, 0.0F, 0.375F, 0.625F, 1.0F, 0.625F);
		setCreativeTab(EtFuturum.enableChorusFruit ? EtFuturum.creativeTab : null);
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
		ForgeDirection dir = ForgeDirection.getOrientation(world.getBlockMetadata(x, y, z));

		if (dir == ForgeDirection.DOWN || dir == ForgeDirection.UP)
			setBlockBounds(0.375F, 0.0F, 0.375F, 0.625F, 1.0F, 0.625F);
		else if (dir == ForgeDirection.WEST || dir == ForgeDirection.EAST)
			setBlockBounds(0.0F, 0.375F, 0.375F, 1.0F, 0.625F, 0.625F);
		else if (dir == ForgeDirection.NORTH || dir == ForgeDirection.SOUTH)
			setBlockBounds(0.375F, 0.375F, 0.0F, 0.625F, 0.625F, 1.0F);
	}

	@Override
	public int getRenderType() {
		return RenderIDs.END_ROD;
	}

	@Override
	public int onBlockPlaced(World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int meta) {
		return side;
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
	public boolean isEnabled() {
		return EtFuturum.enableChorusFruit;
	}
}