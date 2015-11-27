package ganymedes01.etfuturum.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.IConfigurable;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class PrismarineBlocks extends BlockGeneric implements IConfigurable {

	public PrismarineBlocks() {
		super(Material.rock, "rough", "bricks", "dark");
		setHardness(1.5F);
		setResistance(10.0F);
		setBlockTextureName("prismarine");
		setBlockName(Utils.getUnlocalisedName("prismarine_block"));
		setCreativeTab(EtFuturum.enablePrismarine ? EtFuturum.creativeTab : null);
	}

	@SideOnly(Side.CLIENT)
	public void setIcon(int index, IIcon icon) {
		if (icons == null)
			icons = new IIcon[types.length];

		icons[index] = icon;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		if (icons == null)
			icons = new IIcon[types.length];

		for (int i = 1; i < types.length; i++)
			if ("".equals(types[i]))
				icons[i] = reg.registerIcon(getTextureName());
			else
				icons[i] = reg.registerIcon(getTextureName() + "_" + types[i]);
	}

	@Override
	public boolean isEnabled() {
		return EtFuturum.enablePrismarine;
	}
}