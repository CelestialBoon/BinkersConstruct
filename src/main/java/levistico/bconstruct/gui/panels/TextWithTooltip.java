package levistico.bconstruct.gui.panels;

public class TextWithTooltip {
    public String text;
    public String tooltip;

    public TextWithTooltip(String text, String tooltip) {
        this.text = text;
        this.tooltip = tooltip;
    }

    public static final TextWithTooltip EMPTY = new TextWithTooltip("","");
}
