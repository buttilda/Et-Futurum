package ganymedes01.etfuturum.client.skins;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class PlayerModelManager {

	private static Map<ResourceLocation, Boolean> analysedTextures = new HashMap<ResourceLocation, Boolean>();

	public static boolean isPlayerModelAlex(ResourceLocation texture) {
		Boolean isAlex = analysedTextures.get(texture);
		if (isAlex == null) {
			isAlex = false;
			analysedTextures.put(texture, false);
		}
		return isAlex;
	}

	public static void analyseTexture(BufferedImage img, ResourceLocation texture) {
		analysedTextures.put(texture, isAreaEmpty(img, 50, 16, 2, 4));
	}

	private static boolean isAreaEmpty(BufferedImage img, int x, int y, int width, int height) {
		for (int i = x; i < x + width; i++)
			for (int j = y; j < y + height; j++) {
				int rgb = img.getRGB(i, j);
				int alpha = rgb >> 24 & 0xFF;
				if (alpha > 0)
					return false;
			}
		return true;
	}
}