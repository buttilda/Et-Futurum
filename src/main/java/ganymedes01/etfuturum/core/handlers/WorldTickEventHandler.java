package ganymedes01.etfuturum.core.handlers;

import ganymedes01.etfuturum.ModBlocks;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.WorldTickEvent;
import cpw.mods.fml.relauncher.Side;

public class WorldTickEventHandler {

	@SubscribeEvent
	@SuppressWarnings("unchecked")
	public void tick(WorldTickEvent event) {
		if (event.side != Side.SERVER || event.phase != Phase.END)
			return;

		World world = event.world;
		for (TileEntity tile : (List<TileEntity>) world.loadedTileEntityList) {
			Block block = world.getBlock(tile.xCoord, tile.yCoord, tile.zCoord);
			if (block == Blocks.brewing_stand) {
				world.setBlock(tile.xCoord, tile.yCoord, tile.zCoord, ModBlocks.brewing_stand);
				return;
			}
		}
	}
}