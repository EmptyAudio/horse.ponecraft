package horse.ponecraft.pony.network;

import horse.ponecraft.pony.Pony;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class ShowBaublesGuiPacket implements IMessage
{
	public ShowBaublesGuiPacket()
	{
		System.out.println("ctor");
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		System.out.println("frombytes");
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		System.out.println("tobytes");
	}

	public static class Handler implements IMessageHandler<ShowBaublesGuiPacket, IMessage>
	{
		@Override
		public IMessage onMessage(ShowBaublesGuiPacket message, MessageContext ctx)
		{
			System.out.println("handle");
			EntityPlayer player = ctx.getServerHandler().playerEntity;

			player.openGui(Pony.instance, 1, player.worldObj, player.serverPosX, player.serverPosY, player.serverPosZ);

			return null;
		}
	}
}
