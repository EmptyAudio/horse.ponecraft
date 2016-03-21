package horse.ponecraft.pegasus.tinkers;

import horse.ponecraft.pegasus.Pegasus;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;
import tconstruct.library.tools.ToolCore;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class PegasusToolEvents
{
	@SubscribeEvent
	public void onBreakSpeed(BreakSpeed event)
	{
		if (event.entityPlayer.inventory.getCurrentItem() != null && event.entityPlayer.inventory.getCurrentItem().getItem() instanceof ToolCore)
		{
			NBTTagCompound toolTags = event.entityPlayer.inventory.getCurrentItem().getTagCompound().getCompoundTag("InfiTool");
			int sapphireCount = 0;

			sapphireCount += toolTags.getInteger("Head") == Pegasus.SapphireMaterial ? 1 : 0;
			sapphireCount += toolTags.getInteger("Handle") == Pegasus.SapphireMaterial ? 1 : 0;
			sapphireCount += toolTags.getInteger("Accessory") == Pegasus.SapphireMaterial ? 1 : 0;
			sapphireCount += toolTags.getInteger("Extra") == Pegasus.SapphireMaterial ? 1 : 0;

			int origDamage = toolTags.getInteger("Damage");
			int maxDamage = toolTags.getInteger("TotalDurability");
			float multiplier = (1.0f - ((float)origDamage / (float)maxDamage)) * 1.5f;

			if (sapphireCount > 0)
			{
				event.newSpeed = event.originalSpeed * multiplier;
			}
		}
	}
}
