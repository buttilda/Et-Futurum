package ganymedes01.etfuturum.items;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.ModBlocks;
import ganymedes01.etfuturum.blocks.BlockWoodDoor;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDoor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class ItemNewDoor extends Item {

	private final Block door;

	public ItemNewDoor(int meta) {
		door = ModBlocks.doors[meta - 1];
		String name = BlockWoodDoor.names[meta];

		setMaxStackSize(64);
		setTextureName("door_" + name);
		setUnlocalizedName(Utils.getUnlocalisedName("door_" + name));
		setCreativeTab(EtFuturum.enableDoors ? EtFuturum.creativeTab : null);
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		if (side != 1)
			return false;
		else {
			y++;
			if (player.canPlayerEdit(x, y, z, side, stack) && player.canPlayerEdit(x, y + 1, z, side, stack)) {
				if (!door.canPlaceBlockAt(world, x, y, z))
					return false;
				else {
					ItemDoor.placeDoorBlock(world, x, y, z, MathHelper.floor_double((player.rotationYaw + 180.0F) * 4.0F / 360.0F - 0.5D) & 3, door);
					stack.stackSize--;
					return true;
				}
			} else
				return false;
		}
	}
}