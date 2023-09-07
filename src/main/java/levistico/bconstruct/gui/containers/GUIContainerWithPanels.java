package levistico.bconstruct.gui.containers;

import levistico.bconstruct.BConstruct;
import levistico.bconstruct.crafting.BContainer;
import levistico.bconstruct.gui.BSlotCustomizable;
import levistico.bconstruct.gui.GUIUtils;
import levistico.bconstruct.gui.panels.BPanelWithSlots;
import levistico.bconstruct.gui.panels.IPanel;
import levistico.bconstruct.gui.texture.TextureUtils;
import levistico.bconstruct.parts.BToolPart;
import levistico.bconstruct.tools.BTool;
import levistico.bconstruct.utils.Key;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiContainer;
import net.minecraft.client.render.Lighting;
import net.minecraft.client.render.TextureFX;
import net.minecraft.client.render.entity.ItemEntityRenderer;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.lang.I18n;
import net.minecraft.core.net.command.TextFormatting;
import net.minecraft.core.player.inventory.InventoryPlayer;
import net.minecraft.core.player.inventory.slot.Slot;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;


import java.util.ArrayList;


import static levistico.bconstruct.gui.GUIUtils.formatDescription;
import static net.minecraft.core.Global.TEXTURE_ATLAS_WIDTH_TILES;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_LIGHTING;

public abstract class GUIContainerWithPanels extends GuiContainer {

    static final ItemEntityRenderer itemRenderer = new ItemEntityRenderer();
    ArrayList<IPanel> panels = new ArrayList<>();

    public GUIContainerWithPanels(BContainer container) {
        super(container);
    }

    public void drawScreen(int mouseX, int mouseY, float renderPartialTicks) {
        this.drawDefaultBackground();
        GL11.glPushMatrix();
        GL11.glRotatef(120.0F, 1.0F, 0.0F, 0.0F);
        Lighting.disable(); //this part somehow keeps all items and squares lit
        GL11.glPopMatrix();
        for(IPanel panel : panels) {
            panel.drawPanel(this.width, this.height, mouseX, mouseY);
        }

        InventoryPlayer inventoryplayer = this.mc.thePlayer.inventory;
        if (inventoryplayer.getHeldItemStack() != null) {
            GL11.glEnable(GUIUtils.GL_BLOCK_ITEM_MAGIC_NUMBER);
            GL11.glTranslatef(0.0f, 0.0f, 32.0f);
            itemRenderer.renderItemIntoGUI(this.fontRenderer, this.mc.renderEngine, inventoryplayer.getHeldItemStack(), mouseX - 8, mouseY - 8, 1.0F);
            itemRenderer.renderItemOverlayIntoGUI(this.fontRenderer, this.mc.renderEngine, inventoryplayer.getHeldItemStack(), mouseX - 8, mouseY - 8, 1.0F);
            GL11.glTranslatef(0.0f, 0.0f, -32.0f);
            GL11.glDisable(GUIUtils.GL_BLOCK_ITEM_MAGIC_NUMBER);
        }
        GL11.glDisable(GL_LIGHTING);
        GL11.glDisable(GL_DEPTH_TEST);
        if(inventoryplayer.getHeldItemStack() == null) {
            for(IPanel panel : panels) {
                panel.tryDrawTooltip(this.width, this.height, mouseX, mouseY);
            }
        }
    }

