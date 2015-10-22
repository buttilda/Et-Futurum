package ganymedes01.etfuturum.core.handlers;

import ganymedes01.etfuturum.ModBlocks;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.WorldTickEvent;
import cpw.mods.fml.relauncher.Side;

public class WorldTickEventHandler {

	private static ArrayList<TileEntity> todoStands = new ArrayList<TileEntity>();
	private static ArrayList<TileEntity> migratedStands = new ArrayList<TileEntity>();

	@SubscribeEvent
	@SuppressWarnings("unchecked")
	public void tick(WorldTickEvent event) {
		if (event.side != Side.SERVER || event.phase != Phase.END)
			return;

		World world = event.world; 
		for (Object te : world.loadedTileEntityList) {
				TileEntity tile = (TileEntity) te;
				Block block = world.getBlock(tile.xCoord, tile.yCoord, tile.zCoord);
				if (block == Blocks.brewing_stand) {
					todoStands.add(tile);
				}
		}
		if (!todoStands.isEmpty()) {
			for (TileEntity te : (ArrayList<TileEntity>) todoStands) {
				TileEntity tile = (TileEntity) te;
				world.setBlock(tile.xCoord, tile.yCoord, tile.zCoord, ModBlocks.brewing_stand);
				migratedStands.add(tile);
			}
		}
		// Avoid ConcurrentModificationException
		if (!migratedStands.isEmpty()) {
			for (TileEntity te : (ArrayList<TileEntity>) migratedStands) {
				if (todoStands.contains(te)) {
					todoStands.remove(te);
				}
			}
			migratedStands.clear();
		}
	}
}