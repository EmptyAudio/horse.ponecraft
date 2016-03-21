package horse.ponecraft.pony.network;

import horse.ponecraft.pony.Pony;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class ShowRaceSelectionPacket implements IMessage
{
	public ShowRaceSelectionPacket()
	{
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
	}

	public static class Handler implements IMessageHandler<ShowRaceSelectionPacket, IMessage>
	{
		@Override
		public IMessage onMessage(ShowRaceSelectionPacket message, MessageContext ctx)
		{
			EntityPlayer player = Minecraft.getMinecraft().thePlayer;

			player.openGui(Pony.instance, 0, player.worldObj, player.serverPosX, player.serverPosY, player.serverPosZ);

			return null;
		}
	}
}