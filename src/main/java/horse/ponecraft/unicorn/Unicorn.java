package horse.ponecraft.unicorn;

import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import scala.Console;
import thaumcraft.api.research.ResearchCategories;

@Mod(modid = Unicorn.MODID, version = Unicorn.VERSION, name = "Ponecraft Unicorn", dependencies = "after:THAUMCRAFT")
public class Unicorn
{
    public static final String MODID = "horse.ponecraft.unicorn";
    public static final String VERSION = "1.0";
    
    @EventHandler
    public void init(FMLPostInitializationEvent event)
    {
    	ResearchCategories.researchCategories.get("ARTIFICE").research.remove("HOVERHARNESS");
    	ResearchCategories.researchCategories.get("ARTIFICE").research.remove("HOVERGIRDLE");
    }   
}