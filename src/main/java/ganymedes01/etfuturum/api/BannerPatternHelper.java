package ganymedes01.etfuturum.api;

import ganymedes01.etfuturum.tileentities.TileEntityBanner.EnumBannerPattern;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;

public class BannerPatternHelper {

	public static void addPattern(String name, String id, ItemStack craftingItem) {
		EnumHelper.addEnum(EnumBannerPattern.class, name.toUpperCase(), new Class[] { String.class, String.class, ItemStack.class }, new Object[] { name, id, craftingItem });
	}

	public static void addPattern(String name, String id, String craftingTop, String craftingMid, String craftingBot) {
		EnumHelper.addEnum(EnumBannerPattern.class, name.toUpperCase(), new Class[] { String.class, String.class, String.class, String.class, String.class }, new Object[] { name, id, craftingTop, craftingMid, craftingBot });
	}
}