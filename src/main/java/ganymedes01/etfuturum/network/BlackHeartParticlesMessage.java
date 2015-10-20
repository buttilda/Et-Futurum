package ganymedes01.etfuturum.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class BlackHeartParticlesMessage implements IMessage {

	public int dimID;
	public double x, y, z;

	public BlackHeartParticlesMessage() {
	}

	public BlackHeartParticlesMessage(World world, double x, double y, double z) {
		dimID = world.provider.dimensionId;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		dimID = buf.readInt();
		x = buf.readDouble();
		y = buf.readDouble();
		z = buf.readDouble();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(dimID);
		buf.writeDouble(x);
		buf.writeDouble(y);
		buf.writeDouble(z);
	}
}