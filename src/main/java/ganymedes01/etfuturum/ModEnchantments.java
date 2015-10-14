package ganymedes01.etfuturum;

import ganymedes01.etfuturum.enchantment.FrostWalker;
import ganymedes01.etfuturum.enchantment.Mending;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class ModEnchantments {

	public static Enchantment frostWalker;
	public static Enchantment mending;

	public static void init() {
		frostWalker = new FrostWalker();
		mending = new Mending();
	}

	public static void onLivingUpdate(EntityLivingBase entity) {
		if (entity.worldObj.isRemote)
			return;

		// Frost Walker logic
		ItemStack boots = entity.getEquipmentInSlot(1);
		int level = 0;
		if ((level = EnchantmentHelper.getEnchantmentLevel(frostWalker.effectId, boots)) > 0)
			if (entity.onGround) {
				int x = (int) entity.posX;
				int y = (int) entity.posY;
				int z = (int) entity.posZ;

				int radius = 1 + level;

				for (int i = -radius; i <= radius; i++)
					for (int j = -radius; j <= radius; j++) {
						Block block = entity.worldObj.getBlock(x + i, y - 1, z + j);
						if (block == Blocks.water || block == Blocks.flowing_water)
							entity.worldObj.setBlock(x + i, y - 1, z + j, ModBlocks.frosted_ice);
					}
			}
	}
}