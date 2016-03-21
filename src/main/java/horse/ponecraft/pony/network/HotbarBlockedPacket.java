package horse.ponecraft.pony.network;

import horse.ponecraft.pony.race.PonyPlayer;
import horse.ponecraft.pony.race.PonyRace;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.Teleporter;
import squeek.applecore.api.AppleCoreAPI;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class HotbarBlockedPacket implements IMessage
{
	private String unlocalizedReason;

	public HotbarBlockedPacket()
	{
	}

	public HotbarBlockedPacket(String unlocalizedReason)
	{
		this.unlocalizedReason = unlocalizedReason;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.unlocalizedReason = ByteBufUtils.readUTF8String(buf);
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		ByteBufUtils.writeUTF8String(buf, this.unlocalizedReason);
	}

	public static class Handler implements IMessageHandler<HotbarBlockedPacket, IMessage>
	{
		@Override
		public IMessage onMessage(HotbarBlockedPacket message, MessageContext ctx)
		{
			Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText(I18n.format(message.unlocalizedReason, new Object[0])));

			return null;
		}
	}
}
