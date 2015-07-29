package ganymedes01.etfuturum.blocks;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.IConfigurable;
import ganymedes01.etfuturum.core.utils.Utils;
import ganymedes01.etfuturum.lib.ModSounds;
import ganymedes01.etfuturum.lib.RenderIDs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class SlimeBlock extends Block implements IConfigurable {

	public SlimeBlock() {
		super(Material.clay);
		setHardness(0.0F);
		slipperiness = 0.0F;
		setBlockTextureName("slime");
		setStepSound(ModSounds.soundSlime);
		setBlockName(Utils.getUnlocalisedName("slime"));
		setCreativeTab(EtFuturum.enableSlimeBlock ? EtFuturum.creativeTab : null);
	}

	@Override
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
		if (!entity.isSneaking()) {
			entity.fallDistance = 0.0F;
			entity.motionY = 1.0F;
		}
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		return AxisAlignedBB.getBoundingBox(x, y, z, x + 1, y + 0.875F, z + 1);
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
	@SideOnly(Side.CLIENT)
	public int getRenderBlockPass() {
		return 1;
	}

	@Override
	public int getRenderType() {
		return RenderIDs.SLIME_BLOCK;
	}

	@Override
	public boolean isEnabled() {
		return EtFuturum.enableSlimeBlock;
	}
}