package ganymedes01.etfuturum.client.renderer.block;

import ganymedes01.etfuturum.client.OpenGLHelper;
import ganymedes01.etfuturum.lib.RenderIDs;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class BlockEndRodRender implements ISimpleBlockRenderingHandler {

	@Override
	public void renderInventoryBlock(Block block, int meta, int modelID, RenderBlocks renderer) {
		Tessellator tessellator = Tessellator.instance;
		OpenGLHelper.translate(-0.5F, -0.5F, -0.5F);

		float f = 0.0625F;
		renderer.setRenderBounds(f * 3, f * 3, f * 3, f * 13, f * 13, f * 13);
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

		renderer.setRenderBounds(0, 0, 0, 1, 1, 1);
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
		renderer.setRenderBounds(0, 0, 0, 2 / 16F, 15 / 16F, 2 / 16F);
		renderBlock(renderer, block, x + 7 / 16F, y + 1 / 16F, z + 7 / 16F);

		renderer.setRenderBounds(2 / 16F, 0, 2 / 16F, 6 / 16F, 2 / 16F, 6 / 16F);
		renderBlock(renderer, block, x + 7 / 16F, y, z + 7 / 16F);

		return true;
	}

	public boolean renderBlock(RenderBlocks renderer, Block block, double x, double y, double z) {
		renderer.enableAO = false;
		Tessellator tessellator = Tessellator.instance;

		int l = block.getMixedBrightnessForBlock(renderer.blockAccess, (int) x, (int) y, (int) z);
		tessellator.setColorOpaque_F(1, 1, 1);
		tessellator.setBrightness(l);

		renderer.renderFaceYNeg(block, x, y, z, renderer.getBlockIcon(block, renderer.blockAccess, (int) x, (int) y, (int) z, 0));
		renderer.renderFaceYPos(block, x, y, z, renderer.getBlockIcon(block, renderer.blockAccess, (int) x, (int) y, (int) z, 1));
		renderer.renderFaceZNeg(block, x, y, z, renderer.getBlockIcon(block, renderer.blockAccess, (int) x, (int) y, (int) z, 2));
		renderer.renderFaceZPos(block, x, y, z, renderer.getBlockIcon(block, renderer.blockAccess, (int) x, (int) y, (int) z, 3));
		renderer.renderFaceXNeg(block, x, y, z, renderer.getBlockIcon(block, renderer.blockAccess, (int) x, (int) y, (int) z, 4));
		renderer.renderFaceXPos(block, x, y, z, renderer.getBlockIcon(block, renderer.blockAccess, (int) x, (int) y, (int) z, 5));

		return true;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return true;
	}

	@Override
	public int getRenderId() {
		return RenderIDs.END_ROD;
	}
}