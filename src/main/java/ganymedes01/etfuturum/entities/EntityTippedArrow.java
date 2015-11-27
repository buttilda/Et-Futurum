package ganymedes01.etfuturum.entities;

import java.awt.Color;

import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import cpw.mods.fml.relauncher.ReflectionHelper;
import ganymedes01.etfuturum.ModItems;
import ganymedes01.etfuturum.items.TippedArrow;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityTippedArrow extends EntityArrow implements IEntityAdditionalSpawnData {

	private PotionEffect effect;

	public EntityTippedArrow(World world) {
		super(world);
	}

	public EntityTippedArrow(World world, EntityLivingBase entity, float f0) {
		super(world, entity, f0);
	}

	public EntityTippedArrow(World world, double x, double y, double z) {
		super(world, x, y, z);
	}

	public EntityTippedArrow(World world, EntityLivingBase entity, EntityLivingBase target, float f0, float f1) {
		super(world, entity, target, f0, f1);
	}

	public void setEffect(PotionEffect effect) {
		this.effect = effect;
	}

	public PotionEffect getEffect() {
		return effect;
	}

	private boolean isEffectValid() {
		return effect != null && Potion.potionTypes[effect.getPotionID()] != null;
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		if (isEffectValid()) {
			Color colour = new Color(Potion.potionTypes[effect.getPotionID()].getLiquidColor());
			worldObj.spawnParticle("mobSpell", posX, posY, posZ, colour.getRed() / 255F, colour.getGreen() / 255F, colour.getBlue() / 255F);
		}
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		if (effect != null) {
			NBTTagCompound effectNBT = new NBTTagCompound();
			effect.writeCustomPotionEffectToNBT(effectNBT);
			nbt.setTag("Effect", effectNBT);
		}
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		if (nbt.hasKey("Effect"))
			effect = PotionEffect.readCustomPotionEffectFromNBT((NBTTagCompound) nbt.getTag("Effect"));
	}

	@Override
	public void onCollideWithPlayer(EntityPlayer player) {
		boolean inGround = false;
		try {
			inGround = ReflectionHelper.getPrivateValue(EntityArrow.class, this, "inGround", "field_70254_i");
		} catch (Exception e) {
		}

		if (!worldObj.isRemote && inGround && arrowShake <= 0 && isEffectValid()) {
			boolean flag = canBePickedUp == 1 || canBePickedUp == 2 && player.capabilities.isCreativeMode;

			ItemStack stack = new ItemStack(ModItems.tipped_arrow);
			TippedArrow.setEffect(stack, Potion.potionTypes[effect.getPotionID()], effect.getDuration());

			if (canBePickedUp == 1 && !player.inventory.addItemStackToInventory(stack))
				flag = false;

			if (flag) {
				playSound("random.pop", 0.2F, ((rand.nextFloat() - rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
				player.onItemPickup(this, 1);
				setDead();
			}
		}
	}

	@Override
	public void writeSpawnData(ByteBuf buffer) {
		buffer.writeFloat(rotationYaw);

		int id = shootingEntity == null ? getEntityId() : shootingEntity.getEntityId();
		buffer.writeInt(id);

		buffer.writeDouble(motionX);
		buffer.writeDouble(motionY);
		buffer.writeDouble(motionZ);

		buffer.writeInt(effect.getPotionID());
		buffer.writeInt(effect.getDuration());
		buffer.writeInt(effect.getAmplifier());
	}

	@Override
	public void readSpawnData(ByteBuf buffer) {
		rotationYaw = buffer.readFloat();
		shootingEntity = worldObj.getEntityByID(buffer.readInt());

		motionX = buffer.readDouble();
		motionY = buffer.readDouble();
		motionZ = buffer.readDouble();

		posX -= MathHelper.cos(rotationYaw / 180.0F * (float) Math.PI) * 0.16F;
		posY -= 0.10000000149011612D;
		posZ -= MathHelper.sin(rotationYaw / 180.0F * (float) Math.PI) * 0.16F;

		effect = new PotionEffect(buffer.readInt(), buffer.readInt(), buffer.readInt());
	}
}