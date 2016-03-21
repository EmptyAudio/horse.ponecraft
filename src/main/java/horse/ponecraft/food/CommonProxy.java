package horse.ponecraft.food;

import horse.ponecraft.food.nutrition.BombAssTeaEvents;
import horse.ponecraft.food.nutrition.FoodEvents;
import horse.ponecraft.food.nutrition.NutritionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy
{
    public Configuration config;
    
	public void preInit(FMLPreInitializationEvent event)
	{		
    	config = new Configuration(event.getSuggestedConfigurationFile());
    	
    	config.load();
	}
	
	public void init(FMLInitializationEvent event)
	{
        MinecraftForge.EVENT_BUS.register(new FoodEvents());
        MinecraftForge.EVENT_BUS.register(new BombAssTeaEvents());
    }
	
	public void postInit(FMLPostInitializationEvent event)
	{		
    	NutritionManager.initialize(config);
    	
    	config.save();
	}
}
