package horse.ponecraft.food.infuser;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class SkyBeverageEffect extends BeverageEffect
{
	private final PotionEffect goodEffect = new PotionEffect(Potion.jump.id, 2400, 0);
	private final PotionEffect badEffect = new PotionEffect(Potion.moveSlowdown.id, 100, 0);
	private final PotionEffect terribleEffect = new PotionEffect(Potion.moveSlowdown.id, 300, 0);
	
	public SkyBeverageEffect(int id)
	{
		super(id);
	}
	
	@Override
	public void doEffect(World arg0, EntityPlayer arg1)
	{
		arg1.addPotionEffect(goodEffect);
		
		if (arg0.rand.nextInt(5) == 0)
		{
			arg1.addPotionEffect(terribleEffect);
		}
		else
		{
			arg1.addPotionEffect(badEffect);
		}
	}

	@Override
	public String getDescription()
	{
		return "Slow Dive";
	}
}
