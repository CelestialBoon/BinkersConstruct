package levistico.bconstruct;

import levistico.bconstruct.crafting.BlockCraftingStation;
import levistico.bconstruct.crafting.BlockPartBuilder;
import levistico.bconstruct.crafting.BlockToolStation;
import levistico.bconstruct.crafting.CraftingTileEntity;
import levistico.bconstruct.gui.containers.GUISmelteryController;
import levistico.bconstruct.materials.BToolMaterials;
import levistico.bconstruct.mixin.AccessorItem;
import levistico.bconstruct.mixinInterfaces.IBinkersEntityPlayerMP;
import levistico.bconstruct.parts.BToolParts;
import levistico.bconstruct.recipes.RecipeRepairKitRepair;
import levistico.bconstruct.recipes.RecipeReplaceToolPart;
import levistico.bconstruct.smeltery.*;
import levistico.bconstruct.tools.BTools;
import levistico.bconstruct.vitems.SlimeSling;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.Minecraft;
import net.minecraft.src.*;
import net.minecraft.src.helper.DamageType;
import net.minecraft.src.material.ArmorMaterial;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sunsetsatellite.fluidapi.FluidAPI;
import sunsetsatellite.fluidapi.FluidRegistry;
import sunsetsatellite.fluidapi.render.RenderFluidInBlock;
import sunsetsatellite.fluidapi.template.gui.GuiFluidTank;
import sunsetsatellite.sunsetutils.util.NBTEditCommand;
import sunsetsatellite.sunsetutils.util.multiblocks.Multiblock;
import sunsetsatellite.sunsetutils.util.multiblocks.RenderMultiblock;
import sunsetsatellite.sunsetutils.util.multiblocks.StructureCommand;
import turniplabs.halplibe.helper.*;

import java.util.ArrayList;
import java.util.HashMap;


public final class BConstruct implements ModInitializer {

    public static final Minecraft mc = Minecraft.getMinecraft();
    public static final String MOD_ID = "bconstruct";
    public static String guiFolder;
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static final CraftingManager craftingManager = CraftingManager.getInstance();
    public static HashMap<String, ArrayList<Class<?>>> nameToGuiMap = new HashMap<>();

    public static int itemIdInc = 140;
    public static final Item blankPattern = ItemHelper.createItem(MOD_ID, new Item(itemIdInc++), "blankPattern", "pattern_blank.png");

    //please use halplibe helpers >:(
    /*public static final ArmorMaterial slimeArmorMaterial = new ArmorMaterial("slime", 6, 100);
    public static String[] armorFilenamePrefix = {"cloth", "chain", "iron", "diamond", "gold", "steel", "slime"};*/
    public static final ArmorMaterial slimeArmorMaterial = ArmorHelper.createArmorMaterial("slime",100,0,0,0,0);
    public static Item slimeBoots;
    public static Item slimeSling;

    //TODO smelter items (including stuff like ladles/cans)

    public static int blockIdInc = 900;
    public static final Block craftingStation = BlockHelper.createBlock(MOD_ID, new BlockCraftingStation(blockIdInc++), "craftingstation", "craftingstation_top.png", "craftingstation_bottom.png", "craftingstation_side.png", Block.soundWoodFootstep, 2.5f, 15f, 0.0f);
    public static final Block partBuilder = BlockHelper.createBlock(MOD_ID, new BlockPartBuilder(blockIdInc++), "partBuilder", "partbuilder_oak_top.png", "partbuilder_oak_bottom.png", "partbuilder_oak_side.png", Block.soundWoodFootstep, 2.5f, 15f, 0.0f);
    public static final Block toolStation = BlockHelper.createBlock(MOD_ID, new BlockToolStation(blockIdInc++), "toolStation", "toolstation_top.png", "toolstation_bottom.png", "toolstation_side.png", Block.soundWoodFootstep, 2.5f, 15f, 0.0f);

    //TODO smelter blocks
    //TODO slime block
//    public static final Block searedBricks = BlockHelper.createBlock(MOD_ID, new Block(blockIdInc++), "searedBricks", "searedBricks.png", 0.1f, 0.1f, 0.0f)
    public static final Block searedBricks = BlockHelper.createBlock(MOD_ID, new Block(blockIdInc++,Material.rock), "searedBricks", "searedbrick.png", Block.soundStoneFootstep ,2.5f, 15f, 0.0f);
    public static final Block smelteryController = BlockHelper.createBlock(MOD_ID,new BlockSmelteryController(blockIdInc++), "smelteryController", "searedbrick.png","searedbrick.png","smeltery_inactive.png","searedbrick.png","searedbrick.png","searedbrick.png", Block.soundStoneFootstep,2.5f,15f,0.0f);
    public static final Block smelteryDrain = BlockHelper.createBlock(MOD_ID,new Block(blockIdInc++,Material.rock), "smelteryDrain", "drain_side.png","drain_side.png","drain_out.png","drain_side.png","drain_basin.png","drain_side.png", Block.soundStoneFootstep,2.5f,15f,0.0f);
    public static final Block searedTank = BlockHelper.createBlock(MOD_ID,new BlockSearedTank(blockIdInc++,Material.rock), "searedTank", "seared_tank_top.png","seared_tank_side.png","seared_tank_side.png","seared_tank_side.png","seared_tank_side.png","seared_tank_side.png", Block.soundStoneFootstep,2.5f,15f,0.0f);

