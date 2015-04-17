package ganymedes01.etfuturum;

import ganymedes01.etfuturum.items.ItemNewDoor;
import ganymedes01.etfuturum.items.MuttonCooked;
import ganymedes01.etfuturum.items.MuttonRaw;
import ganymedes01.etfuturum.items.PrismarineItems;

import java.lang.reflect.Field;

import net.minecraft.block.BlockWood;
import net.minecraft.item.Item;
import cpw.mods.fml.common.registry.GameRegistry;

public class ModItems {

	// 1.8 Stuff
	public static final Item rawMutton = new MuttonRaw();
	public static final Item cookedMutton = new MuttonCooked();
	public static final Item prismarineItems = new PrismarineItems();
	public static final Item[] doors = new Item[BlockWood.field_150096_a.length - 1];

	static {
		for (int i = 0; i < doors.length; i++)
			doors[i] = new ItemNewDoor(i + 1);
	}

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
		String name = item.getUnlocalizedName();
		String[] strings = name.split("\\.");
		GameRegistry.registerItem(item, strings[strings.length - 1]);
	}
}