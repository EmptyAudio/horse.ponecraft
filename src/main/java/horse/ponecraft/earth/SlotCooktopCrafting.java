package horse.ponecraft.earth;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemStack;

public class SlotCooktopCrafting extends SlotCrafting
{
	private TileEntityCooktop tileEntity;
	
	public SlotCooktopCrafting(EntityPlayer player, TileEntityCooktop tileEntity, int slot, int x, int y)
	{
		super(player, tileEntity, tileEntity.craftResult, slot, x, y);
		
		this.tileEntity = tileEntity;
	}
	
	@Override
    public void onPickupFromSlot(EntityPlayer player, ItemStack stack)
    {
		super.onPickupFromSlot(player, stack);
		
		this.tileEntity.updateCraftResult();
    }
}
