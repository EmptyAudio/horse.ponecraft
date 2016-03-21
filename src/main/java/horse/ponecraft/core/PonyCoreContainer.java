package horse.ponecraft.core;

import java.util.Arrays;

import com.google.common.eventbus.EventBus;

import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.LoadController;
import cpw.mods.fml.common.ModMetadata;

public class PonyCoreContainer extends DummyModContainer
{
	public PonyCoreContainer()
	{
		super(new ModMetadata());

		ModMetadata myMeta = super.getMetadata();

		myMeta.authorList = Arrays.asList(new String[] { "EmptyAudio" });
		myMeta.description = "Core mod for ASM tweaks";
		myMeta.modId = "horse.ponecraft.core";
		myMeta.version = "1.0";
		myMeta.name = "Ponecraft Core";
	}

	@Override
	public boolean registerBus(EventBus bus, LoadController controller)
	{
		bus.register(this);

		return true;
	}
}
