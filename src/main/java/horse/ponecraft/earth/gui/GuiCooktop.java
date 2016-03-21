package horse.ponecraft.earth.gui;

import horse.ponecraft.earth.Earth;
import horse.ponecraft.earth.tiles.ContainerCooktop;
import horse.ponecraft.earth.tiles.TileEntityCooktop;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

public class GuiCooktop extends GuiContainer
{
	private final ResourceLocation guiBackground = new ResourceLocation(Earth.MODID, "textures/gui/cooktop.png");

	public GuiCooktop(InventoryPlayer playerInventory, TileEntityCooktop tileEntity)
	{
		super(new ContainerCooktop(playerInventory, tileEntity));

		this.xSize = 176;
		this.ySize = 244;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
	{
		this.mc.renderEngine.bindTexture(this.guiBackground);

		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;

		this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
	}
}
