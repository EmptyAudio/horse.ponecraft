package horse.ponecraft.unicorn;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.research.ResearchCategories;
import thaumcraft.api.research.ResearchItem;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;

@Mod(modid = Unicorn.MODID, version = Unicorn.VERSION, name = "Ponecraft Unicorn", dependencies = "after:THAUMCRAFT")
public class Unicorn
{
	public static final String MODID = "horse.ponecraft.unicorn";
	public static final String VERSION = "1.0";

	private static ResourceLocation BotaniaTabBackground = new ResourceLocation(MODID, "textures/gui/gui_botaniaresearchback.png");
	private static ResourceLocation BotaniaTabIcon = new ResourceLocation(MODID, "textures/gui/gui_botaniatab.png");

	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		ResearchCategories.researchCategories.get("ARTIFICE").research.remove("HOVERHARNESS");
		ResearchCategories.researchCategories.get("ARTIFICE").research.remove("HOVERGIRDLE");

		ResearchCategories.registerCategory("BOTANIA", BotaniaTabIcon, BotaniaTabBackground);

		ThaumcraftApi.registerObjectTag(stackFromBlock("Botania:pool", 0), new AspectList().add(Aspect.EARTH, 5).add(Aspect.LIFE, 5));
		ThaumcraftApi.registerObjectTag(stackFromBlock("Botania:spreader", 0), new AspectList().add(Aspect.TREE, 5).add(Aspect.LIFE, 5).add(Aspect.METAL, 1));
		ThaumcraftApi.registerObjectTag(stackFromItem("Botania:manaResource", 0), new AspectList().add(Aspect.METAL, 4).add(Aspect.MAGIC, 1));
		ThaumcraftApi.registerObjectTag(stackFromItem("Botania:manaResource", 4), new AspectList().add(Aspect.METAL, 4).add(Aspect.MAGIC, 16));
		ThaumcraftApi.registerObjectTag(stackFromItem("Botania:manaResource", 17), new AspectList().add(Aspect.METAL, 1));
		ThaumcraftApi.registerObjectTag(stackFromItem("Botania:manaResource", 18), new AspectList().add(Aspect.METAL, 1));
		
		new ResearchItem("mana", "BOTANIA", new AspectList(), 2, 3, 1, stackFromBlock("Botania:pool", 1))
		.setItemTriggers(stackFromBlock("Botania:pool", 0))
		.setEntityTriggers()
		.setAspectTriggers()
		.setParentsHidden("BASICTHAUMATURGY")
		.setHidden()
		.setRound()
		.registerResearchItem();
		
		new ResearchItem("spectator", "BOTANIA", new AspectList(), 0, 3, 1, stackFromItem("Botania:itemFinder"))
		.setParents("mana")
		.setConcealed()
		.registerResearchItem();
		
		new ResearchItem("tinyPlanet", "BOTANIA", new AspectList(), 2, 1, 1, stackFromItem("Botania:tinyPlanet"))
		.setParents("mana")
		.setConcealed()
		.registerResearchItem();
		
		new ResearchItem("manaweave", "BOTANIA", new AspectList(), 2, 5, 1, stackFromItem("Botania:manaResource", 22))
		.setParents("mana")
		.setConcealed()
		.registerResearchItem();
		
		new ResearchItem("spellbind", "BOTANIA", new AspectList(), 2, 6, 1, stackFromItem("Botania:spellCloth"))
		.setParents("mana", "manaweave")
		.setConcealed()
		.registerResearchItem();

		new ResearchItem("manasteel", "BOTANIA", new AspectList(), 5, 3, 1, stackFromItem("Botania:manaResource", 22))
		.setItemTriggers(stackFromItem("Botania:manaResource", 0))
		.setParents("mana")
		.setConcealed()
		.setHidden()
		.setRound()
		.registerResearchItem();
		
