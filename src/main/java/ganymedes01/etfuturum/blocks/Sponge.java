package ganymedes01.etfuturum.blocks;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.block.BlockSponge;

public class Sponge extends BlockSponge {

	public Sponge() {
		setHardness(0.6F);
		setStepSound(soundTypeGrass);
		setBlockTextureName("sponge");
		setBlockName(Utils.getUnlocalisedName("sponge"));
		setCreativeTab(EtFuturum.enableSponge ? EtFuturum.creativeTab : null);
	}
}