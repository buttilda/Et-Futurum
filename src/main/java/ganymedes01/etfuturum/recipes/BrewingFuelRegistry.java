package ganymedes01.etfuturum.recipes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import ganymedes01.etfuturum.api.brewing.IBrewingFuel;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class BrewingFuelRegistry {

	private static Map<ItemStack, Integer> fuels = new HashMap<ItemStack, Integer>();
	private static List<IBrewingFuel> advFuels = new ArrayList<IBrewingFuel>();

	static {
		fuels.put(new ItemStack(Items.blaze_powder), 30);
	}

	public static void registerFuel(ItemStack fuel, int brews) {
		fuels.put(fuel, brews);
	}

	public static void registerAdvancedFuel(IBrewingFuel fuel) {
		advFuels.add(fuel);
	}

	public static boolean isFuel(ItemStack fuel) {
		return getBrewAmount(fuel) > 0;
	}

	public static int getBrewAmount(ItemStack fuel) {
		for (IBrewingFuel advFuel : advFuels) {
			int time = advFuel.getBrewingAmount(fuel);
			if (time > 0)
				return time;
		}

		for (Entry<ItemStack, Integer> entry : fuels.entrySet())
			if (OreDictionary.itemMatches(entry.getKey(), fuel, false))
				return entry.getValue();

		return 0;
	}
}