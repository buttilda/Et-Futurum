package ganymedes01.etfuturum.api.brewing;

import net.minecraft.item.ItemStack;

public interface IBrewingFuel {

	/**
	 * Returns how many brews the ItemStack will fuel.
	 *
	 * The default burn time for Blaze Powder is 30 brews
	 *
	 * @param stack
	 */
	int getBrewingAmount(ItemStack stack);
}