package ganymedes01.etfuturum.dispenser;

import ganymedes01.etfuturum.items.ItemEntityEgg;
import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;

public class DispenserBehaviourSpawnEgg extends BehaviorDefaultDispenseItem {

	@Override
	public ItemStack dispenseStack(IBlockSource block, ItemStack stack) {
		EnumFacing enumfacing = BlockDispenser.func_149937_b(block.getBlockMetadata());
		double d0 = block.getX() + enumfacing.getFrontOffsetX();
		double d1 = block.getYInt() + 0.2F;
		double d2 = block.getZ() + enumfacing.getFrontOffsetZ();
		Entity entity = ItemEntityEgg.spawnEntity(block.getWorld(), stack.getItemDamage(), d0, d1, d2);

		if (entity instanceof EntityLivingBase && stack.hasDisplayName())
			((EntityLiving) entity).setCustomNameTag(stack.getDisplayName());

		stack.splitStack(1);
		return stack;
	}
}