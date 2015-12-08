package ganymedes01.etfuturum.client.skins;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.client.model.ModelPlayer;
import ganymedes01.etfuturum.lib.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class NewRenderPlayer extends RenderPlayer {

	public static final ResourceLocation STEVE_SKIN = new ResourceLocation("etfuturum", "textures/steve.png");

	public NewRenderPlayer() {
		renderManager = RenderManager.instance;
		mainModel = new ModelPlayer(0.0F, false);
		modelBipedMain = (ModelBiped) mainModel;
	}

	@Override
	protected ResourceLocation getEntityTexture(AbstractClientPlayer player) {
		if (!EtFuturum.enablePlayerSkinOverlay || player.getLocationSkin() == null)
			return super.getEntityTexture(player);
		return new ResourceLocation(Reference.MOD_ID, player.getLocationSkin().getResourcePath());
	}

	@Override
	protected boolean func_110813_b(EntityLivingBase entity) {
		boolean isGUiEnabled = Minecraft.isGuiEnabled();
		boolean isPlayer = entity != renderManager.livingPlayer;
		boolean isInvisible = !entity.isInvisibleToPlayer(Minecraft.getMinecraft().thePlayer);
		boolean isBeingRidden = entity.riddenByEntity == null;

		return isGUiEnabled && isPlayer && isInvisible && isBeingRidden;
	}

	@Override
	public void renderFirstPersonArm(EntityPlayer player) {
		Minecraft.getMinecraft().getTextureManager().bindTexture(getEntityTexture(player));

		super.renderFirstPersonArm(player);
		((ModelPlayer) modelBipedMain).bipedRightArmwear.render(0.0625F);
	}
}