package levistico.bconstruct;

import levistico.bconstruct.crafting.BlockCraftingStation;
import levistico.bconstruct.crafting.BlockPartBuilder;
import levistico.bconstruct.crafting.BlockToolStation;
import levistico.bconstruct.materials.BToolMaterials;
import levistico.bconstruct.parts.BToolParts;
import levistico.bconstruct.recipes.RecipeRepairKitRepair;
import levistico.bconstruct.recipes.RecipeReplaceToolPart;
import levistico.bconstruct.tools.BTools;
import net.fabricmc.api.ModInitializer;
import net.minecraft.src.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.helper.BlockHelper;
import turniplabs.halplibe.helper.ItemHelper;
import turniplabs.halplibe.helper.RecipeHelper;


public final class BConstruct implements ModInitializer {
    public static final String MOD_ID = "bconstruct";
    public static String guiFolder;
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static final CraftingManager craftingManager = CraftingManager.getInstance();

    public static int itemIdInc = 140;
    public static final Item blankPattern = ItemHelper.createItem(MOD_ID, new Item(itemIdInc++), "blankPattern", "pattern_blank.png");

    //TODO smelter items (including stuff like ladles/cans)

    public static int blockIdInc = 900;
    public static final Block craftingStation = BlockHelper.createBlock(MOD_ID, new BlockCraftingStation(blockIdInc++), "craftingstation", "craftingstation_top.png", "craftingstation_bottom.png", "craftingstation_side.png", Block.soundWoodFootstep, 2.5f, 15f, 0.0f);
    public static final Block partBuilder = BlockHelper.createBlock(MOD_ID, new BlockPartBuilder(blockIdInc++), "partBuilder", "partbuilder_oak_top.png", "partbuilder_oak_bottom.png", "partbuilder_oak_side.png", Block.soundWoodFootstep, 2.5f, 15f, 0.0f);
    public static final Block toolStation = BlockHelper.createBlock(MOD_ID, new BlockToolStation(blockIdInc++), "toolStation", "toolstation_top.png", "toolstation_bottom.png", "toolstation_side.png", Block.soundWoodFootstep, 2.5f, 15f, 0.0f);

    //TODO smelter blocks
//    public static final Block searedBricks = BlockHelper.createBlock(MOD_ID, new Block(blockIdInc++), "searedBricks", "searedBricks.png", 0.1f, 0.1f, 0.0f)


    @Override
    public void onInitialize() {
        BToolMaterials.InitializeMaterialMaps();
        BToolParts.InitializeToolParts(MOD_ID);
        BTools.InitializeTools(MOD_ID);

        guiFolder = String.format("/assets/%s/gui/", MOD_ID);
        LOGGER.info("Binkers initialized.");

        RecipeHelper.Crafting.createShapelessRecipe(craftingStation, 1, new Object[] {new ItemStack(Block.workbench, 1)});
        RecipeHelper.Crafting.createRecipe(partBuilder, 1, new Object[] {"P", "W", 'P', blankPattern, 'W', Block.logOak});
        RecipeHelper.Crafting.createRecipe(toolStation, 1, new Object[] {"P", "W", 'P', blankPattern, 'W', Block.workbench});
        RecipeHelper.Crafting.createRecipe(blankPattern, 4, new Object[] {"PS", "SP", 'P', Block.planksOak, 'S', Item.stick});

        addRecipe(new RecipeRepairKitRepair());
        addRecipe(new RecipeReplaceToolPart());
    }
    @SuppressWarnings("unchecked")
    void addRecipe(IRecipe recipe) {
        craftingManager.getRecipeList().add(recipe);
    }
}
