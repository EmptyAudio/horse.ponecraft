package horse.ponecraft.pony.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import baubles.api.BaubleType;
import baubles.api.IBauble;
import baubles.common.container.InventoryBaubles;
import baubles.common.container.SlotBauble;
import baubles.common.lib.PlayerHandler;

public class ContainerBaubles extends Container
{
	public InventoryBaubles baubles;
	private final EntityPlayer thePlayer;

	public ContainerBaubles(EntityPlayer player)
	{
		this.thePlayer = player;
		
		baubles = new InventoryBaubles(player);
		baubles.setEventHandler(this);
		if (!player.worldObj.isRemote)
		{
			baubles.stackList = PlayerHandler.getPlayerBaubles(player).stackList;
		}

		for (int i = 0; i < 4; ++i)
		{
			final int k = i;
			this.addSlotToContainer(new Slot(player.inventory, player.inventory.getSizeInventory() - 1 - i, 8, 8 + i * 18)
			{
				@Override
				public int getSlotStackLimit()
				{
					return 1;
				}

				@Override
				public boolean isItemValid(ItemStack par1ItemStack)
				{
					if (par1ItemStack == null)
						return false;
					return par1ItemStack.getItem().isValidArmor(par1ItemStack, k, thePlayer);
				}
			});
		}
		
		this.addSlotToContainer(new SlotBauble(baubles, BaubleType.AMULET, 0, 80, 8 + 0 * 18));
		this.addSlotToContainer(new SlotBauble(baubles, BaubleType.RING, 1, 80, 8 + 1 * 18));
		this.addSlotToContainer(new SlotBauble(baubles, BaubleType.RING, 2, 80, 8 + 2 * 18));
		this.addSlotToContainer(new SlotBauble(baubles, BaubleType.BELT, 3, 80, 8 + 3 * 18));
		
		for (int i = 0; i < 3; ++i)
		{
			for (int j = 0; j < 9; ++j)
			{
				this.addSlotToContainer(new Slot(player.inventory, j + (i + 1) * 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for (int i = 0; i < 9; ++i)
		{
			this.addSlotToContainer(new Slot(player.inventory, i, 8 + i * 18, 142));
		}

	}

	@Override
	public void onContainerClosed(EntityPlayer player)
	{
		super.onContainerClosed(player);

		if (!player.worldObj.isRemote)
		{
			PlayerHandler.setPlayerBaubles(player, baubles);
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer par1EntityPlayer)
	{
		return true;
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
	{
		ItemStack itemstack = null;
		Slot slot = (Slot)this.inventorySlots.get(par2);

		if (slot != null && slot.getHasStack())
		{
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (par2 >= 0 && par2 < 4)
			{
				if (!this.mergeItemStack(itemstack1, 8, 44, false))
				{
					return null;
				}
			}
			else if (itemstack.getItem() instanceof ItemArmor && !((Slot)this.inventorySlots.get(5 + ((ItemArmor)itemstack.getItem()).armorType)).getHasStack())
			{
				int j = ((ItemArmor)itemstack.getItem()).armorType;

				if (!this.mergeItemStack(itemstack1, j, j + 1, false))
				{
					return null;
				}
			}
			else if (itemstack.getItem() instanceof IBauble && ((IBauble)itemstack.getItem()).getBaubleType(itemstack) == BaubleType.AMULET
					&& ((IBauble)itemstack.getItem()).canEquip(itemstack, thePlayer) && !((Slot)this.inventorySlots.get(4)).getHasStack())
			{
				int j = 4;
				if (!this.mergeItemStack(itemstack1, j, j + 1, false))
				{
					return null;
				}
			}
			else if (par2 > 11 && itemstack.getItem() instanceof IBauble && ((IBauble)itemstack.getItem()).getBaubleType(itemstack) == BaubleType.RING
					&& ((IBauble)itemstack.getItem()).canEquip(itemstack, thePlayer) && !((Slot)this.inventorySlots.get(5)).getHasStack())
			{
				int j = 5;
				if (!this.mergeItemStack(itemstack1, j, j + 1, false))
				{
					return null;
				}
			}
			else if (par2 > 11 && itemstack.getItem() instanceof IBauble && ((IBauble)itemstack.getItem()).getBaubleType(itemstack) == BaubleType.RING
					&& ((IBauble)itemstack.getItem()).canEquip(itemstack, thePlayer) && !((Slot)this.inventorySlots.get(6)).getHasStack())
			{
				int j = 6;
				if (!this.mergeItemStack(itemstack1, j, j + 1, false))
				{
					return null;
				}
			}
			else if (itemstack.getItem() instanceof IBauble && ((IBauble)itemstack.getItem()).getBaubleType(itemstack) == BaubleType.BELT
					&& ((IBauble)itemstack.getItem()).canEquip(itemstack, thePlayer) && !((Slot)this.inventorySlots.get(7)).getHasStack())
			{
				int j = 7;
				if (!this.mergeItemStack(itemstack1, j, j + 1, false))
				{
					return null;
				}
			}
			else if (par2 >= 8 && par2 < 35)
			{
				if (!this.mergeItemStack(itemstack1, 35, 44, false))
				{
					return null;
				}
			}
			else if (par2 >= 35 && par2 < 44)
			{
				if (!this.mergeItemStack(itemstack1, 8, 35, false))
				{
					return null;
				}
			}
			else if (!this.mergeItemStack(itemstack1, 8, 44, false, slot))
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

			slot.onPickupFromSlot(par1EntityPlayer, itemstack1);
		}

		return itemstack;
	}

	@Override
	public void putStacksInSlots(ItemStack[] p_75131_1_)
	{
		baubles.blockEvents = true;
		super.putStacksInSlots(p_75131_1_);
	}

	protected boolean mergeItemStack(ItemStack par1ItemStack, int par2, int par3, boolean par4, Slot ss)
	{
		boolean flag1 = false;
		int k = par2;

		if (par4)
		{
			k = par3 - 1;
		}

		Slot slot;
		ItemStack itemstack1;

		if (par1ItemStack.isStackable())
		{
			while (par1ItemStack.stackSize > 0 && (!par4 && k < par3 || par4 && k >= par2))
			{
				slot = (Slot)this.inventorySlots.get(k);
				itemstack1 = slot.getStack();

				if (itemstack1 != null && itemstack1.getItem() == par1ItemStack.getItem() && (!par1ItemStack.getHasSubtypes() || par1ItemStack.getItemDamage() == itemstack1.getItemDamage())
						&& ItemStack.areItemStackTagsEqual(par1ItemStack, itemstack1))
				{
					int l = itemstack1.stackSize + par1ItemStack.stackSize;
					if (l <= par1ItemStack.getMaxStackSize())
					{
						par1ItemStack.stackSize = 0;
						itemstack1.stackSize = l;
						slot.onSlotChanged();
						flag1 = true;
					}
					else if (itemstack1.stackSize < par1ItemStack.getMaxStackSize())
					{
						par1ItemStack.stackSize -= par1ItemStack.getMaxStackSize() - itemstack1.stackSize;
						itemstack1.stackSize = par1ItemStack.getMaxStackSize();
						slot.onSlotChanged();
						flag1 = true;
					}
				}

				if (par4)
				{
					--k;
				}
				else
				{
					++k;
				}
			}
		}

		if (par1ItemStack.stackSize > 0)
		{
			if (par4)
			{
				k = par3 - 1;
			}
			else
			{
				k = par2;
			}

			while (!par4 && k < par3 || par4 && k >= par2)
			{
				slot = (Slot)this.inventorySlots.get(k);
				itemstack1 = slot.getStack();

				if (itemstack1 == null)
				{
					slot.putStack(par1ItemStack.copy());
					slot.onSlotChanged();
					par1ItemStack.stackSize = 0;
					flag1 = true;
					break;
				}

				if (par4)
				{
					--k;
				}
				else
				{
					++k;
				}
			}
		}
		return flag1;
	}
}
