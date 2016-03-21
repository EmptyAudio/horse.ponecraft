package horse.ponecraft.pony.gui;

import horse.ponecraft.earth.Earth;
import horse.ponecraft.pony.Pony;
import horse.ponecraft.pony.network.ShowBaublesGuiPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import tconstruct.client.tabs.AbstractTab;

public class InventoryTabBaubles extends AbstractTab
{
	public InventoryTabBaubles()
	{
		super(100, 0, 0, new ItemStack((Item)Item.itemRegistry.getObject("Thaumcraft:ItemBaubleBlanks")));
	}

	@Override
	public void onTabClicked()
	{
		System.out.println("sending packet");
		Pony.net.sendToServer(new ShowBaublesGuiPacket());
	}

	@Override
	public boolean shouldAddToList()
	{
		return true;
	}
}
