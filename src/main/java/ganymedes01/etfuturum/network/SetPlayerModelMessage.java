package ganymedes01.etfuturum.network;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

public class SetPlayerModelMessage implements IMessage {

	public String playerName;
	public boolean isAlex;

	public SetPlayerModelMessage() {
	}

	public SetPlayerModelMessage(EntityPlayer player, boolean isAlex) {
		playerName = player.getCommandSenderName();
		this.isAlex = isAlex;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		playerName = ByteBufUtils.readUTF8String(buf);
		isAlex = buf.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeUTF8String(buf, playerName);
		buf.writeBoolean(isAlex);
	}
}