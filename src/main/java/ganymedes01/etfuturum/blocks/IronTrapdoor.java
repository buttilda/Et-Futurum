package ganymedes01.etfuturum.blocks;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.IConfigurable;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.block.BlockTrapDoor;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class IronTrapdoor extends BlockTrapDoor implements IConfigurable {

	public IronTrapdoor() {
		super(Material.iron);
		setHardness(5.0F);
		setStepSound(soundTypeMetal);
		setBlockTextureName("iron_trapdoor");
		setBlockName(Utils.getUnlocalisedName("iron_trapdoor"));
		setCreativeTab(EtFuturum.enableIronTrapdoor ? EtFuturum.creativeTab : null);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		return false;
	}

	@Override
	public boolean isEnabled() {
		return EtFuturum.enableIronTrapdoor;
	}
}