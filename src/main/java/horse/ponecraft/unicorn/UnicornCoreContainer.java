package horse.ponecraft.unicorn;

import java.util.Arrays;

import com.google.common.eventbus.EventBus;

import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.LoadController;
import cpw.mods.fml.common.ModMetadata;

public class UnicornCoreContainer extends DummyModContainer
{
	public UnicornCoreContainer()
	{
        super(new ModMetadata());

        ModMetadata myMeta = super.getMetadata();
        
        myMeta.authorList = Arrays.asList(new String[] { "EmptyAudio" });
        myMeta.description = "Core mod for renaming Humanus";
        myMeta.modId = "horse.ponecraft.unicorn.core";
        myMeta.version = "1.0";
        myMeta.name = "Ponecraft Unicorn Core";
	}

	@Override
	public boolean registerBus(EventBus bus, LoadController controller)
	{
		bus.register(this);
		
		return true;
	}
}
