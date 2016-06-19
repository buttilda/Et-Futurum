package ganymedes01.etfuturum.lib;

public class Reference {

	public static final String MOD_ID = "etfuturum";
	public static final String MOD_NAME = "Et Futurum";
	public static final String DEPENDENCIES = "required-after:Forge@[10.13.4.1558,);";
	public static final String VERSION_NUMBER = "@VERSION@";
	public static final String ITEM_BLOCK_TEXTURE_PATH = MOD_ID + ":";
	public static final String ARMOUR_TEXTURE_PATH = ITEM_BLOCK_TEXTURE_PATH + "textures/models/armor/";
	public static final String ENTITY_TEXTURE_PATH = ITEM_BLOCK_TEXTURE_PATH + "textures/entities/";

	public static final String GUI_FACTORY_CLASS = "ganymedes01.etfuturum.configuration.ConfigGuiFactory";
	public static final String SERVER_PROXY_CLASS = "ganymedes01.etfuturum.core.proxy.CommonProxy";
	public static final String CLIENT_PROXY_CLASS = "ganymedes01.etfuturum.core.proxy.ClientProxy";
}