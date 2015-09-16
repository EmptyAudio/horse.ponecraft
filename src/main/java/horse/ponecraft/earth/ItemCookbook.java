package horse.ponecraft.earth;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemCookbook extends Item
{
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float px, float py, float pz)
	{
        player.openGui(Earth.instance, 1, world, x, y, z);		
		
		return true;
	}
}
