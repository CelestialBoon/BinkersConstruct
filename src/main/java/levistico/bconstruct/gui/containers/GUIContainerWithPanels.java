package levistico.bconstruct.gui.containers;

import levistico.bconstruct.BConstruct;
import levistico.bconstruct.crafting.BContainer;
import levistico.bconstruct.gui.BSlotCustomizable;
import levistico.bconstruct.gui.GUIUtils;
import levistico.bconstruct.gui.panels.IPanel;
import levistico.bconstruct.parts.BToolPart;
import levistico.bconstruct.gui.texture.TextureUtils;
import levistico.bconstruct.tools.BTool;
import levistico.bconstruct.utils.Key;
import net.minecraft.client.Minecraft;
import net.minecraft.src.*;
import net.minecraft.src.command.ChatColor;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;

import static net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_LIGHTING;

public abstract class GUIContainerWithPanels extends GuiContainer {

    static final RenderItem itemRenderer = new RenderItem();
    ArrayList<IPanel> panels = new ArrayList<>();

    public GUIContainerWithPanels(BContainer container) {
        super(container);
    }

    public void drawScreen(int mouseX, int mouseY, float renderPartialTicks) {
        this.drawDefaultBackground();
        GL11.glPushMatrix();
        GL11.glRotatef(120.0F, 1.0F, 0.0F, 0.0F);
        RenderHelper.enableStandardItemLighting(); //this part somehow keeps all items and squares lit
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
    public void mouseClicked(int x, int y, int button) {
        //super.super.mouseClicked
        /*if (button == 0) {
            for (GuiButton guiButton : this.controlList) {
                if (guiButton.mousePressed(this.mc, x, y)) {
                    ((AccessorGuiScreen) this).setSelectedButton(guiButton);
                    this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
                    if (guiButton.listener != null) {
                        guiButton.listener.listen(guiButton);
                    } else {
                        this.actionPerformed(guiButton);
                    }
                }
            }
        }*/

        for(IPanel panel : panels) {
            panel.tryMouseClicked(this.width, this.height, x, y, button);
        }
    }

    @Override
    public void keyTyped(char c, int i) {
        if (i == Key.ESCAPE) {
            this.mc.thePlayer.closeScreen();
        } else {
            boolean captured = false;
            for(IPanel panel: panels) {
                captured = panel.keyTyped(c, i);
                if(captured) break;
            }
            if(!captured && (i == Key.BACKSPACE || this.mc.gameSettings.keyInventory.isKey(i))) {
                this.mc.thePlayer.closeScreen();
            }
        }
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
            GL11.glDisable(GL_LIGHTING);
            GL11.glDisable(GL_DEPTH_TEST);
            int j1 = slot.xDisplayPosition;
            int l1 = slot.yDisplayPosition;
            this.drawGradientRect(j1, l1, j1 + 16, l1 + 16, -2130706433, -2130706433);
            GL11.glEnable(GL_LIGHTING);
            GL11.glEnable(GL_DEPTH_TEST);
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

        itemRenderer.renderItemIntoGUI(fontRenderer, mc.renderEngine, itemstack, i, j, slot.discovered ? 1.0F : 0.0F, 1.0F);
        itemRenderer.renderItemOverlayIntoGUI(fontRenderer, mc.renderEngine, itemstack, i, j, slot.discovered);
    }


    public void drawItemTooltip(ItemStack stack, int mouseX, int mouseY, boolean discovered, boolean isCrafting) {

        Minecraft mc = BConstruct.mc;

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        StringTranslate trans = StringTranslate.getInstance();
        StringBuilder text = new StringBuilder();
        boolean control = Keyboard.isKeyDown(29) || Keyboard.isKeyDown(157);
        boolean shift = Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54);
        if (discovered) {
//            boolean isCrafting = mouseOverSlot instanceof SlotCrafting;
            Item item = stack.getItem();
            if(item instanceof BToolPart) {
                text = GUIUtils.getToolPartTooltip(text, stack);
            } else if(item instanceof BTool) {
                text = GUIUtils.getToolTooltip(text, stack, control, shift);
            } else {
                String itemName = trans.translateKey(stack.getItemName() + ".name");
                String itemNick = stack.getItem().getItemNickname(stack);
                if (itemNick != null && itemNick.length() > 0 && stack.tag.getBoolean("overrideName")) {
                    itemName = itemNick;
                }

                if (stack.tag.getBoolean("overrideColor")) {
                    text.append(ChatColor.get(stack.getItem().getItemNameColor(stack)));
                }

                text.append(itemName);
                boolean debug = (Boolean) mc.gameSettings.showDebugScreen.value;
                if (debug) {
                    text.append(" #").append(stack.itemID).append(":").append(stack.getMetadata());
                }

                if (debug) {
                    text.append('\n').append(ChatColor.lightGray).append(stack.getItemName());
                }

                if (stack.isItemStackDamageable() && !control && (Boolean) mc.gameSettings.showItemDurability.value) {
                    int durability = stack.getMaxDamage();
                    int remainingUses = durability - stack.getMetadata();
                    text.append('\n').append(ChatColor.lightGray).append(remainingUses).append(" / ").append(durability);
                }

                if (control) {
                    text.append('\n').append(formatDescription(trans.translateKey(stack.getItemName() + ".desc"), 16));
                }

                if (isCrafting) {
                    if (shift && !control) {
                        text.append('\n').append(ChatColor.lightGray).append("Craft Stack");
                    }

                    if (control && !shift) {
                        text.append('\n').append(ChatColor.lightGray).append("Craft Once");
                    }

                    if (control && shift) {
                        text.append('\n').append(ChatColor.lightGray).append("Craft All");
                    }
                }
            }
        } else {
            text.append("???");
            if (control) {
                text.append("\n").append(trans.translateKey("item.unknown.desc"));
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
        this.drawTooltip(text, mouseX, mouseY, 8, -8, true);
    }

    public void drawRect(int x, int y, int w, int h, int color){
        super.drawRect(x,y,x+w,y+h,color);
    }
}
