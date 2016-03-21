package horse.ponecraft.pegasus.items;

import horse.ponecraft.pegasus.Pegasus;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ItemGemShard extends Item
{
	private IIcon shardIcon;
	
	public ItemGemShard(String unlocalizedName)
	{
		setMaxStackSize(64);
		setCreativeTab(CreativeTabs.tabMisc);
		setUnlocalizedName(unlocalizedName);
		setTextureName(Pegasus.MODID + ":" + unlocalizedName);
	}
}
