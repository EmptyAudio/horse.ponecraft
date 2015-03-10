package horse.ponecraft.unicorn;

import java.util.Map;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;

@IFMLLoadingPlugin.Name(value = "Ponecraft Unicorn Core")
@IFMLLoadingPlugin.MCVersion(value = "1.7.10")
@IFMLLoadingPlugin.TransformerExclusions(value = "horse.")
@IFMLLoadingPlugin.SortingIndex(value = 2001)
public class UnicornCore implements IFMLLoadingPlugin
{
	@Override
	public String[] getASMTransformerClass()
	{
		return new String[] { "horse.ponecraft.unicorn.AspectDisplayNameTransformer" };
	}

	@Override
	public String getModContainerClass()
	{
		return "horse.ponecraft.unicorn.UnicornCoreContainer";
	}

	@Override
	public String getSetupClass()
	{
		return null;
	}

	@Override
	public void injectData(Map<String, Object> data)
	{
	}

	@Override
	public String getAccessTransformerClass()
	{
		return null;
	}
}