    public static final int[] moltenMetalTex = FluidAPI.registerFluidTexture(MOD_ID,"molten_metal.png");
    public static final Block moltenMetalFlowing = BlockHelper.createBlock(MOD_ID,new BlockMoltenMetalFlowing(blockIdInc++,Material.lava,0xFFFFFFFF),"moltenMetalFlowing","molten_metal.png",Block.soundPowderFootstep,1.0f,1.0f,0).setNotInCreativeMenu().setPlaceOverwrites().setTexCoords(moltenMetalTex[0], moltenMetalTex[1], moltenMetalTex[2], moltenMetalTex[3], moltenMetalTex[4], moltenMetalTex[5], moltenMetalTex[6], moltenMetalTex[7], moltenMetalTex[8], moltenMetalTex[9], moltenMetalTex[10], moltenMetalTex[11]);
    public static final Block moltenMetalStill = BlockHelper.createBlock(MOD_ID,new BlockMoltenMetalStill(blockIdInc++,Material.lava,0xFFFFFFFF),"moltenMetalStill","molten_metal.png",Block.soundPowderFootstep,1.0f,1.0f,0).setNotInCreativeMenu().setPlaceOverwrites().setTexCoords(moltenMetalTex[0], moltenMetalTex[1], moltenMetalTex[2], moltenMetalTex[3], moltenMetalTex[4], moltenMetalTex[5], moltenMetalTex[6], moltenMetalTex[7], moltenMetalTex[8], moltenMetalTex[9], moltenMetalTex[10], moltenMetalTex[11]);
    public static final Item bucketMoltenMetal = ItemHelper.createItem(MOD_ID,new ItemBucket(itemIdInc++,moltenMetalFlowing.blockID),"bucketMoltenMetal","bucketMoltenMetal.png").setContainerItem(Item.bucket);

    public static final Block moltenIronFlowing = createFluid("moltenIronFlowing",0xFFFF0000,true);
    public static final Block moltenIronStill = createFluid("moltenIronStill",0xFFFF0000,false);
    public static final Block moltenCopperFlowing = createFluid("moltenCopperFlowing",0xFFB87333,true);
    public static final Block moltenCopperStill = createFluid("moltenCopperStill",0xFFB87333,false);
    public static final Block moltenTinFlowing = createFluid("moltenTinFlowing",0xFFD8DFED,true);
    public static final Block moltenTinStill = createFluid("moltenTinStill",0xffD8DFED,false);
    public static final Block moltenBronzeFlowing = createFluid("moltenBronzeFlowing",0xFFDB9f6B,true);
    public static final Block moltenBronzeStill = createFluid("moltenBronzeStill",0xFFDB9f6B,false);

    public static final Item bucketMoltenIron = createFluidBucket("bucketMoltenIron",moltenIronFlowing);
    public static final Item bucketMoltenCopper = createFluidBucket("bucketMoltenCopper",moltenCopperFlowing);
    public static final Item bucketMoltenTin = createFluidBucket("bucketMoltenTin",moltenTinFlowing);
    public static final Item bucketMoltenBronze = createFluidBucket("bucketMoltenBronze",moltenBronzeFlowing);

    public static final Multiblock smelteryMultiblock = new Multiblock(MOD_ID,new Class[]{BConstruct.class},"smeltery","smeltery",false);

