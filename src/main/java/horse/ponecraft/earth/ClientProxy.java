package horse.ponecraft.earth;

import horse.ponecraft.earth.client.TileEntityCooktopRenderer;
import horse.ponecraft.earth.tiles.TileEntityCooktop;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.event.FMLInitializationEvent;

public class ClientProxy extends CommonProxy
{
	@Override
	public void init(FMLInitializationEvent event)
	{
		super.init(event);

		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCooktop.class, new TileEntityCooktopRenderer());
	}
}
