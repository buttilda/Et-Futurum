package ganymedes01.etfuturum.configuration;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.lib.Reference;

import java.io.File;

import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class ConfigurationHandler {

	public static ConfigurationHandler INSTANCE = new ConfigurationHandler();
	public Configuration configFile;
	public String[] usedCategories = { Configuration.CATEGORY_GENERAL };

	private int configInteger(String name, boolean requireRestart, int def) {
		return configInteger(name, null, requireRestart, def);
	}

	private int configInteger(String name, String tooltip, boolean requireRestart, int def) {
		int config = configFile.get(Configuration.CATEGORY_GENERAL, name, def, tooltip).getInt(def);
		return config > 0 ? config : def;
	}

	private boolean configBoolean(String name, String tooltip, boolean requireRestart, boolean def) {
		return configFile.get(Configuration.CATEGORY_GENERAL, name, def, tooltip).getBoolean(def);
	}

	private boolean configBoolean(String name, boolean requireRestart, boolean def) {
		return configBoolean(name, null, requireRestart, def);
	}

	public void init(File file) {
		configFile = new Configuration(file);

		syncConfigs();
	}

	private void syncConfigs() {
		// 1.8 Stuff
		EtFuturum.enable18Stones = configBoolean("Enable 1.8 Stones", true, true);
		EtFuturum.enableIronTrapdoor = configBoolean("Enable Iron Trapdoor", true, true);
		EtFuturum.enableMutton = configBoolean("Enable Mutton", true, true);
		EtFuturum.enableSpongeTexture = configBoolean("Enable new sponge texture", true, true);
		EtFuturum.enablePrismarineStuff = configBoolean("Enable Prismarine stuff", true, true);
		EtFuturum.enableDoors = configBoolean("Enable 1.8 style doors", true, true);
		EtFuturum.enableInvertedDaylightSensor = configBoolean("Enable inverted daylight sensor", true, true);
		EtFuturum.enableCoarseDirt = configBoolean("Enable coarse dirt", true, true);
		EtFuturum.enable18Enchants = configBoolean("Enable 1.8 Enchanting Table", true, true);
		EtFuturum.enableFences = configBoolean("Enable 1.8 wood fences", true, true);
		EtFuturum.enableSilkTouchingMushrooms = configBoolean("Enable Silk Touching of mushroom blocks", true, true);
		EtFuturum.max18StonesPerCluster = configInteger("Max number of 1.8 stones in a cluster", true, EtFuturum.max18StonesPerCluster);
		EtFuturum.enableBanners = configBoolean("Enable 1.8 banners", true, true);
		EtFuturum.enableRedSandstone = configBoolean("Enable 1.8 red sandstone", true, true);

		if (configFile.hasChanged())
			configFile.save();
	}

	@SubscribeEvent
	public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent eventArgs) {
		if (Reference.MOD_ID.equals(eventArgs.modID))
			syncConfigs();
	}
}