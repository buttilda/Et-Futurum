package ganymedes01.etfuturum.client.skins;

import java.awt.image.BufferedImage;
import java.io.File;

import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;
import com.mojang.authlib.minecraft.MinecraftSessionService;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.api.client.ISkinDownloadCallback;
import ganymedes01.etfuturum.lib.Reference;
import net.minecraft.client.renderer.IImageBuffer;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.SkinManager;
import net.minecraft.util.ResourceLocation;

/**
 * Stolen from 1.8 and modified to work with 1.7.10
 */
@SideOnly(Side.CLIENT)
public class NewSkinManager extends SkinManager {

	private final TextureManager textureManager;
	private final File skinFolder;

	public NewSkinManager(SkinManager oldManager, TextureManager textureManager, File skinFolder, MinecraftSessionService sessionService) {
		super(textureManager, skinFolder, sessionService);

		this.textureManager = textureManager;
		this.skinFolder = skinFolder;
	}

	@Override
	public ResourceLocation func_152789_a(final MinecraftProfileTexture texture, final Type type, final SkinManager.SkinAvailableCallback callBack) {
		if (type != Type.SKIN)
			return super.func_152789_a(texture, type, callBack);

		final boolean isSpecialCallBack = callBack instanceof ISkinDownloadCallback;
		final ResourceLocation resLocationOld = new ResourceLocation("skins/" + texture.getHash());
		final ResourceLocation resLocation = new ResourceLocation(Reference.MOD_ID, resLocationOld.getResourcePath());
		ITextureObject itextureobject = textureManager.getTexture(resLocation);

		if (itextureobject != null) {
			if (callBack != null)
				callBack.func_152121_a(type, resLocation);
		} else {
			File file1 = new File(skinFolder, texture.getHash().substring(0, 2));
			File file2 = new File(file1, texture.getHash());
			final NewImageBufferDownload imgDownload = new NewImageBufferDownload();
			ITextureObject imgData = new NewThreadDownloadImageData(file2, texture.getUrl(), field_152793_a, imgDownload, resLocationOld, new IImageBuffer() {

				@Override
				public BufferedImage parseUserSkin(BufferedImage buffImg) {
					if (buffImg != null)
						PlayerModelManager.analyseTexture(buffImg, resLocation);
					return imgDownload.parseUserSkin(buffImg);
				}

				@Override
				public void func_152634_a() {
					imgDownload.func_152634_a();
					if (callBack != null)
						callBack.func_152121_a(type, isSpecialCallBack ? resLocation : resLocationOld);
				}
			});
			textureManager.loadTexture(resLocation, imgData);
			textureManager.loadTexture(resLocationOld, imgData); // Avoid thrown exception if the image is requested before the download is done
		}

		return isSpecialCallBack ? resLocation : resLocationOld;
	}
}