package ganymedes01.etfuturum.items;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.IConfigurable;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.item.Item;

public class EndCrystal extends Item implements IConfigurable {

	public EndCrystal() {
		setTextureName("end_crystal");
		setUnlocalizedName(Utils.getUnlocalisedName("end_crystal"));
		setCreativeTab(EtFuturum.enableDragonRespawn ? EtFuturum.creativeTab : null);
	}

	@Override
	public boolean isEnabled() {
		return EtFuturum.enableDragonRespawn;
	}
}