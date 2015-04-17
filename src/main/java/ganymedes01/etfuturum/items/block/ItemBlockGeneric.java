package ganymedes01.etfuturum.items.block;

import ganymedes01.etfuturum.blocks.BlockGeneric;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockGeneric extends ItemBlock {

	public ItemBlockGeneric(Block block) {
		super(block);
		setHasSubtypes(true);
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return getUnlocalizedName() + "_" + (field_150939_a instanceof BlockGeneric ? ((BlockGeneric) field_150939_a).getNameFor(stack.getItemDamage()) : stack.getItemDamage());
	}

	@Override
	public int getMetadata(int meta) {
		return meta;
	}
}