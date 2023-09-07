package levistico.bconstruct.materials;

import levistico.bconstruct.parts.BToolPart;
import levistico.bconstruct.parts.PartsFlag;
import levistico.bconstruct.tools.properties.Properties;
import levistico.bconstruct.utils.Pair;
import net.minecraft.core.block.Block;
import net.minecraft.core.item.Item;;;
import net.minecraft.core.item.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

public class BToolMaterials {
    static public ArrayList<BToolMaterial> matList = new ArrayList<>(); //items are automatically added to this list
    static public HashMap<Item, BToolMaterial> partBuilderItemMap = new HashMap<Item, BToolMaterial>();

    //    public static final ToolMaterial wood = (new ToolMaterial()).setDurability(64).setEfficiency(2.0F, 4.0F).setMiningLevel(0);
//    public static final ToolMaterial stone = (new ToolMaterial()).setDurability(128).setEfficiency(4.0F, 6.0F).setMiningLevel(1);
//    public static final ToolMaterial iron = (new ToolMaterial()).setDurability(256).setEfficiency(6.0F, 8.0F).setMiningLevel(2);
//    public static final ToolMaterial steel = (new ToolMaterial()).setDurability(4608).setEfficiency(7.0F, 14.0F).setMiningLevel(3);
//    public static final ToolMaterial diamond = (new ToolMaterial()).setDurability(1536).setEfficiency(14.0F, 45.0F).setMiningLevel(3).setDamage(4).setBlockHitDelay(4);
//    public static final ToolMaterial gold = (new ToolMaterial()).setDurability(64).setEfficiency(4.0F, 8.0F).setMiningLevel(0).setSilkTouch(true);
    //TODO paper, slime, nether, etc.
    public static final BToolMaterial wood = new BToolMaterial("wood", "#876627", EToolMaterial.wood.ordinal(), 64, 2f, 0, 0, PartsFlag.BASIC_TOOLPARTS)
            .addAllProperty(Properties.ECOLOGICAL,1);
    public static final BToolMaterial cactus = new BToolMaterial("cactus", "#0D6418", EToolMaterial.cactus.ordinal(), 50, 2f, 2, 0, PartsFlag.HEAD);
    public static final BToolMaterial bone = new BToolMaterial("bone", "#E8E5D2", EToolMaterial.bone.ordinal(),70, 3f, 2, 0, PartsFlag.BASIC_TOOLPARTS);
    public static final BToolMaterial stone = new BToolMaterial("stone", "#B1AFAD", EToolMaterial.stone.ordinal(),128, 3f, 1, 1, PartsFlag.BASIC_TOOLPARTS);
    public static final BToolMaterial flint = new BToolMaterial("flint", "#3D3C3C", EToolMaterial.flint.ordinal(),100, 3.5f, 3, 1, PartsFlag.BASIC_TOOLPARTS);
    public static final BToolMaterial quartz = new BToolMaterial("quartz","#F9DDDC", EToolMaterial.quartz.ordinal(),200, 5f, 3, 1, PartsFlag.BASIC_TOOLPARTS);
    public static final BToolMaterial gold = new BToolMaterial("gold", "#FDF55F", EToolMaterial.gold.ordinal(),64, 4f, 1, 1, PartsFlag.BASIC_TOOLPARTS)
            .addHeadProperty(Properties.SILKTOUCH, 1);
    public static final BToolMaterial iron = new BToolMaterial("iron", "#D8D8D8", EToolMaterial.iron.ordinal(),256, 6f, 2, 2, PartsFlag.BASIC_TOOLPARTS);
    public static final BToolMaterial diamond = new BToolMaterial("diamond", "#8CF4E2", EToolMaterial.diamond.ordinal(),1536, 14f, 4, 3, PartsFlag.BASIC_TOOLPARTS);
    public static final BToolMaterial obsidian = new BToolMaterial("obsidian","#3B2754", EToolMaterial.obsidian.ordinal(),200, 10f, 4, 3, PartsFlag.BASIC_TOOLPARTS);
    public static final BToolMaterial steel = new BToolMaterial("steel", "#959595", EToolMaterial.steel.ordinal(),4608, 7f, 3, 3, PartsFlag.BASIC_TOOLPARTS);
    public static final BToolMaterial string = new BToolMaterial("string", "#FFFFFF", EToolMaterial.string.ordinal(),25, 0f, 0, 0, PartsFlag.BINDING);
    public static final BToolMaterial leather = new BToolMaterial("leather", "#C65C35", EToolMaterial.leather.ordinal(),50, 0f, 0, 0, PartsFlag.BINDING);
    public static final BToolMaterial chain = new BToolMaterial("chain", "#3E4453", EToolMaterial.chain.ordinal(),100, 0f, 0, 0, PartsFlag.BINDING);


//    static public final Fraction ONE_NINTH = Fraction.getFraction(1, 9);
//    static public final Fraction FOUR = Fraction.getFraction(4,1);
//    static public final Fraction NINE = Fraction.getFraction(9,1);

    static private final Pattern logPattern = Pattern.compile("^tile\\.log\\.([^.]*)");
    static private final Pattern planksPattern = Pattern.compile("^tile\\.planks\\.([^.]*)");
    static private final Pattern stonePattern = Pattern.compile("^tile\\.(stone|granite|limestone|basalt|marble|slate)");

