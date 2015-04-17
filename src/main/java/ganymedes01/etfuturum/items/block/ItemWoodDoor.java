package ganymedes01.etfuturum.items.block;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemDoor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class ItemWoodDoor extends ItemBlock {

	public ItemWoodDoor(Block block) {
		super(block);
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		if (side != 1)
			return false;
		else {
			y++;
			if (player.canPlayerEdit(x, y, z, side, stack) && player.canPlayerEdit(x, y + 1, z, side, stack)) {
				if (!field_150939_a.canPlaceBlockAt(world, x, y, z))
					return false;
				else {
					ItemDoor.placeDoorBlock(world, x, y, z, MathHelper.floor_double((player.rotationYaw + 180.0F) * 4.0F / 360.0F - 0.5D) & 3, field_150939_a);
					stack.stackSize--;
					return true;
				}
			} else
				return false;
		}
	}
}