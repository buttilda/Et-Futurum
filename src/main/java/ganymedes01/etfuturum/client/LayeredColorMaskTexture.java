package ganymedes01.etfuturum.client;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.compress.utils.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.lib.EnumColour;
import net.minecraft.block.material.MapColor;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class LayeredColorMaskTexture extends AbstractTexture {

	private static final Logger field_174947_f = LogManager.getLogger();
	/** The location of the texture. */
	private final ResourceLocation textureLocation;
	private final List<String> field_174949_h;
	private final List<EnumColour> field_174950_i;

	public LayeredColorMaskTexture(ResourceLocation textureLocationIn, List<String> p_i46101_2_, List<EnumColour> p_i46101_3_) {
		textureLocation = textureLocationIn;
		field_174949_h = p_i46101_2_;
		field_174950_i = p_i46101_3_;
	}

	@Override
	public void loadTexture(IResourceManager resourceManager) throws IOException {
		deleteGlTexture();
		BufferedImage bufferedimage;

		try {
			BufferedImage bufferedimage1 = readBufferedImage(resourceManager.getResource(textureLocation).getInputStream());
			int i = bufferedimage1.getType();

			if (i == 0)
				i = 6;

			bufferedimage = new BufferedImage(bufferedimage1.getWidth(), bufferedimage1.getHeight(), i);
			Graphics graphics = bufferedimage.getGraphics();
			graphics.drawImage(bufferedimage1, 0, 0, (ImageObserver) null);

			for (int j = 0; j < field_174949_h.size() && j < field_174950_i.size(); ++j) {
				String s = field_174949_h.get(j);
				MapColor mapcolor = field_174950_i.get(j).getMapColour();

				if (s != null) {
					InputStream inputstream = resourceManager.getResource(new ResourceLocation(s)).getInputStream();
					BufferedImage bufferedimage2 = readBufferedImage(inputstream);

					if (bufferedimage2.getWidth() == bufferedimage.getWidth() && bufferedimage2.getHeight() == bufferedimage.getHeight() && bufferedimage2.getType() == 6) {
						for (int k = 0; k < bufferedimage2.getHeight(); ++k)
							for (int l = 0; l < bufferedimage2.getWidth(); ++l) {
								int i1 = bufferedimage2.getRGB(l, k);

								if ((i1 & -16777216) != 0) {
									int j1 = (i1 & 16711680) << 8 & -16777216;
									int k1 = bufferedimage1.getRGB(l, k);
									int l1 = func_180188_d(k1, mapcolor.colorValue) & 16777215;
									bufferedimage2.setRGB(l, k, j1 | l1);
								}
							}

						bufferedimage.getGraphics().drawImage(bufferedimage2, 0, 0, (ImageObserver) null);
					}
				}
			}
		} catch (IOException ioexception) {
			field_174947_f.error("Couldn\'t load layered image", ioexception);
			return;
		}

		TextureUtil.uploadTextureImage(getGlTextureId(), bufferedimage);
	}

	@SideOnly(Side.CLIENT)
	private int func_180188_d(int p_180188_0_, int p_180188_1_) {
		int k = (p_180188_0_ & 16711680) >> 16;
		int l = (p_180188_1_ & 16711680) >> 16;
		int i1 = (p_180188_0_ & 65280) >> 8;
		int j1 = (p_180188_1_ & 65280) >> 8;
		int k1 = (p_180188_0_ & 255) >> 0;
		int l1 = (p_180188_1_ & 255) >> 0;
		int i2 = (int) ((float) k * (float) l / 255.0F);
		int j2 = (int) ((float) i1 * (float) j1 / 255.0F);
		int k2 = (int) ((float) k1 * (float) l1 / 255.0F);
		return p_180188_0_ & -16777216 | i2 << 16 | j2 << 8 | k2;
	}

	private BufferedImage readBufferedImage(InputStream imageStream) throws IOException {
		BufferedImage bufferedimage;

		try {
			bufferedimage = ImageIO.read(imageStream);
		} finally {
			IOUtils.closeQuietly(imageStream);
		}

		return bufferedimage;
	}
}