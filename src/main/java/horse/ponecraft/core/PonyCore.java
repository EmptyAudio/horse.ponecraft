package horse.ponecraft.core;

import horse.ponecraft.core.unicorn.AspectDisplayNameTransformer;

import java.util.Map;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;

@IFMLLoadingPlugin.Name(value = "Ponecraft Unicorn Core")
@IFMLLoadingPlugin.MCVersion(value = "1.7.10")
@IFMLLoadingPlugin.TransformerExclusions(value = "horse.ponecraft.core.")
@IFMLLoadingPlugin.SortingIndex(value = 2001)
public class PonyCore implements IFMLLoadingPlugin
{
	@Override
	public String[] getASMTransformerClass()
	{
		return new String[] { AspectDisplayNameTransformer.class.getName() };
	}

	@Override
	public String getModContainerClass()
	{
		return PonyCoreContainer.class.getName();
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
