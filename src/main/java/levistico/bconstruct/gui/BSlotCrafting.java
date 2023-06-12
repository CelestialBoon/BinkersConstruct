package levistico.bconstruct.gui;

import levistico.bconstruct.crafting.IOnCraftResult;
import net.minecraft.src.*;

public class BSlotCrafting extends Slot {
    private IOnCraftResult container;
    private EntityPlayer player;

    public BSlotCrafting(IOnCraftResult container, int id, EntityPlayer entityplayer, IInventory iinventory, int x, int y) {
        super(iinventory, id, x, y);
        this.player = entityplayer;
        this.container = container;
    }

    public boolean canPutStackInSlot(ItemStack itemstack) {
        return false;
    }

    public void onPickupFromSlot(ItemStack stack) {
        //crafting achievements here
        if (stack.itemID == Block.workbench.blockID) {
            player.addStat(AchievementList.buildWorkBench, 1);
        } else if (stack.itemID == Item.toolPickaxeWood.itemID) {
            player.addStat(AchievementList.buildPickaxe, 1);
        } else if (stack.itemID == Block.furnaceStoneIdle.blockID) {
            player.addStat(AchievementList.buildFurnace, 1);
        } else if (stack.itemID == Item.toolHoeWood.itemID) {
            player.addStat(AchievementList.buildHoe, 1);
        } else if (stack.itemID == Item.foodBread.itemID) {
            player.addStat(AchievementList.makeBread, 1);
        } else if (stack.itemID == Item.foodCake.itemID) {
            player.addStat(AchievementList.bakeCake, 1);
        } else if (stack.itemID == Item.toolPickaxeStone.itemID) {
            player.addStat(AchievementList.buildBetterPickaxe, 1);
        } else if (stack.itemID == Item.toolSwordWood.itemID) {
            player.addStat(AchievementList.buildSword, 1);
        } else if (stack.itemID == Item.handcannonUnloaded.itemID) {
            player.addStat(AchievementList.craftHandcannon, 1);
        } else if (stack.itemID == Item.armorBootsChainmail.itemID || stack.itemID == Item.armorHelmetChainmail.itemID || stack.itemID == Item.armorChestplateChainmail.itemID || stack.itemID == Item.armorLeggingsChainmail.itemID) {
            player.addStat(AchievementList.repairArmour, 1);
        }

        stack.onCrafting(this.player.worldObj, this.player);
        container.onCraftResult(stack, player);
    }
}
