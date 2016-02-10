package ganymedes01.etfuturum.recipes;

import java.util.List;

import ganymedes01.etfuturum.ModItems;
import ganymedes01.etfuturum.items.LingeringPotion;
import ganymedes01.etfuturum.items.TippedArrow;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class RecipeTippedArrow extends ShapedOreRecipe {

	public RecipeTippedArrow(ItemStack result, Object... recipe) {
		super(result, recipe);
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting grid) {
		ItemStack potion = grid.getStackInRowAndColumn(1, 1);
		List<PotionEffect> effects = ((LingeringPotion) ModItems.lingering_potion).getEffects(potion);

		ItemStack stack = new ItemStack(ModItems.tipped_arrow, 8);
		if (!effects.isEmpty()) {
			PotionEffect effect = effects.get(0);
			TippedArrow.setEffect(stack, Potion.potionTypes[effect.getPotionID()], effect.getDuration());
		}

		return stack;
	}
}