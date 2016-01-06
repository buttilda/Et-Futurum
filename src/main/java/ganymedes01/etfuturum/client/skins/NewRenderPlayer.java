package ganymedes01.etfuturum.client.skins;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.client.model.ModelPlayer;
import ganymedes01.etfuturum.command.SetPlayerModelCommand;
import ganymedes01.etfuturum.lib.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.Constants;

@SideOnly(Side.CLIENT)
public class NewRenderPlayer extends RenderPlayer {

	public static final ResourceLocation STEVE_SKIN = new ResourceLocation(Reference.MOD_ID, "textures/steve.png");
	private static final ModelBase STEVE = new ModelPlayer(0.0F, false);
	private static final ModelBase ALEX = new ModelPlayer(0.0F, true);

	public NewRenderPlayer() {
		renderManager = RenderManager.instance;
		mainModel = STEVE;
		modelBipedMain = (ModelBiped) mainModel;
	}

	private void setModel(EntityPlayer player) {
		boolean isAlex;

		NBTTagCompound nbt = player.getEntityData();
		if (nbt.hasKey(SetPlayerModelCommand.MODEL_KEY, Constants.NBT.TAG_BYTE))
			isAlex = nbt.getBoolean(SetPlayerModelCommand.MODEL_KEY);
		else
			isAlex = PlayerModelManager.isPlayerModelAlex(getEntityTexture(player));

		mainModel = isAlex ? ALEX : STEVE;
		modelBipedMain = (ModelBiped) mainModel;
	}

	@Override
	protected int shouldRenderPass(AbstractClientPlayer player, int pass, float partialTickTime) {
		setModel(player);
		return super.shouldRenderPass(player, pass, partialTickTime);
	}

	@Override
	public void doRender(AbstractClientPlayer player, double x, double y, double z, float someFloat, float partialTickTime) {
		setModel(player);
		super.doRender(player, x, y, z, someFloat, partialTickTime);
	}

	@Override
	protected void renderEquippedItems(AbstractClientPlayer player, float partialTickTime) {
		setModel(player);
		super.renderEquippedItems(player, partialTickTime);
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
		setModel(player);
		Minecraft.getMinecraft().getTextureManager().bindTexture(getEntityTexture(player));

		super.renderFirstPersonArm(player);
		((ModelPlayer) modelBipedMain).bipedRightArmwear.render(0.0625F);
	}
}