package ganymedes01.etfuturum.blocks;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.IConfigurable;
import ganymedes01.etfuturum.ModItems;
import ganymedes01.etfuturum.core.utils.Utils;
import ganymedes01.etfuturum.lib.RenderIDs;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;

public class ChorusPlant extends Block implements IConfigurable {

	public ChorusPlant() {
		super(Material.wood);
		setHardness(0.5F);
		setStepSound(soundTypeWood);
		setBlockTextureName("chorus_plant");
		setBlockName(Utils.getUnlocalisedName("chorus_plant"));
		setCreativeTab(EtFuturum.enableChorusFruit ? EtFuturum.creativeTab : null);
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
	protected boolean canSilkHarvest() {
		return false;
	}

	@Override
	public int getRenderType() {
		return RenderIDs.CHORUS_PLANT;
	}

	@Override
	public Item getItemDropped(int meta, Random rand, int fortune) {
		return ModItems.chorus_fruit;
	}

	@Override
	public boolean isEnabled() {
		return EtFuturum.enableChorusFruit;
	}
}