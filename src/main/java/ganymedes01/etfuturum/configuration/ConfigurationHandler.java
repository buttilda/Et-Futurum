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
		EtFuturum.enableStones = configBoolean("Stones", true, EtFuturum.enableStones);
		EtFuturum.enableIronTrapdoor = configBoolean("Iron Trapdoor", true, EtFuturum.enableIronTrapdoor);
		EtFuturum.enableMutton = configBoolean("Mutton", true, EtFuturum.enableMutton);
		EtFuturum.enableSponge = configBoolean("Sponge", true, EtFuturum.enableSponge);
		EtFuturum.enablePrismarine = configBoolean("Prismarine", true, EtFuturum.enablePrismarine);
		EtFuturum.enableDoors = configBoolean("Doors", true, EtFuturum.enableDoors);
		EtFuturum.enableInvertedDaylightSensor = configBoolean("Inverted Daylight Sensor", true, EtFuturum.enableInvertedDaylightSensor);
		EtFuturum.enableCoarseDirt = configBoolean("Coarse Dirt", true, EtFuturum.enableCoarseDirt);
		EtFuturum.enableRedSandstone = configBoolean("Red Sandstone", true, EtFuturum.enableRedSandstone);
		EtFuturum.enableEnchants = configBoolean("Enchanting Table", true, EtFuturum.enableEnchants);
		EtFuturum.enableFences = configBoolean("Fences and Gates", true, EtFuturum.enableFences);
		EtFuturum.enableSilkTouchingMushrooms = configBoolean("Mushroom Blocks", true, EtFuturum.enableSilkTouchingMushrooms);
		EtFuturum.enableBanners = configBoolean("Banners", true, EtFuturum.enableBanners);
		EtFuturum.enableSlimeBlock = configBoolean("Slime Block", true, EtFuturum.enableSlimeBlock);
		EtFuturum.enableArmourStand = configBoolean("Armour Stand", true, EtFuturum.enableArmourStand);
		EtFuturum.enableRabbit = configBoolean("Rabbit", true, EtFuturum.enableRabbit);
		EtFuturum.enableOldGravel = configBoolean("Old Gravel", true, EtFuturum.enableOldGravel);
		EtFuturum.enableRecipeForPrismarine = configBoolean("Recipes for prismarine", true, EtFuturum.enableRecipeForPrismarine);

		EtFuturum.enableFancySkulls = configBoolean("Fancy Skulls", true, EtFuturum.enableFancySkulls);
		EtFuturum.enableSkullDrop = configBoolean("Skulls drop from charged creeper kills", true, EtFuturum.enableSkullDrop);
		EtFuturum.enableBurnableBlocks = configBoolean("Fences, gates and dead bushes burn", true, EtFuturum.enableBurnableBlocks);

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