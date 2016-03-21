package horse.ponecraft.food.nutrition;

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
    
    static Map<Object, NutrientEntry> nutrients;
    
	public static void initialize(Configuration config)
	{
		nutrients = new HashMap<Object, NutrientEntry>();
		
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
    	
    	for (Object key : nutrients.keySet())
    	{
    		nutrients.get(key).ensureFinished();
    	}
	}

	private static void loadFromConfig(Configuration config)
	{
		for (String nutrientLine : config.getStringList("item", "Nutrition", new String[0], "A list of nutrients of the form 'itemname=[D][V][F][G][P]'."))
		{
			String itemName = nutrientLine.substring(0, nutrientLine.indexOf('='));
			String itemNutrients = nutrientLine.substring(nutrientLine.indexOf('=') + 1);
		
			////System.out.println("Adding item [" + itemName + "] with nutrients " + itemNutrients);
			
			addConfigEntry(new HashComparableItemStack(new ItemStack((Item)Item.itemRegistry.getObject(itemName))), itemNutrients);
		}
		
		for (String nutrientLine : config.getStringList("ore", "Nutrition", new String[0], "A list of nutrients of the form 'orename=[D][V][F][G][P]'."))
		{
			String oreName = nutrientLine.substring(0, nutrientLine.indexOf('='));
			String oreNutrients = nutrientLine.substring(nutrientLine.indexOf('=') + 1);
			
			////System.out.println("Adding ore [" + oreName + "] with nutrients " + oreNutrients);
			
			addConfigEntry(oreName, oreNutrients);
		}
	}
	
	private static void addConfigEntry(Object key, String configString)
	{
		////System.out.println("Added config nutrients for [" + key.toString() + "] as " + configString);
		
		nutrients.put(key, new NutrientEntry(configString.contains("D"), configString.contains("V"), configString.contains("F"), configString.contains("G"), configString.contains("P")));
	}
	
	private static void handleRecipe(ItemStack output, Object[] ingredients)
	{
		List rawIngredients = new ArrayList();
		HashComparableItemStack outputKey = new HashComparableItemStack(new ItemStack(output.getItem()));
		
		for (Object ingredient : ingredients)
		{
			if (ingredient instanceof ItemStack)
			{
				rawIngredients.add(new HashComparableItemStack(new ItemStack(((ItemStack)ingredient).getItem())));
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
		
		if (!nutrients.containsKey(outputKey))
		{
			nutrients.put(outputKey, new NutrientEntry());
		}
		
		nutrients.get(outputKey).addRecipe(rawIngredients);
	}

	private static Object locateOreDictEntryName(ArrayList arrayList)
	{
		if (arrayList.size() == 1)
		{
			return new HashComparableItemStack(new ItemStack(((ItemStack)arrayList.get(0)).getItem()));
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
		HashComparableItemStack key = new HashComparableItemStack(new ItemStack(stack.getItem()));
		
		if (nutrients.containsKey(key))
		{
			return nutrients.get(key).hasDairy();
		}
		
		return false;
	}

	public static boolean hasVegetable(ItemStack stack)
	{
		HashComparableItemStack key = new HashComparableItemStack(new ItemStack(stack.getItem()));
		
		if (nutrients.containsKey(key))
		{
			return nutrients.get(new HashComparableItemStack(stack)).hasVegetable();
		}
		
		return false;
	}

	public static boolean hasFruit(ItemStack stack)
	{
		HashComparableItemStack key = new HashComparableItemStack(new ItemStack(stack.getItem()));
		
		if (nutrients.containsKey(key))
		{
			return nutrients.get(new HashComparableItemStack(stack)).hasFruit();
		}
		
		return false;
	}

	public static boolean hasGrain(ItemStack stack)
	{
		HashComparableItemStack key = new HashComparableItemStack(new ItemStack(stack.getItem()));
		
		if (nutrients.containsKey(key))
		{
			return nutrients.get(new HashComparableItemStack(stack)).hasGrain();
		}
		
		return false;
	}
	
	public static boolean hasProtein(ItemStack stack)
	{
		HashComparableItemStack key = new HashComparableItemStack(new ItemStack(stack.getItem()));
		
		if (nutrients.containsKey(key))
		{
			return nutrients.get(new HashComparableItemStack(stack)).hasProtein();
		}
		
		return false;
	}
}
