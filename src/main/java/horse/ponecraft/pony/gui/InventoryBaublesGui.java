package horse.ponecraft.pony.gui;

import horse.ponecraft.pony.Pony;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.InventoryEffectRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import tconstruct.client.tabs.TabRegistry;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.common.items.baubles.ItemAmuletVis;
import thaumcraft.common.items.wands.ItemWandCasting;
import vazkii.botania.api.mana.IManaItem;

public class InventoryBaublesGui extends InventoryEffectRenderer
{
	public static final ResourceLocation background = new ResourceLocation(Pony.MODID, "textures/gui/baubles-gui.png");

	private float xSize_f;
	private float ySize_f;

	public InventoryBaublesGui(EntityPlayer player)
	{
		super(new ContainerBaubles(player));

	}

	@Override
	public void initGui()
	{
		super.initGui();

		int cornerX = guiLeft;
		int cornerY = guiTop;
		this.buttonList.clear();

		TabRegistry.updateTabValues(cornerX, cornerY, InventoryTabBaubles.class);
		TabRegistry.addTabsToList(this.buttonList);
	}

	@Override
	public void drawScreen(int par1, int par2, float par3)
	{
		super.drawScreen(par1, par2, par3);
		this.xSize_f = (float)par1;
		this.ySize_f = (float)par2;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		this.mc.getTextureManager().bindTexture(background);

		int cornerX = guiLeft;
		int cornerY = guiTop;

		drawTexturedModalRect(cornerX, guiTop, 0, 0, xSize, ySize);

		cornerX = this.guiLeft;
		cornerY = this.guiTop;
		drawPlayerOnGui(this.mc, cornerX + 51, cornerY + 75, 30, (float)(cornerX + 51) - this.xSize_f, (float)(cornerY + 75 - 50) - this.ySize_f);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_)
	{
		super.drawGuiContainerForegroundLayer(p_146979_1_, p_146979_2_);
		this.fontRendererObj.drawString(I18n.format("container.baubles.mana", new Object[0]), 101, 15, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.baubles.vis", new Object[0]), 101, 38, 4210752);

		ItemStack neck = this.inventorySlots.getSlot(4).getStack();
		int manaCur = 0;
		int manaMax = 0;
		int visCur[] = new int[6];
		int visMax[] = new int[6];
		Aspect visAspect[] = new Aspect[] { Aspect.AIR, Aspect.EARTH, Aspect.FIRE, Aspect.WATER, Aspect.ORDER, Aspect.ENTROPY };

		for (int x = 0; x < 44; x++)
		{
			ItemStack item = this.inventorySlots.getSlot(x).getStack();

			if (item != null)
			{
				if (item.getItem() instanceof ItemAmuletVis)
				{
					for (int v = 0; v < visAspect.length; v++)
					{
						visCur[v] += ((ItemAmuletVis)item.getItem()).getVis(item, visAspect[v]);
						visMax[v] += ((ItemAmuletVis)item.getItem()).getMaxVis(item);
					}
				}
				else if (item.getItem() instanceof ItemWandCasting)
				{
					for (int v = 0; v < visAspect.length; v++)
					{
						visCur[v] += ((ItemWandCasting)item.getItem()).getVis(item, visAspect[v]);
						visMax[v] += ((ItemWandCasting)item.getItem()).getMaxVis(item);
					}
				}
				else if (item.getItem() instanceof IManaItem)
				{
					manaCur += ((IManaItem)item.getItem()).getMana(item);
					manaMax += ((IManaItem)item.getItem()).getMaxMana(item);
				}
			}
		}

		drawVisLabel(String.format("%d/%d", visCur[0] / 100, visMax[0] / 100), 103, 49, visAspect[0].getColor());
		drawVisLabel(String.format("%d/%d", visCur[1] / 100, visMax[1] / 100), 135, 49, visAspect[1].getColor());
		drawVisLabel(String.format("%d/%d", visCur[2] / 100, visMax[2] / 100), 103, 57, visAspect[2].getColor());
		drawVisLabel(String.format("%d/%d", visCur[3] / 100, visMax[3] / 100), 135, 57, visAspect[3].getColor());
		drawVisLabel(String.format("%d/%d", visCur[4] / 100, visMax[4] / 100), 103, 66, visAspect[4].getColor());
		drawVisLabel(String.format("%d/%d", visCur[5] / 100, visMax[5] / 100), 135, 66, visAspect[5].getColor());

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(background);

		if (manaMax != 0)
		{
			drawTexturedModalRect(102, 25, 176, 0, (int)(((float)manaCur / (float)manaMax) * 66.0f), 8);
		}
	}

	private void drawVisLabel(String vis, int x, int y, int color)
	{
		GL11.glPushMatrix();
		GL11.glScalef(0.75f, 0.75f, 0.75f);
		this.fontRendererObj.drawStringWithShadow(vis, (x + (43 - this.fontRendererObj.getStringWidth(vis)) / 2) * 4 / 3, y * 4 / 3, color);
		GL11.glPopMatrix();
	}

	public static void drawPlayerOnGui(Minecraft par0Minecraft, int par1, int par2, int par3, float par4, float par5)
	{
		GL11.glEnable(GL11.GL_COLOR_MATERIAL);
		GL11.glPushMatrix();
		GL11.glTranslatef((float)par1, (float)par2, 50.0F);
		GL11.glScalef((float)(-par3), (float)par3, (float)par3);
		GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
		float f2 = par0Minecraft.thePlayer.renderYawOffset;
		float f3 = par0Minecraft.thePlayer.rotationYaw;
		float f4 = par0Minecraft.thePlayer.rotationPitch;
		par4 -= 19;
		GL11.glRotatef(135.0F, 0.0F, 1.0F, 0.0F);
		RenderHelper.enableStandardItemLighting();
		GL11.glRotatef(-135.0F, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(-((float)Math.atan((double)(par5 / 40.0F))) * 20.0F, 1.0F, 0.0F, 0.0F);
		par0Minecraft.thePlayer.renderYawOffset = (float)Math.atan((double)(par4 / 40.0F)) * 20.0F;
		par0Minecraft.thePlayer.rotationYaw = (float)Math.atan((double)(par4 / 40.0F)) * 40.0F;
		par0Minecraft.thePlayer.rotationPitch = -((float)Math.atan((double)(par5 / 40.0F))) * 20.0F;
		par0Minecraft.thePlayer.rotationYawHead = par0Minecraft.thePlayer.rotationYaw;
		GL11.glTranslatef(0.0F, par0Minecraft.thePlayer.yOffset, 0.0F);
		RenderManager.instance.playerViewY = 180.0F;
		RenderManager.instance.renderEntityWithPosYaw(par0Minecraft.thePlayer, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F);
		par0Minecraft.thePlayer.renderYawOffset = f2;
		par0Minecraft.thePlayer.rotationYaw = f3;
		par0Minecraft.thePlayer.rotationPitch = f4;
		GL11.glPopMatrix();
		RenderHelper.disableStandardItemLighting();
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
	}
}
