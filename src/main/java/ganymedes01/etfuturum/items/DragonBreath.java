package ganymedes01.etfuturum.items;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.IConfigurable;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class DragonBreath extends Item implements IConfigurable {

	public DragonBreath() {
		setPotionEffect("-14+13");
		setTextureName("dragon_breath");
		setContainerItem(Items.glass_bottle);
		setUnlocalizedName(Utils.getUnlocalisedName("dragon_breath"));
		setCreativeTab(EtFuturum.enableLingeringPotions ? EtFuturum.creativeTab : null);
	}

	@Override
	public boolean isEnabled() {
		return EtFuturum.enableLingeringPotions;
	}
}