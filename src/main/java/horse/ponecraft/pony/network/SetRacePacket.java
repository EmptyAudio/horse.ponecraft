package horse.ponecraft.pony.network;

import horse.ponecraft.pony.race.PonyPlayer;
import horse.ponecraft.pony.race.PonyRace;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
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

public class SetRacePacket implements IMessage
{
	private PonyRace race;

	public SetRacePacket()
	{
	}

	public SetRacePacket(PonyRace race)
	{
		this.race = race;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.race = PonyRace.valueOf(ByteBufUtils.readUTF8String(buf).trim().toUpperCase());
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		ByteBufUtils.writeUTF8String(buf, this.race.toString());
	}

	public static class Handler implements IMessageHandler<SetRacePacket, IMessage>
	{
		@Override
		public IMessage onMessage(SetRacePacket message, MessageContext ctx)
		{
			switch (ctx.side)
			{
			case CLIENT:
				PonyPlayer.get(Minecraft.getMinecraft().thePlayer).setRace(message.race);

				Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText("Your race has been set to " + message.race.toString()));

				break;
			case SERVER:
				EntityPlayerMP player = ctx.getServerHandler().playerEntity;

				PonyPlayer.get(player).setRace(message.race);

				ServerConfigurationManager manager = MinecraftServer.getServer().getConfigurationManager();

				manager.respawnPlayer(player, 0, false);
				
				AppleCoreAPI.mutator.setHunger(player, 20);
				AppleCoreAPI.mutator.setSaturation(player, 20);
				AppleCoreAPI.mutator.setExhaustion(player, 0);
				
				break;
			}

			return null;
		}

		private class SpawnTeleporter extends Teleporter
		{
			public SpawnTeleporter()
			{
				super(MinecraftServer.getServer().worldServerForDimension(0));
			}

			@Override
			public void placeInPortal(Entity p_77185_1_, double p_77185_2_, double p_77185_4_, double p_77185_6_, float p_77185_8_)
			{
			}

			@Override
			public boolean placeInExistingPortal(Entity p_77184_1_, double p_77184_2_, double p_77184_4_, double p_77184_6_, float p_77184_8_)
			{
				return false;
			}

			@Override
			public boolean makePortal(Entity p_85188_1_)
			{
				return false;
			}

			@Override
			public void removeStalePortalLocations(long p_85189_1_)
			{
			}
		}
	}
}
