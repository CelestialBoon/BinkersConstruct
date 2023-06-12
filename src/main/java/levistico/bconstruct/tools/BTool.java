package levistico.bconstruct.tools;

import levistico.bconstruct.materials.BToolMaterial;
import levistico.bconstruct.materials.BToolMaterials;
import levistico.bconstruct.mixin.AccessorNBTTagCompound;
import levistico.bconstruct.parts.BToolParts;
import levistico.bconstruct.parts.EToolPart;
import levistico.bconstruct.parts.PartFlags;
import levistico.bconstruct.properties.Properties;
import levistico.bconstruct.gui.texture.ITexturedPart;
import levistico.bconstruct.utils.Pair;
import levistico.bconstruct.utils.Utils;
import net.minecraft.src.*;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

// what should this class contain? what is common between tinkers tools?
// the integer/used/broken mechanic
// the repair-ability (for now at least)

//TODO tool should be stressed more for improper actions (eg. hitting mobs with pickaxe)

public abstract class BTool extends Item {

    public static final String MATERIALS = "bic_materials";
    public static final String BASE_STATS = "bic_base";
    public static final String UPGRADES = "bic_upgrades";
    public static final String TOTAL_STATS = "bic_total";
    public static final String PROPERTIES = "properties";

    public static final String MOBDAMAGE = "mobdamage";
    public static final String EFFICIENCY = "efficiency";
    public static final String MININGLEVEL = "mininglevel";
    public static final String DURABILITY = "durability";

    public static final String EXPERIENCE = "experience";
    public static final String LEVEL = "level";
    public static final String BROKEN = "broken";

    public static final String IS_CUSTOM_NAME = "iscustomname";
    public static final String NAME = "name";

    public final String toolName;
    public final String toolFolder;
    private final Material[] materialsEffectiveAgainst;
    public final float baseDurabilityMultiplier = 1;
    public final float baseEfficiencyMultiplier = 1;
    public final int baseDamageBonus = 2;
    public final int baseDamageMultiplier = 1;
    public final int baseLevelExp = 1;
    public final Pair<Integer, Integer> baseTextureUV;
    public final List<EToolPart> composition = new ArrayList<>();
    public final List<ITexturedPart> texturedParts = new ArrayList<>();
    public final List<Integer> renderOrder = new ArrayList<>();

    protected BTool(int id, String toolName, String toolFolder, Material[] materialsEffectiveAgainst, Pair<Integer, Integer> baseTextureUV) {
        super(id);
        this.toolName = toolName;
        this.toolFolder = toolFolder;
        this.materialsEffectiveAgainst = materialsEffectiveAgainst;
        this.baseTextureUV = baseTextureUV;
        this.maxStackSize = 1;
        this.setMaxDamage(1); //this is to enable the damage bar
        this.notInCreativeMenu = true;
    }

    @Override
    public String getItemNameIS(ItemStack itemstack) {
        return getItemNickname(itemstack);
    }

    public static void setCustomName(ItemStack stack, String name) {
        stack.tag.setString(NAME, name);
        stack.tag.setBoolean(IS_CUSTOM_NAME, true);
    }

    public ItemStack onItemRightClick(@NotNull ItemStack itemstack, World world, EntityPlayer entityplayer) {
        StringBuilder sb = new StringBuilder("Composition: ");

        BToolMaterial[] materials = getMaterials(itemstack);
        for(int i = 0; i< materials.length; i++) {
            sb.append(String.format("Material%d:", i));
            sb.append(materials[i].getName());
            sb.append(", ");
        }

        entityplayer.addChatMessage(sb.toString());

        entityplayer.addChatMessage(String.format("Mining level: %d, Efficiency: %.1f, Damage: %d, Durability: %d, MaxDurability: %d, Experience:%d, Level:%d, Silktouch:%d",
                getTotalTags(itemstack).getInteger(MININGLEVEL),
                getTotalTags(itemstack).getFloat(EFFICIENCY),
                getTotalTags(itemstack).getInteger(MOBDAMAGE),
                itemstack.getMetadata(),
                getTotalTags(itemstack).getInteger(DURABILITY),
                itemstack.tag.getInteger(EXPERIENCE),
                itemstack.tag.getInteger(LEVEL),
                getPropertyTags(getTotalTags(itemstack)).getInteger(Properties.SILKTOUCH)));
        return itemstack;
    }

