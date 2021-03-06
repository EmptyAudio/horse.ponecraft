package horse.ponecraft.food.nutrition;

import java.util.UUID;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.client.event.RenderPlayerEvent;
import scala.Console;
import squeek.applecore.api.food.FoodEvent;
import squeek.applecore.api.hunger.HealthRegenEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class FoodEvents
{
	private final int nutrientLength = 20;
	private final UUID wellFedIdentifier = UUID.fromString("ca81a150-11b9-4e95-ab65-b645dbe20610");
	
    @SubscribeEvent
    public void onFoodEaten(FoodEvent.FoodEaten event)
    {
    	NBTTagCompound tag = getPlayerFoodTag(event.player);
    	
    	fillNutrients(tag, "v", event.foodValues.hunger, NutritionManager.hasVegetable(event.food));
    	fillNutrients(tag, "f", event.foodValues.hunger, NutritionManager.hasFruit(event.food));
    	fillNutrients(tag, "g", event.foodValues.hunger, NutritionManager.hasGrain(event.food));
    	fillNutrients(tag, "d", event.foodValues.hunger, NutritionManager.hasDairy(event.food));
    	fillNutrients(tag, "p", event.foodValues.hunger, NutritionManager.hasProtein(event.food));
    	
    	float wellFed = calculateWellFed(getPlayerFoodTag(event.player));
    	
        IAttributeInstance maxHealth = event.player.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.maxHealth);
    	AttributeModifier modifier = maxHealth.getModifier(wellFedIdentifier);
    	double beforeModifier = 0.0f;
    	
    	if (modifier != null)
    	{
    		beforeModifier = modifier.getAmount();
    		maxHealth.removeModifier(modifier);
        }
        
    	double afterModifier = 0.0;
    	
    	if (wellFed > 80.0)
    	{
    		afterModifier = 20.0;
    	}
    	else if (wellFed > 60.0)
    	{
    		afterModifier = 12.0;
    	}
    	else if (wellFed > 40.0)
    	{
    		afterModifier = 6.0;
    	}
    	else if (wellFed > 20.0)
    	{
    		afterModifier = 2.0;
    	}
    	
    	if (afterModifier != 0.0)
    	{
    		maxHealth.applyModifier(new AttributeModifier(wellFedIdentifier, "horse.ponecraft.food.wellfed", afterModifier, 0));
    	}
    }

	private void fillNutrients(NBTTagCompound tag, String nutrient, int count, boolean hasNutrient)
	{
		byte[] nutrients = tag.getByteArray(nutrient);
    	
    	for (int x = count; x < nutrients.length; x++)
    	{
    		nutrients[x] = nutrients[x - count];
    	}
    	
    	for (int x = 0; x < count; x++)
    	{
    		nutrients[x] = (byte)(hasNutrient ? 1 : 0);
    	}
    	
    	tag.setByteArray(nutrient, nutrients);
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
