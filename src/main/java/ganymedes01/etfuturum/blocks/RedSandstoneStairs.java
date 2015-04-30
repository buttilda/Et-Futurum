package ganymedes01.etfuturum.blocks;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.IConfigurable;
import ganymedes01.etfuturum.ModBlocks;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.block.BlockStairs;

public class RedSandstoneStairs extends BlockStairs implements IConfigurable {

	public RedSandstoneStairs() {
		super(ModBlocks.red_sandstone, 0);
		setHardness(0.8F);
		setLightOpacity(0);
		setBlockName(Utils.getUnlocalisedName("red_sandstone_stairs"));
		setCreativeTab(EtFuturum.enableRedSandstone ? EtFuturum.creativeTab : null);
	}

	@Override
	public boolean isEnabled() {
		return EtFuturum.enableRedSandstone;
	}
}