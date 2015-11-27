package ganymedes01.etfuturum.items.block;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.lib.EnumColour;
import ganymedes01.etfuturum.tileentities.TileEntityBanner;
import ganymedes01.etfuturum.tileentities.TileEntityBanner.EnumBannerPattern;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class ItemBanner extends ItemBlock {

	public ItemBanner(Block block) {
		super(block);
		setMaxDamage(0);
		setMaxStackSize(16);
		setHasSubtypes(true);
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		if (world.getBlock(x, y, z) == Blocks.cauldron) {
			int meta = world.getBlockMetadata(x, y, z);
			if (meta > 0) {
				stack.setTagCompound(null);
				world.setBlockMetadataWithNotify(x, y, z, meta - 1, 3);
				return true;
			}
		}

		if (side == 0)
			return false;
		else if (!world.getBlock(x, y, z).getMaterial().isSolid())
			return false;
		else {
			if (side == 1)
				y++;
			if (side == 2)
				z--;
			if (side == 3)
				z++;
			if (side == 4)
				x--;
			if (side == 5)
				x++;

			if (!player.canPlayerEdit(x, y, z, side, stack))
				return false;
			else if (!field_150939_a.canPlaceBlockAt(world, x, y, z))
				return false;
			else {
				if (side == 1) {
					int meta = MathHelper.floor_double((player.rotationYaw + 180.0F) * 16.0F / 360.0F + 0.5D) & 15;
					world.setBlock(x, y, z, field_150939_a, meta, 3);
				} else
					world.setBlock(x, y, z, field_150939_a, side, 3);

				stack.stackSize--;
				TileEntityBanner banner = (TileEntityBanner) world.getTileEntity(x, y, z);
				if (banner != null) {
					banner.isStanding = side == 1;
					banner.setItemValues(stack);
				}
				return true;
			}
		}
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		return StatCollector.translateToLocal("item.banner." + getBaseColor(stack).getMojangName() + ".name");
	}

	@Override
	@SideOnly(Side.CLIENT)
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List tooltip, boolean advanced) {
		NBTTagCompound nbttagcompound = getSubTag(stack, "BlockEntityTag", false);

		if (nbttagcompound != null && nbttagcompound.hasKey("Patterns")) {
			NBTTagList nbttaglist = nbttagcompound.getTagList("Patterns", 10);

			for (int i = 0; i < nbttaglist.tagCount() && i < 6; i++) {
				NBTTagCompound nbt = nbttaglist.getCompoundTagAt(i);
				EnumColour colour = EnumColour.fromDamage(nbt.getInteger("Color"));
				EnumBannerPattern pattern = EnumBannerPattern.getPatternByID(nbt.getString("Pattern"));

				if (pattern != null)
					tooltip.add(StatCollector.translateToLocal("item.banner." + pattern.getPatternName() + "." + colour.getMojangName()));
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack stack, int renderPass) {
		if (renderPass == 0)
			return 0xFFFFFF;
		else {
			EnumColour EnumColour = getBaseColor(stack);
			return EnumColour.getRGB();
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void getSubItems(Item item, CreativeTabs tab, List subItems) {
		for (EnumColour colour : EnumColour.values())
			subItems.add(new ItemStack(item, 1, colour.getDamage()));
	}

	private EnumColour getBaseColor(ItemStack stack) {
		NBTTagCompound nbttagcompound = getSubTag(stack, "BlockEntityTag", false);
		EnumColour colour = null;

		if (nbttagcompound != null && nbttagcompound.hasKey("Base"))
			colour = EnumColour.fromDamage(nbttagcompound.getInteger("Base"));
		else
			colour = EnumColour.fromDamage(stack.getItemDamage());

		return colour;
	}

	public static NBTTagCompound getSubTag(ItemStack stack, String key, boolean create) {
		if (stack.stackTagCompound != null && stack.stackTagCompound.hasKey(key, 10))
			return stack.stackTagCompound.getCompoundTag(key);
		else if (create) {
			NBTTagCompound nbttagcompound = new NBTTagCompound();
			stack.setTagInfo(key, nbttagcompound);
			return nbttagcompound;
		} else
			return null;
	}
}