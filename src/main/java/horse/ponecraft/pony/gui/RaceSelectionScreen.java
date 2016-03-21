package horse.ponecraft.pony.gui;

import horse.ponecraft.pony.Pony;
import horse.ponecraft.pony.network.SetRacePacket;
import horse.ponecraft.pony.race.PonyPlayer;
import horse.ponecraft.pony.race.PonyRace;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

public class RaceSelectionScreen extends GuiScreen
{
	private final static int DISCONNECT = 100;
	private final static int PLAY = 101;

	private final double backgroundAspectRatio = 538.0d / 303.0d;
	private final double backgroundAspectRatioInverse = 303.0d / 538.0d;
	private final ResourceLocation noneBackground = new ResourceLocation(Pony.MODID, "textures/gui/none-bg.png");
	private final ResourceLocation earthPonyBackground = new ResourceLocation(Pony.MODID, "textures/gui/earthpony-bg.png");
	private final ResourceLocation pegasusBackground = new ResourceLocation(Pony.MODID, "textures/gui/pegasus-bg.png");
	private final ResourceLocation unicornBackground = new ResourceLocation(Pony.MODID, "textures/gui/unicorn-bg.png");
	private final ResourceLocation zebraBackground = new ResourceLocation(Pony.MODID, "textures/gui/zebra-bg.png");
	private final ResourceLocation descriptionScroll = new ResourceLocation(Pony.MODID, "textures/gui/scroll.png");

	private GuiButton playButton;
	private int raceSelected = -1;

	@Override
	public void initGui()
	{
		super.initGui();

		this.buttonList.add(new RaceFlagButton(RaceFlagButton.EARTHPONY, (this.width - 400) / 2, (this.height - 256) / 2 + 12));
		this.buttonList.add(new RaceFlagButton(RaceFlagButton.PEGASUS, (this.width - 400) / 2 + 107, (this.height - 256) / 2 + 12));
		this.buttonList.add(new RaceFlagButton(RaceFlagButton.UNICORN, (this.width - 400) / 2, (this.height - 256) / 2 + 130));
		this.buttonList.add(new RaceFlagButton(RaceFlagButton.ZEBRA, (this.width - 400) / 2 + 107, (this.height - 256) / 2 + 130));

		playButton = new GuiButton(PLAY, (this.width - 400) / 2 + 238, (this.height - 256) / 2 + 169, 139, 20, I18n.format("selectServer.select", new Object[0]));

		playButton.enabled = false;

		this.buttonList.add(playButton);
		this.buttonList.add(new GuiButton(DISCONNECT, (this.width - 400) / 2 + 238, (this.height - 256) / 2 + 191, 139, 20, I18n.format("menu.disconnect", new Object[0])));
	}

	@Override
	public void drawScreen(int par1, int par2, float par3)
	{
		double viewRatio = (double)this.width / (double)this.height;

		ResourceLocation background = this.noneBackground;
		String title = I18n.format("raceselection.none.title", new Object[0]);
		String description = I18n.format("raceselection.none.description", new Object[0]);

		switch (this.raceSelected)
		{
		case RaceFlagButton.EARTHPONY:
			background = this.earthPonyBackground;
			title = I18n.format("raceselection.earthpony.title", new Object[0]);
			description = I18n.format("raceselection.earthpony.description", new Object[0]);
			break;
		case RaceFlagButton.PEGASUS:
			background = this.pegasusBackground;
			title = I18n.format("raceselection.pegasus.title", new Object[0]);
			description = I18n.format("raceselection.pegasus.description", new Object[0]);
			break;
		case RaceFlagButton.UNICORN:
			background = this.unicornBackground;
			title = I18n.format("raceselection.unicorn.title", new Object[0]);
			description = I18n.format("raceselection.unicorn.description", new Object[0]);
			break;
		case RaceFlagButton.ZEBRA:
			background = this.zebraBackground;
			title = I18n.format("raceselection.zebra.title", new Object[0]);
			description = I18n.format("raceselection.zebra.description", new Object[0]);
			break;
		}
		
		if (viewRatio > backgroundAspectRatio)
		{
			int backgroundHeight = (int)(backgroundAspectRatioInverse * this.width) + 1;

			this.drawTexturedRectFull(background, 0, this.height - backgroundHeight, this.width, backgroundHeight);
		}
		else
		{
			int backgroundWidth = (int)(backgroundAspectRatio * this.height) + 1;
			int backgroundHeight = (int)(backgroundAspectRatioInverse * backgroundWidth) + 1;

			this.drawTexturedRectFull(background, (this.width - backgroundWidth) / 2, 0, backgroundWidth, backgroundHeight);
		}

		this.mc.renderEngine.bindTexture(this.descriptionScroll);
		this.drawTexturedModalRect((this.width - 400) / 2 + 216, (this.height - 256) / 2, 0, 0, 184, 256);
		
		title = EnumChatFormatting.BOLD.toString() + EnumChatFormatting.UNDERLINE.toString() + title;
		
		this.fontRendererObj.drawString(title, (this.width - 400) / 2 + 238 + (139 - this.fontRendererObj.getStringWidth(title)) / 2, (this.height - 256) / 2 + 47, 0x5B4816);
		this.fontRendererObj.drawSplitString(description, (this.width - 400) / 2 + 238, (this.height - 256) / 2 + 63, 139, 0x5B4816);
		
		super.drawScreen(par1, par2, par3);
	}

