package ganymedes01.etfuturum.core.handlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.WorldTickEvent;
import cpw.mods.fml.relauncher.Side;
import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.IConfigurable;
import ganymedes01.etfuturum.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class WorldTickEventHandler {

	private static Map<Block, Block> replacements;
	private boolean isReplacing = false;

	@SubscribeEvent
	@SuppressWarnings("unchecked")
	public void tick(WorldTickEvent event) {
		if (event.side != Side.SERVER || event.phase != Phase.END || isReplacing)
			return;

		if (replacements == null) {
			replacements = new HashMap<Block, Block>();
			if (EtFuturum.enableBrewingStands)
				replacements.put(Blocks.brewing_stand, ModBlocks.brewing_stand);
			if (EtFuturum.enableColourfulBeacons)
				replacements.put(Blocks.beacon, ModBlocks.beacon);
			if (EtFuturum.enableEnchants)
				replacements.put(Blocks.enchanting_table, ModBlocks.enchantment_table);
			if (EtFuturum.enableInvertedDaylightSensor)
				replacements.put(Blocks.daylight_detector, ModBlocks.daylight_sensor);
		}

		if (replacements.isEmpty())
			return;

		isReplacing = true;
		World world = event.world;

		for (TileEntity tile : new ArrayList<TileEntity>(world.loadedTileEntityList)) {
			int x = tile.xCoord;
			int y = tile.yCoord;
			int z = tile.zCoord;
			Block replacement = replacements.get(world.getBlock(x, y, z));
			if (replacement != null && ((IConfigurable) replacement).isEnabled()) {
				NBTTagCompound nbt = new NBTTagCompound();
				tile.writeToNBT(nbt);
				if (tile instanceof IInventory) {
					IInventory invt = (IInventory) tile;
					for (int i = 0; i < invt.getSizeInventory(); i++)
						invt.setInventorySlotContents(i, null);
				}
				world.setBlock(x, y, z, replacement);
				TileEntity newTile = world.getTileEntity(x, y, z);
				newTile.readFromNBT(nbt);
				break;
			}
		}
		isReplacing = false;
	}
}