package ganymedes01.etfuturum.core.proxy;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.ModBlocks;
import ganymedes01.etfuturum.client.renderer.block.BlockChestRenderer;
import ganymedes01.etfuturum.client.renderer.block.BlockDoorRenderer;
import ganymedes01.etfuturum.client.renderer.block.BlockSlimeBlockRender;
import ganymedes01.etfuturum.client.renderer.entity.ArmourStandRenderer;
import ganymedes01.etfuturum.client.renderer.item.ItemBannerRenderer;
import ganymedes01.etfuturum.client.renderer.item.ItemSkullRenderer;
import ganymedes01.etfuturum.client.renderer.tileentity.TileEntityBannerRenderer;
import ganymedes01.etfuturum.client.renderer.tileentity.TileEntityFancySkullRenderer;
import ganymedes01.etfuturum.entities.EntityArmourStand;
import ganymedes01.etfuturum.tileentities.TileEntityBanner;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraftforge.client.MinecraftForgeClient;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy {

	@Override
	public void registerRenderers() {
		registerItemRenderers();
		registerBlockRenderers();
		registerEntityRenderers();
	}

	private void registerItemRenderers() {
		if (EtFuturum.enableBanners)
			MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.banner), new ItemBannerRenderer());
		if (EtFuturum.enableFancySkulls)
			MinecraftForgeClient.registerItemRenderer(Items.skull, new ItemSkullRenderer());
	}

	private void registerBlockRenderers() {
		if (EtFuturum.enableSlimeBlock)
			RenderingRegistry.registerBlockHandler(new BlockSlimeBlockRender());

		if (EtFuturum.enableDoors)
			RenderingRegistry.registerBlockHandler(new BlockDoorRenderer());

		if (EtFuturum.enableBanners)
			ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBanner.class, new TileEntityBannerRenderer());

		if (EtFuturum.enableFancySkulls)
			ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySkull.class, new TileEntityFancySkullRenderer());

		RenderingRegistry.registerBlockHandler(new BlockChestRenderer());
	}

	private void registerEntityRenderers() {
		if (EtFuturum.enableArmourStand)
			RenderingRegistry.registerEntityRenderingHandler(EntityArmourStand.class, new ArmourStandRenderer());
	}
}