package ganymedes01.etfuturum.client.renderer.entity;

import ganymedes01.etfuturum.client.OpenGLHelper;
import ganymedes01.etfuturum.client.model.ModelArmorStand;
import ganymedes01.etfuturum.client.model.ModelArmorStandArmor;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ArmourStandRenderer extends RendererLivingEntity {

	private static final ResourceLocation TEXTURE_ARMOR_STAND = new ResourceLocation("textures/entity/armorstand/wood.png");

	public ArmourStandRenderer() {
		super(new ModelArmorStand(), 0.0F);
		LayerBipedArmor layerbipedarmor = new LayerBipedArmor(this) {

			protected void func_177177_a() {
				field_177189_c = new ModelArmorStandArmor(0.5F);
				field_177186_d = new ModelArmorStandArmor(1.0F);
			}
		};
		this.addLayer(layerbipedarmor);
		this.addLayer(new LayerHeldItem(this));
		this.addLayer(new LayerCustomHead(func_177100_a().bipedHead));
	}

	@Override
	protected void rotateCorpse(EntityLivingBase entity, float x, float y, float z) {
		OpenGLHelper.rotate(180.0F - y, 0.0F, 1.0F, 0.0F);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return TEXTURE_ARMOR_STAND;
	}
}