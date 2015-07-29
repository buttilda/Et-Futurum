package ganymedes01.etfuturum.blocks;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.IConfigurable;
import ganymedes01.etfuturum.ModBlocks;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.block.BlockStairs;

public class PurpurStairs extends BlockStairs implements IConfigurable {

	public PurpurStairs() {
		super(ModBlocks.purpur_block, 0);
		setHardness(0.8F);
		setLightOpacity(0);
		setBlockName(Utils.getUnlocalisedName("purpur_stairs"));
		setCreativeTab(EtFuturum.enableChorusFruit ? EtFuturum.creativeTab : null);
	}

	@Override
	public boolean isEnabled() {
		return EtFuturum.enableChorusFruit;
	}
}