package ganymedes01.etfuturum.blocks;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.ModBlocks.ISubBlocksBlock;
import ganymedes01.etfuturum.core.utils.Utils;
import ganymedes01.etfuturum.items.block.ItemBlockGeneric;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class Stones18 extends Block implements ISubBlocksBlock {

	@SideOnly(Side.CLIENT)
	private IIcon[] icons;

	public static final int GRANITE = 1;
	public static final int POLISHED_GRANITE = 2;
	public static final int DIORITE = 3;
	public static final int POLISHED_DIORITE = 4;
	public static final int ANDESITE = 5;
	public static final int POLISHED_ANDESITE = 6;

	public Stones18() {
		super(Material.rock);
		setHardness(1.5F);
		setResistance(10.0F);
		setBlockTextureName("stone");
		setStepSound(soundTypePiston);
		setBlockName(Utils.getUnlocalisedName("stone"));
		setCreativeTab(EtFuturum.enable18Stones ? EtFuturum.surfaceTab : null);
	}

	@Override
	public boolean isReplaceableOreGen(World world, int x, int y, int z, Block target) {
		return this == target || target == Blocks.stone;
	}

	@Override
	public int damageDropped(int meta) {
		return meta;
	}

	@Override
	@SideOnly(Side.CLIENT)
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		for (int i = 1; i < 7; i++)
			list.add(new ItemStack(item, 1, i));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return icons[Math.max(Math.min(meta, icons.length - 1), 1)];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		icons = new IIcon[7];
		icons[1] = reg.registerIcon(getTextureName() + "_granite");
		icons[2] = reg.registerIcon(getTextureName() + "_granite_smooth");
		icons[3] = reg.registerIcon(getTextureName() + "_diorite");
		icons[4] = reg.registerIcon(getTextureName() + "_diorite_smooth");
		icons[5] = reg.registerIcon(getTextureName() + "_andesite");
		icons[6] = reg.registerIcon(getTextureName() + "_andesite_smooth");
	}

	@Override
	public Class<? extends ItemBlock> getItemBlockClass() {
		return ItemBlockGeneric.class;
	}
}