    public static NBTTagList getMaterialTags(@NotNull ItemStack stack) {
        return stack.tag.getTagList(MATERIALS);
    }

    public static NBTTagCompound getBaseTags(@NotNull ItemStack stack) {
        return stack.tag.getCompoundTag(BASE_STATS);
    }
    public static NBTTagCompound getUpgradeTags(@NotNull ItemStack stack) {
        return stack.tag.getCompoundTag(UPGRADES);
    }
    public static NBTTagCompound getTotalTags(@NotNull ItemStack stack) {
        return stack.tag.getCompoundTag(TOTAL_STATS);
    }
    public static NBTTagCompound getPropertyTags(@NotNull NBTTagCompound tags) {
        return tags.getCompoundTag(PROPERTIES);
    }


    public int getPartFlag(int i) {
        return BToolParts.partArray.get(composition.get(i).ordinal()).partFlag;
    }

    public NBTTagCompound constructBaseTags(@NotNull NBTTagCompound rootTags,
                                            List<BToolMaterial> materials) {
        rootTags.setBoolean(BROKEN, false);

        ///////////////////////MATERIALS/////////////////////////
        NBTTagList materialsTag = new NBTTagList();
        rootTags.setTag(MATERIALS, materialsTag);

        List<BToolMaterial> listHeadMaterials = new ArrayList<>();
        List<BToolMaterial> listBindingMaterials = new ArrayList<>();
        List<BToolMaterial> listHandleMaterials = new ArrayList<>();
        Set<BToolMaterial> setHeadMaterials = new LinkedHashSet<>();
        for(int i = 0; i < materials.size(); i++) {
            switch (getPartFlag(i)) {
                case PartFlags.HEAD:
                    setHeadMaterials.add(materials.get(i));
                    listHeadMaterials.add(materials.get(i));
                    break;
                case PartFlags.BINDING:
                    listBindingMaterials.add(materials.get(i));
                    break;
                case PartFlags.HANDLE:
                    listHandleMaterials.add(materials.get(i));
                    break;
            }
            materialsTag.setTag(new NBTTagInt(materials.get(i).eNumber));
        }

        ///////////////////////NAME/////////////////////////
        if(! rootTags.getBoolean(IS_CUSTOM_NAME)) {
            rootTags.setString(NAME, String.format("%s %s", StringUtils.join(setHeadMaterials.stream().map(BToolMaterial::getName).collect(Collectors.toList()), '-'), toolName));
        }

        ///////////////////////BASE STATS/////////////////////////
        NBTTagCompound baseTags = new NBTTagCompound();
        rootTags.setCompoundTag(BASE_STATS, baseTags);

        baseTags.setInteger(MININGLEVEL, listHeadMaterials.stream().map(BToolMaterial::getMiningLevel).max(Integer::compareTo).orElse(0));

        Integer baseDamage = Utils.sumInt(listHeadMaterials.stream().map(BToolMaterial::getMobDamage));
        baseTags.setInteger(MOBDAMAGE, baseDamageBonus + baseDamageMultiplier * baseDamage);

        Float baseEfficiency = Utils.average(listHeadMaterials.stream().map(BToolMaterial::getEfficiency), listHeadMaterials.size());
        baseTags.setFloat(EFFICIENCY, baseEfficiencyMultiplier * baseEfficiency);

        //TODO include handles etc. in durability calculation and all the extra stuff
        Integer baseDurability = getBaseDurability(listHeadMaterials, listBindingMaterials, listHandleMaterials);
        baseTags.setInteger(DURABILITY,  (int)(baseDurabilityMultiplier * baseDurability));

        ///////////////////////BASE PROPERTIES/////////////////////////
        NBTTagCompound propertiesTag = new NBTTagCompound();
        baseTags.setCompoundTag(PROPERTIES, propertiesTag);

        propertiesTag.setInteger(Properties.SILKTOUCH, setHeadMaterials.contains(BToolMaterials.gold) ? 1 : 0);

        calculateTotalTags(rootTags);
        return rootTags;
    }

