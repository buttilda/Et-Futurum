package ganymedes01.etfuturum.client.renderer.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.client.OpenGLHelper;
import ganymedes01.etfuturum.tileentities.TileEntityBanner;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

@SideOnly(Side.CLIENT)
public class ItemBannerRenderer implements IItemRenderer {

	private final TileEntityBanner banner = new TileEntityBanner();

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return type != ItemRenderType.FIRST_PERSON_MAP;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack stack, ItemRendererHelper helper) {
		return true;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack stack, Object... data) {
		banner.setItemValues(stack);
		banner.isStanding = true;

		switch (type) {
			case ENTITY:
				renderBanner(-0.5F, -0.75F, -0.5F, 90, 1);
				break;
			case EQUIPPED:
				renderBanner(0, 0, 0, 0, 1);
				break;
			case EQUIPPED_FIRST_PERSON:
				renderBanner(-0.75F, 0.0625F, -0.0625F, 67.5F, 1);
				break;
			case INVENTORY:
				renderBanner(0.0F, -0.25F, 0.6F, 22.5F, 0.9F);
				break;
			default:
				break;
		}
	}

	private void renderBanner(float x, float y, float z, float angle, float scale) {
		OpenGLHelper.pushMatrix();
		OpenGLHelper.rotate(angle, 0, 1, 0);
		OpenGLHelper.scale(scale, scale, scale);
		OpenGLHelper.translate(x, y, z);

		TileEntityRendererDispatcher.instance.renderTileEntityAt(banner, 0, 0, 0, 0);

		OpenGLHelper.popMatrix();
	}
}