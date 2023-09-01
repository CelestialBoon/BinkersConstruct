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
import levistico.bconstruct.gadgets.SlimeSling;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.sound.block.BlockSounds;
import net.minecraft.core.block.Block;
import net.minecraft.core.crafting.CraftingManager;
import net.minecraft.core.crafting.recipe.IRecipe;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemArmor;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ArmorMaterial;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.helper.*;


public final class BConstruct implements ModInitializer {

    public static final Minecraft mc = Minecraft.getMinecraft(Minecraft.class);
    public static final String MOD_ID = "bconstruct";
    public static String guiFolder;
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static int idInc = 16530;
    public static Item blankPattern;

    public static ArmorMaterial slimeArmorMaterial;
    public static Item slimeBoots;
    public static Item slimeSling;

    public static int blockIdInc = 901;
    public static Block craftingStation;
    public static Block partBuilder;
    public static Block toolStation;


    @SuppressWarnings("unchecked")
    void addRecipe(IRecipe recipe) {
        CraftingManager.getInstance().getRecipeList().add(recipe);
    }
    @Override
    public void onInitialize() {
        LOGGER.info("Binkers initialized.");

        //this is here to possibly fix some class loading issues, do not delete
        try {
            Class.forName("net.minecraft.core.block.Block");
            Class.forName("net.minecraft.core.item.Item");
        } catch (ClassNotFoundException ignored) {}

        /*int inizioIntervallo = 1;
        int larghezzaIntervallo =30;
        for (int i = inizioIntervallo; i<Item.itemsList.length; i++){
            if (Item.itemsList[i]!=null) {
                inizioIntervallo = i + 1;
            }
            if (i - inizioIntervallo + 1 == larghezzaIntervallo) {
                break;
            }
        }
        LOGGER.info("Inizio intervallo blocchi: " + inizioIntervallo);

        inizioIntervallo = 16384;
        larghezzaIntervallo = 20;
        for (int i = inizioIntervallo; i<Item.itemsList.length; i++){
            if (Item.itemsList[i]!=null) {
                inizioIntervallo = i + 1;
            }
            if (i - inizioIntervallo + 1 == larghezzaIntervallo) {
                break;
            }
        }
        LOGGER.info("Inizio intervallo oggetti: " + inizioIntervallo);*/


        blankPattern = ItemHelper.createItem(MOD_ID, new Item(idInc++), "blankPattern", "pattern_blank.png");
        slimeArmorMaterial = ArmorHelper.createArmorMaterial("slime", 100,  0,0,0,200);




        craftingStation = new BlockBuilder(MOD_ID)
                .setBlockSound(BlockSounds.WOOD)
                .setHardness(2)
                .setResistance(15)
                .setLuminance(0).setTextures("craftingstation_side.png")
                .setTopTexture("craftingstation_top.png")
                .setBottomTexture("craftingstation_bottom.png")
                .build(new BlockCraftingStation(blockIdInc++));

        partBuilder = new BlockBuilder(MOD_ID)
                .setBlockSound(BlockSounds.WOOD)
                .setHardness(2)
                .setResistance(15)
                .setLuminance(0).setTextures("partbuilder_oak_side.png")
                .setTopTexture("partbuilder_oak_top.png")
                .setBottomTexture("partbuilder_oak_bottom.png")
                .build(new BlockPartBuilder(blockIdInc++));

        toolStation = new BlockBuilder(MOD_ID)
                .setBlockSound(BlockSounds.WOOD)
                .setHardness(2)
                .setResistance(15)
                .setLuminance(0).setTextures("toolstation_side.png")
                .setTopTexture("toolstation_top.png")
                .setBottomTexture("toolstation_bottom.png")
                .build(new BlockToolStation(blockIdInc++));

                //BlockHelper.createBlock(MOD_ID, new BlockCraftingStation(blockIdInc++), "craftingstation_top.png", "craftingstation_bottom.png", "craftingstation_side.png", BlockSounds.WOOD, 2.5f, 15f, 0.0f);
        //partBuilder = BlockHelper.createBlock(MOD_ID, new BlockPartBuilder(blockIdInc++), "partbuilder_oak_top.png", "partbuilder_oak_bottom.png", "partbuilder_oak_side.png", BlockSounds.WOOD, 2.5f, 15f, 0.0f);
        //toolStation = BlockHelper.createBlock(MOD_ID, new BlockToolStation(blockIdInc++), "toolstation_top.png", "toolstation_bottom.png", "toolstation_side.png", BlockSounds.WOOD, 2.5f, 15f, 0.0f);

        BToolMaterials.InitializeMaterialMaps();
        BToolParts.InitializeToolParts(MOD_ID);
        BTools.InitializeTools(MOD_ID);

        slimeBoots = ItemHelper.createItem(MOD_ID, new ItemArmor("slimeBoots",idInc++, slimeArmorMaterial, 3), "slimeBoots", "slime_boots.png");
        slimeSling = ItemHelper.createItem(MOD_ID, new SlimeSling(idInc++).setMaxStackSize(1), "slimeSling", "slime_sling.png");
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