    public NBTTagCompound calculateTotalTags(@NotNull NBTTagCompound rootTags) {
        NBTTagCompound baseTags = rootTags.getCompoundTag(BASE_STATS);
        NBTTagCompound totalTags = new NBTTagCompound();
        rootTags.setCompoundTag(TOTAL_STATS, totalTags);

        ///////////////////////PROPERTIES/////////////////////////
        NBTTagCompound baseProperties = rootTags.getCompoundTag(BASE_STATS).getCompoundTag(PROPERTIES);
        NBTTagCompound upgradeProperties = rootTags.getCompoundTag(UPGRADES).getCompoundTag(PROPERTIES);

        NBTTagCompound totalProperties = new NBTTagCompound();
        totalTags.setCompoundTag(PROPERTIES, totalProperties);

        Set<String> propertiesKeys = ((AccessorNBTTagCompound)(Object)baseProperties).getTagMap().keySet();
        propertiesKeys.addAll(((AccessorNBTTagCompound)(Object)upgradeProperties).getTagMap().keySet());

        for(String key : propertiesKeys) {
            totalProperties.setInteger(key, baseProperties.getInteger(key) + upgradeProperties.getInteger(key));
        }

        ///////////////////////TOTAL STATS/////////////////////////
        //TODO multiply total stats with multipliers
        totalTags.setInteger(MININGLEVEL, baseTags.getInteger(MININGLEVEL));

        totalTags.setInteger(MOBDAMAGE, baseTags.getInteger(MOBDAMAGE));

        totalTags.setFloat(EFFICIENCY, baseTags.getFloat(EFFICIENCY));

        totalTags.setInteger(DURABILITY,  baseTags.getInteger(DURABILITY));

        return rootTags;
    }

    private static Integer getBaseDurability(List<BToolMaterial> headMaterials, List<BToolMaterial> listBindingMaterials, List<BToolMaterial> listHandleMaterials) { //TODO expand (that differs (is overridden) for some complicated tools, but that's for later)
        return Utils.sumInt(headMaterials.stream().map(BToolMaterial::getDurability));
    }
    public static boolean isToolBroken(@NotNull ItemStack itemstack) {
        return itemstack.tag.getBoolean(BROKEN);
    }
    public static int getMaxDurability(@NotNull ItemStack stack) {
        return getTotalTags(stack).getInteger(DURABILITY);
    }
    public static int getMiningLevel(@NotNull ItemStack stack) {
        return getTotalTags(stack).getInteger(MININGLEVEL);
    }
    public static float getEfficiency(@NotNull ItemStack stack) {
        return getTotalTags(stack).getFloat(EFFICIENCY);
    }
    public static int getMobDamage(@NotNull ItemStack stack) {
        return getTotalTags(stack).getInteger(MOBDAMAGE);
    }
    public static BToolMaterial[] getMaterials(@NotNull ItemStack stack) {
        NBTTagList materialTags = getMaterialTags(stack);
        int numMats = materialTags.tagCount();
        BToolMaterial[] materials = new BToolMaterial[numMats];
        for(int i = 0; i < numMats; i++) {
            materials[i] = BToolMaterials.matArray.get(((NBTTagInt)materialTags.tagAt(i)).intValue);
        }
        return materials;
    }
    public Set<BToolMaterial> getRepairMaterials(ItemStack stack) {
        BToolMaterial[] materials = getMaterials(stack);

        Set<BToolMaterial> materialSet = new HashSet<>();
        for(BToolMaterial mat : materials) {
            materialSet.add(mat);
        }

        return materialSet;
    }

