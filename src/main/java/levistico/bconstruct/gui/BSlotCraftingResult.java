package levistico.bconstruct.gui;

import levistico.bconstruct.crafting.IOnCraftResult;
import net.minecraft.core.achievement.AchievementList;
import net.minecraft.core.block.Block;
import net.minecraft.core.crafting.CraftingManager;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.IInventory;
import net.minecraft.core.player.inventory.InventoryCrafting;
import net.minecraft.core.player.inventory.slot.Slot;

public class BSlotCraftingResult extends Slot {
    private IOnCraftResult container;
    private EntityPlayer player;

    public BSlotCraftingResult(IOnCraftResult container, int id, EntityPlayer entityPlayer, IInventory iinventory, int x, int y) {
        super(iinventory, id, x, y);
        this.player = entityPlayer;
        this.container = container;
    }

    public boolean canPutStackInSlot(ItemStack itemstack) {
        return false;
    }

    public void onPickupFromSlot(final ItemStack itemstack) {
        itemstack.onCrafting(this.player.world, this.player);
        if (itemstack.itemID == Block.workbench.id) {
            this.player.addStat(AchievementList.BUILD_WORKBENCH, 1);
        }
        else if (itemstack.itemID == Item.toolPickaxeWood.id) {
            this.player.addStat(AchievementList.BUILD_PICKAXE, 1);
        }
        else if (itemstack.itemID == Block.furnaceStoneIdle.id) {
            this.player.addStat(AchievementList.BUILD_FURNACE, 1);
        }
        else if (itemstack.itemID == Item.toolHoeWood.id) {
            this.player.addStat(AchievementList.BUILD_HOE, 1);
        }
        else if (itemstack.itemID == Item.foodBread.id) {
            this.player.addStat(AchievementList.MAKE_BREAD, 1);
        }
        else if (itemstack.itemID == Item.foodCake.id) {
            this.player.addStat(AchievementList.BAKE_CAKE, 1);
        }
        else if (itemstack.itemID == Item.toolPickaxeStone.id) {
            this.player.addStat(AchievementList.BUILD_BETTER_PICKAXE, 1);
        }
        else if (itemstack.itemID == Item.toolSwordWood.id) {
            this.player.addStat(AchievementList.BUILD_SWORD, 1);
        }
        else if (itemstack.itemID == Item.handcannonUnloaded.id) {
            this.player.addStat(AchievementList.CRAFT_HANDCANNON, 1);
        }
        else if (itemstack.itemID == Item.armorBootsChainmail.id || itemstack.itemID == Item.armorHelmetChainmail.id || itemstack.itemID == Item.armorChestplateChainmail.id || itemstack.itemID == Item.armorLeggingsChainmail.id) {
            this.player.addStat(AchievementList.REPAIR_ARMOR, 1);
        }

        itemstack.onCrafting(this.player.world, this.player);
        container.onCraftResult(itemstack, player); //todo
    }
}
