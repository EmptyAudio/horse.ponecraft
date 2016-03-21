package horse.ponecraft.earth.tiles;

import squeek.applecore.api.AppleCoreAPI;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TileEntityCooktop extends TileEntity implements ISidedInventory
{
	public final InventoryCraftResult craftResult;
	private int facing = 0;
	private ItemStack[] slots;

	public TileEntityCooktop()
	{
		this.craftResult = new InventoryCraftResult();
		this.slots = new ItemStack[14];
	}
	
	public int getFacingDirection()
	{
		return this.facing;
	}

	public void setFacingDireciton(int side)
	{
		this.facing = side;
	}

	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		
		nbt.setInteger("facing", (int) this.facing);

        NBTTagList nbttaglist = new NBTTagList();

        for (int i = 0; i < this.slots.length; ++i)
        {
            if (this.slots[i] != null)
            {
                NBTTagCompound slot = new NBTTagCompound();
                
                slot.setByte("Slot", (byte)i);
                
                this.slots[i].writeToNBT(slot);
                
                nbttaglist.appendTag(slot);
            }
        }

        nbt.setTag("Items", nbttaglist);
	}

	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		
		this.facing = nbt.getInteger("facing");
		
        NBTTagList nbttaglist = nbt.getTagList("Items", 10);
        
        this.slots = new ItemStack[this.getSizeInventory()];

        for (int i = 0; i < nbttaglist.tagCount(); ++i)
        {
            NBTTagCompound slot = nbttaglist.getCompoundTagAt(i);            
            int j = slot.getByte("Slot") & 255;

            if (j >= 0 && j < this.slots.length)
            {
                this.slots[j] = ItemStack.loadItemStackFromNBT(slot);
            }
        }
        
        updateCraftResult();
	}
	
	@Override
	public Packet getDescriptionPacket()
	{
		NBTTagCompound nbt = new NBTTagCompound();
		
		writeToNBT(nbt);
		
		return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 0, nbt);
	}
	
	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet)
	{
		readFromNBT(packet.func_148857_g());
	}

	@Override
	public int getSizeInventory()
	{
		return this.slots.length;
	}

	@Override
	public ItemStack getStackInSlot(int slot)
	{
		return this.slots[slot];
	}

	@Override
	public ItemStack decrStackSize(int slot, int amount)
	{
        if (this.slots[slot] != null)
        {
            ItemStack itemstack;

            if (this.slots[slot].stackSize <= amount)
            {
                itemstack = this.slots[slot];
                this.slots[slot] = null;
                this.markDirty();
                return itemstack;
            }
            else
            {
                itemstack = this.slots[slot].splitStack(amount);

                if (this.slots[slot].stackSize == 0)
                {
                    this.slots[slot] = null;
                }

                this.markDirty();
                return itemstack;
            }
        }
        else
        {
            return null;
        }
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot)
	{
        if (this.slots[slot] != null)
        {
            ItemStack stack = this.slots[slot];
            
            this.slots[slot] = null;
            
            return stack;
        }
        else
        {
            return null;
        }
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack)
	{
        this.slots[slot] = stack;

        if (stack != null && stack.stackSize > this.getInventoryStackLimit())
        {
            stack.stackSize = this.getInventoryStackLimit();
        }
        
        this.updateCraftResult();
	}

	@Override
	public String getInventoryName()
	{
		return "container.cooktop";
	}

	@Override
	public boolean hasCustomInventoryName()
	{
		return false;
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player)
	{
        return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : player.getDistanceSq((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D) <= 64.0D;
	}

	@Override
	public void openInventory()
	{
	}

	@Override
	public void closeInventory()
	{
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack)
	{
		if (slot == 0)
		{
			return stack.isItemEqual(new ItemStack(Items.string));
		}
		else if (slot == 1)
		{
			for (int dv = 0; dv < 16; dv++)
			{
				if (stack.isItemEqual(new ItemStack(Blocks.carpet, 1, dv)))
				{
					return true;
				}
			}
			
			return false;
		}
		
		return AppleCoreAPI.accessor.isFood(stack);
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side)
	{
		return new int[] { 0, 1 };
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, int side)
	{
		return slot < 2 && this.isItemValidForSlot(slot, stack);
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack stack, int side)
	{
		return slot < 2 && this.isItemValidForSlot(slot, stack);
	}
	
	public void updateCraftResult()
	{
		if (this.slots[0] != null && this.slots[1] != null)
		{
			this.craftResult.setInventorySlotContents(0, new ItemStack(Blocks.dirt));
		}
		else
		{
			this.craftResult.setInventorySlotContents(0, null);
		}
	}
}
