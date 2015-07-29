package ganymedes01.etfuturum.items;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.IConfigurable;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.item.Item;

public class PoppedChorusFruit extends Item implements IConfigurable {

	public PoppedChorusFruit() {
		setTextureName("chorus_fruit_popped");
		setUnlocalizedName(Utils.getUnlocalisedName("chorus_fruit_popped"));
		setCreativeTab(EtFuturum.enableChorusFruit ? EtFuturum.creativeTab : null);
	}

	@Override
	public boolean isEnabled() {
		return EtFuturum.enableChorusFruit;
	}
}