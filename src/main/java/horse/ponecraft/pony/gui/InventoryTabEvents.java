package horse.ponecraft.pony.gui;

import java.util.Iterator;

import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraftforge.client.event.GuiScreenEvent;
import baubles.client.gui.GuiBaublesButton;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class InventoryTabEvents
{
	@SideOnly(value = Side.CLIENT)
	@SubscribeEvent(priority = EventPriority.LOW)
	public void guiPostInit(GuiScreenEvent.InitGuiEvent.Post event)
	{
		if (event.gui instanceof GuiInventory)
		{
			Iterator<Object> iter = event.buttonList.iterator();

			while (iter.hasNext())
			{
				if (iter.next() instanceof GuiBaublesButton)
				{
					iter.remove();

					return;
				}
			}
		}
	}
}
