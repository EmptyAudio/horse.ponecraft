package horse.ponecraft.earth;

import horse.ponecraft.food.NutritionManager;

import java.util.ArrayList;
import java.util.HashSet;

import scala.Console;
import squeek.applecore.api.AppleCoreAPI;
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
import cpw.mods.fml.common.registry.GameRegistry;

public class CommonProxy
{
    public static Item oven;
    
    public static final BlockCooktop cooktop = new BlockCooktop("cooktop");

    public void init(FMLInitializationEvent event)
    {
        oven = (Item)Item.itemRegistry.getObject("harvestcraft:oven");
        
        GameRegistry.registerBlock(cooktop, cooktop.getUnlocalizedName());
        
        GameRegistry.registerTileEntity(TileEntityCooktop.class, "CooktopTileEntity");
        
        GameRegistry.addShapedRecipe(new ItemStack(cooktop), "sss", "coc", "iri",
        		's', new ItemStack(Blocks.stone_slab),
        		'c', new ItemStack(Blocks.chest),
        		'o', new ItemStack(oven),
        		'i', new ItemStack(Blocks.iron_block),
        		'r', new ItemStack(Blocks.redstone_block));
    }
}
