package ganymedes01.etfuturum.entities;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import cpw.mods.fml.common.registry.EntityRegistry;
import ganymedes01.etfuturum.lib.Reference;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ModEntityList {

	public static Item entity_egg;

	private static EntityData[] array = new EntityData[0];
	private static Map<Integer, Class<? extends Entity>> map = new HashMap<Integer, Class<? extends Entity>>();

	public static void registerEntity(Class<? extends Entity> entityClass, String entityName, int id, Object mod, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates) {
		registerEntity(entityClass, entityName, id, mod, trackingRange, updateFrequency, sendsVelocityUpdates, -1, -1, false);
	}

	public static void registerEntity(Class<? extends Entity> entityClass, String entityName, int id, Object mod, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates, int eggColour1, int eggColour2) {
		registerEntity(entityClass, entityName, id, mod, trackingRange, updateFrequency, sendsVelocityUpdates, eggColour1, eggColour2, true);
	}

	private static void registerEntity(Class<? extends Entity> entityClass, String entityName, int id, Object mod, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates, int eggColour1, int eggColour2, boolean hasEgg) {
		EntityRegistry.registerModEntity(entityClass, entityName, id, mod, trackingRange, updateFrequency, sendsVelocityUpdates);

		if (id >= array.length) {
			EntityData[] newArray = new EntityData[id + 5];
			for (int i = 0; i < array.length; i++)
				newArray[i] = array[i];
			array = newArray;
		}
		if (array[id] != null)
			throw new IllegalArgumentException("ID " + id + " is already being used! Please report this error!");
		else {
			array[id] = new EntityData(entityName, id, eggColour1, eggColour2, hasEgg);
			map.put(id, entityClass);
		}
	}

	public static String getName(int id) {
		EntityData data = getData(id);
		if (data == null)
			return null;
		return Reference.MOD_ID + "." + data.name;
	}

	public static EntityData getData(int id) {
		if (id >= array.length)
			return null;
		return array[id];
	}

	public static boolean hasEntitiesWithEggs() {
		for (EntityData data : array)
			if (data != null && data.hasEgg)
				return true;
		return false;
	}

	public static Entity createEntityByID(int id, World world) {
		EntityData data = getData(id);
		if (data == null || !data.hasEgg)
			return null;

		try {
			Class<? extends Entity> cls = map.get(id);
			if (cls != null)
				return cls.getConstructor(World.class).newInstance(world);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public static EntityData[] getDatasWithEggs() {
		List<EntityData> list = new LinkedList<EntityData>();
		for (Integer id : map.keySet()) {
			EntityData data = getData(id);
			if (data != null && data.hasEgg)
				list.add(data);
		}
		return list.toArray(new EntityData[list.size()]);
	}

	public static ItemStack getEggFor(Class<? extends Entity> entityCls) {
		for (Entry<Integer, Class<? extends Entity>> entry : map.entrySet())
			if (entry.getValue() == entityCls)
				return new ItemStack(entity_egg, 1, entry.getKey());
		return null;
	}

	public static class EntityData {

		public final String name;
		public final int id, eggColour1, eggColour2;
		public final boolean hasEgg;

		private EntityData(String name, int id, int eggColour1, int eggColour2, boolean hasEgg) {
			this.name = name;
			this.id = id;
			this.eggColour1 = eggColour1;
			this.eggColour2 = eggColour2;
			this.hasEgg = hasEgg;
		}
	}
}