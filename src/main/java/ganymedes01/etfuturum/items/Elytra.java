package ganymedes01.etfuturum.items;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.IConfigurable;
import ganymedes01.etfuturum.client.model.ModelElytra;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.common.util.EnumHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class Elytra extends ItemArmor implements IConfigurable {

	@SideOnly(Side.CLIENT)
	private IIcon broken;

	public Elytra() {
		super(EnumHelper.addArmorMaterial("elytra", 27, new int[] { 0, 0, 0, 0 }, 0), 0, 1);
		setMaxDamage(432);
		setMaxStackSize(1);
		setTextureName("elytra");
		setUnlocalizedName(Utils.getUnlocalisedName("elytra"));
		setCreativeTab(EtFuturum.enableElytra ? EtFuturum.creativeTab : null);
	}

	@Override
	public boolean getIsRepairable(ItemStack item, ItemStack material) {
		return material.getItem() == Items.leather;
	}

	@Override
	public boolean isValidArmor(ItemStack stack, int armorType, Entity entity) {
		return armorType == 1;
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
		return "textures/entity/elytra.png";
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot) {
		return new ModelElytra();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int meta) {
		return meta >= getMaxDamage() ? broken : super.getIconFromDamage(meta);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister reg) {
		super.registerIcons(reg);
		broken = reg.registerIcon("broken_elytra");
	}

	@Override
	public boolean isEnabled() {
		return EtFuturum.enableElytra;
	}
}