    @Override
    public void onInitialize() {
        Multiblock.multiblocks.put("smeltery",smelteryMultiblock);

        CommandHelper.createCommand(new NBTEditCommand());
        CommandHelper.createCommand(new StructureCommand("structure","struct"));

        BToolMaterials.InitializeMaterialMaps();
        BToolParts.InitializeToolParts(MOD_ID);
        BTools.InitializeTools(MOD_ID);

        slimeBoots = ItemHelper.createItem(MOD_ID, new ItemArmor(itemIdInc++, slimeArmorMaterial, 3), "slimeBoots", "slime_boots.png");
        slimeSling = ItemHelper.createItem(MOD_ID, new SlimeSling(itemIdInc++).setMaxStackSize(1), "slimeSling", "slime_sling.png");
        ArmorMaterial.setProtectionValuePercent(slimeArmorMaterial, DamageType.FALL, 200);
        ((AccessorItem)slimeBoots).setMaxDamage(0);

        guiFolder = String.format("/assets/%s/gui/", MOD_ID);
        LOGGER.info("Binkers initialized.");

        EntityHelper.createTileEntity(CraftingTileEntity.class,"Crafting Table");
        EntityHelper.createSpecialTileEntity(TileEntitySmelteryController.class, new RenderSmeltery(),"Smeltery");
        addToNameGuiMap("Smeltery", GUISmelteryController.class, TileEntitySmelteryController.class);
        EntityHelper.createSpecialTileEntity(TileEntitySearedTank.class,new RenderFluidInBlock(),"Seared Tank");
        addToNameGuiMap("Seared Tank", GuiFluidTank.class, TileEntitySearedTank.class);

        RecipeHelper.Crafting.createShapelessRecipe(craftingStation, 1, new Object[] {new ItemStack(Block.workbench, 1)});
        RecipeHelper.Crafting.createRecipe(partBuilder, 1, new Object[] {"P", "W", 'P', blankPattern, 'W', Block.logOak});
        RecipeHelper.Crafting.createRecipe(toolStation, 1, new Object[] {"P", "W", 'P', blankPattern, 'W', Block.workbench});
        RecipeHelper.Crafting.createRecipe(blankPattern, 4, new Object[] {"PS", "SP", 'P', Block.planksOak, 'S', Item.stick});
        RecipeHelper.Crafting.createRecipe(slimeBoots, 1, new Object[] {"###", "S#S", "S#S", 'S', Item.slimeball});
        RecipeHelper.Crafting.createRecipe(slimeSling, 1, new Object[] {"SsS", "#S#", "#S#", 'S', Item.slimeball, 's', Item.string});

        addRecipe(new RecipeRepairKitRepair());
        addRecipe(new RecipeReplaceToolPart());
    }

    public static Block createFluid(String name, int color, boolean flowing){
        if(flowing){
            return BlockHelper.createBlock(MOD_ID,new BlockMoltenMetalFlowing(blockIdInc++,Material.lava,color),name,"molten_metal.png",Block.soundPowderFootstep,1.0f,1.0f,0).setNotInCreativeMenu().setPlaceOverwrites().setTexCoords(moltenMetalTex[0], moltenMetalTex[1], moltenMetalTex[2], moltenMetalTex[3], moltenMetalTex[4], moltenMetalTex[5], moltenMetalTex[6], moltenMetalTex[7], moltenMetalTex[8], moltenMetalTex[9], moltenMetalTex[10], moltenMetalTex[11]);
        } else {
            return BlockHelper.createBlock(MOD_ID,new BlockMoltenMetalStill(blockIdInc++,Material.lava,color),name,"molten_metal.png",Block.soundPowderFootstep,1.0f,1.0f,0).setNotInCreativeMenu().setPlaceOverwrites().setTexCoords(moltenMetalTex[0], moltenMetalTex[1], moltenMetalTex[2], moltenMetalTex[3], moltenMetalTex[4], moltenMetalTex[5], moltenMetalTex[6], moltenMetalTex[7], moltenMetalTex[8], moltenMetalTex[9], moltenMetalTex[10], moltenMetalTex[11]);
        }
    }

    //TODO: Make bucket textures distinct (based on the fluids color)
    public static Item createFluidBucket(String name, Block fluid){
        return ItemHelper.createItem(MOD_ID,new ItemBucket(itemIdInc++,fluid.blockID),name,"bucketMoltenMetal.png").setContainerItem(Item.bucket);
    }

    public static void displayGui(EntityPlayer entityplayer, GuiScreen guiScreen, Container container, IInventory tile, int x, int y, int z) {
        if(entityplayer instanceof EntityPlayerMP) {
            ((IBinkersEntityPlayerMP)entityplayer).displayBinkersGuiScreen(guiScreen,container,tile,x,y,z);
        } else {
            Minecraft.getMinecraft().displayGuiScreen(guiScreen);
        }
    }

    public static void displayGui(EntityPlayer entityplayer, GuiScreen guiScreen, Container container, IInventory tile, ItemStack stack) {
        if(entityplayer instanceof EntityPlayerMP) {
            ((IBinkersEntityPlayerMP)entityplayer).displayBinkersGuiScreen(guiScreen,container,tile,stack);
        } else {
            Minecraft.getMinecraft().displayGuiScreen(guiScreen);
        }
    }

    public static void addToNameGuiMap(String name, Class<? extends Gui> guiClass, Class<? extends IInventory> tileEntityClass){
        ArrayList<Class<?>> list = new ArrayList<>();
        list.add(guiClass);
        list.add(tileEntityClass);
        nameToGuiMap.put(name,list);
    }

    @SuppressWarnings("unchecked")
    void addRecipe(IRecipe recipe) {
        craftingManager.getRecipeList().add(recipe);
    }
}
