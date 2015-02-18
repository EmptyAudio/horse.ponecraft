package horse.ponecraft.food;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import squeek.applecore.api.AppleCoreAPI;

public class NutritionManager
{
	public static final Map<String, boolean[]> nutrients = new HashMap<String, boolean[]>();
	public static final Map<String, String[]> ingredients = new HashMap<String, String[]>();
	
    private static final int VEGETABLE = 0;
    private static final int FRUIT = 1;
    private static final int GRAIN = 2;
    private static final int DAIRY = 3;
    private static final int PROTEIN = 4;
    
	public static void initialize(Configuration config)
	{
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

    public static boolean hasVegetable(ItemStack food)
    {
		return false;
    }
    
    public static boolean hasFruit(ItemStack food)
    {
		return false;
    }
    
    public static boolean hasGrain(ItemStack food)
    {
		return false;
    }
    
    public static boolean hasProtein(ItemStack food)
    {
		return false;
    }
    
    public static boolean hasDairy(ItemStack food)
    {
		return false;
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
	}
}
