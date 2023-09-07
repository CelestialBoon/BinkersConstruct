package levistico.bconstruct.mixin;


import levistico.bconstruct.crafting.ContainerCraftingStation;
import levistico.bconstruct.crafting.ContainerPartBuilder;
import levistico.bconstruct.crafting.ContainerToolStation;
import levistico.bconstruct.crafting.CraftingTileEntity;
import levistico.bconstruct.mixinInterfaces.IBinkersEntityPlayerMP;
import levistico.bconstruct.utils.InventoryType;
import net.minecraft.core.net.packet.Packet100OpenWindow;
import net.minecraft.server.entity.player.EntityPlayerMP;
import net.minecraft.server.net.handler.NetServerHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = EntityPlayerMP.class, remap = false)
public class MixinEntityPlayerMP implements IBinkersEntityPlayerMP {

    @Shadow
    private void getNextWindowId() {}
    @Shadow
    private int currentWindowId;
    @Shadow
    public NetServerHandler playerNetServerHandler;

    @Override
    public void displayGUICraftingStation(CraftingTileEntity tileEntity) {
        this.getNextWindowId();
        NetServerHandler var10000 = this.playerNetServerHandler;
        //NetServerHandler.logger.info(((EntityPlayerMP)(Object)this).username + " interacted with crafting table at (" + ((EntityPlayerMP)(Object)this).x + ", " + ((EntityPlayerMP)(Object)this).y + ", " + ((EntityPlayerMP)(Object)this).z + ")");
        this.playerNetServerHandler.sendPacket(new Packet100OpenWindow(this.currentWindowId, InventoryType.Crafting.ordinal(), tileEntity.inventoryCrafting.getInvName(), tileEntity.inventoryCrafting.getSizeInventory()));
        ((EntityPlayerMP)(Object)this).craftingInventory = new ContainerCraftingStation(((EntityPlayerMP)(Object)this).inventory, tileEntity);
        ((EntityPlayerMP)(Object)this).craftingInventory.windowId = this.currentWindowId;
        ((EntityPlayerMP)(Object)this).craftingInventory.onContainerInit(((EntityPlayerMP)(Object)this));
    }

    @Override
    public void displayGUIPartBuilder(CraftingTileEntity tileEntity) {
        this.getNextWindowId();
        NetServerHandler var10000 = this.playerNetServerHandler;
        //NetServerHandler.logger.info(((EntityPlayerMP)(Object)this).username + " interacted with part builder at (" + ((EntityPlayerMP)(Object)this).x + ", " + ((EntityPlayerMP)(Object)this).y + ", " + ((EntityPlayerMP)(Object)this).z + ")");
        this.playerNetServerHandler.sendPacket(new Packet100OpenWindow(this.currentWindowId, InventoryType.Crafting.ordinal(), tileEntity.inventoryCrafting.getInvName(), tileEntity.inventoryCrafting.getSizeInventory()));
        ((EntityPlayerMP)(Object)this).craftingInventory = new ContainerPartBuilder(((EntityPlayerMP)(Object)this).inventory, tileEntity);
        ((EntityPlayerMP)(Object)this).craftingInventory.windowId = this.currentWindowId;
        ((EntityPlayerMP)(Object)this).craftingInventory.onContainerInit(((EntityPlayerMP)(Object)this));
    }

    @Override
    public void displayGUIToolStation(CraftingTileEntity tileEntity) {
        this.getNextWindowId();
        NetServerHandler var10000 = this.playerNetServerHandler;
        //NetServerHandler.logger.info(((EntityPlayerMP)(Object)this).username + " interacted with tool builder at (" + ((EntityPlayerMP)(Object)this).x + ", " + ((EntityPlayerMP)(Object)this).y + ", " + ((EntityPlayerMP)(Object)this).z + ")");
        this.playerNetServerHandler.sendPacket(new Packet100OpenWindow(this.currentWindowId, InventoryType.Crafting.ordinal(), tileEntity.inventoryCrafting.getInvName(), tileEntity.inventoryCrafting.getSizeInventory()));
        ((EntityPlayerMP)(Object)this).craftingInventory = new ContainerToolStation(((EntityPlayerMP)(Object)this).inventory, tileEntity);
        ((EntityPlayerMP)(Object)this).craftingInventory.windowId = this.currentWindowId;
        ((EntityPlayerMP)(Object)this).craftingInventory.onContainerInit(((EntityPlayerMP)(Object)this));
    }
}
