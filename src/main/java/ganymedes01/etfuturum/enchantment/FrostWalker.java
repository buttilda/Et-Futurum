package ganymedes01.etfuturum.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class FrostWalker extends Enchantment {

	public static int ID = 36;

	public FrostWalker() {
		super(ID, 0, EnumEnchantmentType.armor_feet);
		Enchantment.addToBookList(this);
		setName("frost_walker");
	}

	@Override
	public int getMaxLevel() {
		return 2;
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack) {
		return stack != null && stack.getItem() == Items.book;
	}
}