	@Override
	protected void keyTyped(char character, int p_73869_2_)
	{
		// super.keyTyped(character, p_73869_2_);
	}

	@Override
	public boolean doesGuiPauseGame()
	{
		return true;
	}

	@Override
	protected void actionPerformed(GuiButton button)
	{
		if (button instanceof RaceFlagButton)
		{
			RaceFlagButton flag = (RaceFlagButton)button;

			this.raceSelected = flag.id;
			this.playButton.enabled = true;

			for (Object other : this.buttonList)
			{
				if (other != flag && other instanceof RaceFlagButton)
				{
					((RaceFlagButton)other).setSelected(false);
				}
			}

			flag.setSelected(true);
		}
		else
		{
			switch (button.id)
			{
			case DISCONNECT:
				disconnect(button);
				break;
			case PLAY:
				play();
				break;
			}
		}
	}

	private void play()
	{
		PonyRace race = PonyRace.NONE;

		switch (this.raceSelected)
		{
		case RaceFlagButton.EARTHPONY:
			race = PonyRace.EARTHPONY;
			break;
		case RaceFlagButton.PEGASUS:
			race = PonyRace.PEGASUS;
			break;
		case RaceFlagButton.UNICORN:
			race = PonyRace.UNICORN;
			break;
		case RaceFlagButton.ZEBRA:
			race = PonyRace.ZEBRA;
			break;
		}

		PonyPlayer.get(Minecraft.getMinecraft().thePlayer).setRace(race);

		Pony.net.sendToServer(new SetRacePacket(race));
		
        this.mc.displayGuiScreen((GuiScreen)null);
        this.mc.setIngameFocus();
	}

	private void disconnect(GuiButton button)
	{
		button.enabled = false;
		this.mc.theWorld.sendQuittingDisconnectingPacket();
		this.mc.loadWorld((WorldClient)null);
		this.mc.displayGuiScreen(new GuiMainMenu());
	}

	private void drawTexturedRectFull(ResourceLocation texture, int x, int y, int width, int height)
	{
		Tessellator tessellator = Tessellator.instance;

		this.mc.renderEngine.bindTexture(texture);

		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(x, y + height, this.zLevel, 0.0, 1.0);
		tessellator.addVertexWithUV(x + width, y + height, this.zLevel, 1.0, 1.0);
		tessellator.addVertexWithUV(x + width, y, this.zLevel, 1.0, 0.0);
		tessellator.addVertexWithUV(x, y, this.zLevel, 0.0, 0.0);
		tessellator.draw();
	}

	private void disconnectOrQuit()
	{
		this.mc.theWorld.sendQuittingDisconnectingPacket();
		this.mc.loadWorld((WorldClient)null);
		this.mc.displayGuiScreen(new GuiMainMenu());
	}
}