    @Override
    public void keyTyped(char c, int i, int mouseX, int mouseY) {
        if (i == Key.ESCAPE) {
            this.mc.thePlayer.closeScreen(); return;
        }
        for(IPanel panel: panels) {
            if(panel.tryKeyTyped(c, i, this.width, this.height, mouseX, mouseY)) return;
        }
        if(i == Key.BACKSPACE || this.mc.gameSettings.keyInventory.isKey(i)) {
            this.mc.thePlayer.closeScreen(); return;
        }
        super.keyTyped(c, i, mouseX, mouseY);
    }
    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {
        for(IPanel panel : panels) {
            if(panel.tryMouseClicked(this.width, this.height, mouseX, mouseY, button)) return;
        }
        doMouseClicked(mouseX, mouseY, button);
    }
    public void doMouseClicked(int mouseX, int mouseY, int button) {
        super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public void mouseMovedOrUp(int mouseX, int mouseY, int mouseButton) {
        for(IPanel panel : panels) {
            if(panel.tryMouseMovedOrUp(this.width, this.height, mouseX, mouseY, mouseButton)) return;
        }
        doMouseMovedOrUp(mouseX, mouseY, mouseButton);
    }
    public void doMouseMovedOrUp(int mouseX, int mouseY, int mouseButton) {
        super.mouseMovedOrUp(mouseX, mouseY, mouseButton);
    }

    @Override
    public Slot getSlotAtPosition(int mouseX, int mouseY) {
        for(IPanel panel : panels) {
            if(! (panel instanceof BPanelWithSlots)) continue;
            Slot slot = (((BPanelWithSlots) panel).getSlotAtPosition(this.width, this.height, mouseX, mouseY));
            if(slot != null) return slot;
        }
        return null;
    }

    public void onGuiClosed() {
        super.onGuiClosed();
        this.inventorySlots.onCraftGuiClosed(this.mc.thePlayer);
    }

    public void drawSlotInventory(Slot slot, boolean isMouseOver) {
        int i = slot.xDisplayPosition;
        int j = slot.yDisplayPosition;
        mc.renderEngine.bindTexture(TextureUtils.GUI_ICONS_INDEX);
        if(slot instanceof BSlotCustomizable) { //draw slot square
            BSlotCustomizable bslot = (BSlotCustomizable) slot;
            GUIUtils.drawLargeGUITexture(i - 1, j - 1, 8, 12, this.zLevel);
            //TODO more personalized slot drawings go here
            if(bslot.textureUV != null && bslot.getStack() == null) GUIUtils.drawLargeGUITexture(i-1, j-1, bslot.textureUV.first, bslot.textureUV.second, this.zLevel);
        }
        if (isMouseOver) {
            //GL11.glDisable(GL_LIGHTING);
            //GL11.glDisable(GL_DEPTH_TEST);
            int j1 = slot.xDisplayPosition;
            int l1 = slot.yDisplayPosition;
            this.drawGradientRect(j1, l1, j1 + 16, l1 + 16, -2130706433, -2130706433);
            //GL11.glEnable(GL_LIGHTING);
            //GL11.glEnable(GL_DEPTH_TEST);
        }
        ItemStack itemstack = slot.getStack();
        if (itemstack == null) {
            int k = slot.getBackgroundIconIndex();
            if (k >= 0) {
//                GL11.glDisable(GL_LIGHTING);
                mc.renderEngine.bindTexture(TextureUtils.MC_ITEMS_TEXTURE_INDEX);
                drawTexturedModalRect(i, j, k % TEXTURE_ATLAS_WIDTH_TILES * TextureFX.tileWidthItems, k / TEXTURE_ATLAS_WIDTH_TILES * TextureFX.tileWidthItems, 16, 16, TextureFX.tileWidthItems, 1.0F / (float)(TEXTURE_ATLAS_WIDTH_TILES * TextureFX.tileWidthItems));
//                GL11.glEnable(GL_LIGHTING);
                return;
            }
        }

        itemRenderer.renderItemIntoGUI(fontRenderer, mc.renderEngine, itemstack, i, j, 1.0F, 1.0F);
        itemRenderer.renderItemOverlayIntoGUI(fontRenderer, mc.renderEngine, itemstack, i, j, 0);
    }


    public void drawItemTooltip(ItemStack stack, int mouseX, int mouseY, boolean isCrafting) {

        Minecraft mc = BConstruct.mc;

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        I18n trans = I18n.getInstance();
        StringBuilder text = new StringBuilder();
        boolean control = Keyboard.isKeyDown(29) || Keyboard.isKeyDown(157);
        boolean shift = Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54);
//            boolean isCrafting = mouseOverSlot instanceof SlotCrafting;
        Item item = stack.getItem();
        if(item instanceof BToolPart) {
            text = GUIUtils.getToolPartTooltip(text, stack);
        } else if(item instanceof BTool) {
            text = GUIUtils.getToolTooltip(text, stack, control, shift);
        } else {
            String itemName = trans.translateKey(stack.getItemName() + ".name");
            String itemNick = stack.getItemName();
            if (itemNick != null && itemNick.length() > 0 && stack.getData().getBoolean("overrideName")) {
                itemName = itemNick;
            }

            if (stack.getData().getBoolean("overrideColor")) {
                text.append(TextFormatting.get(stack.getCustomColor()));
            }

            text.append(itemName);
            boolean debug = (Boolean) mc.gameSettings.showDebugScreen.value;
            if (debug) {
                text.append(" #").append(stack.itemID).append(":").append(stack.getMetadata());
            }

            if (debug) {
                text.append('\n').append(TextFormatting.LIGHT_GRAY).append(stack.getItemName());
            }

            if (stack.isItemStackDamageable() && !control) { // c'era un showitemdurability prima ma e sparito
                int durability = stack.getMaxDamage();
                int remainingUses = durability - stack.getMetadata();
                text.append('\n').append(TextFormatting.LIGHT_GRAY).append(remainingUses).append(" / ").append(durability);
            }

            if (control) {
                text.append('\n').append(formatDescription(trans.translateKey(stack.getItemName() + ".desc"), 16));
            }

            if (isCrafting) {
                if (shift && !control) {
                    text.append('\n').append(TextFormatting.LIGHT_GRAY).append("Craft Stack");
                }

                if (control && !shift) {
                    text.append('\n').append(TextFormatting.LIGHT_GRAY).append("Craft Once");
                }

                if (control && shift) {
                    text.append('\n').append(TextFormatting.LIGHT_GRAY).append("Craft All");
                }
            }
        }

        String str = text.toString();
        if (str.length() > 0) {
            drawTooltipAtMouse(str, mouseX, mouseY);
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float renderPartialTicksApparently) {}

    public void drawTooltipAtMouse(String text, int mouseX, int mouseY) {
        GUIUtils.tooltip.render(text, mouseX, mouseY, 8, -8);
    }

    public void drawRect(int x, int y, int w, int h, int color){
        super.drawRect(x,y,x+w,y+h,color);
    }
}
