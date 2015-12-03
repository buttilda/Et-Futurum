package ganymedes01.etfuturum.inventory;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.recipes.BrewingFuelRegistry;
import ganymedes01.etfuturum.tileentities.TileEntityNewBrewingStand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.AchievementList;

public class ContainerNewBrewingStand extends Container {

	private TileEntityNewBrewingStand tile;
	private final Slot ingredientSlot;
	private int prevBrewTime, prevFuel, prevCurrentFuel;

	public ContainerNewBrewingStand(InventoryPlayer playerInvt, TileEntityNewBrewingStand tile) {
		this.tile = tile;
		addSlotToContainer(new PotionSlot(tile, 0, 56, 51));
		addSlotToContainer(new PotionSlot(tile, 1, 79, 58));
		addSlotToContainer(new PotionSlot(tile, 2, 102, 51));
		ingredientSlot = addSlotToContainer(new IngredientSlot(tile, 3, 79, 17));
		addSlotToContainer(new BlazePowderSlot(tile, 4, 17, 17));

		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 9; j++)
				addSlotToContainer(new Slot(playerInvt, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));

		for (int i = 0; i < 9; i++)
			addSlotToContainer(new Slot(playerInvt, i, 8 + i * 18, 142));
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();

		for (int i = 0; i < crafters.size(); i++) {
			ICrafting icrafting = (ICrafting) crafters.get(i);

			if (prevBrewTime != tile.getBrewTime())
				icrafting.sendProgressBarUpdate(this, 0, tile.getBrewTime());
			if (prevFuel != tile.getFuel())
				icrafting.sendProgressBarUpdate(this, 1, tile.getFuel());
			if (prevCurrentFuel != tile.getCurrentFuel())
				icrafting.sendProgressBarUpdate(this, 2, tile.getCurrentFuel());
		}
		prevBrewTime = tile.getBrewTime();
		prevFuel = tile.getFuel();
		prevCurrentFuel = tile.getCurrentFuel();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int value) {
		if (id == 0)
			tile.func_145938_d(value);
		else if (id == 1)
			tile.setFuel(value);
		else if (id == 2)
			tile.setCurrentFuel(value);
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return tile.isUseableByPlayer(player);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotIndex) {
		ItemStack itemstack = null;
		Slot slot = (Slot) inventorySlots.get(slotIndex);

		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if ((slotIndex < 0 || slotIndex > 2) && slotIndex != 3) {
				if (!ingredientSlot.getHasStack() && ingredientSlot.isItemValid(itemstack1)) {
					if (!mergeItemStack(itemstack1, 3, 4, false))
						return null;
				} else if (PotionSlot.canHoldPotion(itemstack)) {
					if (!mergeItemStack(itemstack1, 0, 3, false))
						return null;
				} else if (slotIndex >= 4 && slotIndex < 31) {
					if (!mergeItemStack(itemstack1, 31, 40, false))
						return null;
				} else if (slotIndex >= 31 && slotIndex < 40) {
					if (!mergeItemStack(itemstack1, 4, 31, false))
						return null;
				} else if (!mergeItemStack(itemstack1, 4, 40, false))
					return null;
			} else {
				if (!mergeItemStack(itemstack1, 4, 40, true))
					return null;

				slot.onSlotChange(itemstack1, itemstack);
			}

			if (itemstack1.stackSize == 0)
				slot.putStack((ItemStack) null);
			else
				slot.onSlotChanged();

			if (itemstack1.stackSize == itemstack.stackSize)
				return null;

			slot.onPickupFromSlot(player, itemstack1);
		}

		return itemstack;
	}

	class BlazePowderSlot extends Slot {

		public BlazePowderSlot(IInventory inventory, int index, int x, int y) {
			super(inventory, index, x, y);
		}

		@Override
		public boolean isItemValid(ItemStack stack) {
			return stack != null && BrewingFuelRegistry.isFuel(stack);
		}
	}

	class IngredientSlot extends Slot {

		public IngredientSlot(IInventory inventory, int index, int x, int y) {
			super(inventory, index, x, y);
		}

		@Override
		public boolean isItemValid(ItemStack stack) {
			return stack != null && stack.getItem().isPotionIngredient(stack);
		}
	}

	static class PotionSlot extends Slot {

		public PotionSlot(IInventory inventory, int index, int x, int y) {
			super(inventory, index, x, y);
		}

		@Override
		public boolean isItemValid(ItemStack stack) {
			return canHoldPotion(stack);
		}

		@Override
		public int getSlotStackLimit() {
			return 1;
		}

		@Override
		public void onPickupFromSlot(EntityPlayer player, ItemStack stack) {
			if (stack.getItem() instanceof ItemPotion && stack.getItemDamage() > 0)
				player.addStat(AchievementList.potion, 1);

			super.onPickupFromSlot(player, stack);
		}

		public static boolean canHoldPotion(ItemStack stack) {
			return stack != null && (stack.getItem() instanceof ItemPotion || stack.getItem() == Items.glass_bottle);
		}
	}
}