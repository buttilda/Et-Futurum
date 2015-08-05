package ganymedes01.etfuturum.entities;

import ganymedes01.etfuturum.ModItems;
import ganymedes01.etfuturum.items.TippedArrow;
import io.netty.buffer.ByteBuf;

import java.awt.Color;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import cpw.mods.fml.relauncher.ReflectionHelper;

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

	@Override
	public void onUpdate() {
		super.onUpdate();
		if (effect != null) {
			Color colour = new Color(Potion.potionTypes[effect.getPotionID()].getLiquidColor());
			worldObj.spawnParticle("mobSpell", posX, posY, posZ, colour.getRed() / 255F, colour.getGreen() / 255F, colour.getBlue() / 255F);
		}
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		NBTTagCompound effectNBT = new NBTTagCompound();
		effect.writeCustomPotionEffectToNBT(effectNBT);
		nbt.setTag("Effect", effectNBT);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		effect = PotionEffect.readCustomPotionEffectFromNBT((NBTTagCompound) nbt.getTag("Effect"));
	}

	@Override
	public void onCollideWithPlayer(EntityPlayer entity) {
		boolean inGround = false;
		try {
			inGround = ReflectionHelper.getPrivateValue(EntityArrow.class, this, "inGround", "field_70254_i");
		} catch (Exception e) {
		}

		if (!worldObj.isRemote && inGround && arrowShake <= 0 && effect != null) {
			boolean flag = canBePickedUp == 1 || canBePickedUp == 2 && entity.capabilities.isCreativeMode;

			ItemStack stack = new ItemStack(ModItems.tipped_arrow);
			TippedArrow.setEffect(stack, Potion.potionTypes[effect.getPotionID()], effect.getDuration());

			if (canBePickedUp == 1 && !entity.inventory.addItemStackToInventory(stack))
				flag = false;

			if (flag) {
				playSound("random.pop", 0.2F, ((rand.nextFloat() - rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
				entity.onItemPickup(this, 1);
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