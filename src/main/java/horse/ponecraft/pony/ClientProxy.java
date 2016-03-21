package horse.ponecraft.pony;

import horse.ponecraft.pony.gui.InventoryTabBaubles;
import horse.ponecraft.pony.gui.InventoryTabEvents;
import horse.ponecraft.pony.gui.InventoryTabQuestbook;
import net.minecraftforge.common.MinecraftForge;
import tconstruct.client.tabs.TabRegistry;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;

public class ClientProxy extends CommonProxy
{
	@Override
	public void init(FMLInitializationEvent event)
	{
		super.init(event);
				
		MinecraftForge.EVENT_BUS.register(new InventoryTabEvents());
		
		TabRegistry.registerTab(new InventoryTabBaubles());		
		TabRegistry.registerTab(new InventoryTabQuestbook());
	}	
}
