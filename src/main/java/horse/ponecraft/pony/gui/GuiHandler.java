package horse.ponecraft.pony.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler
{
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		switch (ID)
		{
		case 0:
			System.out.println("no server element for race selection");
			return null;
		case 1:
			System.out.println("server");
			return new ContainerBaubles(player);
		}
		
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		switch (ID)
		{
		case 0:
			System.out.println("client race selection");
			return new RaceSelectionScreen();
		case 1:
			System.out.println("client");
			return new InventoryBaublesGui(player);
		}

		return null;
	}
}
