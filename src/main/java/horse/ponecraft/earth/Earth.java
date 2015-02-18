package horse.ponecraft.earth;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = Earth.MODID, version = Earth.VERSION, name = "Ponecraft Earth Pony", dependencies = "after:harvestcraft")
public class Earth
{
    public static final String MODID = "horse.ponecraft.earth";
    public static final String VERSION = "1.0";
    
    public static final BlockCooktop cooktop = new BlockCooktop("cooktop");
    
    @Instance("horse.ponecraft.earth")
    public static Earth instance;

    @SidedProxy(modId = MODID, clientSide = "horse.ponecraft.earth.ClientProxy", serverSide = "horse.ponecraft.earth.CommonProxy")
    public static CommonProxy proxy;
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    	proxy.init(event);
    }
}
