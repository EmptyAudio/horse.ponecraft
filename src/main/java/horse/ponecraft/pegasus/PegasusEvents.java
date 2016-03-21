package horse.ponecraft.pegasus;

import horse.ponecraft.pony.race.PonyPlayer;
import horse.ponecraft.pony.race.PonyRace;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.FoodStats;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;
import tconstruct.library.tools.ToolCore;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class PegasusEvents
{
	@SubscribeEvent
	public void onLivingUpdateEvent(LivingEvent.LivingUpdateEvent event)
	{
		if (event.entityLiving instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer)event.entityLiving;

			if (!player.capabilities.isCreativeMode)
			{
				if (player.capabilities.isFlying)
				{
					if (player.dimension == -1)
					{
						// the heat of the nether is extra taxing
						player.getFoodStats().addExhaustion(3f / 45f);
					}
					else
					{
						// typing is fast and legible?
						player.getFoodStats().addExhaustion(1f / 45f);
					}
				}

				if (PonyPlayer.get(player).getRace() == PonyRace.PEGASUS)
				{
					if (player.capabilities.allowFlying)
					{
						if (!foodAllowsFlight(player.getFoodStats()))
						{
							if (player.capabilities.isFlying)
							{
								if (!event.entity.worldObj.isRemote)
								{
									player.addChatComponentMessage(new ChatComponentText(I18n.format("flight.lose.food.flying", new Object[0])));
								}
							}
							else
							{
								if (!event.entity.worldObj.isRemote)
								{
									player.addChatComponentMessage(new ChatComponentText(I18n.format("flight.lose.food.flying", new Object[0])));
								}
							}

							player.capabilities.allowFlying = false;
							player.capabilities.isFlying = false;
						}
						if (!armorAllowsFlight(player.inventory))
						{
							if (player.capabilities.isFlying)
							{
								if (!event.entity.worldObj.isRemote)
								{
									player.addChatComponentMessage(new ChatComponentText(I18n.format("flight.lose.armor.flying", new Object[0])));
								}
							}
							else
							{
								if (!event.entity.worldObj.isRemote)
								{
									player.addChatComponentMessage(new ChatComponentText(I18n.format("flight.lose.armor", new Object[0])));
								}
							}

							player.capabilities.allowFlying = false;
							player.capabilities.isFlying = false;
						}
					}
					else
					{
						if (foodAllowsFlight(player.getFoodStats()) && armorAllowsFlight(player.inventory))
						{
							if (!event.entity.worldObj.isRemote)
							{
								player.addChatComponentMessage(new ChatComponentText(I18n.format("You feel fit for flight!", new Object[0])));
							}

							player.capabilities.allowFlying = true;
						}
					}
				}
				else if (player.capabilities.allowFlying)
				{
					player.capabilities.allowFlying = false;
					player.capabilities.isFlying = false;
				}
			}
		}
	}

	private boolean foodAllowsFlight(FoodStats foodStats)
	{
		return foodStats.getFoodLevel() >= 7;
	}

	private boolean armorAllowsFlight(InventoryPlayer inventory)
	{
		for (ItemStack armor : inventory.armorInventory)
		{
			if (armor == null)
			{
				continue;
			}

			boolean okay = false;

			for (ItemStack allowed : Pegasus.allowableFlightArmor)
			{
				if (armor.getUnlocalizedName().equals(allowed.getUnlocalizedName()))
				{
					okay = true;

					break;
				}
			}

			if (!okay)
			{
				return false;
			}
		}

		return true;
	}
}