    static public Pair<Boolean, BToolMaterial> tryIsEnoughMaterial(ItemStack stack, BToolPart toolPart) {
        Pair<Boolean, BToolMaterial> falseresult = new Pair<>(false, null);
        if(stack == null || stack.getItem() == null) return falseresult;
        BToolMaterial material = partBuilderItemMap.getOrDefault(stack.getItem(), null);
        if(material == null || stack.stackSize < toolPart.weight) return falseresult;
        else return new Pair<>(true, material);
    }

    public static void InitializeMaterialMaps() {
        for (Item item : Item.itemsList) {
            if(item == null) continue;
            String itemName = item.getKey();
            if (itemName.startsWith("tile.planks.")) {
                partBuilderItemMap.put(item, BToolMaterials.wood);
            } else if (stonePattern.matcher(itemName).find()) {
                partBuilderItemMap.put(item, BToolMaterials.stone);
//            } else if (itemName.startsWith("tile.log.")) {
//                partBuilderBlockMap.put(block, ToolMaterials.wood);
            }
        }
        partBuilderItemMap.put(Item.itemsList[Block.cactus.id], BToolMaterials.cactus);
        partBuilderItemMap.put(Item.itemsList[Block.obsidian.id], BToolMaterials.obsidian);

//        partBuilderItemMap.put(Item.itemsList[Block.blockIron.blockID], ToolMaterials.iron);
//        partBuilderItemMap.put(Item.itemsList[Block.blockGold.blockID], ToolMaterials.gold);
//        partBuilderItemMap.put(Item.itemsList[Block.blockDiamond.blockID], ToolMaterials.diamond);
//        partBuilderItemMap.put(Item.itemsList[Block.blockSteel.blockID], ToolMaterials.steel);


//        itemMap.put(Item.nuggetIron, ToolMaterials.iron, ONE_NINTH);
//        itemMap.put(Item.nuggetGold, ToolMaterials.gold, ONE_NINTH);

//        itemMap.put(Item.stick, ToolMaterials.wood, Fraction.ONE_HALF);

        partBuilderItemMap.put(Item.bone, BToolMaterials.bone);
        partBuilderItemMap.put(Item.flint, BToolMaterials.flint);
        partBuilderItemMap.put(Item.quartz, BToolMaterials.quartz);

        partBuilderItemMap.put(Item.ingotIron, BToolMaterials.iron);
        partBuilderItemMap.put(Item.ingotGold, BToolMaterials.gold);
        partBuilderItemMap.put(Item.ingotSteel, BToolMaterials.steel);
        partBuilderItemMap.put(Item.diamond, BToolMaterials.diamond);

        partBuilderItemMap.put(Item.string, BToolMaterials.string);
        partBuilderItemMap.put(Item.leather, BToolMaterials.leather);
        partBuilderItemMap.put(Item.chainlink, BToolMaterials.chain);


        /*for (Block block : Block.blocksList) {
            String blockName = block.getBlockName(0);
            if (blockName.startsWith("planks.")) {
                blockMap.put(block, new ValuePair(ToolMaterials.wood, Fraction.ONE));
            } else if (stonePattern.matcher(blockName).find()) {
                blockMap.put(block, new ValuePair(ToolMaterials.stone, Fraction.ONE));
            } else if (blockName.startsWith("log.")) {
                blockMap.put(block, new ValuePair(ToolMaterials.wood, FOUR));
            }
        }
        blockMap.put(Block.cactus, new ValuePair(ToolMaterials.cactus, Fraction.ONE));
        blockMap.put(Block.obsidian, new ValuePair(ToolMaterials.obsidian, Fraction.ONE));

        blockMap.put(Block.blockIron, new ValuePair(ToolMaterials.iron, NINE));
        blockMap.put(Block.blockGold, new ValuePair(ToolMaterials.gold, NINE));
        blockMap.put(Block.blockDiamond, new ValuePair(ToolMaterials.diamond, NINE));
        blockMap.put(Block.blockSteel, new ValuePair(ToolMaterials.steel, NINE));


//        itemMap.put(Item.nuggetIron, new ValuePair(ToolMaterials.iron, ONE_NINTH));
//        itemMap.put(Item.nuggetGold, new ValuePair(ToolMaterials.gold, ONE_NINTH));

//        itemMap.put(Item.stick, new ValuePair(ToolMaterials.wood, Fraction.ONE_HALF));

        itemMap.put(Item.bone, new ValuePair(ToolMaterials.bone, Fraction.ONE));
        itemMap.put(Item.flint, new ValuePair(ToolMaterials.flint, Fraction.ONE));
        itemMap.put(Item.quartz, new ValuePair(ToolMaterials.quartz, Fraction.ONE));
        itemMap.put(Item.ingotIron, new ValuePair(ToolMaterials.iron, Fraction.ONE));
        itemMap.put(Item.ingotGold, new ValuePair(ToolMaterials.gold, Fraction.ONE));
        itemMap.put(Item.ingotSteel, new ValuePair(ToolMaterials.steel, Fraction.ONE));
        itemMap.put(Item.diamond, new ValuePair(ToolMaterials.diamond, Fraction.ONE));*/
    }
}
