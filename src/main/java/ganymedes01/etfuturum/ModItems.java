package ganymedes01.etfuturum;

import java.lang.reflect.Field;

import cpw.mods.fml.common.registry.GameRegistry;
import ganymedes01.etfuturum.items.Beetroot;
import ganymedes01.etfuturum.items.BeetrootSeeds;
import ganymedes01.etfuturum.items.BeetrootSoup;
import ganymedes01.etfuturum.items.ChorusFruit;
import ganymedes01.etfuturum.items.DragonBreath;
import ganymedes01.etfuturum.items.Elytra;
import ganymedes01.etfuturum.items.EndCrystal;
import ganymedes01.etfuturum.items.ItemArmourStand;
import ganymedes01.etfuturum.items.LingeringPotion;
import ganymedes01.etfuturum.items.MuttonCooked;
import ganymedes01.etfuturum.items.MuttonRaw;
import ganymedes01.etfuturum.items.PoppedChorusFruit;
import ganymedes01.etfuturum.items.PrismarineCrystals;
import ganymedes01.etfuturum.items.PrismarineShard;
import ganymedes01.etfuturum.items.RabbitCooked;
import ganymedes01.etfuturum.items.RabbitFoot;
import ganymedes01.etfuturum.items.RabbitHide;
import ganymedes01.etfuturum.items.RabbitRaw;
import ganymedes01.etfuturum.items.RabbitStew;
import ganymedes01.etfuturum.items.TippedArrow;
import net.minecraft.item.Item;

public class ModItems {

	public static final Item raw_mutton = new MuttonRaw();
	public static final Item cooked_mutton = new MuttonCooked();
	public static final Item prismarine_shard = new PrismarineShard();
	public static final Item prismarine_crystals = new PrismarineCrystals();
	public static final Item armour_stand = new ItemArmourStand();
	public static final Item raw_rabbit = new RabbitRaw();
	public static final Item cooked_rabbit = new RabbitCooked();
	public static final Item rabbit_foot = new RabbitFoot();
	public static final Item rabbit_hide = new RabbitHide();
	public static final Item rabbit_stew = new RabbitStew();
	public static final Item beetroot = new Beetroot();
	public static final Item beetroot_seeds = new BeetrootSeeds();
	public static final Item beetroot_soup = new BeetrootSoup();
	public static final Item chorus_fruit = new ChorusFruit();
	public static final Item popped_chorus_fruit = new PoppedChorusFruit();
	public static final Item tipped_arrow = new TippedArrow();
	public static final Item lingering_potion = new LingeringPotion();
	public static final Item dragon_breath = new DragonBreath();
	public static final Item elytra = new Elytra();
	public static final Item end_crystal = new EndCrystal();

	public static void init() {
		try {
			for (Field f : ModItems.class.getDeclaredFields()) {
				Object obj = f.get(null);
				if (obj instanceof Item)
					registerItem((Item) obj);
				else if (obj instanceof Item[])
					for (Item item : (Item[]) obj)
						registerItem(item);
			}
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	private static void registerItem(Item item) {
		if (!(item instanceof IConfigurable) || ((IConfigurable) item).isEnabled()) {
			String name = item.getUnlocalizedName();
			String[] strings = name.split("\\.");
			GameRegistry.registerItem(item, strings[strings.length - 1]);
		}
	}
}