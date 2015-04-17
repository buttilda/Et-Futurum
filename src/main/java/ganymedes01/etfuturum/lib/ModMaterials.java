package ganymedes01.etfuturum.lib;

import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.common.util.EnumHelper;

public class ModMaterials {

	// Armour
	public static final ArmorMaterial WOOD = EnumHelper.addArmorMaterial("WOOD", 8, new int[] { 2, 4, 3, 2 }, 9);

	// Tool
	public static final ToolMaterial ICE = EnumHelper.addToolMaterial("ICE", 2, 512, 6.0F, 2.0F, 14);

}
