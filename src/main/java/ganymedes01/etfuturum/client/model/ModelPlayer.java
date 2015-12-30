package ganymedes01.etfuturum.client.model;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.client.OpenGLHelper;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

@SideOnly(Side.CLIENT)
public class ModelPlayer extends ModelBiped {

	public ModelRenderer bipedLeftArmwear;
	public ModelRenderer bipedRightArmwear;
	public ModelRenderer bipedLeftLegwear;
	public ModelRenderer bipedRightLegwear;
	public ModelRenderer bipedBodyWear;

	public ModelPlayer(float size, boolean isAlex) {
		super(size, 0.0F, 64, 64);

		bipedEars = new ModelRenderer(this, 24, 0);
		bipedEars.addBox(-3.0F, -6.0F, -1.0F, 6, 6, 1, size);

		bipedCloak = new ModelRenderer(this, 0, 0);
		bipedCloak.setTextureSize(64, 32);
		bipedCloak.addBox(-5.0F, 0.0F, -1.0F, 10, 16, 1, size);

		if (isAlex) {
			bipedLeftArm = new ModelRenderer(this, 32, 48);
			bipedLeftArm.addBox(-1.0F, -2.0F, -2.0F, 3, 12, 4, size);
			bipedLeftArm.setRotationPoint(5.0F, 2.5F, 0.0F);
			bipedRightArm = new ModelRenderer(this, 40, 16);
			bipedRightArm.addBox(-2.0F, -2.0F, -2.0F, 3, 12, 4, size);
			bipedRightArm.setRotationPoint(-5.0F, 2.5F, 0.0F);
			bipedLeftArmwear = new ModelRenderer(this, 48, 48);
			bipedLeftArmwear.addBox(-1.0F, -2.0F, -2.0F, 3, 12, 4, size + 0.25F);
			bipedLeftArmwear.setRotationPoint(5.0F, 2.5F, 0.0F);
			bipedRightArmwear = new ModelRenderer(this, 40, 32);
			bipedRightArmwear.addBox(-2.0F, -2.0F, -2.0F, 3, 12, 4, size + 0.25F);
			bipedRightArmwear.setRotationPoint(-5.0F, 2.5F, 10.0F);
		} else {
			bipedLeftArm = new ModelRenderer(this, 32, 48);
			bipedLeftArm.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, size);
			bipedLeftArm.setRotationPoint(5.0F, 2.0F, 0.0F);
			bipedLeftArmwear = new ModelRenderer(this, 48, 48);
			bipedLeftArmwear.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, size + 0.25F);
			bipedLeftArmwear.setRotationPoint(5.0F, 2.0F, 0.0F);
			bipedRightArmwear = new ModelRenderer(this, 40, 32);
			bipedRightArmwear.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, size + 0.25F);
			bipedRightArmwear.setRotationPoint(-5.0F, 2.0F, 10.0F);
		}

		bipedLeftLeg = new ModelRenderer(this, 16, 48);
		bipedLeftLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, size);
		bipedLeftLeg.setRotationPoint(1.9F, 12.0F, 0.0F);
		bipedLeftLegwear = new ModelRenderer(this, 0, 48);
		bipedLeftLegwear.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, size + 0.25F);
		bipedLeftLegwear.setRotationPoint(1.9F, 12.0F, 0.0F);
		bipedRightLegwear = new ModelRenderer(this, 0, 32);
		bipedRightLegwear.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, size + 0.25F);
		bipedRightLegwear.setRotationPoint(-1.9F, 12.0F, 0.0F);
		bipedBodyWear = new ModelRenderer(this, 16, 32);
		bipedBodyWear.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, size + 0.25F);
		bipedBodyWear.setRotationPoint(0.0F, 0.0F, 0.0F);
	}

	@Override
	public void render(Entity entity, float f0, float f1, float f2, float f3, float f4, float scale) {
		setRotationAngles(f0, f1, f2, f3, f4, scale, entity);

		if (isChild) {
			float f6 = 2.0F;
			OpenGLHelper.pushMatrix();
			OpenGLHelper.scale(1.5F / f6, 1.5F / f6, 1.5F / f6);
			OpenGLHelper.translate(0.0F, 16.0F * scale, 0.0F);
			bipedHead.render(scale);
			OpenGLHelper.popMatrix();
			OpenGLHelper.pushMatrix();
			OpenGLHelper.scale(1.0F / f6, 1.0F / f6, 1.0F / f6);
			OpenGLHelper.translate(0.0F, 24.0F * scale, 0.0F);
			bipedBody.render(scale);
			bipedRightArm.render(scale);
			bipedLeftArm.render(scale);
			bipedRightLeg.render(scale);
			bipedLeftLeg.render(scale);
			OpenGLHelper.popMatrix();
		} else {
			bipedHead.render(scale);
			bipedBody.render(scale);
			bipedRightArm.render(scale);
			bipedLeftArm.render(scale);
			bipedRightLeg.render(scale);
			bipedLeftLeg.render(scale);
			OpenGLHelper.enableBlend();
			OpenGLHelper.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			bipedHeadwear.render(scale);
			OpenGLHelper.disableBlend();
		}

		OpenGLHelper.pushMatrix();
		OpenGLHelper.enableBlend();
		OpenGLHelper.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

		if (isChild) {
			float f6 = 2.0F;
			OpenGLHelper.scale(1.0F / f6, 1.0F / f6, 1.0F / f6);
			OpenGLHelper.translate(0.0F, 24.0F * scale, 0.0F);
			bipedLeftLegwear.render(scale);
			bipedRightLegwear.render(scale);
			bipedLeftArmwear.render(scale);
			bipedRightArmwear.render(scale);
			bipedBodyWear.render(scale);
		} else {
			bipedLeftLegwear.render(scale);
			bipedRightLegwear.render(scale);
			bipedLeftArmwear.render(scale);
			bipedRightArmwear.render(scale);
			bipedBodyWear.render(scale);
		}

		OpenGLHelper.disableBlend();
		OpenGLHelper.popMatrix();
	}

	@Override
	public void setRotationAngles(float f0, float f1, float f2, float f3, float f4, float f5, Entity entity) {
		super.setRotationAngles(f0, f1, f2, f3, f4, f5, entity);
		copyModelAngles(bipedLeftLeg, bipedLeftLegwear);
		copyModelAngles(bipedRightLeg, bipedRightLegwear);
		copyModelAngles(bipedLeftArm, bipedLeftArmwear);
		copyModelAngles(bipedRightArm, bipedRightArmwear);
		copyModelAngles(bipedBody, bipedBodyWear);
	}

	private void copyModelAngles(ModelRenderer source, ModelRenderer dest) {
		dest.rotateAngleX = source.rotateAngleX;
		dest.rotateAngleY = source.rotateAngleY;
		dest.rotateAngleZ = source.rotateAngleZ;
		dest.rotationPointX = source.rotationPointX;
		dest.rotationPointY = source.rotationPointY;
		dest.rotationPointZ = source.rotationPointZ;
	}
}