package ganymedes01.etfuturum.blocks;

import ganymedes01.etfuturum.ModBlocks.ISubBlocksBlock;
import ganymedes01.etfuturum.items.block.ItemBlockGeneric;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockGeneric extends Block implements ISubBlocksBlock {

	@SideOnly(Side.CLIENT)
	private IIcon[] icons;
	private final String[] types;

	protected int startMeta = 0;

	public BlockGeneric(Material material, String... types) {
		super(Material.rock);
		this.types = types;
	}

	public String getNameFor(int meta) {
		return types[Math.max(Math.min(meta, types.length - 1), 0)];
	}

	@Override
	public int damageDropped(int meta) {
		return meta;
	}

	@Override
	@SideOnly(Side.CLIENT)
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		for (int i = startMeta; i < types.length; i++)
			list.add(new ItemStack(item, 1, i));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return icons[Math.max(Math.min(meta, types.length - 1), startMeta)];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		icons = new IIcon[types.length];
		for (int i = 0; i < types.length; i++)
			icons[i] = reg.registerIcon(getTextureName() + "_" + types[i]);
	}

	@Override
	public Class<? extends ItemBlock> getItemBlockClass() {
		return ItemBlockGeneric.class;
	}
}