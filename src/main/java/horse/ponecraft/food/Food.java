package horse.ponecraft.food;

import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import scala.Console;
import squeek.applecore.api.food.FoodEvent;

@Mod(modid = Food.MODID, version = Food.VERSION, name = "Ponecraft Food", dependencies = "after:harvestcraft")
public class Food
{
    public static final String MODID = "horse.ponecraft.food";
    public static final String VERSION = "1.0";
    
    public static ItemStack juicer;
    public static ItemStack cuttingboard;
    public static ItemStack pot;
    public static ItemStack skillet;
    public static ItemStack saucepan;
    public static ItemStack bakeware;
    public static ItemStack mortarandpestle;
    public static ItemStack mixingbowl;
    
    private static final HashMap<String, boolean[]> nutrientMap = new HashMap<String, boolean[]>();
    
    public static Configuration config;
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
    	config = new Configuration(event.getSuggestedConfigurationFile());
    	
    	config.load();
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        juicer = new ItemStack((Item)Item.itemRegistry.getObject("harvestcraft:juicerItem"));
        cuttingboard = new ItemStack((Item)Item.itemRegistry.getObject("harvestcraft:cuttingboardItem"));
        pot = new ItemStack((Item)Item.itemRegistry.getObject("harvestcraft:potItem"));
        skillet = new ItemStack((Item)Item.itemRegistry.getObject("harvestcraft:skilletItem"));
        saucepan = new ItemStack((Item)Item.itemRegistry.getObject("harvestcraft:saucepanItem"));
        bakeware = new ItemStack((Item)Item.itemRegistry.getObject("harvestcraft:bakewareItem"));
        mortarandpestle = new ItemStack((Item)Item.itemRegistry.getObject("harvestcraft:mortarandpestleItem"));
        mixingbowl = new ItemStack((Item)Item.itemRegistry.getObject("harvestcraft:mixingbowlItem"));
        
        MinecraftForge.EVENT_BUS.register(new FoodEvents());
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
    	NutritionManager.initialize(config);
    	
    	config.save();
    }

	private ArrayList<IRecipe> collectRecipes()
	{
		ArrayList<IRecipe> foodRecipes = new ArrayList<IRecipe>();

        for (Object recipe : CraftingManager.getInstance().getRecipeList())
        {
        	if (recipe instanceof ShapelessOreRecipe)
        	{
        		ShapelessOreRecipe shapeless = (ShapelessOreRecipe)recipe;
        		
        		for (Object item : shapeless.getInput())
        		{
        			if (item instanceof ItemStack)
        			{
	        			if (isHarvestRecipe((ItemStack)item))
	        			{
	        				foodRecipes.add((IRecipe)recipe);
	        			}
        			}
        			else if (item instanceof ArrayList)
        			{
        				ArrayList list = (ArrayList)item;
            			
        				for (Object listitem : list)
        				{
		        			if (isHarvestRecipe((ItemStack)listitem))
		        			{
		        				foodRecipes.add((IRecipe)recipe);
		        				
		        				break;
		        			}
        				}
        			}
        		}
        	}
        }
        
        return foodRecipes;
	}
    
    private boolean isHarvestRecipe(ItemStack target)
    {
		return OreDictionary.itemMatches(target, juicer, false) ||
				OreDictionary.itemMatches(target, cuttingboard, false) ||
				OreDictionary.itemMatches(target, pot, false) ||
				OreDictionary.itemMatches(target, skillet, false) ||
				OreDictionary.itemMatches(target, saucepan, false) ||
				OreDictionary.itemMatches(target, bakeware, false) ||
				OreDictionary.itemMatches(target, mortarandpestle, false) ||
				OreDictionary.itemMatches(target, mixingbowl, false);
    }
}