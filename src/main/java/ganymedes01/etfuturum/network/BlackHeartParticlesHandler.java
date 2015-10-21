package ganymedes01.etfuturum.network;

import ganymedes01.etfuturum.client.particle.BlackHeartFX;
import net.minecraft.client.Minecraft;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlackHeartParticlesHandler implements IMessageHandler<BlackHeartParticlesMessage, IMessage> {

	@Override
	public IMessage onMessage(BlackHeartParticlesMessage message, MessageContext ctx) {
		World world = DimensionManager.getWorld(message.dimID);
		double x = message.x;
		double y = message.y;
		double z = message.z;

		spawnParticles(world, x, y, z);

		return null;
	}

	@SideOnly(Side.CLIENT)
	private void spawnParticles(World world, double x, double y, double z) {
		Minecraft.getMinecraft().effectRenderer.addEffect(new BlackHeartFX(world, x, y, z));
	}
}