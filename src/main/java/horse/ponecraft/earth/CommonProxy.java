package horse.ponecraft.earth;

import java.util.ArrayList;
import java.util.HashSet;

import scala.Console;
import squeek.applecore.api.AppleCoreAPI;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;

public class CommonProxy
{
    public static Item juicer;
    public static Item cuttingboard;
    public static Item pot;
    public static Item skillet;
    public static Item saucepan;
    public static Item bakeware;
    public static Item mortarandpestle;
    public static Item mixingbowl;
    public static Item oven;
    
    public static final BlockCooktop cooktop = new BlockCooktop("cooktop");

    public void init(FMLInitializationEvent event)
    {
		// some example code
        juicer = (Item)Item.itemRegistry.getObject("harvestcraft:juicerItem");
        cuttingboard = (Item)Item.itemRegistry.getObject("harvestcraft:cuttingboardItem");
        pot = (Item)Item.itemRegistry.getObject("harvestcraft:potItem");
        skillet = (Item)Item.itemRegistry.getObject("harvestcraft:skilletItem");
        saucepan = (Item)Item.itemRegistry.getObject("harvestcraft:saucepanItem");
        bakeware = (Item)Item.itemRegistry.getObject("harvestcraft:bakewareItem");
        mortarandpestle = (Item)Item.itemRegistry.getObject("harvestcraft:mortarandpestleItem");
        mixingbowl = (Item)Item.itemRegistry.getObject("harvestcraft:mixingbowlItem");
        oven = (Item)Item.itemRegistry.getObject("harvestcraft:oven");
        
        GameRegistry.registerBlock(cooktop, cooktop.getUnlocalizedName());
        
        GameRegistry.registerTileEntity(TileEntityCooktop.class, "CooktopTileEntity");
        
        GameRegistry.addShapedRecipe(new ItemStack(cooktop), "sss", "coc", "iri",
        		's', new ItemStack(Blocks.stone_slab), 'c', new ItemStack(Blocks.chest), 'o', new ItemStack(oven), 'i', new ItemStack(Blocks.iron_block), 'r', new ItemStack(Blocks.redstone_block));
    }

    public void postInit(FMLPostInitializationEvent event)
    {    	
    	ArrayList<IRecipe> foodRecipes = new ArrayList<IRecipe>();
    	
    	for (Object item : CraftingManager.getInstance().getRecipeList())
        {
        	IRecipe recipe = (IRecipe)item;
        	
        	if (AppleCoreAPI.accessor.isFood(recipe.getRecipeOutput()))
        	{
        		if (recipe instanceof ShapedOreRecipe)
        		{        		
        		}
        		else if (recipe instanceof ShapedRecipes)
        		{
        		}
        		else if (recipe instanceof ShapelessOreRecipe)
        		{
        		}
        		else if (recipe instanceof ShapelessRecipes)
        		{
        		}
        		
        		foodRecipes.add(recipe);
        	}
        }
    }
}
