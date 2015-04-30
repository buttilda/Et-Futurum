package ganymedes01.etfuturum.blocks;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.IConfigurable;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.block.material.Material;

public class PrismarineBlocks extends BlockGeneric implements IConfigurable {

	public PrismarineBlocks() {
		super(Material.rock, "rough", "bricks", "dark");
		setHardness(1.5F);
		setResistance(10.0F);
		setBlockTextureName("prismarine");
		setBlockName(Utils.getUnlocalisedName("prismarine_block"));
		setCreativeTab(EtFuturum.enablePrismarine ? EtFuturum.creativeTab : null);
	}

	@Override
	public boolean isEnabled() {
		return EtFuturum.enablePrismarine;
	}
}