package ganymedes01.etfuturum.items;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.item.ItemFood;

public class RabbitCooked extends ItemFood {

	public RabbitCooked() {
		super(5, 0.6F, true);
		setTextureName("rabbit_cooked");
		setUnlocalizedName(Utils.getUnlocalisedName("rabbit_cooked"));
		setCreativeTab(EtFuturum.enableRabbit ? EtFuturum.creativeTab : null);
	}
}