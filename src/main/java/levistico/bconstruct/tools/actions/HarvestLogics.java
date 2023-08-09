package levistico.bconstruct.tools.actions;

import levistico.bconstruct.utils.Pair;
import levistico.bconstruct.utils.Utils;
import net.minecraft.core.block.*;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.item.Item;

import java.util.*;

public class HarvestLogics {

    static Material[] swordEffectives = {Material.web};
    static Material[] axeEffectives = {Material.wood, Material.leaves, Material.vegetable, Material.woodWet, Material.cactus};
    static Material[] pickaxeEffectives = {Material.stone, Material.metal, Material.piston, Material.moss, Material.ice, Material.glass};
    static Material[] shovelEffectives = {Material.grass, Material.dirt, Material.clay, Material.sand};
    static Material[] shearsEffectives = {Material.leaves, Material.grass, Material.plant};

    public static HarvestLogic NONE = new HarvestLogic(new ArrayList<>());
    public static HarvestLogic swordHarvestLogic = new HarvestLogic(Arrays.asList(swordEffectives))
            .multiplyEffectiveness(Material.web, 15f);
    public static HarvestLogic hatchetHarvestLogic = new HarvestLogic(Arrays.asList(axeEffectives))
            .setFreeMaterial(Material.leaves);
    public static HarvestLogic pickaxeHarvestLogic = new HarvestLogic(Arrays.asList(pickaxeEffectives));
            //.setFreeMaterial(Material.circuits);
    public static HarvestLogic mattockHarvestLogic = new HarvestLogic(Arrays.asList(Utils.concatArrays(axeEffectives, shovelEffectives)))
            .setFreeMaterial(Material.grass)
            .setSpecialLoot(b -> {
                if (b.id == Block.tallgrass.id)
                    return Optional.of(Utils.lootTable(Collections.singletonList(new Pair<>(0.2f, Item.seedsWheat))));
                else return Optional.empty();
            });
    public static HarvestLogic pickadzeHarvestLogic = new HarvestLogic(Arrays.asList(Utils.concatArrays(pickaxeEffectives, shovelEffectives)));
    public static HarvestLogic shearsHarvestLogic = new HarvestLogic(Arrays.asList(shearsEffectives))
            .multiplyEffectiveness(Material.web, 15f)
            .multiplyEffectiveness(Material.leaves, 3f)
            .setSpecialLoot(b -> {
        if(b instanceof BlockLeavesBase || b instanceof BlockTallGrass || b instanceof BlockCobweb || b instanceof BlockAlgae || b instanceof BlockDeadBush) {
            return Optional.of(Utils.lootTable(Collections.singletonList(new Pair<>(1f, Item.itemsList[b.id]))));
        } else return Optional.empty();
    });
}
