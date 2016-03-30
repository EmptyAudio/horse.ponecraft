package horse.ponecraft.food;

import forestry.api.food.BeverageManager;
import forestry.api.food.IBeverageEffect;
import horse.ponecraft.food.infuser.Infusion;
import horse.ponecraft.food.nutrition.BombAssTeaEvents;
import horse.ponecraft.food.nutrition.FoodEvents;
import horse.ponecraft.food.nutrition.NutritionManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
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

        Infusion.init();
    }

	public void postInit(FMLPostInitializationEvent event)
	{
		NutritionManager.initialize(config);

		config.save();
	}
}
