package ganymedes01.etfuturum.client.renderer.entity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.entities.EntityNewSnowGolem;
import net.minecraft.client.renderer.entity.RenderSnowMan;
import net.minecraft.entity.monster.EntitySnowman;

@SideOnly(Side.CLIENT)
public class NewSnowGolemRenderer extends RenderSnowMan {

	@Override
	protected void renderEquippedItems(EntitySnowman entity, float partialTickTime) {
		if (((EntityNewSnowGolem) entity).hasPumpkin())
			super.renderEquippedItems(entity, partialTickTime);
	}
}