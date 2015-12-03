package ganymedes01.etfuturum.tileentities;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.ModItems;
import ganymedes01.etfuturum.lib.Reference;
import ganymedes01.etfuturum.recipes.BrewingFuelRegistry;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.PotionHelper;
import net.minecraft.tileentity.TileEntityBrewingStand;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.event.ForgeEventFactory;

/*
 * Class copied from vanilla and modified to fit my needs.
 *
 * Mojang code everywhere!
 *
 */
public class TileEntityNewBrewingStand extends TileEntityBrewingStand {

	private static final int[] TOP_SLOTS = new int[] { 3 };
	private static final int[] SIDE_SLOTS = new int[] { 0, 1, 2 };

	private ItemStack[] inventory = new ItemStack[5];
	private int brewTime;
	private int prevFilledSlots;
	private Item ingredientID;
	private int fuel, currentFuel;

	@Override
	public int getSizeInventory() {
		return inventory.length;
	}

	@Override
	public void updateEntity() {
		if (fuel <= 0 && inventory[4] != null) {
			fuel = currentFuel = BrewingFuelRegistry.getBrewAmount(inventory[4]);
			if (--inventory[4].stackSize <= 0)
				inventory[4] = inventory[4].getItem().hasContainerItem(inventory[4]) ? inventory[4].getItem().getContainerItem(inventory[4]) : null;
			markDirty();
		}

		if (brewTime > 0) {
			brewTime--;

			if (brewTime == 0) {
				brewPotions();
				markDirty();
			} else if (!canBrew()) {
				brewTime = 0;
				markDirty();
			} else if (ingredientID != inventory[3].getItem()) {
				brewTime = 0;
				markDirty();
			}
		} else if (canBrew()) {
			brewTime = 400;
			ingredientID = inventory[3].getItem();
		}

		int filledSlots = getFilledSlots();
		if (filledSlots != prevFilledSlots) {
			prevFilledSlots = filledSlots;
			worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, filledSlots, 2);
		}
	}

	@Override
	public int getBrewTime() {
		return brewTime;
	}

	private boolean canBrew() {
		if (fuel > 0 && inventory[3] != null && inventory[3].stackSize > 0) {
			ItemStack itemstack = inventory[3];

			if (!itemstack.getItem().isPotionIngredient(itemstack))
				return false;
			else if (itemstack.getItem() == ModItems.dragon_breath) {
				for (int i = 0; i < 3; i++)
					if (inventory[i] != null && inventory[i].getItem() == Items.potionitem)
						if (ItemPotion.isSplash(inventory[i].getItemDamage()))
							return true;
				return false;
			} else {
				boolean flag = false;

				for (int i = 0; i < 3; i++)
					if (inventory[i] != null && inventory[i].getItem() instanceof ItemPotion) {
						int j = inventory[i].getItemDamage();
						int k = applyIngredient(j, itemstack);

						if (!ItemPotion.isSplash(j) && ItemPotion.isSplash(k)) {
							flag = true;
							break;
						}

						List<?> list = Items.potionitem.getEffects(j);
						List<?> list1 = Items.potionitem.getEffects(k);

						if ((j <= 0 || list != list1) && (list == null || !list.equals(list1) && list1 != null) && j != k) {
							flag = true;
							break;
						}
					}

				return flag;
			}
		} else
			return false;
	}

	private void brewPotions() {
		if (ForgeEventFactory.onPotionAttemptBreaw(new ItemStack[] { inventory[0], inventory[1], inventory[2], inventory[3] }))
			return;
		if (canBrew()) {
			for (int i = 0; i < 3; i++)
				if (inventory[i] != null && inventory[i].getItem() instanceof ItemPotion) {
					int j = inventory[i].getItemDamage();
					if (ItemPotion.isSplash(j) && inventory[3].getItem() == ModItems.dragon_breath)
						inventory[i] = new ItemStack(ModItems.lingering_potion, inventory[i].stackSize, inventory[i].getItemDamage());
					else {
						int k = applyIngredient(j, inventory[3]);
						List<?> list = Items.potionitem.getEffects(j);
						List<?> list1 = Items.potionitem.getEffects(k);

						if ((j <= 0 || list != list1) && (list == null || !list.equals(list1) && list1 != null)) {
							if (j != k)
								inventory[i].setItemDamage(k);
						} else if (!ItemPotion.isSplash(j) && ItemPotion.isSplash(k))
							inventory[i].setItemDamage(k);
					}
				}

			boolean hasContainerItem = inventory[3].getItem().hasContainerItem(inventory[3]);
			if (--inventory[3].stackSize <= 0)
				inventory[3] = hasContainerItem ? inventory[3].getItem().getContainerItem(inventory[3]) : null;
			else if (hasContainerItem && !worldObj.isRemote) {
				float f = 0.7F;
				double x = worldObj.rand.nextFloat() * f + (1.0F - f) * 0.5D;
				double y = worldObj.rand.nextFloat() * f + (1.0F - f) * 0.5D;
				double z = worldObj.rand.nextFloat() * f + (1.0F - f) * 0.5D;
				EntityItem entityitem = new EntityItem(worldObj, xCoord + x, yCoord + y, zCoord + z, inventory[3].getItem().getContainerItem(inventory[3]));
				entityitem.delayBeforeCanPickup = 10;
				worldObj.spawnEntityInWorld(entityitem);
			}

			fuel--;
			ForgeEventFactory.onPotionBrewed(new ItemStack[] { inventory[0], inventory[1], inventory[2], inventory[3] });
			worldObj.playSound(xCoord, yCoord, zCoord, Reference.MOD_ID + ":block.brewing_stand.brew", 1.0F, 1.0F, true);
		}
	}

	private int applyIngredient(int meta, ItemStack stack) {
		return stack == null ? meta : stack.getItem().isPotionIngredient(stack) ? PotionHelper.applyIngredient(meta, stack.getItem().getPotionEffect(stack)) : meta;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		NBTTagList nbttaglist = nbt.getTagList("Items", 10);
		inventory = new ItemStack[getSizeInventory()];

		for (int i = 0; i < nbttaglist.tagCount(); i++) {
			NBTTagCompound nbt1 = nbttaglist.getCompoundTagAt(i);
			byte b0 = nbt1.getByte("Slot");

			if (b0 >= 0 && b0 < inventory.length)
				inventory[b0] = ItemStack.loadItemStackFromNBT(nbt1);
		}

		brewTime = nbt.getShort("BrewTime");

		if (nbt.hasKey("Fuel", Constants.NBT.TAG_SHORT)) {
			fuel = nbt.getShort("Fuel");
			if (fuel > 0)
				currentFuel = 30;
		} else {
			fuel = nbt.getInteger("Fuel");
			currentFuel = nbt.getInteger("CurrentFuel");
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setShort("BrewTime", (short) brewTime);
		NBTTagList nbttaglist = new NBTTagList();

		for (int i = 0; i < inventory.length; i++)
			if (inventory[i] != null) {
				NBTTagCompound nbt1 = new NBTTagCompound();
				nbt1.setByte("Slot", (byte) i);
				inventory[i].writeToNBT(nbt1);
				nbttaglist.appendTag(nbt1);
			}

		nbt.setTag("Items", nbttaglist);

		nbt.setInteger("Fuel", fuel);
		nbt.setInteger("CurrentFuel", currentFuel);
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return slot >= 0 && slot < inventory.length ? inventory[slot] : null;
	}

	@Override
	public ItemStack decrStackSize(int slot, int size) {
		if (slot >= 0 && slot < inventory.length) {
			ItemStack itemstack = inventory[slot];
			inventory[slot] = null;
			return itemstack;
		} else
			return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		if (slot >= 0 && slot < inventory.length) {
			ItemStack itemstack = inventory[slot];
			inventory[slot] = null;
			return itemstack;
		} else
			return null;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		if (slot >= 0 && slot < inventory.length)
			inventory[slot] = stack;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		if (slot == 4)
			return stack.getItem() == Items.blaze_powder;
		else if (slot == 3)
			return stack.getItem().isPotionIngredient(stack);
		else
			return stack.getItem() instanceof ItemPotion || stack.getItem() == Items.glass_bottle;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void func_145938_d(int brewTime) {
		this.brewTime = brewTime;
	}

	@Override
	public int getFilledSlots() {
		int i = 0;

		for (int j = 0; j < 3; j++)
			if (inventory[j] != null)
				i |= 1 << j;

		return i;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return side == 1 ? TOP_SLOTS : SIDE_SLOTS;
	}

	public int getFuel() {
		return fuel;
	}

	public int getCurrentFuel() {
		return currentFuel;
	}

	public void setFuel(int fuel) {
		this.fuel = fuel;
	}

	public void setCurrentFuel(int currentFuel) {
		this.currentFuel = currentFuel;
	}
}