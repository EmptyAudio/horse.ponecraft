package horse.ponecraft.food.gui;

import horse.ponecraft.food.Food;
import horse.ponecraft.food.nutrition.NutritionManager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import squeek.applecore.AppleCore;
import squeek.applecore.ModConfig;
import squeek.applecore.ModInfo;
import squeek.applecore.api.AppleCoreAPI;
import squeek.applecore.api.food.FoodValues;
import squeek.applecore.asm.Hooks;
import squeek.applecore.helpers.KeyHelper;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.ObfuscationReflectionHelper;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.RenderTickEvent;
import cpw.mods.fml.relauncher.ReflectionHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/* Shamelessly stolen from AppleCore and hacked to bits */
@SideOnly(Side.CLIENT)
public class NutritionTipOverlayHandler
{
	private static ResourceLocation icons = new ResourceLocation(Food.MODID, "textures/gui/icons.png");
	
	public static void init()
	{
		FMLCommonHandler.instance().bus().register(new NutritionTipOverlayHandler());		
	}
	
	public static final Field theSlot = ReflectionHelper.findField(GuiContainer.class, ObfuscationReflectionHelper.remapFieldNames(GuiContainer.class.getName(), "theSlot", "field_147006_u", "u"));
	private static Class<?> tinkersContainerGui = null;
	private static Field mainSlot = null;
	private static Method getStackMouseOver = null;
	private static Field itemPanel = null;
	private static boolean neiLoaded = false;
	private static Class<?> foodJournalGui = null;
	private static Field foodJournalHoveredStack = null;
	static
	{
		try
		{
			neiLoaded = Loader.isModLoaded("NotEnoughItems");
			if (neiLoaded)
			{
				Class<?> LayoutManager = Class.forName("codechicken.nei.LayoutManager");
				itemPanel = LayoutManager.getDeclaredField("itemPanel");
				getStackMouseOver = Class.forName("codechicken.nei.ItemPanel").getDeclaredMethod("getStackMouseOver", int.class, int.class);
			}
		}
		catch (Exception e)
		{
			AppleCore.Log.error("Unable to integrate the food values tooltip overlay with NEI: ");
			e.printStackTrace();
		}

		try
		{
			if (Loader.isModLoaded("TConstruct"))
			{
				tinkersContainerGui = ReflectionHelper.getClass(NutritionTipOverlayHandler.class.getClassLoader(), "tconstruct.client.gui.NewContainerGui");
				mainSlot = ReflectionHelper.findField(tinkersContainerGui, "mainSlot");
			}
		}
		catch (Exception e)
		{
			// NewContainerGui only exists in early 1.7.10 versions of TiC
			// Keeping the reflection but removing the error printing will
			// maintain compatibility with those versions while removing the
			// confusing/misleading errors in logs when using recent versions
		}

		try
		{
			if (Loader.isModLoaded("SpiceOfLife"))
			{
				foodJournalGui = ReflectionHelper.getClass(NutritionTipOverlayHandler.class.getClassLoader(), "squeek.spiceoflife.gui.GuiScreenFoodJournal");
				foodJournalHoveredStack = ReflectionHelper.findField(foodJournalGui, "hoveredStack");
			}
		}
		catch (Exception e)
		{
			AppleCore.Log.error("Unable to integrate the food values tooltip overlay with The Spice of Life: ");
			e.printStackTrace();
		}
	}

