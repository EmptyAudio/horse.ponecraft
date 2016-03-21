package horse.ponecraft.pony.gui;

import horse.ponecraft.pony.Pony;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

public class RaceFlagButton extends GuiButton
{
	public static final int PEGASUS = 0;
	public static final int UNICORN = 1;
	public static final int EARTHPONY = 2;
	public static final int ZEBRA = 3;
	
	private final ResourceLocation raceFlags = new ResourceLocation(Pony.MODID, "textures/gui/raceflags.png");
	private final ResourceLocation raceFlagsSelect = new ResourceLocation(Pony.MODID, "textures/gui/raceflags-select.png");
	private final ResourceLocation raceFlagsHot = new ResourceLocation(Pony.MODID, "textures/gui/raceflags-hot.png");
	private boolean isSelected;
	
	public RaceFlagButton(int id, int x, int y)
	{
		super(id, x, y, 103, 114, "");
	}

	@Override
	public void drawButton(Minecraft minecraft, int mouseX, int mouseY)
	{
        if (this.visible)
        {
            FontRenderer fontrenderer = minecraft.fontRenderer;
            minecraft.getTextureManager().bindTexture(raceFlags);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            
            this.field_146123_n = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
            int hover = this.getHoverState(this.field_146123_n);
            
            this.drawTexturedModalRect(this.xPosition, this.yPosition, (this.id % 2) * 103, (this.id / 2) * 114, this.width, this.height);

            if (this.isSelected)
            {
                minecraft.getTextureManager().bindTexture(raceFlagsSelect);
                this.drawTexturedModalRect(this.xPosition, this.yPosition, (this.id % 2) * 103, (this.id / 2) * 114, this.width, this.height);            	
            }
            
            if (hover == 2)
            {
                minecraft.getTextureManager().bindTexture(raceFlagsHot);
                this.drawTexturedModalRect(this.xPosition, this.yPosition, (this.id % 2) * 103, (this.id / 2) * 114, this.width, this.height);            	
            }
            
            this.mouseDragged(minecraft, mouseX, mouseY);
        }
	}
	
	public void setSelected(boolean selected)
	{
		this.isSelected = selected;
	}
}
