package horse.ponecraft.food.nutrition;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import scala.Console;
import squeek.applecore.api.food.FoodEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class BombAssTeaEvents
{
	private static final int talkDistance = 10;
	private static final int talkChance = 10;
	
    @SubscribeEvent
    public void onFoodEaten(FoodEvent.FoodEaten event)
    {
    	if (!event.player.worldObj.isRemote && event.food.getUnlocalizedName().equals("item.teaItem") && event.player.getRNG().nextInt(talkChance) == 0)
    	{
    		AxisAlignedBB bounds = AxisAlignedBB.getBoundingBox(
    				event.player.posX - talkDistance,
    				event.player.posY - talkDistance,
    				event.player.posZ - talkDistance,
    				event.player.posX + talkDistance,
    				event.player.posY + talkDistance,
    				event.player.posZ + talkDistance);
    		List players = event.player.worldObj.getEntitiesWithinAABB(EntityPlayer.class, bounds);
    		
    		if (players.size() == 1)
    		{    			
    			event.player.addChatComponentMessage(new ChatComponentText("You think to yourself, \"This is some bomb ass tea.\""));
    		}
    		else
    		{
    			event.player.addChatMessage(new ChatComponentText("<" + event.player.getGameProfile().getName() + "> This is some bomb ass tea."));
    			
    			for (Object nearObj : players)
    			{
    				EntityPlayer near = (EntityPlayer)nearObj;
    				
    				if (near != event.player)
    				{
    					near.addChatMessage(new ChatComponentText("<" + event.player.getGameProfile().getName() + "> This is some bomb ass tea."));
    				}
    			}
    		}
    	}
    }
}
