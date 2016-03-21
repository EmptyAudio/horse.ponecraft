package horse.ponecraft.pony.race;

import horse.ponecraft.pony.Pony;
import horse.ponecraft.pony.network.SetRacePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;

public class SetRaceCommand extends CommandBase
{
	@Override
	public String getCommandName()
	{
		return "setrace";
	}

	@Override
	public String getCommandUsage(ICommandSender sender)
	{
		return "setrace <race> [player]";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args)
	{
		switch (args.length)
		{
		case 1:
			setRace(sender, args[0]);
			break;
		case 2:
			setRace(sender, args[1], args[0]);
			break;
		default:
			printUsage(sender);
			break;
		}
	}

	private void printUsage(ICommandSender sender)
	{
		ChatComponentText message = new ChatComponentText("Usage: ");
		ChatStyle style = new ChatStyle();

		style.setColor(EnumChatFormatting.RED);

		message.appendText(getCommandUsage(sender));
		message.setChatStyle(style);

		sender.addChatMessage(message);
	}

	private void setRace(ICommandSender sender, String race)
	{
		setRace(sender, sender.getCommandSenderName(), race);
	}

	private void setRace(ICommandSender sender, String playerName, String raceName)
	{
		try
		{
			EntityPlayerMP player = MinecraftServer.getServer().getConfigurationManager().func_152612_a(playerName);
			PonyRace race = PonyRace.valueOf(raceName.trim().toUpperCase());

			PonyPlayer.get(player).setRace(race);
			Pony.net.sendTo(new SetRacePacket(race), player);
		}
		catch (Throwable t)
		{
			printUsage(sender);
		}
	}
}
