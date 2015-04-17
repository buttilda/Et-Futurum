package ganymedes01.etfuturum.creativetab;

import ganymedes01.etfuturum.lib.Reference;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;

public class CreativeTabSurface extends CreativeTabs {

	public CreativeTabSurface() {
		super(Reference.MOD_ID);
	}

	@Override
	public Item getTabIconItem() {
		return Item.getItemFromBlock(Blocks.grass);
	}
}