package ganymedes01.etfuturum.client.renderer.block;

import ganymedes01.etfuturum.ModBlocks;
import ganymedes01.etfuturum.lib.RenderIDs;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.init.Blocks;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class BlockChorusPlantRender extends BlockChorusFlowerRender {

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		renderer.renderAllFaces = true;

		Block neighbourUp = world.getBlock(x + ForgeDirection.UP.offsetX, y + ForgeDirection.UP.offsetY, z + ForgeDirection.UP.offsetZ);
		Block neighbourDown = world.getBlock(x + ForgeDirection.DOWN.offsetX, y + ForgeDirection.DOWN.offsetY, z + ForgeDirection.DOWN.offsetZ);
		Block neighbourWest = world.getBlock(x + ForgeDirection.WEST.offsetX, y + ForgeDirection.WEST.offsetY, z + ForgeDirection.WEST.offsetZ);
		Block neighbourEast = world.getBlock(x + ForgeDirection.EAST.offsetX, y + ForgeDirection.EAST.offsetY, z + ForgeDirection.EAST.offsetZ);
		Block neighbourNorth = world.getBlock(x + ForgeDirection.NORTH.offsetX, y + ForgeDirection.NORTH.offsetY, z + ForgeDirection.NORTH.offsetZ);
		Block neighbourSouth = world.getBlock(x + ForgeDirection.SOUTH.offsetX, y + ForgeDirection.SOUTH.offsetY, z + ForgeDirection.SOUTH.offsetZ);
		float conWidth = 4 / 16F;

		if (neighbourUp == ModBlocks.chorus_flower || neighbourUp == Blocks.end_stone || neighbourUp == block) {
			renderer.setRenderBounds(conWidth, 1 - conWidth, conWidth, 1 - conWidth, 1, 1 - conWidth);
			renderer.renderStandardBlock(block, x, y, z);
		}
		if (neighbourDown == ModBlocks.chorus_flower || neighbourDown == Blocks.end_stone || neighbourDown == block) {
			renderer.setRenderBounds(conWidth, 0, conWidth, 1 - conWidth, conWidth, 1 - conWidth);
			renderer.renderStandardBlock(block, x, y, z);
		}
		if (neighbourWest == ModBlocks.chorus_flower || neighbourWest == Blocks.end_stone || neighbourWest == block) {
			renderer.setRenderBounds(0, conWidth, conWidth, conWidth, 1 - conWidth, 1 - conWidth);
			renderer.renderStandardBlock(block, x, y, z);
		}
		if (neighbourEast == ModBlocks.chorus_flower || neighbourEast == Blocks.end_stone || neighbourEast == block) {
			renderer.setRenderBounds(1 - conWidth, conWidth, conWidth, 1, 1 - conWidth, 1 - conWidth);
			renderer.renderStandardBlock(block, x, y, z);
		}
		if (neighbourNorth == ModBlocks.chorus_flower || neighbourNorth == Blocks.end_stone || neighbourNorth == block) {
			renderer.setRenderBounds(conWidth, conWidth, 0, 1 - conWidth, 1 - conWidth, conWidth);
			renderer.renderStandardBlock(block, x, y, z);
		}
		if (neighbourSouth == ModBlocks.chorus_flower || neighbourSouth == Blocks.end_stone || neighbourSouth == block) {
			renderer.setRenderBounds(conWidth, conWidth, 1 - conWidth, 1 - conWidth, 1 - conWidth, 1);
			renderer.renderStandardBlock(block, x, y, z);
		}

		renderer.setRenderBounds(conWidth, conWidth, conWidth, 1 - conWidth, 1 - conWidth, 1 - conWidth);
		renderer.renderStandardBlock(block, x, y, z);

		return true;
	}

	@Override
	public int getRenderId() {
		return RenderIDs.CHORUS_PLANT;
	}
}