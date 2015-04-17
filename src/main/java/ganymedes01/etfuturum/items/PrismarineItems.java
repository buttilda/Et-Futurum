package ganymedes01.etfuturum.items;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.core.utils.Utils;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class PrismarineItems extends Item {

	@SideOnly(Side.CLIENT)
	private IIcon[] icons;

	public PrismarineItems() {
		setMaxDamage(0);
		setHasSubtypes(true);
		setTextureName("prismarine");
		setUnlocalizedName(Utils.getUnlocalisedName("prismarine"));
		setCreativeTab(EtFuturum.enablePrismarineStuff ? EtFuturum.creativeTab : null);
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return super.getUnlocalizedName(stack) + stack.getItemDamage();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int meta) {
		return icons[Math.max(Math.min(meta, icons.length - 1), 0)];
	}

	@Override
	@SideOnly(Side.CLIENT)
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void getSubItems(Item item, CreativeTabs tabs, List list) {
		for (int i = 0; i < 2; i++)
			list.add(new ItemStack(item, 1, i));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister reg) {
		icons = new IIcon[2];
		icons[0] = reg.registerIcon(getIconString() + "_shard");
		icons[1] = reg.registerIcon(getIconString() + "_crystals");
	}
}