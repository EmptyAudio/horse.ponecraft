package horse.ponecraft.pegasus;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import scala.Console;
import tconstruct.library.ActiveToolMod;
import tconstruct.library.tools.AbilityHelper;
import tconstruct.library.tools.ToolCore;

public class PegasusToolMod extends ActiveToolMod
{
	@Override
	public int attackDamage(int modDamage, int damage, ToolCore tool, NBTTagCompound tags, NBTTagCompound toolTags, ItemStack stack, EntityLivingBase player, Entity entity)
	{
		applyPeridotStun(toolTags, entity);
		
		int rubyCount = 0;
		
		rubyCount += toolTags.getInteger("Head") == Pegasus.RubyMaterial ? 1 : 0;
		rubyCount += toolTags.getInteger("Handle") == Pegasus.RubyMaterial ? 1 : 0;
		rubyCount += toolTags.getInteger("Accessory") == Pegasus.RubyMaterial ? 1 : 0;
		rubyCount += toolTags.getInteger("Extra") == Pegasus.RubyMaterial ? 1 : 0;
		
		int origDamage = toolTags.getInteger("Damage");
        int maxDamage = toolTags.getInteger("TotalDurability");
        float multiplier = (1.0f - ((float)origDamage / (float)maxDamage)) * 0.5f;
        
        Console.out().println(damage);
        Console.out().println(multiplier);
        
        if (rubyCount > 0)
        {
			int result = Math.round(damage * multiplier) + modDamage;
	        Console.out().println(result);
	        
	        return result;
		}
        
		return modDamage;
	}

	private void applyPeridotStun(NBTTagCompound toolTags, Entity entity)
	{
		int peridotCount = 0;
		
		peridotCount += toolTags.getInteger("Head") == Pegasus.PeridotMaterial ? 1 : 0;
		peridotCount += toolTags.getInteger("Handle") == Pegasus.PeridotMaterial ? 1 : 0;
		peridotCount += toolTags.getInteger("Accessory") == Pegasus.PeridotMaterial ? 1 : 0;
		peridotCount += toolTags.getInteger("Extra") == Pegasus.PeridotMaterial ? 1 : 0;
		
		int origDamage = toolTags.getInteger("Damage");
        int maxDamage = toolTags.getInteger("TotalDurability");
                
		if (peridotCount > 0 && entity instanceof EntityLivingBase && entity.worldObj.rand.nextInt(maxDamage) >= origDamage)
		{
			EntityLivingBase living = (EntityLivingBase)entity;
			
			living.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 40, 10));
			
			if (living instanceof EntityPlayer)
			{
				living.addPotionEffect(new PotionEffect(Potion.weakness.id, 40, 10));
			}
			else
			{
				//living.addPotionEffect(new PotionEffect(Pegasus.pacify.id, 40, 10));
			}
		}
	}

	@Override
	public boolean damageTool(ItemStack stack, int damage, EntityLivingBase entity)
	{
		if (damage <= 0)
		{
			return false;
		}
		
		NBTTagCompound tags = stack.getTagCompound().getCompoundTag("InfiTool");
		int gemCount = 0;
		
		gemCount += isGemMaterial(tags.getInteger("Head")) ? 1 : 0;
		gemCount += isGemMaterial(tags.getInteger("Handle")) ? 1 : 0;
		gemCount += isGemMaterial(tags.getInteger("Accessory")) ? 1 : 0;
		gemCount += isGemMaterial(tags.getInteger("Extra")) ? 1 : 0;
		
		for (int chance = 0; chance < gemCount; chance++)
		{
			if (entity.worldObj.rand.nextInt(2) == 0)
			{
				damage++;
				
				if (entity.worldObj.rand.nextInt(4) == 0)
				{
					damage++;
				}
			}
		}
		
		int origDamage = tags.getInteger("Damage");
        int damageTrue = origDamage + damage;
        int maxDamage = tags.getInteger("TotalDurability");
        
        if (damageTrue <= 0)
        {
            tags.setInteger("Damage", 0);
            tags.setBoolean("Broken", false);
        }
        else if (damageTrue > maxDamage)
        {
            AbilityHelper.breakTool(stack, stack.getTagCompound(), entity);
        }
        else
        {
            tags.setInteger("Damage", damageTrue);
        }
        
        return true;
	}
	
	private boolean isGemMaterial(int materialId)
	{
		return materialId == Pegasus.RubyMaterial || materialId == Pegasus.SapphireMaterial || materialId == Pegasus.PeridotMaterial;
	}
}
