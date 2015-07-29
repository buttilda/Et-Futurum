package ganymedes01.etfuturum.items;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.IConfigurable;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.item.ItemFood;

public class Beetroot extends ItemFood implements IConfigurable {

	public Beetroot() {
		super(1, 0.6F, false);
		setTextureName("beetroot");
		setUnlocalizedName(Utils.getUnlocalisedName("beetroot"));
		setCreativeTab(EtFuturum.enableBeetroot ? EtFuturum.creativeTab : null);
	}

	@Override
	public boolean isEnabled() {
		return EtFuturum.enableBeetroot;
	}
}