package ganymedes01.etfuturum.client.renderer.tileentity;

import java.util.List;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.client.OpenGLHelper;
import ganymedes01.etfuturum.tileentities.TileEntityNewBeacon;
import ganymedes01.etfuturum.tileentities.TileEntityNewBeacon.BeamSegment;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class TileEntityNewBeaconRenderer extends TileEntitySpecialRenderer {

	private static final ResourceLocation BEAM_TEXTURE = new ResourceLocation("textures/entity/beacon_beam.png");

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float partialTickTime) {
		TileEntityNewBeacon beacon = (TileEntityNewBeacon) tile;

		float f1 = beacon.func_146002_i();
		OpenGLHelper.alphaFunc(GL11.GL_GREATER, 0.1F);

		if (f1 > 0.0F) {
			Tessellator tessellator = Tessellator.instance;
			List<BeamSegment> list = beacon.getSegments();
			int j = 0;

			for (int i = 0; i < list.size(); i++) {
				BeamSegment beamsegment = list.get(i);
				int l = j + beamsegment.func_177264_c();
				bindTexture(BEAM_TEXTURE);
				GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, 10497.0F);
				GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, 10497.0F);
				OpenGLHelper.disableLighting();
				OpenGLHelper.disableCull();
				OpenGLHelper.disableBlend();
				OpenGLHelper.depthMask(true);
				OpenGlHelper.glBlendFunc(770, 1, 1, 0);
				float f2 = tile.getWorldObj().getTotalWorldTime() + partialTickTime;
				float f3 = -f2 * 0.2F - MathHelper.floor_float(-f2 * 0.1F);
				double d3 = f2 * 0.025D * -1.5D;
				tessellator.startDrawingQuads();
				double d4 = 0.2D;
				double d5 = 0.5D + Math.cos(d3 + 2.356194490192345D) * d4;
				double d6 = 0.5D + Math.sin(d3 + 2.356194490192345D) * d4;
				double d7 = 0.5D + Math.cos(d3 + Math.PI / 4D) * d4;
				double d8 = 0.5D + Math.sin(d3 + Math.PI / 4D) * d4;
				double d9 = 0.5D + Math.cos(d3 + 3.9269908169872414D) * d4;
				double d10 = 0.5D + Math.sin(d3 + 3.9269908169872414D) * d4;
				double d11 = 0.5D + Math.cos(d3 + 5.497787143782138D) * d4;
				double d12 = 0.5D + Math.sin(d3 + 5.497787143782138D) * d4;
				double d13 = 0.0D;
				double d14 = 1.0D;
				double d15 = -1.0F + f3;
				double d16 = beamsegment.func_177264_c() * f1 * (0.5D / d4) + d15;
				tessellator.setColorRGBA_F(beamsegment.func_177263_b()[0], beamsegment.func_177263_b()[1], beamsegment.func_177263_b()[2], 0.125F);
				tessellator.addVertexWithUV(x + d5, y + l, z + d6, d14, d16);
				tessellator.addVertexWithUV(x + d5, y + j, z + d6, d14, d15);
				tessellator.addVertexWithUV(x + d7, y + j, z + d8, d13, d15);
				tessellator.addVertexWithUV(x + d7, y + l, z + d8, d13, d16);
				tessellator.addVertexWithUV(x + d11, y + l, z + d12, d14, d16);
				tessellator.addVertexWithUV(x + d11, y + j, z + d12, d14, d15);
				tessellator.addVertexWithUV(x + d9, y + j, z + d10, d13, d15);
				tessellator.addVertexWithUV(x + d9, y + l, z + d10, d13, d16);
				tessellator.addVertexWithUV(x + d7, y + l, z + d8, d14, d16);
				tessellator.addVertexWithUV(x + d7, y + j, z + d8, d14, d15);
				tessellator.addVertexWithUV(x + d11, y + j, z + d12, d13, d15);
				tessellator.addVertexWithUV(x + d11, y + l, z + d12, d13, d16);
				tessellator.addVertexWithUV(x + d9, y + l, z + d10, d14, d16);
				tessellator.addVertexWithUV(x + d9, y + j, z + d10, d14, d15);
				tessellator.addVertexWithUV(x + d5, y + j, z + d6, d13, d15);
				tessellator.addVertexWithUV(x + d5, y + l, z + d6, d13, d16);
				tessellator.draw();
				OpenGLHelper.enableBlend();
				OpenGlHelper.glBlendFunc(770, 771, 1, 0);
				OpenGLHelper.depthMask(false);
				tessellator.startDrawingQuads();
				tessellator.setColorRGBA_F(beamsegment.func_177263_b()[0], beamsegment.func_177263_b()[1], beamsegment.func_177263_b()[2], 0.125F);
				d3 = 0.2D;
				d4 = 0.2D;
				d5 = 0.8D;
				d6 = 0.2D;
				d7 = 0.2D;
				d8 = 0.8D;
				d9 = 0.8D;
				d10 = 0.8D;
				d11 = 0.0D;
				d12 = 1.0D;
				d13 = -1.0F + f3;
				d14 = beamsegment.func_177264_c() * f1 + d13;
				tessellator.addVertexWithUV(x + d3, y + l, z + d4, d12, d14);
				tessellator.addVertexWithUV(x + d3, y + j, z + d4, d12, d13);
				tessellator.addVertexWithUV(x + d5, y + j, z + d6, d11, d13);
				tessellator.addVertexWithUV(x + d5, y + l, z + d6, d11, d14);
				tessellator.addVertexWithUV(x + d9, y + l, z + d10, d12, d14);
				tessellator.addVertexWithUV(x + d9, y + j, z + d10, d12, d13);
				tessellator.addVertexWithUV(x + d7, y + j, z + d8, d11, d13);
				tessellator.addVertexWithUV(x + d7, y + l, z + d8, d11, d14);
				tessellator.addVertexWithUV(x + d5, y + l, z + d6, d12, d14);
				tessellator.addVertexWithUV(x + d5, y + j, z + d6, d12, d13);
				tessellator.addVertexWithUV(x + d9, y + j, z + d10, d11, d13);
				tessellator.addVertexWithUV(x + d9, y + l, z + d10, d11, d14);
				tessellator.addVertexWithUV(x + d7, y + l, z + d8, d12, d14);
				tessellator.addVertexWithUV(x + d7, y + j, z + d8, d12, d13);
				tessellator.addVertexWithUV(x + d3, y + j, z + d4, d11, d13);
				tessellator.addVertexWithUV(x + d3, y + l, z + d4, d11, d14);
				tessellator.draw();
				OpenGLHelper.enableLighting();
				OpenGLHelper.enableTexture2D();
				OpenGLHelper.depthMask(true);
				j = l;
			}
		}
	}
}