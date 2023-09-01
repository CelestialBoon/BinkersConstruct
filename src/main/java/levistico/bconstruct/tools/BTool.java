package levistico.bconstruct.tools;

import com.mojang.nbt.CompoundTag;
import com.mojang.nbt.IntTag;
import com.mojang.nbt.ListTag;
import levistico.bconstruct.BConstruct;
import levistico.bconstruct.materials.BToolMaterial;
import levistico.bconstruct.parts.BToolPart;
import levistico.bconstruct.parts.BToolParts;
import levistico.bconstruct.parts.EToolPart;
import levistico.bconstruct.parts.PartsFlag;
import levistico.bconstruct.tools.properties.*;
import levistico.bconstruct.gui.texture.ITexturedPart;
import levistico.bconstruct.tools.actions.*;
import levistico.bconstruct.tools.properties.Properties;
import levistico.bconstruct.tools.stats.EToolStat;
import levistico.bconstruct.tools.stats.StatBoosts;
import levistico.bconstruct.utils.IHasTranslateKey;
import levistico.bconstruct.utils.Pair;
import levistico.bconstruct.utils.Utils;
import net.minecraft.core.block.Block;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

import static levistico.bconstruct.tools.ToolStack.*;

// what should this class contain? what is common between tinkers tools?
// the integer/used/broken mechanic
// the repair-ability (for now at least)

//TODO tool should be stressed more for improper actions (eg. hitting mobs with pickaxe)

public abstract class BTool extends Item implements IHasTranslateKey {

    /*
    BTOOL REWORK
    what a tool has:

    FIXED
    multipliers
    base bonuses
    tool properties
    base actions
    part composition
    part bits and render order

    ONBUILD
    materials
    base stats derived from materials + multipliers
    material properties

    VARIABLE
    extra properties
    total stats

    MIXED
    left click actions
    right click actions
     */

    public final String translationKey;
    public final String name;
    public int baseDamageBonus = 2;
    public float durabilityMultiplier = 1f;
    public float attackDamageMultiplier = 1f;
    public float miningSpeedMultiplier = 1f;
    public float attackSpeedMultiplier = 1f;
    public float expMultiplier = 1f;
    public static final int baseLevelExp = 10;

    public HarvestAction harvestAction = ToolActions.mine;
    public AttackAction attackAction = ToolActions.attack;
    final HarvestLogic harvestLogic;
    public final boolean isWarTool;
    ArrayList<Property> baseToolProperties = new ArrayList<>();
    ArrayList<ToolAction> baseActions = new ArrayList<>();
    ArrayList<RightClickAction> baseRightClickActions = new ArrayList<>();

    public final Pair<Integer, Integer> baseTextureUV; //this is for the base tool representation in the tool builders
    public final List<BToolPart> composition = new ArrayList<>();
    public final List<ITexturedPart> texturedParts = new ArrayList<>();
    public final List<Integer> renderOrder = new ArrayList<>();
    public Pair<Integer, Integer>[] slotArrangement;

    protected BTool(int id, String name, HarvestLogic harvestLogic, Boolean isWarTool, Pair<Integer, Integer> baseTextureUV) {
        super(id);
        this.name = name;
        this.translationKey = String.format("item.%s.%s", BConstruct.MOD_ID, name);
        this.harvestLogic = harvestLogic;
        this.isWarTool = isWarTool;
        this.baseTextureUV = baseTextureUV;
        this.maxStackSize = 1;
        this.setMaxDamage(1); //this is to enable the damage bar
        this.notInCreativeMenu = true;
    }

    //////////////////////////////// SETTERS ///////////////////////////////////
    public BTool addBaseToolProperty(Property property) {baseToolProperties.add(property); return this;}
    public BTool addBaseAction(ToolAction action) {baseActions.add(action); return this;}
    public BTool addBaseRightClickAction(RightClickAction action) {baseRightClickActions.add(action); return this;}
    public BTool setBaseDamageBonus(int value) {this.baseDamageBonus = value; return this;}
    public BTool setDurabilityMultiplier(float value) {this.durabilityMultiplier = value; return this;}
    public BTool setAttackDamageMultiplier(float value) {this.attackDamageMultiplier = value; return this;}
    public BTool setMiningSpeedMultiplier(float value) {this.miningSpeedMultiplier = value; return this;}
    public BTool setAttackSpeedMultiplier(float value) {this.attackSpeedMultiplier = value; return this;}
    public BTool setExpMultiplier(float value) {this.expMultiplier = value; return this;}
    public BTool setharvestAction(HarvestAction value) {this.harvestAction = value; return this;}
    public BTool setattackAction(AttackAction value) {this.attackAction = value; return this;}

