package ganymedes01.etfuturum.client.renderer.block;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.client.OpenGLHelper;
import ganymedes01.etfuturum.lib.RenderIDs;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;

@SideOnly(Side.CLIENT)
public class BlockChorusFlowerRender implements ISimpleBlockRenderingHandler {

	@Override
	public void renderInventoryBlock(Block block, int meta, int modelID, RenderBlocks renderer) {
		OpenGLHelper.translate(-0.5F, -0.5F, -0.5F);

		renderer.setRenderBounds(2 / 16F, 14 / 16F, 2 / 16F, 14 / 16F, 1, 14 / 16F);
		renderCube(renderer, block, meta);
		renderer.setRenderBounds(0, 2 / 16F, 2 / 16F, 2 / 16F, 14 / 16F, 14 / 16F);
		renderCube(renderer, block, meta);
		renderer.setRenderBounds(2 / 16F, 2 / 16F, 0, 14 / 16F, 14 / 16F, 2 / 16F);
		renderCube(renderer, block, meta);
		renderer.setRenderBounds(2 / 16F, 2 / 16F, 14 / 16F, 14 / 16F, 14 / 16F, 1);
		renderCube(renderer, block, meta);
		renderer.setRenderBounds(14 / 16F, 2 / 16F, 2 / 16F, 1, 14 / 16F, 14 / 16F);
		renderCube(renderer, block, meta);
		renderer.setRenderBounds(2 / 16F, 0, 2 / 16F, 14 / 16F, 14 / 16F, 14 / 16F);
		renderCube(renderer, block, meta);
	}

	private void renderCube(RenderBlocks renderer, Block block, int meta) {
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, -1.0F, 0.0F);
		renderer.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 0, meta));
		tessellator.setNormal(0.0F, 1.0F, 0.0F);
		renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 1, meta));
		tessellator.setNormal(0.0F, 0.0F, -1.0F);
		renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 2, meta));
		tessellator.setNormal(0.0F, 0.0F, 1.0F);
		renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 3, meta));
		tessellator.setNormal(-1.0F, 0.0F, 0.0F);
		renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 4, meta));
		tessellator.setNormal(1.0F, 0.0F, 0.0F);
		renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 5, meta));
		tessellator.draw();
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		renderer.renderAllFaces = true;

		renderer.setRenderBounds(2 / 16F, 14 / 16F, 2 / 16F, 14 / 16F, 1, 14 / 16F);
		renderer.renderStandardBlock(block, x, y, z);

		renderer.setRenderBounds(0, 2 / 16F, 2 / 16F, 2 / 16F, 14 / 16F, 14 / 16F);
		renderer.renderStandardBlock(block, x, y, z);

		renderer.setRenderBounds(2 / 16F, 2 / 16F, 0, 14 / 16F, 14 / 16F, 2 / 16F);
		renderer.renderStandardBlock(block, x, y, z);

		renderer.setRenderBounds(2 / 16F, 2 / 16F, 14 / 16F, 14 / 16F, 14 / 16F, 1);
		renderer.renderStandardBlock(block, x, y, z);

		renderer.setRenderBounds(14 / 16F, 2 / 16F, 2 / 16F, 1, 14 / 16F, 14 / 16F);
		renderer.renderStandardBlock(block, x, y, z);

		renderer.setRenderBounds(2 / 16F, 0, 2 / 16F, 14 / 16F, 14 / 16F, 14 / 16F);
		renderer.renderStandardBlock(block, x, y, z);

		renderer.renderAllFaces = false;
		return true;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return true;
	}

	@Override
	public int getRenderId() {
		return RenderIDs.CHORUS_FLOWER;
	}
}