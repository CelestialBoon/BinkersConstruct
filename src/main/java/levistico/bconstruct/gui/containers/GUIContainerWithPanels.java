package levistico.bconstruct.gui.containers;

import levistico.bconstruct.gui.BSlotCrafting;
import levistico.bconstruct.gui.GUIUtils;
import levistico.bconstruct.gui.panels.IPanel;
import levistico.bconstruct.parts.BToolPart;
import levistico.bconstruct.gui.texture.TextureUtils;
import levistico.bconstruct.tools.BTool;
import net.minecraft.src.*;
import net.minecraft.src.command.ChatColor;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;

import static net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES;

public abstract class GUIContainerWithPanels extends GuiContainer {

    static final RenderItem itemRenderer = new RenderItem();
    ArrayList<IPanel> panels = new ArrayList<>();

    //TODO handle cursor actions (maybe here?)

    public GUIContainerWithPanels(ContainerCraftingWithPanels container) {
        super(container);
    }

    public void drawScreen(int mouseX, int mouseY, float renderPartialTicks) {
        this.drawDefaultBackground();
        for(IPanel panel : panels) {
            panel.drawPanel(this.width, this.height, mouseX, mouseY);
        }

        InventoryPlayer inventoryplayer = this.mc.thePlayer.inventory;
        if (inventoryplayer.getHeldItemStack() != null) {
            itemRenderer.renderItemIntoGUI(this.fontRenderer, this.mc.renderEngine, inventoryplayer.getHeldItemStack(), mouseX - 8, mouseY - 8, 1.0F);
            itemRenderer.renderItemOverlayIntoGUI(this.fontRenderer, this.mc.renderEngine, inventoryplayer.getHeldItemStack(), mouseX - 8, mouseY - 8, 1.0F);
        } else {
            for(IPanel panel : panels) {
                panel.drawTooltip(this.width, this.height, mouseX, mouseY);
            }
        }
    }

    @Override
    public void mouseClicked(int x, int y, int button) {
        super.mouseClicked(x, y, button);
        for(IPanel panel : panels) {
            panel.mouseClicked(this.width, this.height, x, y, button);
        }
    }
    @Override
    public void keyTyped(char c, int i) {
        if (i == 1 || i == 14) {
            this.mc.thePlayer.closeScreen();
        } else {
            boolean captured = false;
            for(IPanel panel: panels) {
                captured = panel.keyTyped(c, i);
                if(captured) break;
            }
            if(!captured && this.mc.gameSettings.keyInventory.isKey(i)) {
                this.mc.thePlayer.closeScreen();
            }
        }
    }

    public void onGuiClosed() {
        super.onGuiClosed();
        this.inventorySlots.onCraftGuiClosed(this.mc.thePlayer);
    }

    public void drawSlotInventory(Slot slot) {
//        if (slot instanceof BSlotActivatable && !((BSlotActivatable)slot).isActive) return;
        int i = slot.xDisplayPosition;
        int j = slot.yDisplayPosition;
        mc.renderEngine.bindTexture(TextureUtils.GUI_ICONS_INDEX);
        if(!(slot instanceof BSlotCrafting)) //draw slot square
            GUIUtils.drawGUITexture(i-1, j-1, this.zLevel, 8, 12);
        ItemStack itemstack = slot.getStack();
        if (itemstack == null) {
            int k = slot.getBackgroundIconIndex();
            if (k >= 0) {
                GL11.glDisable(2896);
                mc.renderEngine.bindTexture(mc.renderEngine.getTexture("/gui/items.png"));
                drawTexturedModalRect(i, j, k % TEXTURE_ATLAS_WIDTH_TILES * TextureFX.tileWidthItems, k / TEXTURE_ATLAS_WIDTH_TILES * TextureFX.tileWidthItems, 16, 16, TextureFX.tileWidthItems, 1.0F / (float)(TEXTURE_ATLAS_WIDTH_TILES * TextureFX.tileWidthItems));
                GL11.glEnable(2896);
                return;
            }
        }

        itemRenderer.renderItemIntoGUI(fontRenderer, mc.renderEngine, itemstack, i, j, slot.discovered ? 1.0F : 0.0F, 1.0F);
        itemRenderer.renderItemOverlayIntoGUI(fontRenderer, mc.renderEngine, itemstack, i, j, slot.discovered);
    }

    public void drawItemTooltip(ItemStack stack, int mouseX, int mouseY, boolean discovered, boolean isCrafting) {

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        StringTranslate trans = StringTranslate.getInstance();
        StringBuilder text = new StringBuilder();
        boolean control = Keyboard.isKeyDown(29) || Keyboard.isKeyDown(157);
        boolean shift = Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54);
        boolean showDescription = control;
        if (discovered) {
//            boolean isCrafting = mouseOverSlot instanceof SlotCrafting;
            if (isCrafting) {
                showDescription = false;
                if (mc.gameSettings.swapCraftingButtons.value) {
                    boolean a = shift;
                    shift = control;
                    control = a;
                }
            }
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
                    text.append(" #" + stack.itemID + ":" + stack.getMetadata());
                }

                if (debug) {
                    text.append('\n').append(ChatColor.lightGray + stack.getItemName());
                }

                if (stack.isItemStackDamageable() && !showDescription && discovered && (Boolean) mc.gameSettings.showItemDurability.value) {
                    int durability = stack.getMaxDamage();
                    int remainingUses = durability - stack.getMetadata();
                    text.append('\n').append(ChatColor.lightGray + "" + remainingUses + " / " + durability);
                }

                if (showDescription) {
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
            drawTooltip(str, mouseX, mouseY, 8, -8, true);
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float renderPartialTicksApparently) {}
}
