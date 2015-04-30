package ganymedes01.etfuturum;

import ganymedes01.etfuturum.items.ItemArmourStand;
import ganymedes01.etfuturum.items.MuttonCooked;
import ganymedes01.etfuturum.items.MuttonRaw;
import ganymedes01.etfuturum.items.PrismarineItems;
import ganymedes01.etfuturum.items.RabbitCooked;
import ganymedes01.etfuturum.items.RabbitFoot;
import ganymedes01.etfuturum.items.RabbitHide;
import ganymedes01.etfuturum.items.RabbitRaw;
import ganymedes01.etfuturum.items.RabbitStew;

import java.lang.reflect.Field;

import net.minecraft.item.Item;
import cpw.mods.fml.common.registry.GameRegistry;

public class ModItems {

	public static final Item raw_mutton = new MuttonRaw();
	public static final Item cooked_mutton = new MuttonCooked();
	public static final Item prismarine = new PrismarineItems();
	public static final Item armour_stand = new ItemArmourStand();
	public static final Item raw_rabbit = new RabbitRaw();
	public static final Item cooked_rabbit = new RabbitCooked();
	public static final Item rabbit_foot = new RabbitFoot();
	public static final Item rabbit_hide = new RabbitHide();
	public static final Item rabbit_stew = new RabbitStew();

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