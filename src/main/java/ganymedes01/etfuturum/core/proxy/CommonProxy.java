package ganymedes01.etfuturum.core.proxy;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.client.gui.inventory.GuiEnchantment;
import ganymedes01.etfuturum.configuration.ConfigurationHandler;
import ganymedes01.etfuturum.core.handlers.ModEventHandler;
import ganymedes01.etfuturum.core.utils.Utils;
import ganymedes01.etfuturum.entities.EntityArmourStand;
import ganymedes01.etfuturum.inventory.ContainerEnchantment;
import ganymedes01.etfuturum.lib.GUIsID;
import ganymedes01.etfuturum.tileentities.TileEntityBanner;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

public class CommonProxy implements IGuiHandler {

	public void registerEvents() {
		FMLCommonHandler.instance().bus().register(ConfigurationHandler.INSTANCE);
		MinecraftForge.EVENT_BUS.register(new ModEventHandler());
	}

	public void registerEntities() {
		if (EtFuturum.enableBanners)
			GameRegistry.registerTileEntity(TileEntityBanner.class, Utils.getUnlocalisedName("banner"));
		if (EtFuturum.enableArmourStand)
			EntityRegistry.registerModEntity(EntityArmourStand.class, "wooden_armorstand", 0, EtFuturum.instance, 64, 1, true);
	}

	public void registerRenderers() {
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		switch (ID) {
			case GUIsID.ENCHANTING_TABLE:
				return new ContainerEnchantment(player.inventory, world, x, y, z);
			default:
				return null;
		}
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		switch (ID) {
			case GUIsID.ENCHANTING_TABLE:
				return new GuiEnchantment(player.inventory, world, null);
			default:
				return null;
		}
	}
}