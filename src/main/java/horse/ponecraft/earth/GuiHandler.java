package horse.ponecraft.earth;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler
{
	public static final int CooktopGui = 0;
	public static final int CookbookGui = 1;
	
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		switch (ID)
		{
			case CooktopGui:
			{
				TileEntity tileEntity = world.getTileEntity(x, y, z);
				
				if (tileEntity instanceof TileEntityCooktop)
				{
					return new ContainerCooktop(player.inventory, (TileEntityCooktop)tileEntity);
				}
			}
			case CookbookGui:
			{
				return new ContainerCookbook(player.inventory);
			}
		}
		
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		
		if (tileEntity instanceof TileEntityCooktop)
		{
			return new GuiCooktop(player.inventory, (TileEntityCooktop)tileEntity);
		}
		
		return null;
	}
}
