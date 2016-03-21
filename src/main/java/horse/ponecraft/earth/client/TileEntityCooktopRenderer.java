package horse.ponecraft.earth.client;

import horse.ponecraft.earth.Earth;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

public class TileEntityCooktopRenderer extends TileEntitySpecialRenderer
{
	private static ResourceLocation modelTexture = new ResourceLocation(Earth.MODID, "textures/blocks/cooktop.png");
	private CooktopModel model = new CooktopModel();

	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float scale)
	{
		GL11.glPushMatrix();
		GL11.glTranslatef((float)x + 0.5F, (float)y + 1.5F, (float)z + 0.5F);

		bindTexture(modelTexture);

		GL11.glPushMatrix();
		GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
		GL11.glScalef(0.95F, 1.0F, 0.95F);

		this.model.render(null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
		
		GL11.glPopMatrix();
		GL11.glPopMatrix();
	}
}
