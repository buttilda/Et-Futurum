package ganymedes01.etfuturum.blocks;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.IConfigurable;
import ganymedes01.etfuturum.ModBlocks.ISubBlocksBlock;
import ganymedes01.etfuturum.core.utils.Utils;
import ganymedes01.etfuturum.items.block.ItemWoodDoor;
import ganymedes01.etfuturum.lib.RenderIDs;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.world.World;

public class BlockWoodDoor extends BlockDoor implements ISubBlocksBlock, IConfigurable {

	public static final String[] names = new String[] { "oak", "spruce", "birch", "jungle", "acacia", "dark_oak" };

	public BlockWoodDoor(int meta) {
		super(Material.wood);
		String name = names[meta];

		disableStats();
		setHardness(3.0F);
		setStepSound(soundTypeWood);
		setBlockTextureName("door_" + name);
		setBlockName(Utils.getUnlocalisedName("door_" + name));
		setCreativeTab(EtFuturum.enableDoors ? EtFuturum.creativeTab : null);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Item getItem(World world, int x, int y, int z) {
		return Item.getItemFromBlock(this);
	}

	@Override
	public Item getItemDropped(int meta, Random rand, int fortune) {
		return (meta & 8) != 0 ? null : Item.getItemFromBlock(this);
	}

	@Override
	public int getRenderType() {
		return RenderIDs.DOOR;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getItemIconName() {
		return getTextureName();
	}

	@Override
	public Class<? extends ItemBlock> getItemBlockClass() {
		return ItemWoodDoor.class;
	}

	@Override
	public boolean isEnabled() {
		return EtFuturum.enableDoors;
	}
}