	@SubscribeEvent
	public void onRenderTick(RenderTickEvent event)
	{
		if (event.phase != TickEvent.Phase.END)
			return;

		if (ModConfig.SHOW_FOOD_VALUES_IN_TOOLTIP)
		{
			Minecraft mc = Minecraft.getMinecraft();
			EntityPlayer player = mc.thePlayer;
			GuiScreen curScreen = mc.currentScreen;

			ScaledResolution scale = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);

			boolean isTinkersContainerGui = (tinkersContainerGui != null && tinkersContainerGui.isInstance(curScreen));
			boolean isFoodJournalGui = foodJournalGui != null && foodJournalGui.isInstance(curScreen);
			boolean isValidContainerGui = curScreen instanceof GuiContainer || isTinkersContainerGui;
			if (isValidContainerGui && KeyHelper.isShiftKeyDown())
			{
				Gui gui = curScreen;
				int mouseX = Mouse.getX() * scale.getScaledWidth() / mc.displayWidth;
				int mouseY = scale.getScaledHeight() - Mouse.getY() * scale.getScaledHeight() / mc.displayHeight;
				ItemStack hoveredStack = null;

				// get the hovered stack from the active container
				try
				{
					// try regular container
					Slot hoveredSlot = !isTinkersContainerGui ? (Slot) NutritionTipOverlayHandler.theSlot.get(gui) : (Slot) NutritionTipOverlayHandler.mainSlot.get(gui);

					// get the stack
					if (hoveredSlot != null)
						hoveredStack = hoveredSlot.getStack();

					// try NEI
					if (hoveredStack == null && getStackMouseOver != null)
						hoveredStack = (ItemStack) (getStackMouseOver.invoke(itemPanel.get(null), mouseX, mouseY));

					// try FoodJournal
					if (hoveredStack == null && isFoodJournalGui)
						hoveredStack = (ItemStack) foodJournalHoveredStack.get(gui);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}

				// if the hovered stack is a food and there is no item being dragged
				if (player.inventory.getItemStack() == null && hoveredStack != null && AppleCoreAPI.accessor.isFood(hoveredStack))
				{
					boolean needsCoordinateShift = isTinkersContainerGui || (!isTinkersContainerGui && !neiLoaded) || isFoodJournalGui;

					int toolTipBottomY = Hooks.toolTipY + Hooks.toolTipH + 1 + (needsCoordinateShift ? 3 : 0);
					int toolTipRightX = Hooks.toolTipX + Hooks.toolTipW + 1 + (needsCoordinateShift ? 3 : 0);

					int rightX = toolTipRightX - 3;
					int leftX = rightX - 51;
					int topY = Hooks.toolTipY - 11 + (needsCoordinateShift ? -4 : 0);
					int bottomY = topY + 11;

					boolean wasLightingEnabled = GL11.glIsEnabled(GL11.GL_LIGHTING);
					if (wasLightingEnabled)
						GL11.glDisable(GL11.GL_LIGHTING);
					GL11.glDisable(GL11.GL_DEPTH_TEST);

					// bg
					Gui.drawRect(leftX - 1, topY, rightX + 1, bottomY, 0xF0100010);
					Gui.drawRect(leftX, topY - 1, rightX, topY, 0xF0100010);
					Gui.drawRect(leftX, topY, rightX, bottomY, 0x66FFFFFF);

					GL11.glColor4f(1f, 1f, 1f, .25f);

					mc.getTextureManager().bindTexture(icons);

					gui.drawTexturedModalRect(rightX - 10, topY + 1, 36, NutritionManager.hasDairy(hoveredStack) ? 0 : 9, 9, 9);
					gui.drawTexturedModalRect(rightX - 20, topY + 1, 27, NutritionManager.hasVegetable(hoveredStack) ? 0 : 9, 9, 9);
					gui.drawTexturedModalRect(rightX - 30, topY + 1, 18, NutritionManager.hasFruit(hoveredStack) ? 0 : 9, 9, 9);
					gui.drawTexturedModalRect(rightX - 40, topY + 1, 9, NutritionManager.hasGrain(hoveredStack) ? 0 : 9, 9, 9);
					gui.drawTexturedModalRect(rightX - 50, topY + 1, 0, NutritionManager.hasProtein(hoveredStack) ? 0 : 9, 9, 9);
					
					GL11.glEnable(GL11.GL_DEPTH_TEST);
					if (wasLightingEnabled)
						GL11.glEnable(GL11.GL_LIGHTING);
					GL11.glColor4f(1f, 1f, 1f, 1f);
				}
			}
		}
	}	
}
