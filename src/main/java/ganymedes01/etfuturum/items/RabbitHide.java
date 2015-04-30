package ganymedes01.etfuturum.items;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.item.Item;

public class RabbitHide extends Item {

	public RabbitHide() {
		setTextureName("rabbit_hide");
		setUnlocalizedName(Utils.getUnlocalisedName("rabbit_hide"));
		setCreativeTab(EtFuturum.enableRabbit ? EtFuturum.creativeTab : null);
	}
}