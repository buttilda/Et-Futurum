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
		EtFuturum.enableStones = configBoolean("Stones", true, true);
		EtFuturum.enableIronTrapdoor = configBoolean("Iron Trapdoor", true, true);
		EtFuturum.enableMutton = configBoolean("Mutton", true, true);
		EtFuturum.enableSponge = configBoolean("Sponge", true, true);
		EtFuturum.enablePrismarine = configBoolean("Prismarine", true, true);
		EtFuturum.enableDoors = configBoolean("Doors", true, true);
		EtFuturum.enableInvertedDaylightSensor = configBoolean("Inverted Daylight Sensor", true, true);
		EtFuturum.enableCoarseDirt = configBoolean("Coarse Dirt", true, true);
		EtFuturum.enableRedSandstone = configBoolean("Red Sandstone", true, true);
		EtFuturum.enableEnchants = configBoolean("Enchanting Table", true, true);
		EtFuturum.enableFences = configBoolean("Fences and Gates", true, true);
		EtFuturum.enableSilkTouchingMushrooms = configBoolean("Mushroom Blocks", true, true);
		EtFuturum.enableBanners = configBoolean("Banners", true, true);
		EtFuturum.enableSlimeBlock = configBoolean("Slime Block", true, true);

		EtFuturum.enableBurnableBlocks = configBoolean("Fences, gates and dead bushes burn", true, true);
		EtFuturum.maxStonesPerCluster = configInteger("Max number of 1.8 stones in a cluster", true, EtFuturum.maxStonesPerCluster);

		if (configFile.hasChanged())
			configFile.save();
	}

	@SubscribeEvent
	public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent eventArgs) {
		if (Reference.MOD_ID.equals(eventArgs.modID))
			syncConfigs();
	}
}