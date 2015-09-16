package horse.ponecraft.food;

import java.util.Objects;

import net.minecraft.item.ItemStack;

public class HashComparableItemStack
{
	private ItemStack stack;

	public HashComparableItemStack(ItemStack itemStack)
	{
		this.stack = itemStack.copy();
	}

	public static boolean areEqual(ItemStack lhs, ItemStack rhs)
	{
		return lhs.getItem().equals(rhs.getItem()) && lhs.getItemDamage() == rhs.getItemDamage();
	}
	
	public static int hash(ItemStack stack)
	{
		return Objects.hash(stack.getItem(), stack.getItemDamage());
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof HashComparableItemStack)
		{
			HashComparableItemStack other = (HashComparableItemStack)obj;
			
			return areEqual(this.stack, other.stack);
		}
		
		return false;
	}

	@Override
	public int hashCode()
	{
		return hash(this.stack);
	}	
}
