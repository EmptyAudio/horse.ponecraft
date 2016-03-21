package horse.ponecraft.earth.tiles;

import horse.ponecraft.earth.gui.SlotCooktop;
import horse.ponecraft.earth.gui.SlotCooktopCrafting;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemStack;

public class ContainerCooktop extends Container
{
	private TileEntityCooktop tileEntity;
	
	public ContainerCooktop(InventoryPlayer playerInventory, TileEntityCooktop tileEntity)
	{
		this.tileEntity = tileEntity;
		
        addSlotToContainer(new SlotCooktop(tileEntity, 0, 8, 17));
        addSlotToContainer(new SlotCooktop(tileEntity, 1, 8, 35));
        
        for (int slot = 0; slot < tileEntity.getSizeInventory() - 2; slot++)
        {
            addSlotToContainer(new SlotCooktop(tileEntity, slot + 2, 36 + (slot % 4) * 18, 8 + (slot / 4) * 18));
        }
        
        addSlotToContainer(new SlotCooktopCrafting(playerInventory.player, tileEntity, 0, 148, 26));
        		
		bindPlayerInventory(playerInventory);
	}
	
	protected void bindPlayerInventory(InventoryPlayer playerInventory)
	{
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 9; j++)
            {
                addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 162 + i * 18));
            }
        }

        for (int i = 0; i < 9; i++)
        {
            addSlotToContainer(new Slot(playerInventory, i, 8 + i * 18, 220));
        }
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer player)
	{
		return this.tileEntity.isUseableByPlayer(player);
	}
	
	public ItemStack transferStackInSlot(EntityPlayer player, int slotNum)
    {
        ItemStack itemstack = null;
        Slot slot = (Slot)this.inventorySlots.get(slotNum);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (slotNum == 0)
            {
                if (!this.mergeItemStack(itemstack1, 10, 46, true))
                {
                    return null;
                }

                slot.onSlotChange(itemstack1, itemstack);
            }
            else if (slotNum >= 10 && slotNum < 37)
            {
                if (!this.mergeItemStack(itemstack1, 37, 46, false))
                {
                    return null;
                }
            }
            else if (slotNum >= 37 && slotNum < 46)
            {
                if (!this.mergeItemStack(itemstack1, 10, 37, false))
                {
                    return null;
                }
            }
            else if (!this.mergeItemStack(itemstack1, 10, 46, false))
            {
                return null;
            }

            if (itemstack1.stackSize == 0)
            {
                slot.putStack((ItemStack)null);
            }
            else
            {
                slot.onSlotChanged();
            }

            if (itemstack1.stackSize == itemstack.stackSize)
            {
                return null;
            }

            slot.onPickupFromSlot(player, itemstack1);
        }

        return itemstack;
    }}
