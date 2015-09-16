package horse.ponecraft.food;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import scala.Console;
import squeek.applecore.api.AppleCoreAPI;

public class NutritionManager
{
    public static ItemStack juicer;
    public static ItemStack cuttingboard;
    public static ItemStack pot;
    public static ItemStack skillet;
    public static ItemStack saucepan;
    public static ItemStack bakeware;
    public static ItemStack mortarandpestle;
    public static ItemStack mixingbowl;
    
	public static void initialize(Configuration config)
	{
        juicer = new ItemStack((Item)Item.itemRegistry.getObject("harvestcraft:juicerItem"));
        cuttingboard = new ItemStack((Item)Item.itemRegistry.getObject("harvestcraft:cuttingboardItem"));
        pot = new ItemStack((Item)Item.itemRegistry.getObject("harvestcraft:potItem"));
        skillet = new ItemStack((Item)Item.itemRegistry.getObject("harvestcraft:skilletItem"));
        saucepan = new ItemStack((Item)Item.itemRegistry.getObject("harvestcraft:saucepanItem"));
        bakeware = new ItemStack((Item)Item.itemRegistry.getObject("harvestcraft:bakewareItem"));
        mortarandpestle = new ItemStack((Item)Item.itemRegistry.getObject("harvestcraft:mortarandpestleItem"));
        mixingbowl = new ItemStack((Item)Item.itemRegistry.getObject("harvestcraft:mixingbowlItem"));
        
		loadFromConfig(config);
		
    	for (Object item : CraftingManager.getInstance().getRecipeList())
        {
        	IRecipe recipe = (IRecipe)item;
        	
        	if (AppleCoreAPI.accessor.isFood(recipe.getRecipeOutput()))
        	{
        		if (recipe instanceof ShapedOreRecipe)
        		{
        			handleRecipe(recipe.getRecipeOutput(), ((ShapedOreRecipe)recipe).getInput());
        		}
        		else if (recipe instanceof ShapedRecipes)
        		{
        			handleRecipe(recipe.getRecipeOutput(), ((ShapedRecipes)recipe).recipeItems);
        		}
        		else if (recipe instanceof ShapelessOreRecipe)
        		{
        			handleRecipe(recipe.getRecipeOutput(), ((ShapelessOreRecipe)recipe).getInput().toArray());
        		}
        		else if (recipe instanceof ShapelessRecipes)
        		{
        			handleRecipe(recipe.getRecipeOutput(), ((ShapelessRecipes)recipe).recipeItems.toArray());
        		}
        	}
        }    	
	}

	private static void loadFromConfig(Configuration config)
	{
		ConfigCategory category = config.getCategory("Nutrients");
		
		for (String name : category.getPropertyOrder())
		{
		}
	}
	
	private static void handleRecipe(ItemStack output, Object[] ingredients)
	{
		List rawIngredients = new ArrayList();
		
		for (Object ingredient : ingredients)
		{
			if (ingredient == null ||
				ingredient == juicer || 
			    ingredient == cuttingboard || 
			    ingredient == pot || 
			    ingredient == skillet || 
			    ingredient == saucepan || 
			    ingredient == bakeware || 
			    ingredient == mortarandpestle || 
			    ingredient == mixingbowl) 
			{
				continue;
			}
			
			if (ingredient instanceof ItemStack)
			{
				rawIngredients.add(ingredient);
			}			
			else if (ingredient instanceof ArrayList)
			{
				ArrayList arrayList = (ArrayList)ingredient;
				
				if (arrayList.size() == 0)
				{
					continue;
				}
				
				rawIngredients.add(locateOreDictEntryName(arrayList));				
			}
		}
	}

	private static Object locateOreDictEntryName(ArrayList arrayList)
	{
		if (arrayList.size() == 1)
		{
			return arrayList.get(0);
		}
		
		HashSet<Integer> ids = createSet(OreDictionary.getOreIDs((ItemStack)arrayList.get(0)));
		
		for (int index = 1; index < arrayList.size(); index++)
		{
			HashSet<Integer> itemSet = createSet(OreDictionary.getOreIDs((ItemStack)arrayList.get(index)));

			ids.retainAll(itemSet);
		}
		
		if (ids.size() != 1)
		{
			int minSize = Integer.MAX_VALUE;
			int minId = 0;
			boolean unique = true;
			
			for (Integer id : ids)
			{
				if (OreDictionary.getOres(OreDictionary.getOreName(id)).size() < minSize)
				{
					minSize = OreDictionary.getOres(OreDictionary.getOreName(id)).size();
					minId = id;	
					unique = true;
				}
				else if (OreDictionary.getOres(OreDictionary.getOreName(id)).size() == minSize)
				{
					unique = false;
				}
			}
			
			if (!unique)
			{
				Console.out().println("Ambiguous oredict found for items:");
				
				for (int index = 0; index < arrayList.size(); index++)
				{
					Console.out().println("  " + Item.itemRegistry.getNameForObject(((ItemStack)arrayList.get(index)).getItem()));
				}
				
				Console.out().println("with IDs:");
				
				for (Integer id : ids)
				{
					Console.out().println("  " + OreDictionary.getOreName(id));
				}
				
				return null;
			}
			
			return OreDictionary.getOreName(minId);
		}
		
		for (Integer id : ids)
		{
			return OreDictionary.getOreName(id);
		}
		
		return null;
	}
	
	private static HashSet<Integer> createSet(int[] array)
	{
		HashSet<Integer> newSet = new HashSet<Integer>();
		
		for (int index = 0; index < array.length; index++)
		{
			newSet.add(array[index]);
		}
		
		return newSet;
	}

	public static boolean hasDairy(ItemStack stack)
	{
		return false;
	}

	public static boolean hasVegetable(ItemStack stack)
	{
		return false;
	}

	public static boolean hasFruit(ItemStack stack)
	{
		return false;
	}

	public static boolean hasGrain(ItemStack stack)
	{
		return false;
	}
	
	public static boolean hasProtein(ItemStack stack)
	{
		return false;
	}
}
