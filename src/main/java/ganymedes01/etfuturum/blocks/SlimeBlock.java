package ganymedes01.etfuturum.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.IConfigurable;
import ganymedes01.etfuturum.core.utils.Utils;
import ganymedes01.etfuturum.lib.ModSounds;
import ganymedes01.etfuturum.lib.Reference;
import ganymedes01.etfuturum.lib.RenderIDs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class SlimeBlock extends Block implements IConfigurable {

	public SlimeBlock() {
		super(Material.clay);
		setHardness(0.0F);
		setBlockTextureName("slime");
		setStepSound(ModSounds.soundSlime);
		setBlockName(Utils.getUnlocalisedName("slime"));
		setCreativeTab(EtFuturum.enableSlimeBlock ? EtFuturum.creativeTab : null);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		float f = 0.125F;
		return AxisAlignedBB.getBoundingBox(x, y, z, x + 1, y + 1 - f, z + 1);
	}

	@Override
	public void onFallenUpon(World world, int x, int y, int z, Entity entity, float fallDistance) {
		if (!entity.isSneaking()) {
			entity.fallDistance = 0;
			if (entity.motionY < 0)
				entity.getEntityData().setDouble(Reference.MOD_ID + ":slime", -entity.motionY);
		}
	}

	@Override
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
		NBTTagCompound data = entity.getEntityData();
		if (data.hasKey(Reference.MOD_ID + ":slime")) {
			entity.motionY = data.getDouble(Reference.MOD_ID + ":slime");
			data.removeTag(Reference.MOD_ID + ":slime");
		}

		if (Math.abs(entity.motionY) < 0.1 && !entity.isSneaking()) {
			double d = 0.4 + Math.abs(entity.motionY) * 0.2;
			entity.motionX *= d;
			entity.motionZ *= d;
		}
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