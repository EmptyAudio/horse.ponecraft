package horse.ponecraft.pony.race;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public enum PonyRace
{
	NONE,
	EARTHPONY,
	PEGASUS,
	UNICORN,
	ZEBRA;
	
	private Map<String, String> bannedHotbarItems = new HashMap<String, String>();
	private Map<String, String> bannedBlockInteractions = new HashMap<String, String>();
	
	public void banHotbarItem(ItemStack bannedItem, String reason)
	{
		bannedHotbarItems.put(itemStackToString(bannedItem), reason);
	}
	
	public void banBlockInteract(Block bannedBlock, String reason)
	{
		bannedBlockInteractions.put(Block.blockRegistry.getNameForObject(bannedBlock), reason);
	}
	
	public void banBlockInteract(Block bannedBlock, int metadata, String reason)
	{
		bannedBlockInteractions.put(Block.blockRegistry.getNameForObject(bannedBlock) + "#" + metadata, reason);
	}
	
	public void banCraftingRecipe(ItemStack craftingResult)
	{
	}
	
	public String isBannedFromHotbar(ItemStack stack)
	{
		return bannedHotbarItems.get(itemStackToString(stack));
	}
	
	public String isBannedFromInteract(Block block, int metadata)
	{
		String blockName = Block.blockRegistry.getNameForObject(block);
		
		if (bannedBlockInteractions.containsKey(blockName))
		{
			return bannedBlockInteractions.get(blockName);
		}
		
		return bannedBlockInteractions.get(blockName + "#" + metadata);
	}
	
	private static String itemStackToString(ItemStack stack)
	{
		return stack.getItem().getUnlocalizedName(stack);
	}
}
