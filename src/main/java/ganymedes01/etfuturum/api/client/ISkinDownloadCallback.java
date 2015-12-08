package ganymedes01.etfuturum.api.client;

import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.resources.SkinManager.SkinAvailableCallback;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public interface ISkinDownloadCallback extends SkinAvailableCallback {

	/**
	 * Gets called when a 1.8 style skin is downloaded
	 */
	@Override
	void func_152121_a(Type skinType, ResourceLocation resourceLocation);
}