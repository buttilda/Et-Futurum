package ganymedes01.etfuturum.blocks;

import java.util.Random;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.IConfigurable;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.block.BlockGravel;
import net.minecraft.item.Item;

public class OldGravel extends BlockGravel implements IConfigurable {

	public OldGravel() {
		setHardness(0.6F);
		setStepSound(soundTypeGravel);
		setBlockTextureName("old_gravel");
		setBlockName(Utils.getUnlocalisedName("old_gravel"));
		setCreativeTab(EtFuturum.enableOldGravel ? EtFuturum.creativeTab : null);
	}

	@Override
	public Item getItemDropped(int meta, Random rand, int fortune) {
		return Item.getItemFromBlock(this);
	}

	@Override
	public boolean isEnabled() {
		return EtFuturum.enableOldGravel;
	}
}