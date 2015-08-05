package ganymedes01.etfuturum.items;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.IConfigurable;
import ganymedes01.etfuturum.core.utils.Utils;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.IIcon;
import net.minecraftforge.common.util.Constants;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TippedArrow extends Item implements IConfigurable {

	@SideOnly(Side.CLIENT)
	private IIcon tipIcon;

	public TippedArrow() {
		setTextureName("tipped_arrow");
		setUnlocalizedName(Utils.getUnlocalisedName("tipped_arrow"));
		setCreativeTab(EtFuturum.enableTippedArrows ? EtFuturum.creativeTab : null);
	}

	private PotionEffect getEffect(ItemStack stack) {
		if (stack.hasTagCompound() && stack.getTagCompound().hasKey("Potion", Constants.NBT.TAG_COMPOUND)) {
			NBTTagCompound nbt = stack.getTagCompound().getCompoundTag("Potion");
			return PotionEffect.readCustomPotionEffectFromNBT(nbt);
		}
		return null;
	}

	private ItemStack setEffect(ItemStack stack, Potion potion) {
		stack.setTagCompound(new NBTTagCompound());
		NBTTagCompound nbt = new NBTTagCompound();
		stack.getTagCompound().setTag("Potion", nbt);

		PotionEffect effect = new PotionEffect(potion.getId(), 0);
		effect.writeCustomPotionEffectToNBT(nbt);

		return stack;
	}

	@Override
	@SideOnly(Side.CLIENT)
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		list.add(setEffect(new ItemStack(this), Potion.nightVision));
		list.add(setEffect(new ItemStack(this), Potion.invisibility));
		list.add(setEffect(new ItemStack(this), Potion.jump));
		list.add(setEffect(new ItemStack(this), Potion.fireResistance));
		list.add(setEffect(new ItemStack(this), Potion.moveSpeed));
		list.add(setEffect(new ItemStack(this), Potion.moveSlowdown));
		list.add(setEffect(new ItemStack(this), Potion.waterBreathing));
		list.add(setEffect(new ItemStack(this), Potion.heal));
		list.add(setEffect(new ItemStack(this), Potion.harm));
		list.add(setEffect(new ItemStack(this), Potion.poison));
		list.add(setEffect(new ItemStack(this), Potion.regeneration));
		list.add(setEffect(new ItemStack(this), Potion.damageBoost));
		list.add(setEffect(new ItemStack(this), Potion.weakness));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister reg) {
		itemIcon = reg.registerIcon(getIconString() + "_base");
		tipIcon = reg.registerIcon(getIconString() + "_head");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses() {
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack stack, int pass) {
		PotionEffect effect = getEffect(stack);
		if (effect == null || effect.getPotionID() < 0 || effect.getPotionID() >= Potion.potionTypes.length)
			return super.getColorFromItemStack(stack, pass);
		return pass == 0 ? Potion.potionTypes[effect.getPotionID()].getLiquidColor() : super.getColorFromItemStack(stack, pass);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(ItemStack stack, int pass) {
		return pass == 0 ? tipIcon : super.getIcon(stack, pass);
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		PotionEffect effect = getEffect(stack);
		if (effect == null || effect.getPotionID() < 0 || effect.getPotionID() >= Potion.potionTypes.length)
			return super.getUnlocalizedName(stack);

		Potion potion = Potion.potionTypes[effect.getPotionID()];
		return "tipped_arrow." + potion.getName();
	}

	@Override
	public boolean isEnabled() {
		return EtFuturum.enableTippedArrows;
	}
}