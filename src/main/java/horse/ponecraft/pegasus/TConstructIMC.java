package horse.ponecraft.pegasus;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import cpw.mods.fml.common.event.FMLInterModComms;

public class TConstructIMC
{
    public static void addMaterial(
    		int id,
    		String name,
    		int harvestLevel,
    		int durability,
    		int miningSpeed,
    		int attack,
    		float handleModifier,
    		int reinforced,
    		float stonebound,
    		int jagged,
    		EnumChatFormatting style,
    		int colorRed,
    		int colorGreen,
    		int colorBlue,
    		int drawSpeed,
    		float projectileSpeed,
    		float projectileMass,
    		float projectileFragility)
    {
    	NBTTagCompound tag = new NBTTagCompound();
    	
    	tag.setInteger("Id", id); // Unique material ID. Reseved IDs: 0-40 Tinker, 41-45 Iguana Tinker Tweaks, 100-200 ExtraTiC
    	tag.setString("Name", name); // Unique material name
    	tag.setInteger("HarvestLevel", harvestLevel); // diamond level
    	tag.setInteger("Durability", durability);
    	tag.setInteger("MiningSpeed", miningSpeed);
    	tag.setInteger("Attack", attack); // optional
    	tag.setFloat("HandleModifier", handleModifier);
    	tag.setInteger("Reinforced", reinforced); // optional
    	
    	if (stonebound != 0.0f)
    	{
    		tag.setFloat("Stonebound", stonebound); // optional, cannot be used if jagged
    	}
    	
    	if (jagged != 0.0f)
    	{
    		tag.setFloat("Jagged", jagged); // optional, cannot be used if stonebound
    	}
    	
    	tag.setString("Style", style.toString()); // optional, color of the material text
    	tag.setInteger("Color", 255 << 24 | colorRed << 16 | colorGreen << 8 | colorBlue); // argb
    	 
    	/* SINCE 1.8.2 - bow and arrow stats
    	 * for bow and arrow stats, best compare to other materials to find good values
    	 */
    	// additional stats for bows
    	tag.setInteger("Bow_DrawSpeed", drawSpeed); // the higher the longer it takes to draw the bow
    	tag.setFloat("Bow_ProjectileSpeed", projectileSpeed); // the higher the faster the projectile goes
    	 
    	// additional stats for arrows
    	tag.setFloat("Projectile_Mass", projectileMass);
    	tag.setFloat("Projectile_Fragility", projectileFragility); // This is a multiplier to the shafts break-chance
    	 
    	FMLInterModComms.sendMessage("TConstruct", "addMaterial", tag);
	}
    
    public static void addMaterialItem(int id, int value, ItemStack itemStack)
    {
    	NBTTagCompound tag = new NBTTagCompound();
    	
    	tag.setInteger("MaterialId", id);
    	tag.setInteger("Value", value);
    	
    	NBTTagCompound item = new NBTTagCompound();
    	
    	itemStack.writeToNBT(item);
    	tag.setTag("Item", item);
    	 
    	FMLInterModComms.sendMessage("TConstruct", "addMaterialItem", tag);
	}
    
    public static void addPartBuilderMaterial(int id, ItemStack itemStack, ItemStack shardStack, int value)
    {
    	NBTTagCompound tag = new NBTTagCompound();
    	
    	tag.setInteger("MaterialId", id);

    	NBTTagCompound item = new NBTTagCompound();
    	
    	itemStack.writeToNBT(item);
    	tag.setTag("Item", item);
    	 
    	item = new NBTTagCompound();
    	
    	shardStack.writeToNBT(item);
    	tag.setTag("Shard", item);
    	 
    	// 1 value = 1 shard. So 1 blocks like stone usually have value 2.
    	tag.setInteger("Value", value);
    	
    	FMLInterModComms.sendMessage("TConstruct", "addPartBuilderMaterial", tag);
	}
}