		new ResearchItem("ringMana", "BOTANIA", new AspectList(), 4, 5, 1, stackFromItem("Botania:manaRing"))
		.setParents("manasteel")
		.setConcealed()
		.registerResearchItem();
		
		new ResearchItem("greaterMana", "BOTANIA", new AspectList(), 4, 6, 1, stackFromItem("Botania:manaRingGreater"))
		.setItemTriggers(stackFromItem("Botania:manaResource", 4))
		.setParents("manasteel")
		.setConcealed()
		.setHidden()
		.setSecondary()
		.registerResearchItem();
		
		/*		
		new ResearchItem("goldenLaurelCrown", "BOTANIA", new AspectList(), 0, 8, 1, stackFromItem("Botania:goldenLaurel")).setConcealed().registerResearchItem();
		new ResearchItem("cloaks", "BOTANIA", new AspectList(), 0, 10, 1, stackFromItem("Botania:holyCloak")).setConcealed().registerResearchItem();
		new ResearchItem("greatFairy", "BOTANIA", new AspectList(), 1, 12, 1, stackFromItem("Botania:pixieRing")).setConcealed().setSecondary().registerResearchItem();
		new ResearchItem("shadedMesa", "BOTANIA", new AspectList(), 2, 9, 1, stackFromItem("Botania:gravityRod")).setConcealed().registerResearchItem();
		new ResearchItem("greaterMagnet", "BOTANIA", new AspectList(), 4, 0, 1, stackFromItem("Botania:magnetRingGreater")).setConcealed().setSecondary().registerResearchItem();
		new ResearchItem("ringMagnet", "BOTANIA", new AspectList(), 4, 1, 1, stackFromItem("Botania:magnetRing")).setConcealed().registerResearchItem();
		new ResearchItem("greaterMana", "BOTANIA", new AspectList(), 4, 6, 1, stackFromItem("Botania:manaRingGreater")).setConcealed().setSecondary().registerResearchItem();
		new ResearchItem("rodDepths", "BOTANIA", new AspectList(), 4, 8, 1, stackFromItem("Botania:cobbleRod")).setConcealed().registerResearchItem();
		new ResearchItem("rodMoltenCore", "BOTANIA", new AspectList(), 4, 10, 1, stackFromItem("Botania:smeltRod")).setConcealed().registerResearchItem();
		new ResearchItem("rodBifrost", "BOTANIA", new AspectList(), 4, 12, 1, stackFromItem("Botania:rainbowRod")).setConcealed().registerResearchItem();
		new ResearchItem("manaseer", "BOTANIA", new AspectList(), 6, 1, 1, stackFromItem("Botania:monocle")).setConcealed().registerResearchItem();
		new ResearchItem("ringCorrection", "BOTANIA", new AspectList(), 6, 5, 1, stackFromItem("Botania:swapRing")).setConcealed().registerResearchItem();
		new ResearchItem("rodTerraFirma", "BOTANIA", new AspectList(), 6, 7, 1, stackFromItem("Botania:terraformRod")).setConcealed().registerResearchItem();
		new ResearchItem("rods", "BOTANIA", new AspectList(), 6, 9, 1, stackFromItem("Botania:diviningRod")).setConcealed().setSpecial().registerResearchItem();
		new ResearchItem("rodHighlands", "BOTANIA", new AspectList(), 6, 11, 1, stackFromItem("Botania:skyDirtRod")).setConcealed().registerResearchItem();
		new ResearchItem("ringChordata", "BOTANIA", new AspectList(), 8, 0, 1, stackFromItem("Botania:waterRing")).setConcealed().registerResearchItem();
		new ResearchItem("ringMantle", "BOTANIA", new AspectList(), 8, 2, 1, stackFromItem("Botania:miningRing")).setConcealed().registerResearchItem();
		new ResearchItem("livingAvatar", "BOTANIA", new AspectList(), 8, 10, 1, stackFromBlock("Botania:avatar")).setConcealed().registerResearchItem();
		new ResearchItem("rodReservoir", "BOTANIA", new AspectList(), 8, 12, 1, stackFromItem("Botania:missileRod")).setConcealed().registerResearchItem();
		new ResearchItem("greaterAura", "BOTANIA", new AspectList(), 10, 1, 1, stackFromItem("Botania:auraRingGreater")).setConcealed().setSecondary().registerResearchItem();
		new ResearchItem("ringAura", "BOTANIA", new AspectList(), 10, 2, 1, stackFromItem("Botania:auraRing")).setConcealed().registerResearchItem();
		new ResearchItem("runes", "BOTANIA", new AspectList(), 10, 4, 1, stackFromItem("Botania:rune", 8)).setConcealed().setSpecial().registerResearchItem();
		new ResearchItem("corporea", "BOTANIA", new AspectList(), 10, 8, 1, stackFromBlock("Botania:corporeaIndex")).setConcealed().registerResearchItem();
		new ResearchItem("ringReach", "BOTANIA", new AspectList(), 11, 11, 1, stackFromItem("Botania:reachRing")).setConcealed().registerResearchItem();
		new ResearchItem("tectonicGirdle", "BOTANIA", new AspectList(), 12, 0, 1, stackFromItem("Botania:knockbackBelt")).setConcealed().registerResearchItem();
		new ResearchItem("snowflakePendant", "BOTANIA", new AspectList(), 12, 7, 1, stackFromItem("Botania:icePendant")).setConcealed().registerResearchItem();
		new ResearchItem("soujournerSash", "BOTANIA", new AspectList(), 13, 2, 1, stackFromItem("Botania:travelBelt")).setConcealed().registerResearchItem();
		new ResearchItem("runeSeasons", "BOTANIA", new AspectList(), 13, 5, 1, stackFromItem("Botania:rune", 4)).setConcealed().setSpecial().registerResearchItem();
		new ResearchItem("runeSins", "BOTANIA", new AspectList(), 13, 9, 1, stackFromItem("Botania:rune", 13)).setConcealed().setSpecial().registerResearchItem();
		new ResearchItem("rodShiftingCrust", "BOTANIA", new AspectList(), 13, 12, 1, stackFromItem("Botania:exchangeRod")).setConcealed().registerResearchItem();
		new ResearchItem("globetrotterSash", "BOTANIA", new AspectList(), 14, 1, 1, stackFromItem("Botania:superTravelBelt")).setConcealed().setSecondary().registerResearchItem();
		new ResearchItem("planestriderSash", "BOTANIA", new AspectList(), 14, 3, 1, stackFromItem("Botania:speedUpBelt")).setConcealed().setSecondary().registerResearchItem();
		new ResearchItem("pyroPendant", "BOTANIA", new AspectList(), 14, 7, 1, stackFromItem("Botania:lavaPendant")).setConcealed().registerResearchItem();
		new ResearchItem("crimsonPendant", "BOTANIA", new AspectList(), 15, 7, 1, stackFromItem("Botania:superLavaPendant")).setConcealed().setSecondary().registerResearchItem();
		new ResearchItem("charmDiva", "BOTANIA", new AspectList(), 15, 11, 1, stackFromItem("Botania:divaCharm")).setConcealed().registerResearchItem();
		*/
	}

	private ItemStack stackFromItem(String itemName)
	{
		return new ItemStack((Item)Item.itemRegistry.getObject(itemName));
	}
	
	private ItemStack stackFromItem(String itemName, int metadata)
	{
		return new ItemStack((Item)Item.itemRegistry.getObject(itemName), 1, metadata);
	}
	
	private ItemStack stackFromBlock(String blockName)
	{
		return new ItemStack(Item.getItemFromBlock(Block.getBlockFromName(blockName)));
	}
	
	private ItemStack stackFromBlock(String blockName, int metadata)
	{
		return new ItemStack(Item.getItemFromBlock(Block.getBlockFromName(blockName)), 1, metadata);
	}
}