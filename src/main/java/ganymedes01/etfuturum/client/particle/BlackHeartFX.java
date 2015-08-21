package ganymedes01.etfuturum.client.particle;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class BlackHeartFX extends EntityFX {

	public BlackHeartFX(World world, double x, double y, double z) {
		super(world, x, y, z);
		setParticleTextureIndex(67);
		particleGravity = 0.1F;
		particleMaxAge = 20;
		noClip = true;
	}

	@Override
	public float getBrightness(float p_70013_1_) {
		return 1.0F;
	}
}