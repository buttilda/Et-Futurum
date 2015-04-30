package ganymedes01.etfuturum.blocks;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.IConfigurable;
import ganymedes01.etfuturum.ModBlocks.ISubBlocksBlock;
import ganymedes01.etfuturum.core.utils.Utils;
import ganymedes01.etfuturum.items.block.ItemBlockRedSandstone;
import net.minecraft.block.BlockSandStone;
import net.minecraft.item.ItemBlock;

public class RedSandstone extends BlockSandStone implements ISubBlocksBlock, IConfigurable {

	public RedSandstone() {
		setHardness(0.8F);
		setBlockTextureName("red_sandstone");
		setBlockName(Utils.getUnlocalisedName("red_sandstone"));
		setCreativeTab(EtFuturum.enableRedSandstone ? EtFuturum.creativeTab : null);
	}

	@Override
	public Class<? extends ItemBlock> getItemBlockClass() {
		return ItemBlockRedSandstone.class;
	}

	@Override
	public boolean isEnabled() {
		return EtFuturum.enableRedSandstone;
	}
}