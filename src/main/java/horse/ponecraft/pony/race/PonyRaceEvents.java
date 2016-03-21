package horse.ponecraft.pony.race;

import com.sun.media.jfxmedia.events.PlayerStateEvent.PlayerState;

import horse.ponecraft.pony.Pony;
import horse.ponecraft.pony.network.ShowRaceSelectionPacket;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.Teleporter;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;
import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerChangedDimensionEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;

public class PonyRaceEvents
{
	@SubscribeEvent
	public void onEntityConstructing(EntityConstructing event)
	{
		if (event.entity instanceof EntityPlayer)
		{
			PonyPlayer.register((EntityPlayer)event.entity);
		}
	}

	@SubscribeEvent
	public void onEntityJoinWorld(EntityJoinWorldEvent e)
	{
		if (e.entity instanceof EntityPlayer)
		{
			PonyPlayer.get((EntityPlayer)e.entity).requestSync();
		}
	}

	@SubscribeEvent
	public void onPlayerCloned(PlayerEvent.Clone e)
	{
		NBTTagCompound nbt = new NBTTagCompound();

		PonyPlayer.get(e.original).saveNBTData(nbt);
		PonyPlayer.get(e.entityPlayer).loadNBTData(nbt);
	}

	@SubscribeEvent
	public void onPlayerLoggedIn(PlayerLoggedInEvent event)
	{
		if (!event.player.worldObj.isRemote)
		{
			if (PonyPlayer.get(event.player).getRace() == PonyRace.NONE)
			{
				ServerConfigurationManager manager = MinecraftServer.getServer().getConfigurationManager();

				manager.transferPlayerToDimension((EntityPlayerMP)event.player, Pony.raceSelectionDimensionId, new LoginTeleporter());
				((EntityPlayerMP)event.player).playerNetServerHandler.setPlayerLocation(0.5, 56, 0.5, event.player.cameraYaw, event.player.cameraPitch);
			}
		}
	}

	@SubscribeEvent
	public void onPlayerChangedDimension(PlayerChangedDimensionEvent event)
	{
		if (event.toDim == Pony.raceSelectionDimensionId)
		{
			System.out.println("sending race selection packet");
			Pony.net.sendTo(new ShowRaceSelectionPacket(), (EntityPlayerMP)event.player);
		}
	}

	@SubscribeEvent
	public void onLivingUpdate(LivingUpdateEvent event)
	{
		if (event.entityLiving instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer)event.entityLiving;
			PonyRace race = PonyPlayer.get(player).getRace();
			ItemStack stack = player.inventory.getCurrentItem();

			if (stack != null)
			{
				String reason = race.isBannedFromHotbar(stack);
				
				if (reason != null)
				{
					player.func_146097_a(stack, false, true);
					player.destroyCurrentEquippedItem();

					if (player instanceof EntityClientPlayerMP)
					{
						Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText(I18n.format(reason, new Object[0])));
					}
					else
					{
						player.worldObj.playSoundAtEntity(player, "horse.ponecraft.pony:hotbar-block", 1.0f, 1.0f);
					}
				}
			}
		}
	}

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onPlayerInteract(PlayerInteractEvent event)
	{
		if (event.action == Action.RIGHT_CLICK_BLOCK)
		{
			PonyRace race = PonyPlayer.get(event.entityPlayer).getRace();
			Block block = event.world.getBlock(event.x, event.y, event.z);
			int metadata = event.world.getBlockMetadata(event.x, event.y, event.z);
			String reason = race.isBannedFromInteract(block, metadata);
			
			if (reason != null)
			{
				event.useBlock = Result.DENY;
				
				event.entityPlayer.worldObj.playSoundAtEntity(event.entityPlayer, "horse.ponecraft.pony:blocked-block", 1, 1);
				
				if (event.entityPlayer instanceof EntityClientPlayerMP)
				{
					Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText(I18n.format(reason, new Object[0])));
				}
			}
		}
	}
	
	private class LoginTeleporter extends Teleporter
	{
		public LoginTeleporter()
		{
			super(MinecraftServer.getServer().worldServerForDimension(Pony.raceSelectionDimensionId));
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
