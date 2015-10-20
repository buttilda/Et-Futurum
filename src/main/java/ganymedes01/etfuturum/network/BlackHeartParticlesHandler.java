package ganymedes01.etfuturum.network;

import ganymedes01.etfuturum.client.particle.BlackHeartFX;
import net.minecraft.client.Minecraft;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class BlackHeartParticlesHandler implements IMessageHandler<BlackHeartParticlesMessage, IMessage> {

	@Override
	public IMessage onMessage(BlackHeartParticlesMessage message, MessageContext ctx) {
		World world = DimensionManager.getWorld(message.dimID);
		double x = message.x;
		double y = message.y;
		double z = message.z;

		Minecraft.getMinecraft().effectRenderer.addEffect(new BlackHeartFX(world, x, y, z));

		return null;
	}
}