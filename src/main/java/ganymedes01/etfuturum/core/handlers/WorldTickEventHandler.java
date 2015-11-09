package ganymedes01.etfuturum.core.handlers;

import java.util.List;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.WorldTickEvent;
import cpw.mods.fml.relauncher.Side;
import ganymedes01.etfuturum.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class WorldTickEventHandler {

	private boolean isReplacing = false;

	@SubscribeEvent
	@SuppressWarnings("unchecked")
	public void tick(WorldTickEvent event) {
		if (event.side != Side.SERVER || event.phase != Phase.END || isReplacing)
			return;

		isReplacing = true;
		World world = event.world;
		for (TileEntity tile : (List<TileEntity>) world.loadedTileEntityList) {
			Block block = world.getBlock(tile.xCoord, tile.yCoord, tile.zCoord);
			if (block == Blocks.brewing_stand) {
				NBTTagCompound nbt = new NBTTagCompound();
				tile.writeToNBT(nbt);
				if (tile instanceof IInventory) {
					IInventory invt = (IInventory) tile;
					for (int i = 0; i < invt.getSizeInventory(); i++)
						invt.setInventorySlotContents(i, null);
				}
				world.setBlock(tile.xCoord, tile.yCoord, tile.zCoord, ModBlocks.brewing_stand);
				TileEntity newTile = world.getTileEntity(tile.xCoord, tile.yCoord, tile.zCoord);
				newTile.readFromNBT(nbt);
				return;
			}
		}
		isReplacing = false;
	}
}