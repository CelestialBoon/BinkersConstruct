package levistico.bconstruct;

import levistico.bconstruct.crafting.BlockCraftingStation;
import levistico.bconstruct.crafting.BlockPartBuilder;
import levistico.bconstruct.crafting.BlockToolStation;
import levistico.bconstruct.materials.BToolMaterials;
import levistico.bconstruct.mixin.AccessorItem;
import levistico.bconstruct.parts.BToolParts;
import levistico.bconstruct.recipes.RecipeRepairKitRepair;
import levistico.bconstruct.recipes.RecipeReplaceToolPart;
import levistico.bconstruct.smeltery.BlockSmelteryController;
import levistico.bconstruct.smeltery.TileEntitySmelteryController;
import levistico.bconstruct.tools.BTools;
import levistico.bconstruct.vitems.SlimeSling;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.Minecraft;
import net.minecraft.src.*;
import net.minecraft.src.helper.DamageType;
import net.minecraft.src.input.InputHandler;
import net.minecraft.src.input.controller.ControllerInput;
import net.minecraft.src.input.controller.ControllerInventoryHandler;
import net.minecraft.src.material.ArmorMaterial;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sunsetsatellite.sunsetutils.util.NBTEditCommand;
import sunsetsatellite.sunsetutils.util.multiblocks.Multiblock;
import sunsetsatellite.sunsetutils.util.multiblocks.RenderMultiblock;
import sunsetsatellite.sunsetutils.util.multiblocks.StructureCommand;
import turniplabs.halplibe.helper.*;

import java.awt.event.MouseEvent;


public final class BConstruct implements ModInitializer {

    public static final Minecraft mc = Minecraft.getMinecraft();
    public static final String MOD_ID = "bconstruct";
    public static String guiFolder;
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static final CraftingManager craftingManager = CraftingManager.getInstance();

    public static int itemIdInc = 140;
    public static final Item blankPattern = ItemHelper.createItem(MOD_ID, new Item(itemIdInc++), "blankPattern", "pattern_blank.png");

    public static final ArmorMaterial slimeArmorMaterial = new ArmorMaterial("slime", 6, 100);
    public static String[] armorFilenamePrefix = {"cloth", "chain", "iron", "diamond", "gold", "steel", "slime"};
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
    public static final Block searedTank = BlockHelper.createBlock(MOD_ID,new Block(blockIdInc++,Material.rock), "searedTank", "seared_tank_top.png","seared_tank_side.png","seared_tank_side.png","seared_tank_side.png","seared_tank_side.png","seared_tank_side.png", Block.soundStoneFootstep,2.5f,15f,0.0f);

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

        EntityHelper.createTileEntity(TileEntitySmelteryController.class,"smeltery");
        EntityHelper.createSpecialTileEntity(TileEntitySmelteryController.class, new RenderMultiblock(),"smeltery");

        RecipeHelper.Crafting.createShapelessRecipe(craftingStation, 1, new Object[] {new ItemStack(Block.workbench, 1)});
        RecipeHelper.Crafting.createRecipe(partBuilder, 1, new Object[] {"P", "W", 'P', blankPattern, 'W', Block.logOak});
        RecipeHelper.Crafting.createRecipe(toolStation, 1, new Object[] {"P", "W", 'P', blankPattern, 'W', Block.workbench});
        RecipeHelper.Crafting.createRecipe(blankPattern, 4, new Object[] {"PS", "SP", 'P', Block.planksOak, 'S', Item.stick});
        RecipeHelper.Crafting.createRecipe(slimeBoots, 1, new Object[] {"###", "S#S", "S#S", 'S', Item.slimeball});
        RecipeHelper.Crafting.createRecipe(slimeSling, 1, new Object[] {"SsS", "#S#", "#S#", 'S', Item.slimeball, 's', Item.string});

        addRecipe(new RecipeRepairKitRepair());
        addRecipe(new RecipeReplaceToolPart());
    }
    @SuppressWarnings("unchecked")
    void addRecipe(IRecipe recipe) {
        craftingManager.getRecipeList().add(recipe);
    }
}
