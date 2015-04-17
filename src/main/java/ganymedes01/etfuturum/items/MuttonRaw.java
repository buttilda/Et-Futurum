package ganymedes01.etfuturum.items;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.item.ItemFood;

public class MuttonRaw extends ItemFood {

	public MuttonRaw() {
		super(2, 0.3F, true);
		setTextureName("mutton_raw");
		setUnlocalizedName(Utils.getUnlocalisedName("mutton_raw"));
		setCreativeTab(EtFuturum.enableMutton ? EtFuturum.creativeTab : null);
	}
}