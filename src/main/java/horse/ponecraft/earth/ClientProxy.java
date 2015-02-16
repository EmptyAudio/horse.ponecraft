package horse.ponecraft.earth;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

public class ClientProxy extends CommonProxy
{
	@Override
	public void init(FMLInitializationEvent event)
	{
		super.init(event);
		
		NetworkRegistry.INSTANCE.registerGuiHandler(Earth.instance, new GuiHandler());
	}
}
