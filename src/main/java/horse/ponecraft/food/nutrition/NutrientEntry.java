package horse.ponecraft.food.nutrition;

import java.util.ArrayList;
import java.util.List;

class NutrientEntry
{
	private boolean dairy;
	private boolean vegetable;
	private boolean fruit;
	private boolean grain;
	private boolean protein;

	private boolean calculating;
	private boolean finished;

	private List<List> recipes;

	public NutrientEntry()
	{
		this.recipes = new ArrayList<List>();
	}

	public NutrientEntry(boolean dairy, boolean vegetable, boolean fruit, boolean grain, boolean protein)
	{
		this.dairy = dairy;
		this.vegetable = vegetable;
		this.fruit = fruit;
		this.grain = grain;
		this.protein = protein;

		this.finished = true;
	}

	public void ensureFinished()
	{
		if (!this.finished)
		{
			if (this.calculating)
			{
				throw new RuntimeException("Loop detected in nutrient calculation!");
			}

			if (recipes.size() == 0)
			{
				this.finished = true;

				return;
			}

			this.calculating = true;

			this.dairy = true;
			this.vegetable = true;
			this.fruit = true;
			this.grain = true;
			this.protein = true;

			for (List ingredients : this.recipes)
			{
				boolean recipeDairy = false;
				boolean recipeVegetable = false;
				boolean recipeFruit = false;
				boolean recipeGrain = false;
				boolean recipeProtein = false;

				for (Object ingredient : ingredients)
				{
					if (NutritionManager.nutrients.containsKey(ingredient))
					{
						recipeDairy |= NutritionManager.nutrients.get(ingredient).hasDairy();
						recipeVegetable |= NutritionManager.nutrients.get(ingredient).hasVegetable();
						recipeFruit |= NutritionManager.nutrients.get(ingredient).hasFruit();
						recipeGrain |= NutritionManager.nutrients.get(ingredient).hasGrain();
						recipeProtein |= NutritionManager.nutrients.get(ingredient).hasProtein();
					}
					else
					{
						////System.out.println("Couldn't find " + ingredient.toString());
					}
				}

				this.dairy &= recipeDairy;
				this.vegetable &= recipeVegetable;
				this.fruit &= recipeFruit;
				this.grain &= recipeGrain;
				this.protein &= recipeProtein;
			}

			this.calculating = false;
			this.finished = true;
		}
	}

	public void addRecipe(List ingredients)
	{
		if (this.recipes != null)
		{
			this.recipes.add(ingredients);
		}
	}

	public boolean hasDairy()
	{
		ensureFinished();

		return this.dairy;
	}

	public boolean hasVegetable()
	{
		ensureFinished();

		return this.vegetable;
	}

	public boolean hasFruit()
	{
		ensureFinished();

		return this.fruit;
	}

	public boolean hasGrain()
	{
		ensureFinished();

		return this.grain;
	}

	public boolean hasProtein()
	{
		ensureFinished();

		return this.protein;
	}
}