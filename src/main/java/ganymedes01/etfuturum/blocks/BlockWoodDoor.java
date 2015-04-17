package ganymedes01.etfuturum.blocks;

import ganymedes01.etfuturum.core.utils.Utils;
import ganymedes01.etfuturum.lib.RenderIDs;

import java.util.Random;

import net.minecraft.block.BlockDoor;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;

public class BlockWoodDoor extends BlockDoor {

	public static final String[] names = new String[] { "oak", "spruce", "birch", "jungle", "acacia", "dark_oak" };

	public BlockWoodDoor(int meta) {
		super(Material.wood);
		String name = names[meta];

		disableStats();
		setHardness(3.0F);
		setStepSound(soundTypeWood);
		setBlockTextureName("door_" + name);
		setBlockName(Utils.getUnlocalisedName("door_" + name));
	}

	@Override
	public Item getItemDropped(int meta, Random rand, int fortune) {
		return (meta & 8) != 0 ? null : super.getItemDropped(meta, rand, fortune);
	}

	@Override
	public int getRenderType() {
		return RenderIDs.DOOR;
	}
}