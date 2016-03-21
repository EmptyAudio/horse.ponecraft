package horse.ponecraft.pony.herbivore;

import horse.ponecraft.pony.Pony;

import java.util.Iterator;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Items;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.PlayerUseItemEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class HerbivoreEvents
{
	@SubscribeEvent
	public void onLivingDrops(LivingDropsEvent event)
	{
		Iterator<EntityItem> it = event.drops.iterator();

		while (it.hasNext())
		{
			EntityItem item = it.next();

			if (item.getEntityItem().getItem() == Items.chicken || item.getEntityItem().getItem() == Items.porkchop || item.getEntityItem().getItem() == Items.beef
					|| item.getEntityItem().getItem() == Items.leather)
			{
				it.remove();
			}
		}
	}
	
	@SubscribeEvent
	public void onPlayerUseItemStart(PlayerUseItemEvent.Start event)
	{
		if (Pony.proxy.meatItems.contains(event.item.getItem()))
		{
			event.setCanceled(true);
		}
	}
}
