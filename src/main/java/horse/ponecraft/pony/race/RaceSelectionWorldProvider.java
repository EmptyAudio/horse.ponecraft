package horse.ponecraft.pony.race;

import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldType;
import net.minecraft.world.chunk.IChunkProvider;

public class RaceSelectionWorldProvider extends WorldProvider
{
	public RaceSelectionWorldProvider()
	{
		this.terrainType = WorldType.FLAT;
	}

	@Override
	protected void registerWorldChunkManager()
	{
		this.worldChunkMgr = WorldType.FLAT.getChunkManager(this.worldObj);
		this.setAllowedSpawnTypes(false, false);
	}

	@Override
	public void setAllowedSpawnTypes(boolean allowHostile, boolean allowPeaceful)
	{
		super.setAllowedSpawnTypes(false, false);
	}

	@Override
	public IChunkProvider createChunkGenerator()
	{
		return WorldType.FLAT.getChunkGenerator(this.worldObj, "2;7,3x1,52x24;2;");
	}

	@Override
	public String getWelcomeMessage()
	{
		return "Loading race selection...";
	}

	@Override
	public String getDepartMessage()
	{
		return "Welcome to Ponecraft!";
	}

	@Override
	public String getDimensionName()
	{
		return "Race Selection Dimension";
	}
}
