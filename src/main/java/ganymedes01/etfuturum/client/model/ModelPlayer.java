package ganymedes01.etfuturum.client.model;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.client.OpenGLHelper;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;

@SideOnly(Side.CLIENT)
public class ModelPlayer extends ModelBiped {

	public ModelPlayer() {
		super(0.0F);
	}

	@Override
	public void render(Entity entity, float f0, float f1, float f2, float f3, float f4, float pixel) {
		setRotationAngles(f0, f1, f2, f3, f4, pixel, entity);

		if (isChild) {
			float f6 = 2.0F;
			OpenGLHelper.pushMatrix();
			OpenGLHelper.scale(1.5F / f6, 1.5F / f6, 1.5F / f6);
			OpenGLHelper.translate(0.0F, 16.0F * pixel, 0.0F);
			bipedHead.render(pixel);
			OpenGLHelper.popMatrix();
			OpenGLHelper.pushMatrix();
			OpenGLHelper.scale(1.0F / f6, 1.0F / f6, 1.0F / f6);
			OpenGLHelper.translate(0.0F, 24.0F * pixel, 0.0F);
			bipedBody.render(pixel);
			bipedRightArm.render(pixel);
			bipedLeftArm.render(pixel);
			bipedRightLeg.render(pixel);
			bipedLeftLeg.render(pixel);
			OpenGLHelper.popMatrix();
		} else {
			bipedHead.render(pixel);
			bipedBody.render(pixel);
			bipedRightArm.render(pixel);
			bipedLeftArm.render(pixel);
			bipedRightLeg.render(pixel);
			bipedLeftLeg.render(pixel);

			OpenGLHelper.enableBlend();
			OpenGLHelper.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			bipedHead.render(pixel);
			OpenGLHelper.disableBlend();
		}
	}
}