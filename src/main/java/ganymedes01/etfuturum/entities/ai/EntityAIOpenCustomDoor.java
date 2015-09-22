package ganymedes01.etfuturum.entities.ai;

import net.minecraft.entity.EntityLiving;

/*
 * Copy paste from vanilla and adapted to work with other doors
 */
public class EntityAIOpenCustomDoor extends EntityAICustomDoorInteract {

	boolean field_75361_i;
	int field_75360_j;

	public EntityAIOpenCustomDoor(EntityLiving p_i1644_1_, boolean p_i1644_2_) {
		super(p_i1644_1_);
		theEntity = p_i1644_1_;
		field_75361_i = p_i1644_2_;
	}

	@Override
	public boolean continueExecuting() {
		return field_75361_i && field_75360_j > 0 && super.continueExecuting();
	}

	@Override
	public void startExecuting() {
		field_75360_j = 20;
		field_151504_e.func_150014_a(theEntity.worldObj, entityPosX, entityPosY, entityPosZ, true);
	}

	@Override
	public void resetTask() {
		if (field_75361_i)
			field_151504_e.func_150014_a(theEntity.worldObj, entityPosX, entityPosY, entityPosZ, false);
	}

	@Override
	public void updateTask() {
		--field_75360_j;
		super.updateTask();
	}
}