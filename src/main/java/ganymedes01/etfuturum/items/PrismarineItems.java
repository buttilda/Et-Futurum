package ganymedes01.etfuturum.items;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.core.utils.Utils;

public class PrismarineItems extends ItemGeneric {

	public PrismarineItems() {
		super("rough", "crystal");
		setTextureName("prismarine");
		setUnlocalizedName(Utils.getUnlocalisedName("prismarine"));
		setCreativeTab(EtFuturum.enablePrismarineStuff ? EtFuturum.creativeTab : null);
	}
}