    //////////////////////// OTHER ///////////////////

    public static void setCustomName(ItemStack stack, String name) {
        stack.getData().putString(NAME, name);
        stack.getData().putBoolean(IS_CUSTOM_NAME, true);
    }

    /*public ItemStack onItemRightClick(@NotNull ItemStack itemstack, World world, EntityPlayer entityPlayer) {
        StringBuilder sb = new StringBuilder("Composition: ");

        BToolMaterial[] materials = getMaterials(itemstack);
        for(int i = 0; i< materials.length; i++) {
            sb.append(String.format("Material%d:", i));
            sb.append(materials[i].getName());
            sb.append(", ");
        }

        entityPlayer.addChatMessage(sb.toString());

        entityPlayer.addChatMessage(String.format("Mining level: %d, Efficiency: %.1f, Damage: %d, Durability: %d, MaxDurability: %d, Experience:%d, Level:%d, Silktouch:%d",
                getTotalTags(itemstack).getInteger(MININGLEVEL),
                getTotalTags(itemstack).getFloat(EFFICIENCY),
                getTotalTags(itemstack).getInteger(MOBDAMAGE),
                itemstack.getMetadata(),
                getTotalTags(itemstack).getInteger(DURABILITY),
                itemstack.getData().getInteger(EXPERIENCE),
                itemstack.getData().getInteger(LEVEL),
                getPropertyTags(getTotalTags(itemstack)).getInteger(Properties.SILKTOUCH)));
        return itemstack;
    }*/
    public String getTranslateKey() {
        return translationKey;
    }
    public int getPartFlag(int i) {
        return composition.get(i).partFlag;
    }

    public CompoundTag initializeTags(@NotNull CompoundTag rootTags,
                                      List<BToolMaterial> materials) {
        rootTags.putBoolean(BROKEN, false);

        ///////////////////////MATERIALS/////////////////////////
        ListTag materialsTag = new ListTag();
        rootTags.put(MATERIALS, materialsTag);

        List<BToolMaterial> listHeadMaterials = new ArrayList<>();
        List<BToolMaterial> listBindingMaterials = new ArrayList<>();
        List<BToolMaterial> listHandleMaterials = new ArrayList<>();
        Set<BToolMaterial> setHeadMaterials = new LinkedHashSet<>();
        for(Integer i : Utils.range(0, materials.size())) {
            switch (getPartFlag(i)) {
                case PartsFlag.HEAD:
                    setHeadMaterials.add(materials.get(i));
                    listHeadMaterials.add(materials.get(i));
                    break;
                case PartsFlag.BINDING:
                    listBindingMaterials.add(materials.get(i));
                    break;
                case PartsFlag.HANDLE:
                    listHandleMaterials.add(materials.get(i));
                    break;
            }
            materialsTag.addTag(new IntTag(materials.get(i).eNumber));
        }

        ///////////////////////NAME/////////////////////////
        if(! rootTags.getBoolean(IS_CUSTOM_NAME)) {
            rootTags.putString(NAME, String.format("%s %s", StringUtils.join(setHeadMaterials.stream().map(Utils::translateKey).collect(Collectors.toList()), '-'), Utils.translateKey(this)));
        }
        if(! rootTags.containsKey(COLOR)) {
            rootTags.putByte(COLOR, (byte) 0);
        }

        ///////////////////////BASE STATS/////////////////////////
        CompoundTag baseTags = new CompoundTag();
        rootTags.putCompound(BASE_STATS, baseTags);

        baseTags.putInt(MININGLEVEL, listHeadMaterials.stream().map(BToolMaterial::getMiningLevel).max(Integer::compareTo).orElse(0));

        Float baseDamage = Utils.average(listHeadMaterials.stream().map(mat -> (float)mat.getAttackDamage()), listHeadMaterials.size());
        baseTags.putFloat(ATTACKDAMAGE, baseDamageBonus + attackDamageMultiplier * baseDamage);

        Float baseMiningSpeed = Utils.average(listHeadMaterials.stream().map(BToolMaterial::getMiningSpeed), listHeadMaterials.size());
        baseTags.putFloat(MININGSPEED, miningSpeedMultiplier * baseMiningSpeed);

        //TODO include handles etc. in durability calculation and all the extra stuff
        int numerator = 0;
        int denominator = 0;
        for(Integer i: Utils.range(0, listHeadMaterials.size())) {
            BToolPart part = composition.get(i);
            denominator += part.weight;
            numerator += part.weight * listHeadMaterials.get(i).getDurability();
        }
        baseTags.putInt(DURABILITY,  Utils.round(durabilityMultiplier * numerator / denominator));

        ///////////////////////BASE PROPERTIES/////////////////////////
        ArrayList<Property> baseProperties = new ArrayList<>(baseToolProperties);

        //add properties from materials
        listHeadMaterials.stream().flatMap(mat -> mat.getProperties(PartsFlag.HEAD).stream()).forEach(baseProperties::add);
        listHandleMaterials.stream().flatMap(mat -> mat.getProperties(PartsFlag.HANDLE).stream()).forEach(baseProperties::add);
        listBindingMaterials.stream().flatMap(mat -> mat.getProperties(PartsFlag.BINDING).stream()).forEach(baseProperties::add);

        //collapse list summing levels
        baseProperties = new ArrayList<>(Properties.collapse(baseProperties.stream()));

        ToolStack.writeProperties(baseTags, baseProperties);

        calculateTotalTags(rootTags);
        return rootTags;
    }

