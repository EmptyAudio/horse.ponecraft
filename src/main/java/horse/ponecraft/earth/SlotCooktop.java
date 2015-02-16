package horse.ponecraft.earth;

import squeek.applecore.api.AppleCoreAPI;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotCooktop extends Slot
{
	public SlotCooktop(IInventory inventory, int slot, int x, int y)
	{
		super(inventory, slot, x, y);
	}

	@Override
	public boolean isItemValid(ItemStack stack)
	{
		return inventory.isItemValidForSlot(this.slotNumber, stack);
	}	
}
