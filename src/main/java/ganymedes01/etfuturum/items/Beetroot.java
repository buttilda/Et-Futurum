package ganymedes01.etfuturum.items;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.item.ItemFood;

public class Beetroot extends ItemFood {

	public Beetroot() {
		super(1, 0.3F, false);
		setTextureName(Utils.getItemTexture("beetroot"));
		setUnlocalizedName(Utils.getUnlocalisedName("beetroot"));
		setCreativeTab(EtFuturum.enableBeetroots ? EtFuturum.creativeTab : null);
	}
}