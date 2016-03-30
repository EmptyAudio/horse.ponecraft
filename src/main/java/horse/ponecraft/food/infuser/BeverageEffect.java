package horse.ponecraft.food.infuser;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import forestry.api.food.BeverageManager;
import forestry.api.food.IBeverageEffect;

public abstract class BeverageEffect implements IBeverageEffect
{
	public static final BeverageEffect blightEffect = new BlightBeverageEffect(25);
	public static final BeverageEffect duskEffect = new DuskBeverageEffect(26);
	public static final BeverageEffect skyEffect = new SkyBeverageEffect(27);
	public static final BeverageEffect stingEffect = new StingBeverageEffect(28);
	
	private int id;
	
	public BeverageEffect(int id)
	{
		this.id = id;
		
		BeverageManager.effectList[id] = this;
	}
	
	@Override
	public int getId()
	{
		return this.id;
	}
}