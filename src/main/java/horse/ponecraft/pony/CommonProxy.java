package horse.ponecraft.pony;

import horse.ponecraft.pony.gui.GuiHandler;
import horse.ponecraft.pony.herbivore.HerbivoreEvents;
import horse.ponecraft.pony.network.HotbarBlockedPacket;
import horse.ponecraft.pony.network.PonyRaceSyncPacket;
import horse.ponecraft.pony.network.SetRacePacket;
import horse.ponecraft.pony.network.ShowBaublesGuiPacket;
import horse.ponecraft.pony.network.ShowRaceSelectionPacket;
import horse.ponecraft.pony.race.PonyRace;
import horse.ponecraft.pony.race.PonyRaceEvents;
import horse.ponecraft.pony.race.RaceSelectionWorldProvider;

import java.util.HashSet;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.oredict.ShapedOreRecipe;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;

public class CommonProxy
{
	private Configuration config;

	public final Set<Item> meatItems = new HashSet<Item>();

	public void preInit(FMLPreInitializationEvent event)
	{
		Pony.net = NetworkRegistry.INSTANCE.newSimpleChannel(Pony.MODID);

		NetworkRegistry.INSTANCE.registerGuiHandler(Pony.instance, new GuiHandler());

		int packetId = 0;

		Pony.net.registerMessage(SetRacePacket.Handler.class, SetRacePacket.class, packetId++, Side.CLIENT);
		Pony.net.registerMessage(SetRacePacket.Handler.class, SetRacePacket.class, packetId++, Side.SERVER);
		Pony.net.registerMessage(ShowRaceSelectionPacket.Handler.class, ShowRaceSelectionPacket.class, packetId++, Side.CLIENT);
		Pony.net.registerMessage(ShowBaublesGuiPacket.Handler.class, ShowBaublesGuiPacket.class, packetId++, Side.SERVER);
		Pony.net.registerMessage(HotbarBlockedPacket.Handler.class, HotbarBlockedPacket.class, packetId++, Side.CLIENT);
		Pony.net.registerMessage(PonyRaceSyncPacket.Handler.class, PonyRaceSyncPacket.class, packetId++, Side.CLIENT);
		Pony.net.registerMessage(PonyRaceSyncPacket.Handler.class, PonyRaceSyncPacket.class, packetId++, Side.SERVER);

		config = new Configuration(event.getSuggestedConfigurationFile());

		config.load();
	}

	private void registerMeat(String[] itemNames)
	{
		for (String itemName : itemNames)
		{
			Item item = (Item)Item.itemRegistry.getObject(itemName);

			if (item != null)
			{
				meatItems.add(item);
			}
			else
			{
				System.out.println("Unable to find [" + itemName + "], ignoring...");
			}
		}
	}

	private void blockHotbarItems(String[] itemNames, String unlocalizedReason, PonyRace... races)
	{
		for (String itemName : itemNames)
		{
			ItemStack stack = null;

			if (itemName.contains("#"))
			{
				Item item = (Item)Item.itemRegistry.getObject(itemName.substring(0, itemName.indexOf('#')));

				if (item == null)
				{
					Block block = (Block)Block.blockRegistry.getObject(itemName.substring(0, itemName.indexOf('#')));

					if (block != null)
					{
						item = Item.getItemFromBlock(block);
					}
				}

				if (item != null)
				{
					stack = new ItemStack(item, 1, Integer.parseInt(itemName.substring(itemName.indexOf('#') + 1)));
				}
			}
			else
			{
				Item item = (Item)Item.itemRegistry.getObject(itemName);

				if (item == null)
				{
					Block block = (Block)Block.blockRegistry.getObject(itemName);

					if (block != null)
					{
						item = Item.getItemFromBlock(block);
					}

				}

				if (item != null)
				{
					stack = new ItemStack(item);
				}
			}

			if (stack != null)
			{
				for (PonyRace race : races)
				{
					race.banHotbarItem(stack, unlocalizedReason);
				}
			}
			else
			{
				System.out.println("Unable to find [" + itemName + "], ignoring...");
			}
		}
	}

