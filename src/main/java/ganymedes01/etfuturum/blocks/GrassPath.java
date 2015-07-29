package ganymedes01.etfuturum.blocks;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.IConfigurable;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class GrassPath extends Block implements IConfigurable {

	@SideOnly(Side.CLIENT)
	private IIcon sideIcon;

	public GrassPath() {
		super(Material.grass);
		setBlockTextureName("grass_path");
		setBlockName(Utils.getUnlocalisedName("grass_path"));
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.9375F, 1.0F);
		setCreativeTab(EtFuturum.enableGrassPath ? EtFuturum.creativeTab : null);
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return side == 0 || side == 1 ? blockIcon : sideIcon;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		blockIcon = reg.registerIcon(getTextureName() + "_top");
		sideIcon = reg.registerIcon(getTextureName() + "_side");
	}

	@Override
	public boolean isEnabled() {
		return EtFuturum.enableGrassPath;
	}
}