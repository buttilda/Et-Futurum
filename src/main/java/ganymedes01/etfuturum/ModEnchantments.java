package ganymedes01.etfuturum;

import ganymedes01.etfuturum.enchantment.FrostWalker;
import ganymedes01.etfuturum.enchantment.Mending;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerPickupXpEvent;

public class ModEnchantments {

	public static Enchantment frostWalker;
	public static Enchantment mending;

	public static void init() {
		if (EtFuturum.enableFrostWalker)
			frostWalker = new FrostWalker();
		if (EtFuturum.enableMending)
			mending = new Mending();
	}

	// Frost Walker logic
	public static void onLivingUpdate(EntityLivingBase entity) {
		if (entity.worldObj.isRemote)
			return;
		if (!EtFuturum.enableFrostWalker)
			return;

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

	// Mending logic
	public static void onPlayerPickupXP(PlayerPickupXpEvent event) {
		EntityPlayer player = event.entityPlayer;
		EntityXPOrb orb = event.orb;
		if (player.worldObj.isRemote)
			return;
		if (!EtFuturum.enableMending)
			return;

		ItemStack[] stacks = new ItemStack[5];
		stacks[0] = player.getCurrentEquippedItem(); // held
		stacks[1] = player.getEquipmentInSlot(1); // boots
		stacks[2] = player.getEquipmentInSlot(2); // leggings
		stacks[3] = player.getEquipmentInSlot(3); // chestplate
		stacks[4] = player.getEquipmentInSlot(4); // helmet

		for (ItemStack stack : stacks)
			if (stack != null && stack.getItemDamage() > 0 && EnchantmentHelper.getEnchantmentLevel(mending.effectId, stack) > 0) {
				int xp = orb.xpValue;
				while (xp > 0 && stack.getItemDamage() > 0) {
					stack.setItemDamage(stack.getItemDamage() - 2);
					xp--;
				}
				if (xp <= 0) {
					orb.setDead();
					event.setCanceled(true);
					return;
				}
			}
	}
}