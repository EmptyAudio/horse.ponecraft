package horse.ponecraft.food;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import squeek.applecore.api.food.FoodEvent;
import squeek.applecore.api.hunger.HealthRegenEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class FoodEvents
{
	private final int nutrientLength = 20;
	
    @SubscribeEvent
    public void onFoodEaten(FoodEvent.FoodEaten event)
    {
    }
    
    @SubscribeEvent
    public void onGetRegenTickPeriod(HealthRegenEvent.GetRegenTickPeriod event)
    {
    	float wellFed = calculateWellFed(getPlayerFoodTag(event.player));
    	
    	if (wellFed > 80.0f)
    	{
    		event.regenTickPeriod /= 2;  
    	}
    	else if (wellFed > 60.0f)
    	{
    		event.regenTickPeriod -= event.regenTickPeriod * 3 / 10;  
    	}
    	else if (wellFed > 40.0f)
    	{
    		event.regenTickPeriod -= event.regenTickPeriod / 5;  
    	}
    	else if (wellFed > 20.0f)
    	{
    		event.regenTickPeriod -= event.regenTickPeriod / 10;  
    	}
	}
    
    private NBTTagCompound getPlayerFoodTag(EntityPlayer player)
    {
    	NBTTagCompound tag = player.getEntityData();
    	
    	if (!tag.hasKey("ponecraft"))
    	{
    		NBTTagCompound newPonecraft = new NBTTagCompound();
    		
    		tag.setTag("ponecraft", newPonecraft);
    	}

    	NBTTagCompound ponecraft = (NBTTagCompound)tag.getTag("ponecraft");
    	
    	if (!ponecraft.hasKey("food"))
    	{
			NBTTagCompound newFood = new NBTTagCompound();
			
			newFood.setByteArray("v", new byte[nutrientLength]);
			newFood.setByteArray("f", new byte[nutrientLength]);
			newFood.setByteArray("g", new byte[nutrientLength]);
			newFood.setByteArray("p", new byte[nutrientLength]);
			newFood.setByteArray("d", new byte[nutrientLength]);

			ponecraft.setTag("food", newFood);
    	}
    	
    	return (NBTTagCompound)ponecraft.getTag("food");
    }
    
    private float calculateWellFed(NBTTagCompound food)
    {
    	return (sumArray(food.getByteArray("v")) + sumArray(food.getByteArray("f")) + sumArray(food.getByteArray("g")) + sumArray(food.getByteArray("p")) + sumArray(food.getByteArray("d"))) * 20.0f / (float)nutrientLength;
    }
    
    private int sumArray(byte[] array)
    {
    	int sum = 0;
    	
    	for (int b : array)
    	{
    		sum += b;
    	}
    	
    	return sum;
    }
}
