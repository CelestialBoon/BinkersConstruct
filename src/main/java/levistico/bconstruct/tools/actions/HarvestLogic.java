package levistico.bconstruct.tools.actions;

import levistico.bconstruct.utils.Pair;
import levistico.bconstruct.utils.Utils;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.item.Item;


import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;

public class HarvestLogic {
    ArrayList<Material> effectiveMaterials;
    ArrayList<Pair<Material, Float>> superEffectiveMaterials = new ArrayList<>();
    ArrayList<Material> freeMaterials = new ArrayList<>();
    Function<Block, Optional<Supplier<Collection<Item>>>> specialLoot = b -> Optional.empty();

    public HarvestLogic(List<Material> effectiveMaterials) {
        this.effectiveMaterials = new ArrayList<>(effectiveMaterials);
    }
    public HarvestLogic multiplyEffectiveness(Material m, float f) {
        superEffectiveMaterials.add(new Pair<>(m, f));
        return this;
    }
    public HarvestLogic setFreeMaterial(Material m) {
        freeMaterials.add(m);
        return this;
    }
    public HarvestLogic setSpecialLoot(Function<Block, Optional<Supplier<Collection<Item>>>> lootChecker) {
        this.specialLoot = lootChecker;
        return this;
    }
    public Optional<Supplier<Collection<Item>>> checkSpecialLoot(Block b) {
        return specialLoot.apply(b);
    }

    public Optional<Integer> howEffectiveOn(Block block, int miningLevel) {
        if(! effectiveMaterials.contains(block.blockMaterial)) return Optional.empty();
        Integer miningChallenge = Utils.orElse(ItemToolPickaxe.miningLevels.get(block), 0);
        return Optional.of(miningLevel - miningChallenge);
    }
    public float getEffectiveness(Material m) { //assume the effective check has been done, so >=1
        return superEffectiveMaterials.stream().filter(p -> p.first == m).findAny().map(p -> p.second).orElse(1f);
    }
    public boolean isFreeMaterial(Material m) {
        return freeMaterials.contains(m);
    }
}