    public CompoundTag calculateTotalTags(@NotNull CompoundTag rootTags) {
        CompoundTag baseTags = rootTags.getCompound(BASE_STATS);
        CompoundTag upgradeTags = rootTags.getCompound(UPGRADES);

        CompoundTag totalTags = new CompoundTag();
        rootTags.putCompound(TOTAL_STATS, totalTags);

        ///////////////////////PROPERTIES/////////////////////////
        Collection<Property> properties = ToolStack.getProperties(baseTags);
        properties.addAll(ToolStack.getProperties(upgradeTags));

        properties = Properties.collapse(properties.stream());

        ToolStack.writeProperties(totalTags, properties);

        ///////////////////////TOTAL STATS/////////////////////////
        StatBoosts totalBoosts = PropertyStats.getTotalStatBoosts(properties.stream().flatMap(Utils.instancesOf(PropertyStats.class)));
        totalTags.putInt(MININGLEVEL, totalBoosts.applyMiningLevel(baseTags.getInteger(MININGLEVEL)));

        totalTags.putInt(ATTACKDAMAGE, Utils.round(totalBoosts.apply(EToolStat.attackDamage.ordinal(), baseTags.getFloat(ATTACKDAMAGE))));

        totalTags.putFloat(MININGSPEED, totalBoosts.apply(EToolStat.miningSpeed.ordinal(), baseTags.getFloat(MININGSPEED)));

        totalTags.putInt(DURABILITY,  Utils.round(totalBoosts.apply(EToolStat.durability.ordinal(), baseTags.getInteger(DURABILITY))));

        totalTags.putFloat(REINFORCED, totalBoosts.apply(EToolStat.reinforced.ordinal(), 1f));

        ///////////////////////RIGHTCLICK ACTIONS/////////////////////////
        List<RightClickAction> rcactions = new ArrayList<>(baseRightClickActions);

        //TODO integrate upgrades

        ToolStack.writeRightClickActions(totalTags, rcactions);

        return rootTags;
    }

    /////////////////////////////// REDIRECTS ////////////////////////////////////
    @Override
    public float getStrVsBlock(ItemStack itemstack, Block block) {
        return ToolStack.getStrVsBlock(itemstack, this, block);
    }
    @Override
    public boolean onBlockDestroyed(ItemStack itemstack, int id, int x, int y, int z, EntityLiving player) {
       return ToolStack.onBlockDestroyed(itemstack, id, x,y,z, player);
    }
    @Override
    public boolean onItemUse(ItemStack itemstack, EntityPlayer entityPlayer, World world, int blockX, int blockY, int blockZ, Side side, double xPlaced, double yPlaced) {
        return ToolStack.onItemUse(itemstack, entityPlayer, world, blockX, blockY, blockZ, side, xPlaced, yPlaced);
    }
    @Override
    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityPlayer) {
        return ToolStack.onItemRightClick(itemstack, world, entityPlayer);
    }
    @Override
    public boolean useItemOnEntity(ItemStack itemstack, EntityLiving entityliving, EntityPlayer entityPlayer) {
        return ToolStack.useItemOnEntity(itemstack, entityliving, entityPlayer);
    }
    @Override
    public boolean hitEntity(ItemStack itemstack, EntityLiving mob, EntityLiving player) {
        return ToolStack.hitEntity(itemstack, this, mob, player);
    }
    public boolean isFull3D() {
        return true;
    }
}
