package ganymedes01.etfuturum.items;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.IConfigurable;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.init.Items;
import net.minecraft.item.ItemSoup;

public class BeetrootSoup extends ItemSoup implements IConfigurable {

	public BeetrootSoup() {
		super(6);
		setContainerItem(Items.bowl);
		setTextureName("beetroot_soup");
		setUnlocalizedName(Utils.getUnlocalisedName("beetroot_soup"));
		setCreativeTab(EtFuturum.enableBeetroot ? EtFuturum.creativeTab : null);
	}

	@Override
	public boolean isEnabled() {
		return EtFuturum.enableBeetroot;
	}
}