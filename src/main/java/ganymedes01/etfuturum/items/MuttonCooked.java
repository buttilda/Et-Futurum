package ganymedes01.etfuturum.items;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.IConfigurable;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.item.ItemFood;

public class MuttonCooked extends ItemFood implements IConfigurable {

	public MuttonCooked() {
		super(6, 0.8F, true);
		setTextureName("mutton_cooked");
		setUnlocalizedName(Utils.getUnlocalisedName("mutton_cooked"));
		setCreativeTab(EtFuturum.enableMutton ? EtFuturum.creativeTab : null);
	}

	@Override
	public boolean isEnabled() {
		return EtFuturum.enableMutton;
	}
}