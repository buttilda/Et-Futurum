package ganymedes01.etfuturum.blocks;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.IConfigurable;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class PurpurBlock extends Block implements IConfigurable {

	public PurpurBlock() {
		super(Material.rock);
		setHardness(1.5F);
		setResistance(10.0F);
		setStepSound(soundTypePiston);
		setBlockTextureName("purpur_block");
		setBlockName(Utils.getUnlocalisedName("purpur_block"));
		setCreativeTab(EtFuturum.enableChorusFruit ? EtFuturum.creativeTab : null);
	}

	@Override
	public boolean isEnabled() {
		return EtFuturum.enableChorusFruit;
	}
}