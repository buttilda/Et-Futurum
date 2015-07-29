package ganymedes01.etfuturum.blocks;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.ModBlocks;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.block.material.Material;

public class RedSandstoneSlab extends GenericSlab {

	public RedSandstoneSlab() {
		super(Material.rock, ModBlocks.red_sandstone);
		setResistance(30);
		setHardness(2.0F);
		setBlockName(Utils.getUnlocalisedName("red_sandstone_slab"));
		setCreativeTab(EtFuturum.enableRedSandstone ? EtFuturum.creativeTab : null);
	}

	@Override
	public boolean isEnabled() {
		return EtFuturum.enableRedSandstone;
	}
}