package horse.ponecraft.pony.network;

import horse.ponecraft.pony.race.PonyPlayer;
import horse.ponecraft.pony.race.PonyRace;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PonyRaceSyncPacket implements IMessage
{
	private int entityId;
	private NBTTagCompound data;

	public PonyRaceSyncPacket()
	{
	}

	public PonyRaceSyncPacket(PonyPlayer player)
	{
		this.entityId = player.player.getEntityId();		
		this.data = new NBTTagCompound();
		
		player.saveNBTData(this.data);
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.entityId = ByteBufUtils.readVarInt(buf, 4);
		this.data = ByteBufUtils.readTag(buf);
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		ByteBufUtils.writeVarInt(buf, this.entityId, 4);
		ByteBufUtils.writeTag(buf, this.data);
	}

	public static class Handler implements IMessageHandler<PonyRaceSyncPacket, IMessage>
	{
		@Override
		public IMessage onMessage(PonyRaceSyncPacket message, MessageContext ctx)
		{
			switch (ctx.side)
			{
			case CLIENT:
			Entity entity = Minecraft.getMinecraft().theWorld.getEntityByID(message.entityId);
			
			if (entity instanceof EntityPlayer)
			{
				PonyPlayer.get((EntityPlayer)entity).loadNBTData(message.data);
			}
			break;
			
			case SERVER:
				PonyPlayer.get(ctx.getServerHandler().playerEntity).sync();
			}
			
			return null;
		}
	}
}
