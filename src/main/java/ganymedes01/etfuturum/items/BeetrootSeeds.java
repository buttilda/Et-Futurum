package ganymedes01.etfuturum.items;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.IConfigurable;
import ganymedes01.etfuturum.ModBlocks;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemSeeds;

public class BeetrootSeeds extends ItemSeeds implements IConfigurable {

	public BeetrootSeeds() {
		super(ModBlocks.beetroot, Blocks.farmland);
		setTextureName("beetroot_seeds");
		setUnlocalizedName(Utils.getUnlocalisedName("beetroot_seeds"));
		setCreativeTab(EtFuturum.enableBeetroot ? EtFuturum.creativeTab : null);
	}

	@Override
	public boolean isEnabled() {
		return EtFuturum.enableBeetroot;
	}
}