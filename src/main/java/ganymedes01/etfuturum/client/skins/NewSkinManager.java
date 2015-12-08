package ganymedes01.etfuturum.client.skins;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Map;
import java.util.concurrent.ExecutorService;

import com.google.common.collect.Maps;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.InsecureTextureException;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;
import com.mojang.authlib.minecraft.MinecraftSessionService;

import cpw.mods.fml.relauncher.ReflectionHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.api.client.ISkinDownloadCallback;
import ganymedes01.etfuturum.lib.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IImageBuffer;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.SkinManager;
import net.minecraft.util.ResourceLocation;

/**
 * Stolen from 1.8 and modified to work with 1.7.10
 */
@SideOnly(Side.CLIENT)
public class NewSkinManager extends SkinManager {

	private static ExecutorService executionService;

	private final TextureManager textureManager;
	private final File skinFolder;
	private final MinecraftSessionService sessionService;

	public NewSkinManager(SkinManager oldManager, TextureManager textureManager, File skinFolder, MinecraftSessionService sessionService) {
		super(textureManager, skinFolder, sessionService);

		executionService = ReflectionHelper.getPrivateValue(SkinManager.class, null, "field_152794_b");

		this.textureManager = textureManager;
		this.skinFolder = skinFolder;
		this.sessionService = sessionService;
	}

	@Override
	public ResourceLocation func_152792_a(MinecraftProfileTexture texture, Type type) {
		return func_152789_a(texture, type, null);
	}

	@Override
	public ResourceLocation func_152789_a(MinecraftProfileTexture texture, final Type type, final SkinManager.SkinAvailableCallback callBack) {
		final ResourceLocation resLocation = new ResourceLocation(Reference.MOD_ID, "skins/" + texture.getHash());
		ITextureObject itextureobject = textureManager.getTexture(resLocation);

		if (itextureobject != null) {
			if (callBack != null)
				callBack.func_152121_a(type, resLocation);
		} else {
			File file1 = new File(skinFolder, texture.getHash().substring(0, 2));
			File file2 = new File(file1, texture.getHash());
			final NewImageBufferDownload imgDownload = type == Type.SKIN ? new NewImageBufferDownload() : null;
			ThreadDownloadImageData imgData = new ThreadDownloadImageData(file2, texture.getUrl(), field_152793_a, new IImageBuffer() {

				@Override
				public BufferedImage parseUserSkin(BufferedImage buffImg) {
					if (imgDownload != null)
						buffImg = imgDownload.parseUserSkin(buffImg);

					return buffImg;
				}

				@Override
				public void func_152634_a() {
					if (imgDownload != null)
						imgDownload.func_152634_a();

					if (callBack != null)
						callBack.func_152121_a(type, resLocation);
				}
			});
			textureManager.loadTexture(resLocation, imgData);
		}

		return super.func_152789_a(texture, type, callBack instanceof ISkinDownloadCallback ? null : callBack);
	}

	@Override
	public void func_152790_a(GameProfile profile, NewSkinManager.SkinAvailableCallback callBack, boolean requireSecure) {
		// Get 1.7.10 style skins and store them in the default cache
		super.func_152790_a(profile, callBack instanceof ISkinDownloadCallback ? null : callBack, requireSecure);

		// Get the 1.8+ style skins and store them in my own cache
		executionService.submit(new Runnable() {

			@Override
			public void run() {
				final Map<MinecraftProfileTexture.Type, MinecraftProfileTexture> hashmap = Maps.newHashMap();

				try {
					hashmap.putAll(sessionService.getTextures(profile, requireSecure));
				} catch (InsecureTextureException e) {
				}

				if (hashmap.isEmpty() && profile.getId().equals(Minecraft.getMinecraft().getSession().func_148256_e().getId()))
					hashmap.putAll(sessionService.getTextures(sessionService.fillProfileProperties(profile, false), false));

				Minecraft.getMinecraft().func_152344_a(new Runnable() {

					@Override
					public void run() {
						if (hashmap.containsKey(Type.SKIN))
							NewSkinManager.this.func_152789_a(hashmap.get(Type.SKIN), Type.SKIN, callBack);

						if (hashmap.containsKey(Type.CAPE))
							NewSkinManager.this.func_152789_a(hashmap.get(Type.CAPE), Type.CAPE, callBack);
					}
				});
			}
		});
	}
}