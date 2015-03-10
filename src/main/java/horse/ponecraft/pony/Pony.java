package horse.ponecraft.pony;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.forgeessentials.api.APIRegistry;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import net.minecraftforge.permissions.PermissionsManager.RegisteredPermValue;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import scala.Console;
import squeek.applecore.api.food.FoodEvent;
import tconstruct.library.TConstructRegistry;

@Mod(modid = Pony.MODID, version = Pony.VERSION, name = "Ponecraft Pony", dependencies = "after:harvestcraft")
public class Pony
{
    public static final String MODID = "horse.ponecraft.pony";
    public static final String VERSION = "1.0";
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    	GameRegistry.addRecipe(new ShapedOreRecipe(Items.leather, true, new Object[] { "www", "ccc", "www", 'w', "materialPressedwax", 'c', Blocks.carpet }));  
    }    
}