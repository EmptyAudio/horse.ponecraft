package horse.ponecraft.food;

import cpw.mods.fml.common.event.FMLInitializationEvent;

public class ClientProxy extends CommonProxy
{
	public void init(FMLInitializationEvent event)
	{
		super.init(event);
		
        NutritionTipOverlayHandler.init();
	}
}
