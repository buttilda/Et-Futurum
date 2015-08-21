package ganymedes01.etfuturum.blocks;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.IConfigurable;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.block.BlockObsidian;

public class CryingObsidian extends BlockObsidian implements IConfigurable {

	public CryingObsidian() {
		setHardness(50.0F);
		setResistance(2000.0F);
		setStepSound(soundTypePiston);
		setHarvestLevel("pickaxe", 3);
		setBlockTextureName("crying_obsidian");
		setBlockName(Utils.getUnlocalisedName("crying_obsidian"));
		setCreativeTab(EtFuturum.enableCryingObsidian ? EtFuturum.creativeTab : null);
	}

	@Override
	public boolean isEnabled() {
		return EtFuturum.enableCryingObsidian;
	}
}