    public float getStrVsBlock(@NotNull ItemStack itemstack, Block block) {
        if(isToolBroken(itemstack)) return 0.1f;
        else if(Arrays.stream(this.materialsEffectiveAgainst).anyMatch(material -> material == block.blockMaterial))
            return getTotalTags(itemstack).getFloat(EFFICIENCY);
        else return 1.0F;
    }

    public boolean hitEntity(ItemStack itemstack, EntityLiving mob, EntityLiving player) {
        stressTool(1, itemstack, player);
        return true;
    }

    public boolean onBlockDestroyed(ItemStack itemstack, int i, int j, int k, int l, EntityLiving player) {
        Block block = Block.blocksList[i];
        if (block != null && block.getHardness() > 0.0F) {
            stressTool(1, itemstack, player);
        }
        return true;
    }

    public static void stressTool(int damageAmount, ItemStack itemStack, Entity entity) {
        if (!isToolBroken(itemStack) && entity instanceof EntityPlayer && ((EntityPlayer)entity).getGamemode().toolDurability) {
            earnToolExp(1, itemStack, (EntityPlayer) entity);
            //here goes reinforced probability calculations to avoid damage (but still earn xp)
            if(true) { //TODO checking for reinforced and shiz
                updateToolDamage(damageAmount, itemStack);
            }
        }
    }

    public static void earnToolExp(int i, ItemStack itemStack, EntityPlayer player) {
        BTool tool = (BTool) itemStack.getItem();
        NBTTagCompound tags = itemStack.tag;
        int experience = tags.getInteger(EXPERIENCE) + i;
        tags.setInteger(EXPERIENCE, experience);
        //check against a table of xp goals and award levels
        int newLevel = 0;
        int expToLevel = tool.baseLevelExp;
        while(experience >= expToLevel) {
            expToLevel *= 2; //FIXME change this to 3 once testing is done (since we want to double the effort each time)
            newLevel++;
        }
        int level = tags.getInteger(LEVEL);
        for(int j = level+1; j <= newLevel; j++) {
            //TODO level up sequence!
            //play ding
            //send text message
//            player.addChatMessage(String.format("Level up! Your %s has reached level %d!", itemStack.getItemName(), j));
            tags.setInteger(LEVEL, j);
            //levels in turn will award other things, like extra upgrade slots
        }
    }

    public static void repairTool(int healAmount, ItemStack itemStack) {
        updateToolDamage(-healAmount, itemStack);
    }

    public static void updateToolDamage(int damageAmount, ItemStack itemStack) {
        int damage = itemStack.getMetadata();
        boolean broken = false;
        damage += damageAmount;
        if (damage >= getMaxDurability(itemStack)) {
            damage = getMaxDurability(itemStack);
            broken = true;
        } else if (damage <= 0) {
            damage = 0;
        }
        itemStack.setMetadata(damage);
        itemStack.tag.setBoolean(BROKEN, broken);
    }

    public boolean canHarvestBlock(ItemStack itemstack, Block block) {
        if (isSilkTouch(itemstack)) return true;
        else return Arrays.stream(materialsEffectiveAgainst).anyMatch(material -> material == block.blockMaterial);
    }

    public int getDamageVsEntity(ItemStack itemstack, Entity noUse) {
        if(isToolBroken(itemstack)) return 1;
        else return getTotalTags(itemstack).getInteger(MOBDAMAGE);
    }

    public boolean isFull3D() {
        return true;
    }

    @Override
    public boolean isSilkTouch() {
        throw new RuntimeException("this should never be called");
    }

    public static Integer getProperty(ItemStack stack, String s) {
        return getPropertyTags(getTotalTags(stack)).getInteger(s);
    }
    public static boolean isSilkTouch(ItemStack stack) {
        return getProperty(stack, Properties.SILKTOUCH) > 0;
    }
    public int getBlockHitDelay() {
        return 4;
        //TODO maybe find a way to dynamically use itemstack
        //in PlayerController (to redirect by mixin)
        //this.blockHitDelay = stack.getItem().getBlockHitDelay();
    }
}
