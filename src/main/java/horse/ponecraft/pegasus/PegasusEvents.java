package horse.ponecraft.pegasus;

import com.forgeessentials.api.APIRegistry;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.FoodStats;
import net.minecraftforge.event.entity.living.LivingEvent;
import squeek.applecore.api.food.FoodEvent;
import squeek.applecore.api.hunger.ExhaustionEvent;
import squeek.applecore.api.hunger.HealthRegenEvent;
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
    				player.getFoodStats().addExhaustion(1f / 45f);
    			}
    		
    			if (APIRegistry.perms.checkPermission(player, Pegasus.FlightPermissionName))
    			{
    				if (player.capabilities.allowFlying)
    				{
    	    	    	if (!foodAllowsFlight(player.getFoodStats()))
    	    	    	{
    	    	    		if (player.capabilities.isFlying)
    	    	    		{
    	    	    	    	if (!event.entity.worldObj.isRemote)
    	    	    	    	{
    	    	    	    		player.addChatComponentMessage(new ChatComponentText("Your wings at too tired to flap! Look out below!"));
    	    	    	    	}
    	    	    		}
    	    	    		else
    	    	    		{
    	    	    	    	if (!event.entity.worldObj.isRemote)
    	    	    	    	{
    	    	    	    		player.addChatComponentMessage(new ChatComponentText("Your tummy rumbles loudly and your wings droop. You'll need to eat if you want to fly."));
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
    	    	    	    		player.addChatComponentMessage(new ChatComponentText("You can't fly with that on! Quick, take it off!"));
    	    	    	    	}
    	    	    		}
    	    	    		else
    	    	    		{
    	    	    	    	if (!event.entity.worldObj.isRemote)
    	    	    	    	{
    	    	    	    		player.addChatComponentMessage(new ChatComponentText("You feel like you're too heavy and stiff to fly with that armor on."));
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
	    	    	    		player.addChatComponentMessage(new ChatComponentText("You feel fit for flight once more."));
	    	    	    	}
	    	    	    	
    	    	    		player.capabilities.allowFlying = true;
    	    			}
	    	    	}
    			}
			}
    	}
    }

	private boolean foodAllowsFlight(FoodStats foodStats)
	{
		return foodStats.getFoodLevel() > 7;
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
