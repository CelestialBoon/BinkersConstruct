package levistico.bconstruct.tools.actions;

import levistico.bconstruct.utils.Utils;
import levistico.bconstruct.utils.Vec3;
import net.minecraft.src.*;

import java.util.ArrayList;
import java.util.Optional;

public class RightClickActions {
    public static ArrayList<RightClickAction> rcactions = new ArrayList<>(); //items are automatically added to this list
    public static final RightClickAction TILL = new RCAOnWorld(ERCActions.Till.ordinal(), (itemstack, entityplayer, world, x, y, z, sideHit, heightPlaced) -> {
            int i1 = world.getBlockId(x, y, z);
            int j1 = world.getBlockId(x, y + 1, z);
            if (!(sideHit != 0 && j1 == 0 && (i1 == Block.grass.blockID || i1 == Block.dirt.blockID || i1 == Block.pathDirt.blockID || i1 == Block.grassRetro.blockID))) {
                return Optional.empty();
            }
            Block block = Block.farmlandDirt;
            world.playSoundEffect( ((float) x + 0.5F),  ((float) y + 0.5F),  ((float) z + 0.5F), block.stepSound.func_1145_d(), (block.stepSound.getVolume() + 1.0F) / 2.0F, block.stepSound.getPitch() * 0.8F);
            if (!world.isMultiplayerAndNotHost) {
                world.setBlockWithNotify(x, y, z, block.blockID);
            }
            return Optional.of(1);
        });
    public static final RightClickAction REPLANT = new RCAOnWorld(ERCActions.Replant.ordinal(), (stack, player, world, x, y, z, sideHit, heightPlaced) -> {
        if(world.getBlockId(x, y, z) != Block.cropsWheat.blockID || world.getBlockMetadata(x, y, z) != 7) return Optional.empty();
        world.setBlockMetadata(x, y, z, 0);
        world.markBlockNeedsUpdate(x, y, z);
        world.dropItem(x, y, z, new ItemStack(Item.wheat));
        return Optional.of(1);
    });
    public static final RightClickAction PATH = new RCAOnWorld(ERCActions.Path.ordinal(), (itemstack, entityplayer, world, x, y, z, sideHit, heightPlaced) -> {
        int id = world.getBlockId(x, y, z);
        int idAbove = world.getBlockId(x, y + 1, z);
        if (!(sideHit != 0 && idAbove == 0 && (id == Block.grass.blockID || id == Block.dirt.blockID || id == Block.pathDirt.blockID || id == Block.grassRetro.blockID))) {
            return Optional.empty();
        }
        Block block = Block.pathDirt;
        world.playSoundEffect( ((float) x + 0.5F),  ((float) y + 0.5F),  ((float) z + 0.5F), block.stepSound.func_1145_d(), (block.stepSound.getVolume() + 1.0F) / 2.0F, block.stepSound.getPitch() * 0.8F);
        if (!world.isMultiplayerAndNotHost) {
            world.setBlockWithNotify(x, y, z, block.blockID);
        }
        return Optional.of(1);
    });
    public static final RightClickAction FIRESTRIKER = new RCAOnWorld(ERCActions.Firestriker.ordinal(), (itemstack, player, world, x, y, z, sideHit, heightPlaced) -> {
        Vec3 b = Utils.getBlockToSide(x, y, z, sideHit);
        if (world.getBlockId(b.x, b.y, b.z) != 0 || !world.setBlockWithNotify(b.x, b.y, b.z, Block.fire.blockID)) return Optional.empty();
        world.playSoundEffect(b.x + 0.5, b.y + 0.5, b.z + 0.5, "fire.ignite", 1.0f, Utils.random.nextFloat() * 0.4f + 0.8f);
        return Optional.of(1);
    });
    public static final RightClickAction SHEAR = new RCAOnEntity(ERCActions.Shear.ordinal(), (itemStack, entity, player) -> { //this is just for the sheep for now
        if(player.worldObj.isMultiplayerAndNotHost || !(entity instanceof EntitySheep) || ((EntitySheep) entity).getSheared()) return Optional.empty();
        EntitySheep sheep = (EntitySheep) entity;
        sheep.setSheared(true);
        for (Integer _i : Utils.range(0, 2 + Utils.random.nextInt(3))) {
            final EntityItem entityitem = sheep.entityDropItem(new ItemStack(Block.wool.blockID, 1, sheep.getFleeceColor()), 1.0f);
            entityitem.motionY += Utils.random.nextFloat() * 0.05f;
            entityitem.motionX += (Utils.random.nextFloat() - Utils.random.nextFloat()) * 0.1f;
            entityitem.motionZ += (Utils.random.nextFloat() - Utils.random.nextFloat()) * 0.1f;
        }
        return Optional.of(3);
    });

    public static final RightClickAction RELOAD = new RightClickAction(ERCActions.Reload.ordinal());
    public static final RightClickAction FIRE = new RightClickAction(ERCActions.Fire.ordinal());
    public static final RightClickAction CHARGEFIRE = new RightClickAction(ERCActions.ReloadFire.ordinal());
    //more to come
}
