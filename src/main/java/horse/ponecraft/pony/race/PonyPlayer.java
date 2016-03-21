package horse.ponecraft.pony.race;

import horse.ponecraft.pony.Pony;
import horse.ponecraft.pony.network.PonyRaceSyncPacket;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;

public class PonyPlayer implements IExtendedEntityProperties
{
	private PonyRace race;
	public final EntityPlayer player;
	
	public PonyPlayer(EntityPlayer player)
	{		
		this.race = PonyRace.NONE;
		this.player = player;
	}
	
	public static final void register(EntityPlayer player)
	{
		if (player.getExtendedProperties("PonyPlayer") == null)
		{
			player.registerExtendedProperties("PonyPlayer", new PonyPlayer(player));
		}
	}
	
	public static final PonyPlayer get(EntityPlayer player)
	{
		return (PonyPlayer)player.getExtendedProperties("PonyPlayer");
	}
	
	public PonyRace getRace()
	{
		return this.race;		
	}
	
	public void setRace(PonyRace race)
	{
		this.race = race;
	}

	public void requestSync()
	{
		Pony.net.sendToServer(new PonyRaceSyncPacket());
	}
	
	public void sync()
	{
		Pony.net.sendToAllAround(new PonyRaceSyncPacket(this), new TargetPoint(this.player.dimension, this.player.posX, this.player.posY, this.player.posZ, 128));
	}
	
	@Override
	public void saveNBTData(NBTTagCompound compound)
	{
		NBTTagCompound props = new NBTTagCompound();
		
		props.setString("Race", race.toString());
		
		compound.setTag("PonyPlayer", props);
	}

	@Override
	public void loadNBTData(NBTTagCompound compound)
	{
		NBTTagCompound props = compound.getCompoundTag("PonyPlayer");
		
		this.race = PonyRace.valueOf(props.getString("Race"));
	}

	@Override
	public void init(Entity entity, World world)
	{
	}
}
