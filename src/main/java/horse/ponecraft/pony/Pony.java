package horse.ponecraft.pony;

import horse.ponecraft.pony.race.SetRaceCommand;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;

@Mod(modid = Pony.MODID, version = Pony.VERSION, name = "Ponecraft Pony", dependencies = "required-after:Forestry;required-after:Thaumcraft;required-after:Botania;required-after:witchery;required-after:TConstruct;required-after:horse.ponecraft.earth")
public class Pony
{
	public static final String MODID = "horse.ponecraft.pony";
	public static final String VERSION = "1.0";
	public static int raceSelectionDimensionId;
	public static SimpleNetworkWrapper net;

	@Instance("horse.ponecraft.pony")
	public static Pony instance;

	@SidedProxy(modId = MODID, clientSide = "horse.ponecraft.pony.ClientProxy", serverSide = "horse.ponecraft.pony.CommonProxy")
	public static CommonProxy proxy;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		proxy.preInit(event);
	}

	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		proxy.init(event);
	}

	@EventHandler
	public void serverLoad(FMLServerStartingEvent event)
	{
		event.registerServerCommand(new SetRaceCommand());
	}
}