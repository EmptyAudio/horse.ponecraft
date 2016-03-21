package horse.ponecraft.earth;

import horse.ponecraft.earth.gui.GuiHandler;
import horse.ponecraft.earth.tiles.TileEntityCooktop;
import horse.ponecraft.food.nutrition.NutritionManager;

import java.util.ArrayList;
import java.util.HashSet;

import scala.Console;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

public class CommonProxy
{
    public static Item oven;
    
    public void init(FMLInitializationEvent event)
    {
        oven = (Item)Item.itemRegistry.getObject("harvestcraft:oven");
        
		NetworkRegistry.INSTANCE.registerGuiHandler(Earth.instance, new GuiHandler());

		GameRegistry.registerBlock(Earth.cooktop, "cooktop");
        System.out.println("Registered cooktop");
        
        GameRegistry.registerTileEntity(TileEntityCooktop.class, "CooktopTileEntity");
        
        GameRegistry.addShapedRecipe(new ItemStack(Earth.cooktop), "sss", "coc", "iri",
        		's', new ItemStack(Blocks.stone_slab),
        		'c', new ItemStack(Blocks.chest),
        		'o', new ItemStack(oven),
        		'i', new ItemStack(Blocks.iron_block),
        		'r', new ItemStack(Blocks.redstone_block));
    }
}
