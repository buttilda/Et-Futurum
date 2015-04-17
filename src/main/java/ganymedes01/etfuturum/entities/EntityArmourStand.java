package ganymedes01.etfuturum.entities;

import ganymedes01.etfuturum.ModItems;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EntityArmourStand extends EntityLivingBase {

	private static final Rotations DEFAULT_HEAD_ROTATION = new Rotations(0.0F, 0.0F, 0.0F);
	private static final Rotations DEFAULT_BODY_ROTATION = new Rotations(0.0F, 0.0F, 0.0F);
	private static final Rotations DEFAULT_LEFTARM_ROTATION = new Rotations(-10.0F, 0.0F, -10.0F);
	private static final Rotations DEFAULT_RIGHTARM_ROTATION = new Rotations(-15.0F, 0.0F, 10.0F);
	private static final Rotations DEFAULT_LEFTLEG_ROTATION = new Rotations(-1.0F, 0.0F, -1.0F);
	private static final Rotations DEFAULT_RIGHTLEG_ROTATION = new Rotations(1.0F, 0.0F, 1.0F);
	private final ItemStack[] contents;
	private boolean canInteract;
	private long punchCooldown;
	private int disabledSlots;
	private Rotations headRotation;
	private Rotations bodyRotation;
	private Rotations leftArmRotation;
	private Rotations rightArmRotation;
	private Rotations leftLegRotation;
	private Rotations rightLegRotation;

	public EntityArmourStand(World worldIn) {
		super(worldIn);
		contents = new ItemStack[5];
		headRotation = DEFAULT_HEAD_ROTATION;
		bodyRotation = DEFAULT_BODY_ROTATION;
		leftArmRotation = DEFAULT_LEFTARM_ROTATION;
		rightArmRotation = DEFAULT_RIGHTARM_ROTATION;
		leftLegRotation = DEFAULT_LEFTLEG_ROTATION;
		rightLegRotation = DEFAULT_RIGHTLEG_ROTATION;
		this.setSilent(true);
		noClip = hasNoGravity();
		setSize(0.5F, 1.975F);
	}

	public EntityArmourStand(World worldIn, double posX, double posY, double posZ) {
		this(worldIn);
		setPosition(posX, posY, posZ);
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		dataWatcher.addObject(10, Byte.valueOf((byte) 0));
		dataWatcher.addObject(11, DEFAULT_HEAD_ROTATION);
		dataWatcher.addObject(12, DEFAULT_BODY_ROTATION);
		dataWatcher.addObject(13, DEFAULT_LEFTARM_ROTATION);
		dataWatcher.addObject(14, DEFAULT_RIGHTARM_ROTATION);
		dataWatcher.addObject(15, DEFAULT_LEFTLEG_ROTATION);
		dataWatcher.addObject(16, DEFAULT_RIGHTLEG_ROTATION);
	}

	@Override
	public ItemStack getHeldItem() {
		return contents[0];
	}

	@Override
	public ItemStack getEquipmentInSlot(int slotIn) {
		return contents[slotIn];
	}

	@SideOnly(Side.CLIENT)
	public ItemStack getCurrentArmor(int slotIn) {
		return contents[slotIn + 1];
	}

	@Override
	public void setCurrentItemOrArmor(int slotIn, ItemStack stack) {
		contents[slotIn] = stack;
	}

	public ItemStack[] getInventory() {
		return contents;
	}

	public boolean replaceItemInInventory(int p_174820_1_, ItemStack p_174820_2_) {
		int j;

		if (p_174820_1_ == 99)
			j = 0;
		else {
			j = p_174820_1_ - 100 + 1;

			if (j < 0 || j >= contents.length)
				return false;
		}

		if (p_174820_2_ != null && EntityLiving.getArmorPosition(p_174820_2_) != j && (j != 4 || !(p_174820_2_.getItem() instanceof ItemBlock)))
			return false;
		else {
			setCurrentItemOrArmor(j, p_174820_2_);
			return true;
		}
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound tagCompound) {
		super.writeEntityToNBT(tagCompound);
		NBTTagList nbttaglist = new NBTTagList();

		for (int i = 0; i < contents.length; ++i) {
			NBTTagCompound nbttagcompound1 = new NBTTagCompound();

			if (contents[i] != null)
				contents[i].writeToNBT(nbttagcompound1);

			nbttaglist.appendTag(nbttagcompound1);
		}

		tagCompound.setTag("Equipment", nbttaglist);
		tagCompound.setBoolean("Invisible", isInvisible());
		tagCompound.setBoolean("Small", isSmall());
		tagCompound.setBoolean("ShowArms", getShowArms());
		tagCompound.setInteger("DisabledSlots", disabledSlots);
		tagCompound.setBoolean("NoGravity", hasNoGravity());
		tagCompound.setBoolean("NoBasePlate", hasNoBasePlate());
		tagCompound.setTag("Pose", readPoseFromNBT());
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound tagCompund) {
		super.readEntityFromNBT(tagCompund);

		if (tagCompund.hasKey("Equipment", 9)) {
			NBTTagList nbttaglist = tagCompund.getTagList("Equipment", 10);

			for (int i = 0; i < contents.length; ++i)
				contents[i] = ItemStack.loadItemStackFromNBT(nbttaglist.getCompoundTagAt(i));
		}

		setInvisible(tagCompund.getBoolean("Invisible"));
		setSmall(tagCompund.getBoolean("Small"));
		setShowArms(tagCompund.getBoolean("ShowArms"));
		disabledSlots = tagCompund.getInteger("DisabledSlots");
		setNoGravity(tagCompund.getBoolean("NoGravity"));
		setNoBasePlate(tagCompund.getBoolean("NoBasePlate"));
		noClip = hasNoGravity();
		NBTTagCompound nbttagcompound1 = tagCompund.getCompoundTag("Pose");
		writePoseToNBT(nbttagcompound1);
	}

	private void writePoseToNBT(NBTTagCompound tagCompound) {
		NBTTagList nbttaglist = tagCompound.getTagList("Head", 5);

		if (nbttaglist.tagCount() > 0)
			setHeadRotation(new Rotations(nbttaglist));
		else
			setHeadRotation(DEFAULT_HEAD_ROTATION);

		NBTTagList nbttaglist1 = tagCompound.getTagList("Body", 5);

		if (nbttaglist1.tagCount() > 0)
			setBodyRotation(new Rotations(nbttaglist1));
		else
			setBodyRotation(DEFAULT_BODY_ROTATION);

		NBTTagList nbttaglist2 = tagCompound.getTagList("LeftArm", 5);

		if (nbttaglist2.tagCount() > 0)
			setLeftArmRotation(new Rotations(nbttaglist2));
		else
			setLeftArmRotation(DEFAULT_LEFTARM_ROTATION);

		NBTTagList nbttaglist3 = tagCompound.getTagList("RightArm", 5);

		if (nbttaglist3.tagCount() > 0)
			setRightArmRotation(new Rotations(nbttaglist3));
		else
			setRightArmRotation(DEFAULT_RIGHTARM_ROTATION);

		NBTTagList nbttaglist4 = tagCompound.getTagList("LeftLeg", 5);

		if (nbttaglist4.tagCount() > 0)
			setLeftLegRotation(new Rotations(nbttaglist4));
		else
			setLeftLegRotation(DEFAULT_LEFTLEG_ROTATION);

		NBTTagList nbttaglist5 = tagCompound.getTagList("RightLeg", 5);

		if (nbttaglist5.tagCount() > 0)
			setRightLegRotation(new Rotations(nbttaglist5));
		else
			setRightLegRotation(DEFAULT_RIGHTLEG_ROTATION);
	}

	private NBTTagCompound readPoseFromNBT() {
		NBTTagCompound nbttagcompound = new NBTTagCompound();

		if (!DEFAULT_HEAD_ROTATION.equals(headRotation))
			nbttagcompound.setTag("Head", headRotation.writeToNBT());

		if (!DEFAULT_BODY_ROTATION.equals(bodyRotation))
			nbttagcompound.setTag("Body", bodyRotation.writeToNBT());

		if (!DEFAULT_LEFTARM_ROTATION.equals(leftArmRotation))
			nbttagcompound.setTag("LeftArm", leftArmRotation.writeToNBT());

		if (!DEFAULT_RIGHTARM_ROTATION.equals(rightArmRotation))
			nbttagcompound.setTag("RightArm", rightArmRotation.writeToNBT());

		if (!DEFAULT_LEFTLEG_ROTATION.equals(leftLegRotation))
			nbttagcompound.setTag("LeftLeg", leftLegRotation.writeToNBT());

		if (!DEFAULT_RIGHTLEG_ROTATION.equals(rightLegRotation))
			nbttagcompound.setTag("RightLeg", rightLegRotation.writeToNBT());

		return nbttagcompound;
	}

	@Override
	public boolean canBePushed() {
		return false;
	}

	@Override
	protected void collideWithEntity(Entity p_82167_1_) {
	}

	@Override
	@SuppressWarnings("unchecked")
	protected void collideWithNearbyEntities() {
		List<Entity> list = worldObj.getEntitiesWithinAABBExcludingEntity(this, getBoundingBox());

		if (list != null && !list.isEmpty())
			for (int i = 0; i < list.size(); i++) {
				Entity entity = list.get(i);

				if (entity instanceof EntityMinecart && ((EntityMinecart) entity).getMinecartType() == 0 && getDistanceSqToEntity(entity) <= 0.2D)
					entity.applyEntityCollision(this);
			}
	}

	public boolean func_174825_a(EntityPlayer p_174825_1_, Vec3 p_174825_2_) {
		if (!worldObj.isRemote) {
			byte b0 = 0;
			ItemStack itemstack = p_174825_1_.getCurrentEquippedItem();
			boolean flag = itemstack != null;

			if (flag && itemstack.getItem() instanceof ItemArmor) {
				ItemArmor itemarmor = (ItemArmor) itemstack.getItem();

				if (itemarmor.armorType == 3)
					b0 = 1;
				else if (itemarmor.armorType == 2)
					b0 = 2;
				else if (itemarmor.armorType == 1)
					b0 = 3;
				else if (itemarmor.armorType == 0)
					b0 = 4;
			}

			if (flag && (itemstack.getItem() == Items.skull || itemstack.getItem() == Item.getItemFromBlock(Blocks.pumpkin)))
				b0 = 4;

			byte b1 = 0;
			boolean flag1 = isSmall();
			double d3 = flag1 ? p_174825_2_.yCoord * 2.0D : p_174825_2_.yCoord;

			if (d3 >= 0.1D && d3 < 0.1D + (flag1 ? 0.8D : 0.45D) && contents[1] != null)
				b1 = 1;
			else if (d3 >= 0.9D + (flag1 ? 0.3D : 0.0D) && d3 < 0.9D + (flag1 ? 1.0D : 0.7D) && contents[3] != null)
				b1 = 3;
			else if (d3 >= 0.4D && d3 < 0.4D + (flag1 ? 1.0D : 0.8D) && contents[2] != null)
				b1 = 2;
			else if (d3 >= 1.6D && contents[4] != null)
				b1 = 4;

			boolean flag2 = contents[b1] != null;

			if ((disabledSlots & 1 << b1) != 0 || (disabledSlots & 1 << b0) != 0) {
				b1 = b0;

				if ((disabledSlots & 1 << b0) != 0) {
					if ((disabledSlots & 1) != 0)
						return true;

					b1 = 0;
				}
			}

			if (flag && b0 == 0 && !getShowArms())
				return true;
			else {
				if (flag)
					func_175422_a(p_174825_1_, b0);
				else if (flag2)
					func_175422_a(p_174825_1_, b1);

				return true;
			}
		} else
			return true;
	}

	private void func_175422_a(EntityPlayer p_175422_1_, int p_175422_2_) {
		ItemStack itemstack = contents[p_175422_2_];

		if (itemstack == null || (disabledSlots & 1 << p_175422_2_ + 8) == 0)
			if (itemstack != null || (disabledSlots & 1 << p_175422_2_ + 16) == 0) {
				int j = p_175422_1_.inventory.currentItem;
				ItemStack itemstack1 = p_175422_1_.inventory.getStackInSlot(j);
				ItemStack itemstack2;

				if (p_175422_1_.capabilities.isCreativeMode && (itemstack == null || itemstack.getItem() == Item.getItemFromBlock(Blocks.air)) && itemstack1 != null) {
					itemstack2 = itemstack1.copy();
					itemstack2.stackSize = 1;
					setCurrentItemOrArmor(p_175422_2_, itemstack2);
				} else if (itemstack1 != null && itemstack1.stackSize > 1) {
					if (itemstack == null) {
						itemstack2 = itemstack1.copy();
						itemstack2.stackSize = 1;
						setCurrentItemOrArmor(p_175422_2_, itemstack2);
						itemstack1.stackSize--;
					}
				} else {
					setCurrentItemOrArmor(p_175422_2_, itemstack1);
					p_175422_1_.inventory.setInventorySlotContents(j, itemstack);
				}
			}
	}

	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		if (!worldObj.isRemote && !canInteract) {
			if (DamageSource.outOfWorld.equals(source)) {
				setDead();
				return false;
			} else if (isEntityInvulnerable(source))
				return false;
			else if (source.isExplosion()) {
				dropContents();
				setDead();
				return false;
			} else if (DamageSource.inFire.equals(source)) {
				if (!isBurning())
					setFire(5);
				else
					damageArmorStand(0.15F);

				return false;
			} else if (DamageSource.onFire.equals(source) && getHealth() > 0.5F) {
				damageArmorStand(4.0F);
				return false;
			} else {
				boolean flag = "arrow".equals(source.getDamageType());
				boolean flag1 = "player".equals(source.getDamageType());

				if (!flag1 && !flag)
					return false;
				else {
					if (source.getSourceOfDamage() instanceof EntityArrow)
						source.getSourceOfDamage().setDead();

					if (source.getEntity() instanceof EntityPlayer && !((EntityPlayer) source.getEntity()).capabilities.allowEdit)
						return false;
					else if (source.isCreativePlayer()) {
						playParticles();
						setDead();
						return false;
					} else {
						long i = worldObj.getTotalWorldTime();

						if (i - punchCooldown > 5L && !flag)
							punchCooldown = i;
						else {
							dropBlock();
							playParticles();
							setDead();
						}

						return false;
					}
				}
			}
		} else
			return false;
	}

	private void playParticles() {
		if (worldObj instanceof WorldServer)
			((WorldServer) worldObj).spawnParticle(EnumParticleTypes.BLOCK_DUST, posX, posY + height / 1.5D, posZ, 10, (double) (width / 4.0F), (double) (height / 4.0F), (double) (width / 4.0F), 0.05D, new int[] { Block.getStateId(Blocks.planks.getDefaultState()) });
	}

	private void damageArmorStand(float p_175406_1_) {
		float f1 = getHealth();
		f1 -= p_175406_1_;

		if (f1 <= 0.5F) {
			dropContents();
			setDead();
		} else
			setHealth(f1);
	}

	private void dropBlock() {
		Block.spawnAsEntity(worldObj, new BlockPos(this), new ItemStack(ModItems.armour_stand));
		dropContents();
	}

	private void dropContents() {
		for (int i = 0; i < contents.length; ++i)
			if (contents[i] != null && contents[i].stackSize > 0) {
				if (contents[i] != null)
					Block.spawnAsEntity(worldObj, new BlockPos(this).up(), contents[i]);

				contents[i] = null;
			}
	}

	@Override
	protected float func_110146_f(float p_110146_1_, float p_110146_2_) {
		prevRenderYawOffset = prevRotationYaw;
		renderYawOffset = rotationYaw;
		return 0.0F;
	}

	@Override
	public float getEyeHeight() {
		return isChild() ? height * 0.5F : height * 0.9F;
	}

	@Override
	public void moveEntityWithHeading(float p_70612_1_, float p_70612_2_) {
		if (!hasNoGravity())
			super.moveEntityWithHeading(p_70612_1_, p_70612_2_);
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		Rotations rotations = dataWatcher.getWatchableObjectRotations(11);

		if (!headRotation.equals(rotations))
			setHeadRotation(rotations);

		Rotations rotations1 = dataWatcher.getWatchableObjectRotations(12);

		if (!bodyRotation.equals(rotations1))
			setBodyRotation(rotations1);

		Rotations rotations2 = dataWatcher.getWatchableObjectRotations(13);

		if (!leftArmRotation.equals(rotations2))
			setLeftArmRotation(rotations2);

		Rotations rotations3 = dataWatcher.getWatchableObjectRotations(14);

		if (!rightArmRotation.equals(rotations3))
			setRightArmRotation(rotations3);

		Rotations rotations4 = dataWatcher.getWatchableObjectRotations(15);

		if (!leftLegRotation.equals(rotations4))
			setLeftLegRotation(rotations4);

		Rotations rotations5 = dataWatcher.getWatchableObjectRotations(16);

		if (!rightLegRotation.equals(rotations5))
			setRightLegRotation(rotations5);
	}

	protected void func_175135_B() {
		setInvisible(canInteract);
	}

	@Override
	public void setInvisible(boolean invisible) {
		canInteract = invisible;
		super.setInvisible(invisible);
	}

	@Override
	public boolean isChild() {
		return isSmall();
	}

	public void onKillCommand() {
		setDead();
	}

	public boolean func_180427_aV() {
		return isInvisible();
	}

	private void setSmall(boolean p_175420_1_) {
		byte b0 = dataWatcher.getWatchableObjectByte(10);

		if (p_175420_1_)
			b0 = (byte) (b0 | 1);
		else
			b0 &= -2;

		dataWatcher.updateObject(10, Byte.valueOf(b0));
	}

	public boolean isSmall() {
		return (dataWatcher.getWatchableObjectByte(10) & 1) != 0;
	}

	private void setNoGravity(boolean p_175425_1_) {
		byte b0 = dataWatcher.getWatchableObjectByte(10);

		if (p_175425_1_)
			b0 = (byte) (b0 | 2);
		else
			b0 &= -3;

		dataWatcher.updateObject(10, Byte.valueOf(b0));
	}

	public boolean hasNoGravity() {
		return (dataWatcher.getWatchableObjectByte(10) & 2) != 0;
	}

	private void setShowArms(boolean p_175413_1_) {
		byte b0 = dataWatcher.getWatchableObjectByte(10);

		if (p_175413_1_)
			b0 = (byte) (b0 | 4);
		else
			b0 &= -5;

		dataWatcher.updateObject(10, Byte.valueOf(b0));
	}

	public boolean getShowArms() {
		return (dataWatcher.getWatchableObjectByte(10) & 4) != 0;
	}

	private void setNoBasePlate(boolean p_175426_1_) {
		byte b0 = dataWatcher.getWatchableObjectByte(10);

		if (p_175426_1_)
			b0 = (byte) (b0 | 8);
		else
			b0 &= -9;

		dataWatcher.updateObject(10, Byte.valueOf(b0));
	}

	public boolean hasNoBasePlate() {
		return (dataWatcher.getWatchableObjectByte(10) & 8) != 0;
	}

	public void setHeadRotation(Rotations p_175415_1_) {
		headRotation = p_175415_1_;
		dataWatcher.updateObject(11, p_175415_1_);
	}

	public void setBodyRotation(Rotations p_175424_1_) {
		bodyRotation = p_175424_1_;
		dataWatcher.updateObject(12, p_175424_1_);
	}

	public void setLeftArmRotation(Rotations p_175405_1_) {
		leftArmRotation = p_175405_1_;
		dataWatcher.updateObject(13, p_175405_1_);
	}

	public void setRightArmRotation(Rotations p_175428_1_) {
		rightArmRotation = p_175428_1_;
		dataWatcher.updateObject(14, p_175428_1_);
	}

	public void setLeftLegRotation(Rotations p_175417_1_) {
		leftLegRotation = p_175417_1_;
		dataWatcher.updateObject(15, p_175417_1_);
	}

	public void setRightLegRotation(Rotations p_175427_1_) {
		rightLegRotation = p_175427_1_;
		dataWatcher.updateObject(16, p_175427_1_);
	}

	public Rotations getHeadRotation() {
		return headRotation;
	}

	public Rotations getBodyRotation() {
		return bodyRotation;
	}

	@SideOnly(Side.CLIENT)
	public Rotations getLeftArmRotation() {
		return leftArmRotation;
	}

	@SideOnly(Side.CLIENT)
	public Rotations getRightArmRotation() {
		return rightArmRotation;
	}

	@SideOnly(Side.CLIENT)
	public Rotations getLeftLegRotation() {
		return leftLegRotation;
	}

	@SideOnly(Side.CLIENT)
	public Rotations getRightLegRotation() {
		return rightLegRotation;
	}

	@Override
	public ItemStack[] getLastActiveItems() {
		return null;
	}
}