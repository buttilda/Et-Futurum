package ganymedes01.etfuturum.api.brewing;

import java.lang.reflect.Method;

import net.minecraft.item.ItemStack;

public class BrewingFuelRegistryHelper {

	public static void registerFuel(ItemStack fuel, int brews) {
		try {
			Class<?> cls = Class.forName("ganymedes01.etfuturum.recipes.BrewingFuelRegistry");
			Method m = cls.getMethod("registerFuel", ItemStack.class, int.class);
			m.invoke(null, fuel, brews);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void registerAdvancedFuel(IBrewingFuel fuel) {
		try {
			Class<?> cls = Class.forName("ganymedes01.etfuturum.recipes.BrewingFuelRegistry");
			Method m = cls.getMethod("registerAdvancedFuel", IBrewingFuel.class);
			m.invoke(null, fuel);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}