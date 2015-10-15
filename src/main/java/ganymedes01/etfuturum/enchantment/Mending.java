package ganymedes01.etfuturum.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class Mending extends Enchantment {

	public static int ID = 37;

	public Mending() {
		super(ID, 0, EnumEnchantmentType.breakable);
		Enchantment.addToBookList(this);
		setName("mending");
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack) {
		return stack != null && stack.getItem() == Items.book;
	}
}