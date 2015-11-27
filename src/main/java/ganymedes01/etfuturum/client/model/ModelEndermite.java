package ganymedes01.etfuturum.client.model;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

@SideOnly(Side.CLIENT)
public class ModelEndermite extends ModelBase {

	private static final int[][] field_178716_a = new int[][] { { 4, 3, 2 }, { 6, 4, 5 }, { 3, 3, 1 }, { 1, 2, 1 } };
	private static final int[][] field_178714_b = new int[][] { { 0, 0 }, { 0, 5 }, { 0, 14 }, { 0, 18 } };
	private static final int field_178715_c = field_178716_a.length;
	private final ModelRenderer[] field_178713_d;

	public ModelEndermite() {
		field_178713_d = new ModelRenderer[field_178715_c];
		float f = -3.5F;

		for (int i = 0; i < field_178713_d.length; ++i) {
			field_178713_d[i] = new ModelRenderer(this, field_178714_b[i][0], field_178714_b[i][1]);
			field_178713_d[i].addBox(field_178716_a[i][0] * -0.5F, 0.0F, field_178716_a[i][2] * -0.5F, field_178716_a[i][0], field_178716_a[i][1], field_178716_a[i][2]);
			field_178713_d[i].setRotationPoint(0.0F, 24 - field_178716_a[i][1], f);

			if (i < field_178713_d.length - 1)
				f += (field_178716_a[i][2] + field_178716_a[i + 1][2]) * 0.5F;
		}
	}

	@Override
	public void render(Entity p_78088_1_, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float p_78088_7_) {
		setRotationAngles(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, p_78088_7_, p_78088_1_);

		for (int i = 0; i < field_178713_d.length; ++i)
			field_178713_d[i].render(p_78088_7_);
	}

	@Override
	public void setRotationAngles(float p_78087_1_, float p_78087_2_, float p_78087_3_, float p_78087_4_, float p_78087_5_, float p_78087_6_, Entity p_78087_7_) {
		for (int i = 0; i < field_178713_d.length; ++i) {
			field_178713_d[i].rotateAngleY = MathHelper.cos(p_78087_3_ * 0.9F + i * 0.15F * (float) Math.PI) * (float) Math.PI * 0.01F * (1 + Math.abs(i - 2));
			field_178713_d[i].rotationPointX = MathHelper.sin(p_78087_3_ * 0.9F + i * 0.15F * (float) Math.PI) * (float) Math.PI * 0.1F * Math.abs(i - 2);
		}
	}
}