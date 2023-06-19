package levistico.bconstruct;

import levistico.bconstruct.crafting.BlockCraftingStation;
import levistico.bconstruct.crafting.BlockPartBuilder;
import levistico.bconstruct.crafting.BlockToolStation;
import levistico.bconstruct.materials.BToolMaterials;
import levistico.bconstruct.mixin.AccessorItem;
import levistico.bconstruct.parts.BToolParts;
import levistico.bconstruct.recipes.RecipeRepairKitRepair;
import levistico.bconstruct.recipes.RecipeReplaceToolPart;
import levistico.bconstruct.tools.BTools;
import levistico.bconstruct.vitems.SlimeSling;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.Minecraft;
import net.minecraft.src.*;
import net.minecraft.src.material.ArmorMaterial;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.helper.ArmorHelper;
import turniplabs.halplibe.helper.BlockHelper;
import turniplabs.halplibe.helper.ItemHelper;
import turniplabs.halplibe.helper.RecipeHelper;


public final class BConstruct implements ModInitializer {

    public static final Minecraft mc = Minecraft.getMinecraft();
    public static final String MOD_ID = "bconstruct";
    public static String guiFolder;
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static final CraftingManager craftingManager = CraftingManager.getInstance();

    public static int itemIdInc = 140;
    public static final Item blankPattern = ItemHelper.createItem(MOD_ID, new Item(itemIdInc++), "blankPattern", "pattern_blank.png");

    public static final ArmorMaterial slimeArmorMaterial = ArmorHelper.createArmorMaterial("slime", 100,  0,0,0,200);
    public static Item slimeBoots;
    public static Item slimeSling;

    public static int blockIdInc = 900;
    public static final Block craftingStation = BlockHelper.createBlock(MOD_ID, new BlockCraftingStation(blockIdInc++), "craftingStation", "craftingstation_top.png", "craftingstation_bottom.png", "craftingstation_side.png", Block.soundWoodFootstep, 2.5f, 15f, 0.0f);
    public static final Block partBuilder = BlockHelper.createBlock(MOD_ID, new BlockPartBuilder(blockIdInc++), "partBuilder", "partbuilder_oak_top.png", "partbuilder_oak_bottom.png", "partbuilder_oak_side.png", Block.soundWoodFootstep, 2.5f, 15f, 0.0f);
    public static final Block toolStation = BlockHelper.createBlock(MOD_ID, new BlockToolStation(blockIdInc++), "toolStation", "toolstation_top.png", "toolstation_bottom.png", "toolstation_side.png", Block.soundWoodFootstep, 2.5f, 15f, 0.0f);


    @SuppressWarnings("unchecked")
    void addRecipe(IRecipe recipe) {
        craftingManager.getRecipeList().add(recipe);
    }
    @Override
    public void onInitialize() {
        LOGGER.info("Binkers initialized.");
        BToolMaterials.InitializeMaterialMaps();
        BToolParts.InitializeToolParts(MOD_ID);
        BTools.InitializeTools(MOD_ID);

        slimeBoots = ItemHelper.createItem(MOD_ID, new ItemArmor(itemIdInc++, slimeArmorMaterial, 3), "slimeBoots", "slime_boots.png");
        slimeSling = ItemHelper.createItem(MOD_ID, new SlimeSling(itemIdInc++).setMaxStackSize(1), "slimeSling", "slime_sling.png");
        ((AccessorItem)slimeBoots).setMaxDamage(0);

        //TODO smelter items (including stuff like ladles/cans)
        //TODO smelter blocks
        //TODO slime block
        //public static final Block searedBricks = BlockHelper.createBlock(MOD_ID, new Block(blockIdInc++), "searedBricks", "searedBricks.png", 0.1f, 0.1f, 0.0f)

        guiFolder = String.format("/assets/%s/gui/", MOD_ID);

        RecipeHelper.Crafting.createShapelessRecipe(craftingStation, 1, new Object[] {new ItemStack(Block.workbench, 1)});
        RecipeHelper.Crafting.createRecipe(partBuilder, 1, new Object[] {"P", "W", 'P', blankPattern, 'W', Block.logOak});
        RecipeHelper.Crafting.createRecipe(toolStation, 1, new Object[] {"P", "W", 'P', blankPattern, 'W', Block.workbench});
        RecipeHelper.Crafting.createRecipe(blankPattern,4, new Object[] {"PS", "SP", 'P', Block.planksOak, 'S', Item.stick});
        RecipeHelper.Crafting.createRecipe(slimeBoots,  1, new Object[] {"###", "S#S", "S#S", 'S', Item.slimeball});
        RecipeHelper.Crafting.createRecipe(slimeSling,  1, new Object[] {"SsS", "#S#", "#S#", 'S', Item.slimeball, 's', Item.string});

        addRecipe(new RecipeRepairKitRepair());
        addRecipe(new RecipeReplaceToolPart());

        //conveniences (later to go into own mod)
        RecipeHelper.Crafting.createRecipe(Block.chestPlanksOak, 4, new Object[]{"LLL", "L#L", "LLL", 'L', Block.logOak});
        RecipeHelper.Crafting.createShapelessRecipe(Item.seedsWheat, 4, new Object[] {new ItemStack(Item.wheat, 1)});
        RecipeHelper.Crafting.createShapelessRecipe(Item.seedsWheat, 1, new Object[] {new ItemStack(Block.tallgrass, 1)});
        RecipeHelper.Crafting.createRecipe(Block.saplingOak, 1, new Object[] {"L","L",'L', Block.leavesOak});
        RecipeHelper.Crafting.createRecipe(Block.saplingBirch, 1, new Object[] {"L","L",'L', Block.leavesBirch});
        RecipeHelper.Crafting.createRecipe(Block.saplingCherry, 1, new Object[] {"L","L",'L', Block.leavesCherry});
        RecipeHelper.Crafting.createRecipe(Block.saplingPine, 1, new Object[] {"L","L",'L', Block.leavesPine});
        RecipeHelper.Crafting.createRecipe(Block.saplingEucalyptus, 1, new Object[] {"L","L",'L', Block.leavesEucalyptus});
        RecipeHelper.Crafting.createRecipe(Block.saplingOakRetro, 1, new Object[] {"L","L",'L', Block.leavesOakRetro});
        RecipeHelper.Crafting.createRecipe(Block.saplingShrub, 1, new Object[] {"L","L",'L', Block.leavesShrub});
    }
}
