package ganymedes01.etfuturum.items;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.IConfigurable;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.item.ItemSoup;

public class RabbitStew extends ItemSoup implements IConfigurable {

	public RabbitStew() {
		super(10);
		setTextureName("rabbit_stew");
		setUnlocalizedName(Utils.getUnlocalisedName("rabbit_stew"));
		setCreativeTab(EtFuturum.enableRabbit ? EtFuturum.creativeTab : null);
	}

	@Override
	public boolean isEnabled() {
		return EtFuturum.enableRabbit;
	}
}