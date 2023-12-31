package levistico.bconstruct.tools;

import levistico.bconstruct.materials.BToolMaterial;
import levistico.bconstruct.materials.BToolMaterials;
import levistico.bconstruct.mixin.AccessorNBTTagCompound;
import levistico.bconstruct.parts.PartsFlag;
import levistico.bconstruct.tools.actions.RightClickAction;
import levistico.bconstruct.tools.actions.RightClickActions;
import levistico.bconstruct.tools.properties.Properties;
import levistico.bconstruct.tools.properties.Property;
import levistico.bconstruct.utils.Utils;
import net.minecraft.src.*;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class ToolStack {
    public static BTool getTool(ItemStack stack) {
        return (BTool) stack.getItem();
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
    public static NBTTagCompound getPropertyTags(@NotNull NBTTagCompound underlyingTags) { //This is inside either of the 3 (base, upgrade, total)
        return underlyingTags.getCompoundTag(PROPERTIES);
    }
    public static NBTTagCompound writeProperties(NBTTagCompound underlyingTags, Collection<Property> properties) {
        NBTTagCompound propertiesTag = new NBTTagCompound();
        underlyingTags.setCompoundTag(PROPERTIES, propertiesTag);
        properties.forEach(p -> propertiesTag.setByte(p.name, (byte)p.level));
        return propertiesTag;
    }
    public static List<Property> getProperties(NBTTagCompound underlyingTags) {
        NBTTagCompound proptags = ToolStack.getPropertyTags(underlyingTags);
        Map<String, NBTBase> propMap = ((AccessorNBTTagCompound)proptags).getTagMap();
        return propMap.keySet().stream().map(s-> Properties.generate(s, proptags.getByte(s))).collect(Collectors.toList());
    }
    public static List<Property> getProperties(ItemStack stack) {
        return getProperties(getTotalTags(stack));
    }

    public static List<RightClickAction> getRightClickActions(ItemStack stack) {
        ArrayList<RightClickAction> actions = new ArrayList<>();
        NBTTagList list = getTotalTags(stack).getTagList(RIGHTCLICKACTIONS);
        Utils.steamTag(list).forEachRemaining(i -> actions.add(RightClickActions.rcactions.get(((NBTTagInt)i).intValue)));
        return actions;
    }
    public static void writeRightClickActions(NBTTagCompound totalTags, List<RightClickAction> rcactions) {
        NBTTagList list = new NBTTagList();
        totalTags.setTag(RIGHTCLICKACTIONS, list);
        rcactions.forEach(a -> list.setTag(new NBTTagInt(a.eRCA)));
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
    public static float getMiningSpeed(@NotNull ItemStack stack) {
        return getTotalTags(stack).getFloat(MININGSPEED);
    }
    public static int getAttackDamage(@NotNull ItemStack stack) {
        return getTotalTags(stack).getInteger(ATTACKDAMAGE);
    }
    public static float getReinforced(@NotNull ItemStack stack) { return getTotalTags(stack).getFloat(REINFORCED);}
    public static BToolMaterial[] getMaterials(@NotNull ItemStack stack) {
        NBTTagList materialTags = getMaterialTags(stack);
        int numMats = materialTags.tagCount();
        BToolMaterial[] materials = new BToolMaterial[numMats];
        for(Integer i : Utils.range(0, numMats)) {
            materials[i] = BToolMaterials.matList.get(((NBTTagInt)materialTags.tagAt(i)).intValue);
        }
        return materials;
    }
    public static Map<Integer, Integer> getRepairMaterials(ItemStack stack) { //<matNumber, how many>
        BToolMaterial[] materials = getMaterials(stack);
        BTool tool = (BTool) stack.getItem();
        Map<Integer, Integer> materialMap = new HashMap<>();
        for(Integer i: Utils.range(0, materials.length)) {
            BToolMaterial mat = materials[i];
            if((tool.getPartFlag(i) & PartsFlag.REPAIR_KIT) > 0 || mat == BToolMaterials.string) {
                materialMap.put(mat.eNumber, 1 + materialMap.getOrDefault(mat.eNumber, 0));
            }
        }
        return materialMap;
    }
    public static void stressTool(int baseDamage, boolean appropriateAction, float conditionalReinforced, ItemStack itemStack, Entity entityPlayer) {
        if (!isToolBroken(itemStack) && baseDamage > 0 && entityPlayer instanceof EntityPlayer && ((EntityPlayer)entityPlayer).getGamemode().toolDurability) {
            if(appropriateAction) earnToolExp(baseDamage, itemStack, (EntityPlayer) entityPlayer);
            //here goes reinforced probability calculations to avoid damage (but still earn xp)
            float reinforced = ToolStack.getReinforced(itemStack) * conditionalReinforced;
            float damage = baseDamage * reinforced;
            updateToolDamage(Utils.roundRandom(damage), itemStack);
        }
    }

    public static void earnToolExp(int i, ItemStack itemStack, EntityPlayer player) {
        BTool tool = (BTool) itemStack.getItem();
        NBTTagCompound tags = itemStack.tag;
        int experience = tags.getInteger(EXPERIENCE) + i;
        tags.setInteger(EXPERIENCE, experience);
        //check against a table of xp goals and award levels
        int newLevel = 0;
        int expToLevel = (int)(BTool.baseLevelExp * tool.expMultiplier);
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

    public static Integer getProperty(ItemStack stack, String s) {
        return (int) getPropertyTags(getTotalTags(stack)).getByte(s);
    }
    public static boolean isSilkTouch(ItemStack stack) {
        return getProperty(stack, Properties.SILKTOUCH) > 0;
    }

    public static boolean canHarvestBlock(ItemStack itemstack, BTool tool, Block block) {
        if (ToolStack.isSilkTouch(itemstack)) return true;
        else {
            return tool.harvestAction.canHarvestBlock(itemstack, tool.harvestLogic, block);
        }
    }
    public static int getBlockHitDelay(ItemStack stack) {
        return 6 - getMiningLevel(stack);
    }
    public static float getStrVsBlock(@NotNull ItemStack itemstack, BTool tool, Block block) {
        if(isToolBroken(itemstack)) return 0.1f;
        if(tool.harvestAction.canHarvestBlock(itemstack, tool.harvestLogic, block)) {
            return getTotalTags(itemstack).getFloat(MININGSPEED);
        }
        return 1.0F;
    }
    public static void harvestBlock(ItemStack stack, Block block, World world, EntityPlayer entityplayer, int x, int y, int z, int blockMetadata) {
        //in here basically you have to handle those special cases like shearing
        if(ToolStack.isToolBroken(stack)) return;
        else if(ToolStack.isSilkTouch(stack)) {
            block.dropBlockWhenCrushed(world, x, y, z, blockMetadata);
            return;
        }
        BTool tool = (BTool) stack.getItem();
        Optional<Supplier<Collection<Item>>> specialLoot = tool.harvestLogic.checkSpecialLoot(block);
        if(specialLoot.isPresent()) {
            Collection<Item> items = specialLoot.get().get();
            for(Item item : items) {
                world.dropItem(x, y, z, new ItemStack(item));
            }
        } else block.harvestBlock(world, entityplayer, x, y, z, blockMetadata);
    }
    public static boolean onBlockDestroyed(ItemStack itemstack, int blockId, int x, int y, int z, EntityLiving player) {
        Block block = Block.blocksList[blockId];
        BTool tool = getTool(itemstack);
        Collection<Property> properties = getProperties(itemstack);
        if (block != null && (block.getHardness() > 0.0F || tool.harvestLogic.checkSpecialLoot(block).isPresent()) && ! tool.harvestLogic.isFreeMaterial(block.blockMaterial)) {
            float conditionalReinforced = 1f;
            Optional<Integer> miningEase = tool.harvestLogic.howEffectiveOn(block, getMiningLevel(itemstack));
            if(miningEase.isPresent()) {
                conditionalReinforced = Utils.reduceProduct(properties.stream().map(p -> p.modifier.getConditionalReinforced(block, miningEase.get()))); }
            stressTool(1, true, conditionalReinforced, itemstack, player);
        }
        return true;
    }

    public static boolean onItemUse(ItemStack itemstack, EntityPlayer entityPlayer, World world, int x, int y, int z, int sideHit, double heightPlaced) {
        if(isToolBroken(itemstack)) return false;
        Optional<Integer> outcome = getRightClickActions(itemstack).stream()
                .map(a ->a.onItemUse(itemstack, entityPlayer, world, x, y, z, sideHit, heightPlaced))
                .filter(Optional::isPresent).findAny().orElse(Optional.empty());
        if(outcome.isPresent()) {
            stressTool(outcome.get(), true, 1, itemstack, entityPlayer);
            return true;
        } else return false;
    }
    public static ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityPlayer) {
        if(isToolBroken(itemstack)) return itemstack;
        Optional<ItemStack> outcome = getRightClickActions(itemstack).stream().map(a -> a.onItemRightClick(itemstack, world, entityPlayer))
                .filter(Optional::isPresent).findAny().orElse(Optional.empty()); //this already only executes one action, so we're good
        return outcome.orElse(itemstack);
    }
    public static boolean useItemOnEntity(ItemStack itemstack, EntityLiving entityliving, EntityPlayer entityPlayer) {
        if(isToolBroken(itemstack)) return false;
        Optional<Integer> outcome = getRightClickActions(itemstack).stream()
                .map(a ->a.useItemOnEntity(itemstack, entityliving, entityPlayer))
                .filter(Optional::isPresent).findAny().orElse(Optional.empty());
        if(outcome.isPresent()) {
            stressTool(outcome.get(), true, 1, itemstack, entityPlayer);
            return true;
        } else return false;
    }

    public static int getDamageVsEntity(ItemStack itemstack, BTool tool, Entity target) {
        if(isToolBroken(itemstack)) return 1;
        else return getTotalTags(itemstack).getInteger(ATTACKDAMAGE);
    }
    public static boolean hitEntity(ItemStack itemstack, BTool tool, EntityLiving mob, EntityLiving player) {
        stressTool(tool.isWarTool ? 1 : 2, tool.isWarTool, 1, itemstack, player);
        return true;
    }

    public static boolean isBTool(ItemStack stack) {
        return stack != null && stack.getItem() instanceof BTool;
    }

    public static final String MATERIALS = "bic_materials";
    public static final String BASE_STATS = "bic_base";
    public static final String UPGRADES = "bic_upgrades";
    public static final String TOTAL_STATS = "bic_total";
    public static final String PROPERTIES = "properties";
    public static final String RIGHTCLICKACTIONS = "rightclickactions";

    public static final String ATTACKDAMAGE = "attackdamage";
    public static final String MININGSPEED = "miningspeed";
    public static final String MININGLEVEL = "mininglevel";
    public static final String DURABILITY = "durability";
    public static final String REINFORCED = "reinforced";

    public static final String EXPERIENCE = "experience";
    public static final String LEVEL = "level";
    public static final String BROKEN = "broken";

    public static final String IS_CUSTOM_NAME = "iscustomname";
    public static final String NAME = "name";
    public static final String COLOR = "color";


}
