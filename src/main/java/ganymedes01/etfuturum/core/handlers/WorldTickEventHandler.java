package ganymedes01.etfuturum.core.handlers;

import ganymedes01.etfuturum.ModBlocks;
import ganymedes01.etfuturum.tileentities.TileEntityNewBrewingStand;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBrewingStand;
import net.minecraft.world.World;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.WorldTickEvent;
import cpw.mods.fml.relauncher.Side;

public class WorldTickEventHandler {

	private boolean isReplacing = false;
	private ArrayList<TileEntity> tiles = new ArrayList<TileEntity>();

	@SubscribeEvent
	public void tick(WorldTickEvent event) {
		if (event.side != Side.SERVER || event.phase != Phase.END || isReplacing == true)
			return;

		World world = event.world;
		tiles = (ArrayList<TileEntity>) world.loadedTileEntityList;

		isReplacing = true;
		if (!tiles.isEmpty()) {
			for (TileEntity tile : tiles) {
				if (tile instanceof TileEntityBrewingStand && !(tile instanceof TileEntityNewBrewingStand)) {
					tiles.remove(tile);
					world.setBlock(tile.xCoord, tile.yCoord, tile.zCoord, ModBlocks.brewing_stand);
				}
			}
		}
		isReplacing = false;
	}
}