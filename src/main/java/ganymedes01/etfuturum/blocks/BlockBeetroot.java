package ganymedes01.etfuturum.blocks;

import ganymedes01.etfuturum.ModItems;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.block.BlockCarrot;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockBeetroot extends BlockCarrot {

	public BlockBeetroot() {
		setCreativeTab(null);
		setBlockName(Utils.getUnlocalisedName("beetroot_crop"));
		setBlockTextureName(Utils.getBlockTexture("beetroot_crop"));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return meta < 7 ? Blocks.carrots.getIcon(side, meta) : blockIcon;
	}

	@Override
	protected Item func_149866_i() {
		return ModItems.beetrootSeeds;
	}

	@Override
	protected Item func_149865_P() {
		return ModItems.beetroot;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		blockIcon = reg.registerIcon(getTextureName());
	}
}