package ganymedes01.etfuturum.items;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.init.Items;
import net.minecraft.item.ItemSoup;

public class BeetrootSoup extends ItemSoup {

	public BeetrootSoup() {
		super(6);
		setContainerItem(Items.bowl);
		setTextureName(Utils.getItemTexture("beetroot_soup"));
		setUnlocalizedName(Utils.getUnlocalisedName("beetroot_soup"));
		setCreativeTab(EtFuturum.enableBeetroots ? EtFuturum.creativeTab : null);
	}
}