package horse.ponecraft.pony.gui;

import hardcorequesting.QuestingData;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import tconstruct.client.tabs.AbstractTab;

public class InventoryTabQuestbook extends AbstractTab
{
	public InventoryTabQuestbook()
	{
		super(100, 0, 0, new ItemStack((Item)Item.itemRegistry.getObject("HardcoreQuesting:quest_book")));
	}

	@Override
	public void onTabClicked()
	{
		QuestingData.getQuestingData(Minecraft.getMinecraft().thePlayer).sendDataToClientAndOpenInterface(Minecraft.getMinecraft().thePlayer, null);
	}

	@Override
	public boolean shouldAddToList()
	{
		return true;
	}
}
