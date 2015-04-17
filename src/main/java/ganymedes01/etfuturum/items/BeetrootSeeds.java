package ganymedes01.etfuturum.items;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.ModBlocks;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemSeeds;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;

public class BeetrootSeeds extends ItemSeeds {

	public BeetrootSeeds() {
		super(ModBlocks.beetroot, Blocks.farmland);
		setTextureName(Utils.getItemTexture("beetroot_seeds"));
		setUnlocalizedName(Utils.getUnlocalisedName("beetroot_seeds"));
		setCreativeTab(EtFuturum.enableBeetroots ? EtFuturum.surfaceTab : null);

		if (EtFuturum.enableBeetroots)
			MinecraftForge.addGrassSeed(new ItemStack(this), 7);
	}
}