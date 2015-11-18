package ganymedes01.etfuturum.blocks;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.IConfigurable;
import ganymedes01.etfuturum.ModBlocks;
import ganymedes01.etfuturum.ModBlocks.ISubBlocksBlock;
import ganymedes01.etfuturum.core.utils.Utils;
import ganymedes01.etfuturum.lib.GUIsID;
import net.minecraft.block.BlockAnvil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAnvilBlock;
import net.minecraft.item.ItemBlock;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

public class NewAnvil extends BlockAnvil implements IConfigurable, ISubBlocksBlock {

	public NewAnvil() {
		setHardness(5.0F);
		setCreativeTab(null);
		setResistance(2000.0F);
		setStepSound(soundTypeAnvil);
		setBlockName(Utils.getUnlocalisedName("anvil"));
	}

	@Override
	public Item getItemDropped(int meta, Random rand, int fortune) {
		return Item.getItemFromBlock(Blocks.anvil);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Item getItem(World world, int x, int y, int z) {
		return Item.getItemFromBlock(Blocks.anvil);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if (world.isRemote)
			return true;
		else {
			player.openGui(EtFuturum.instance, GUIsID.ANVIL, world, x, y, z);
			return true;
		}
	}

	@Override
	public boolean isEnabled() {
		return EtFuturum.enableAnvil;
	}

	@Override
	public Class<? extends ItemBlock> getItemBlockClass() {
		return ItemAnvilBlock.class;
	}

	public static void onPlayerInteract(PlayerInteractEvent event) {
		if (!EtFuturum.enableAnvil)
			return;

		World world = event.world;
		int x = event.x;
		int y = event.y;
		int z = event.z;

		if (world == null || world.isRemote)
			return;
		if (world.getBlock(x, y, z) == Blocks.anvil)
			world.setBlock(x, y, z, ModBlocks.anvil, world.getBlockMetadata(x, y, z), 3);
	}
}