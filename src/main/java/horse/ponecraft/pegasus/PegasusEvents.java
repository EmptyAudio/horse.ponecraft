package horse.ponecraft.pegasus;

import com.forgeessentials.api.APIRegistry;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
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
    			
    	    	if (player.getFoodStats().getFoodLevel() <= 6 ||
    	    		player.inventory.armorItemInSlot(2) != null ||
    	    		!APIRegistry.perms.checkPermission(player, Pegasus.FlightPermissionName))
    	    	{
    	    		player.capabilities.allowFlying = false;
    	    		player.capabilities.isFlying = false;
    	    	}
    	    	else    	    		
    	    	{
    	    		player.capabilities.allowFlying = true;
    	    	}
			}
    	}
    }
}
