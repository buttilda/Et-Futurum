package ganymedes01.etfuturum.items;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.item.ItemFood;

public class RabbitRaw extends ItemFood {

	public RabbitRaw() {
		super(3, 0.3F, true);
		setTextureName("rabbit_raw");
		setUnlocalizedName(Utils.getUnlocalisedName("rabbit_raw"));
		setCreativeTab(EtFuturum.enableRabbit ? EtFuturum.creativeTab : null);
	}
}