package ganymedes01.etfuturum.items.block;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockRedSandstone extends ItemBlock {

	private final String[] types = { "default", "chiseled", "smooth" };

	public ItemBlockRedSandstone(Block block) {
		super(block);
		setHasSubtypes(true);
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return getUnlocalizedName() + "_" + types[Math.min(Math.max(0, stack.getItemDamage()), types.length - 1)];
	}

	@Override
	public int getMetadata(int meta) {
		return meta;
	}
}