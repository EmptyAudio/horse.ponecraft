package horse.ponecraft.food.infuser;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import forestry.api.food.BeverageManager;
import forestry.api.food.IBeverageEffect;

public class Infusion
{
	public static void init()
	{
		Item netherBerry = (Item)Item.itemRegistry.getObject("Natura:berry.nether");

		ItemStack blightBerry = new ItemStack(netherBerry, 1, 0);
		ItemStack duskBerry = new ItemStack(netherBerry, 1, 1);
		ItemStack skyBerry = new ItemStack(netherBerry, 1, 2);
		ItemStack stingBerry = new ItemStack(netherBerry, 1, 3);

		BeverageManager.ingredientManager.addIngredient(blightBerry, "Blightberry");
		BeverageManager.ingredientManager.addIngredient(duskBerry, "Duskberry");
		BeverageManager.ingredientManager.addIngredient(skyBerry, "Skyberry");
		BeverageManager.ingredientManager.addIngredient(stingBerry, "Stingberry");

		BeverageManager.infuserManager.addMixture(0, blightBerry, BeverageEffect.blightEffect);
		BeverageManager.infuserManager.addMixture(0, duskBerry, BeverageEffect.duskEffect);
		BeverageManager.infuserManager.addMixture(0, skyBerry, BeverageEffect.skyEffect);
		BeverageManager.infuserManager.addMixture(0, stingBerry, BeverageEffect.stingEffect);
	}
}
