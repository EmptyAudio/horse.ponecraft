package horse.ponecraft.pegasus;

import java.util.ArrayList;
import java.util.HashMap;

import com.forgeessentials.api.APIRegistry;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import net.minecraftforge.permissions.PermissionsManager.RegisteredPermValue;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import scala.Console;
import squeek.applecore.api.food.FoodEvent;
import tconstruct.library.TConstructRegistry;

@Mod(modid = Pegasus.MODID, version = Pegasus.VERSION, name = "Ponecraft Pegasus", dependencies = "after:ProjRed|Core")
public class Pegasus
{
    public static final String MODID = "horse.ponecraft.pegasus";
    public static final String VERSION = "1.0";
    
    public static final String FlightPermissionName = "horse.ponecraft.pegasus.flight";
    
    public static final ItemGemShard rubyShard = new ItemGemShard("rubyShard");
    public static final ItemGemShard sapphireShard = new ItemGemShard("sapphireShard");
    public static final ItemGemShard peridotShard = new ItemGemShard("peridotShard");
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        MinecraftForge.EVENT_BUS.register(new PegasusEvents());
        
        TConstructRegistry.activeModifiers.add(new PegasusToolMod());
        
        APIRegistry.perms.registerPermission(FlightPermissionName, RegisteredPermValue.FALSE, "Allows pegasus-style flight when granted.");
        
        TConstructIMC.addMaterial(1001, "Ruby", 2, 400, 800, 1, 0.8f, 0, 0, 0, EnumChatFormatting.RED, 180, 72, 72, 1, 1, 1, 1);
        TConstructIMC.addMaterial(1002, "Sapphire", 2, 400, 800, 1, 0.8f, 0, 0, 0, EnumChatFormatting.BLUE, 72, 112, 208, 1, 1, 1, 1);
        TConstructIMC.addMaterial(1003, "Peridot", 2, 400, 800, 1, 0.8f, 0, 0, 0, EnumChatFormatting.GREEN, 88, 164, 44, 1, 1, 1, 1);
        
        ItemStack rubyGem = new ItemStack((Item)Item.itemRegistry.getObject("ProjRed|Core:projectred.core.part"), 1, 37);
        ItemStack sapphireGem = new ItemStack((Item)Item.itemRegistry.getObject("ProjRed|Core:projectred.core.part"), 1, 38);
        ItemStack peridotGem = new ItemStack((Item)Item.itemRegistry.getObject("ProjRed|Core:projectred.core.part"), 1, 39);
    
        GameRegistry.registerItem(rubyShard, "rubyShard");
        GameRegistry.registerItem(sapphireShard, "sapphireShard");
        GameRegistry.registerItem(peridotShard, "peridotShard");
        
        TConstructIMC.addMaterialItem(1001,	1, rubyGem);
        TConstructIMC.addMaterialItem(1002,	1, sapphireGem);
        TConstructIMC.addMaterialItem(1003,	1, peridotGem);
        
        TConstructIMC.addPartBuilderMaterial(1001, rubyGem, new ItemStack(rubyShard), 2);
        TConstructIMC.addPartBuilderMaterial(1002, sapphireGem, new ItemStack(sapphireShard), 2);
        TConstructIMC.addPartBuilderMaterial(1003, peridotGem, new ItemStack(peridotShard), 2);
    }    
}