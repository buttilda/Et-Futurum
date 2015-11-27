package ganymedes01.etfuturum.client.renderer.entity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.client.model.ModelEndermite;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class EndermiteRenderer extends RenderLiving {

	private static final ResourceLocation texture = new ResourceLocation("textures/entity/endermite.png");

	public EndermiteRenderer() {
		super(new ModelEndermite(), 0.3F);
	}

	@Override
	protected float getDeathMaxRotation(EntityLivingBase entity) {
		return 180.0F;
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return texture;
	}
}