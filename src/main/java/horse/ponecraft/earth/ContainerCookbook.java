package horse.ponecraft.earth;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;

public class ContainerCookbook extends Container
{
	private InventoryPlayer playerInventory;
	
	public ContainerCookbook(InventoryPlayer playerInventory)
	{
		this.playerInventory = playerInventory;
	}

	@Override
	public boolean canInteractWith(EntityPlayer player)
	{
		return true;
	}
}