	private void blockBlockInteract(String[] blockNames, String unlocalizedReason, PonyRace... races)
	{
		for (String blockName : blockNames)
		{
			Block block = null;
			Integer metadata = null;

			if (blockName.contains("#"))
			{
				block = (Block)Block.blockRegistry.getObject(blockName.substring(0, blockName.indexOf('#')));
				metadata = Integer.parseInt(blockName.substring(blockName.indexOf('#') + 1));
			}
			else
			{
				block = (Block)Block.blockRegistry.getObject(blockName);
			}

			if (block != null && block != Blocks.air)
			{
				for (PonyRace race : races)
				{
					if (metadata == null)
					{
						race.banBlockInteract(block, unlocalizedReason);
					}
					else
					{
						race.banBlockInteract(block, metadata, unlocalizedReason);
					}
				}
			}
			else
			{
				System.out.println("Unable to find [" + blockName + "], ignoring...");
			}
		}
	}

	public void init(FMLInitializationEvent event)
	{
		GameRegistry.addRecipe(new ShapedOreRecipe(Items.leather, true, new Object[] { "www", "ccc", "www", 'w', "itemBeeswax", 'c', Blocks.carpet }));

		Pony.raceSelectionDimensionId = DimensionManager.getNextFreeDimId();

		DimensionManager.registerProviderType(Pony.raceSelectionDimensionId, RaceSelectionWorldProvider.class, false);
		DimensionManager.registerDimension(Pony.raceSelectionDimensionId, Pony.raceSelectionDimensionId);

		PonyRaceEvents events = new PonyRaceEvents();

		MinecraftForge.EVENT_BUS.register(events);
		MinecraftForge.EVENT_BUS.register(new HerbivoreEvents());

		FMLCommonHandler.instance().bus().register(events);

		registerMeat(config.getStringList("meat-items", "herbivore", new String[0], null));

		blockHotbarItems(config.getStringList("earthpony-only", "hotbar", new String[0], null), "race.hotbar.only.earthpony", PonyRace.PEGASUS, PonyRace.UNICORN, PonyRace.ZEBRA);
		blockHotbarItems(config.getStringList("pegasus-only", "hotbar", new String[0], null), "race.hotbar.only.pegasus", PonyRace.EARTHPONY, PonyRace.UNICORN, PonyRace.ZEBRA);
		blockHotbarItems(config.getStringList("unicorn-only", "hotbar", new String[0], null), "race.hotbar.only.unicorn", PonyRace.EARTHPONY, PonyRace.PEGASUS, PonyRace.ZEBRA);
		blockHotbarItems(config.getStringList("zebra-only", "hotbar", new String[0], null), "race.hotbar.only.zebra", PonyRace.EARTHPONY, PonyRace.PEGASUS, PonyRace.UNICORN);

		blockBlockInteract(config.getStringList("earthpony-only", "blocks", new String[0], null), "race.blocks.only.earthpony", PonyRace.PEGASUS, PonyRace.UNICORN, PonyRace.ZEBRA);
		blockBlockInteract(config.getStringList("pegasus-only", "blocks", new String[0], null), "race.blocks.only.pegasus", PonyRace.EARTHPONY, PonyRace.UNICORN, PonyRace.ZEBRA);
		blockBlockInteract(config.getStringList("unicorn-only", "blocks", new String[0], null), "race.blocks.only.unicorn", PonyRace.EARTHPONY, PonyRace.PEGASUS, PonyRace.ZEBRA);
		blockBlockInteract(config.getStringList("zebra-only", "blocks", new String[0], null), "race.blocks.only.zebra", PonyRace.EARTHPONY, PonyRace.PEGASUS, PonyRace.UNICORN);

		config.save();
	}
}
