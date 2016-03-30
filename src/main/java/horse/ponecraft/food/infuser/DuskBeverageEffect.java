package horse.ponecraft.food.infuser;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class DuskBeverageEffect extends BeverageEffect
{
	private final PotionEffect goodEffect = new PotionEffect(Potion.nightVision.id, 2400, 0);
	private final PotionEffect badEffect = new PotionEffect(Potion.blindness.id, 100, 0);
	private final PotionEffect terribleEffect = new PotionEffect(Potion.blindness.id, 300, 0);
	
	public DuskBeverageEffect(int id)
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
		return "Visible Night";
	}
}
