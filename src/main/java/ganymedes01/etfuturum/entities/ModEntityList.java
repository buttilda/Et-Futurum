package ganymedes01.etfuturum.entities;

import ganymedes01.etfuturum.lib.Reference;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList.EntityEggInfo;
import net.minecraft.world.World;
import cpw.mods.fml.common.registry.EntityRegistry;

public class ModEntityList {

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

	public static EntityEggInfo getEggInfo(int id) {
		EntityData data = getData(id);
		if (data == null)
			return null;
		return data.hasEgg ? data.eggInfo : null;
	}

	public static String getName(int id) {
		EntityData data = getData(id);
		if (data == null)
			return null;
		return Reference.MOD_ID + "." + data.name;
	}

	private static EntityData getData(int id) {
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

	public static EntityEggInfo[] getEggInfos() {
		List<EntityEggInfo> list = new LinkedList<EntityEggInfo>();
		for (Integer id : map.keySet()) {
			EntityEggInfo info = getEggInfo(id);
			if (info != null)
				list.add(info);
		}
		return list.toArray(new EntityEggInfo[list.size()]);
	}

	private static class EntityData {

		final String name;
		final EntityEggInfo eggInfo;
		final boolean hasEgg;

		private EntityData(String name, int id, int eggColour1, int eggColour2, boolean hasEgg) {
			this.name = name;
			eggInfo = new EntityEggInfo(id, eggColour1, eggColour2);
			this.hasEgg = hasEgg;
		}
	}
}